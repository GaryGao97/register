package com.example.register.util;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @FileName: JxlsTemplate.java
 * @Description: JxlsTemplate.java类说明
 * @Author: XDreamc
 * @Date: 2018/7/30 11:05
 */
public class JxlsTemplate {
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
    public static void processTemplate(String template, OutputStream out, Map<String, ?> params) throws IOException {
        processTemplate(JxlsTemplate.class, template, out, params);
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
    public static void processTemplate(Class resourceBaseClass, String template, OutputStream out, Map<String, ?> params) throws IOException {
        InputStream in = resourceBaseClass.getResourceAsStream(TEMPLATE_DIR + template);
        if (null == in) {
            throw new RuntimeException("找不到excel模板！,位置:" + TEMPLATE_DIR + template);
        }
        processTemplate(in, out, params);
    }

    /**
     * @param templateStream excel模板流
     * @param out            生成excel写入的输出流
     * @param context        jxsl上下文
     * @throws IOException
     */
    private static void processTemplate(InputStream templateStream, OutputStream out, Context context) throws IOException {
        JxlsHelper.getInstance().processTemplate(templateStream, out, context);
    }

    /**
     * @param templateStream excel模板流
     * @param out            生成excel写入的输出流
     * @param params         交给jxls处理模板需要的参数
     * @throws IOException
     */
    public static void processTemplate(InputStream templateStream, OutputStream out, Map<String, ?> params) throws IOException {
        Context context = new Context();
        if (params != null) {
            for (String key : params.keySet()) {
                context.putVar(key, params.get(key));
            }
        }
        processTemplate(templateStream, out, context);
    }

    /**
     * @param template 模板名称，相当于TEMPLATE_DIR设置的路径
     * @param fileName 输出文件名称
     * @param model    交给jxls处理模板需要的参数
     * @throws IOException
     */
    public static synchronized void processTemplate(HttpServletResponse response, String template, String fileName, Map<String, Object> model) throws Exception {
        response.setContentType("application/x-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" +
                new String(fileName.getBytes("UTF-8"), "iso-8859-1") + ".xlsx");
        OutputStream out = response.getOutputStream();
        processTemplate(template, out, model);
        out.flush();
        out.close();
    }
}