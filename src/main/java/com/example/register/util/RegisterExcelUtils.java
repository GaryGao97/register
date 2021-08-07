package com.example.register.util;

/**
 * @author: Gary Gao(修远)
 * @date: 2021/7/21
 */

import com.example.register.domain.dao.BasRegisterDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc: Excel 工具类 @Author: 修远
 * @date: 2020/6/9
 */
public class RegisterExcelUtils {
    private static final Map<Integer, String> INDEX2FIELD = new LinkedHashMap<>();

    static {
        INDEX2FIELD.put(0, "姓名");
        INDEX2FIELD.put(1, "身份证");
        INDEX2FIELD.put(4, "现住址");
        INDEX2FIELD.put(5, "电话");
        INDEX2FIELD.put(6, "所属社区");
        INDEX2FIELD.put(7, "体检日期");
        INDEX2FIELD.put(8, "备注");
    }

    private static DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    public static <T extends BasRegisterDO> List<T> readExcel(Class<T> cl, InputStream inputStream, String format) {
        List<T> list;
        try {
            switch (format) {
                case "xlsx":
                    list = parseExcel(new XSSFWorkbook(inputStream), cl);
                    break;
                case "xls":
                    list = parseExcel(new HSSFWorkbook(inputStream), cl);
                    break;
                default:
                    list = Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = Collections.emptyList();
        }

        return list;
    }

    private static boolean checkTableHead(Sheet sheetAt) {
        if (sheetAt == null || sheetAt.getLastRowNum() < 1) {
            return false;
        }

        // 表头校验
        Row head = sheetAt.getRow(0);
        boolean invalid = INDEX2FIELD.entrySet().stream().anyMatch(entry -> head.getCell(entry.getKey()) == null
                || !head.getCell(entry.getKey()).getStringCellValue().equals(entry.getValue())
        );

        return !invalid;
    }

    private static <T extends BasRegisterDO> List<T> parseExcel(Workbook workbook, Class<T> cl) {
        List<T> items = new LinkedList<>();
        if (workbook == null) {
            return items;
        }

        // 循环工作表
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheetAt = workbook.getSheetAt(numSheet);
            if (!checkTableHead(sheetAt)) {
                continue;
            }

            // 循环行
            for (int rowNum = 1; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
                try {
                    Row row = sheetAt.getRow(rowNum);
                    T item = cl.newInstance();

                    INDEX2FIELD.keySet().forEach(index -> {
                        Cell cell = row.getCell(index);
                        if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())) {
                            final String filedValue = cell.getStringCellValue();
                            switch (index) {
                                case 0:
                                    item.setName(filedValue);
                                    break;
                                case 1:
                                    if (IdCardUtil.isValidCard(filedValue)) {
                                        item.setIdCard(filedValue);
                                    }
                                    break;
                                case 4:
                                    item.setAddress(filedValue);
                                    break;
                                case 5:
                                    item.setPhone(filedValue);
                                    break;
                                case 6:
                                    item.setCommunity(filedValue);
                                    break;
                                case 7:
                                    try {
                                        item.setExaminationTime(fmt.parse(filedValue));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 8:
                                    item.setRemark(filedValue);
                                    break;
                                default:
                            }
                        }
                    });

                    if (!StringUtils.isAnyBlank(item.getName(), item.getIdCard(), item.getAddress(),
                            item.getPhone(), item.getCommunity()) && item.getExaminationTime() != null) {
                        items.add(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return items;
    }
}
