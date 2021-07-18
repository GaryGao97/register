package com.example.register.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: minchen
 * 创建时间: 2020/12/23
 * 功能描述: jxls 下载
 */
@Slf4j
public class JxlsUtils {
    /**
     * 模板路径
     */
    private static final String TEMPLATE_DIR = "/jxls-templates/";

    /**
     * 使用JxlsTemplate.class.getResourceAsStream load 模板
     *
     * @param template 模板名称，相当于TEMPLATE_DIR设置的路径
     * @param out      生成excel写入的输出流
     * @param params   交给jxls处理模板需要的参数
     * @throws IOException
     */
    private static void processTemplate(String template, OutputStream out, Map<String, ?> params) throws IOException {
        processTemplate(JxlsUtils.class, template, out, params);
    }

    /**
     * 使用resourceBaseClassgetResourceAsStream load 模板
     *
     * @param resourceBaseClass class load的类
     * @param template          模板名称
     * @param out               生成excel写入的输出流
     * @param params            交给jxls处理模板需要的参数
     * @throws IOException
     */
    private static void processTemplate(Class resourceBaseClass, String template, OutputStream out, Map<String, ?> params) throws IOException {
        InputStream in = resourceBaseClass.getResourceAsStream(TEMPLATE_DIR + template);
        if (null == in) {
            throw new RuntimeException("找不到excel模板！,位置:" + TEMPLATE_DIR + template);
        }
        Context context = new Context();
        if (params != null) {
            for (String key : params.keySet()) {
                context.putVar(key, params.get(key));
            }
        }

        Transformer transformer = TransformerFactory.createTransformer(in, out);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> functionMap = new HashMap<>();
        functionMap.put("utils", new JxlsUtils());

        JexlEngine customJexlEngine = new JexlBuilder().namespaces(functionMap).create();
        evaluator.setJexlEngine(customJexlEngine);


        JxlsHelper.getInstance().processTemplate(context, transformer);
    }


    /**
     * @param template 模板名称，相当于TEMPLATE_DIR设置的路径
     * @param fileName 输出文件名称
     * @param model    交给jxls处理模板需要的参数
     * @throws IOException
     */
    public static void processTemplate(String template, String fileName, Map<String, Object> model,
                                       HttpServletResponse response) {
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/x-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" +
                    new String(fileName.getBytes("UTF-8"), "iso-8859-1") + ".xlsx");
            processTemplate(template, out, model);
        } catch (Exception e) {
            log.error("导出错误！jxls error!", e);
        }


    }
}
