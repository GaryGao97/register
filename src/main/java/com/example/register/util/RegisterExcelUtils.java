package com.example.register.util;

/**
 * @author: Gary Gao(修远)
 * @date: 2021/7/21
 */

import com.example.register.domain.dao.BasRegisterDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @desc: Excel 工具类
 * @Author: 修远
 * @date: 2020/6/9
 */
public class RegisterExcelUtils {
    public static final String[] HEAD = {"姓名", "身份证", "性别", "出生日期", "现住址", "电话", "所属社区", "体检日期", "备注"};

    public static <T extends BasRegisterDO> List<T> readExcel(Class<T> cl, InputStream inputStream) {
        SXSSFWorkbook workbook = null;
        try {
            // 读取Excel文件
            workbook = new SXSSFWorkbook(new XSSFWorkbook(inputStream));
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseExcel(workbook, cl);
    }

    private static boolean checkTableHead(XSSFSheet sheetAt) {
        if (sheetAt == null || sheetAt.getLastRowNum() < 1) {
            return false;
        }

        // 表头校验
        XSSFRow head = sheetAt.getRow(0);
        for (int column = 0; column < HEAD.length; column++) {
            if (!head.getCell(column).getStringCellValue().equals(HEAD[column])) {
                return false;
            }
        }

        return true;
    }

    private static <T extends BasRegisterDO> List<T> parseExcel(SXSSFWorkbook workbook, Class<T> cl) {
        List<T> items = new LinkedList<>();
        if (workbook == null) {
            return items;
        }

        XSSFWorkbook xssfWorkbook = workbook.getXSSFWorkbook();

        // 循环工作表
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(numSheet);
            if (!checkTableHead(sheetAt)) {
                continue;
            }

            // 循环行
            for (int rowNum = 1; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
                try {
                    XSSFRow row = sheetAt.getRow(rowNum);
                    T item = cl.newInstance();

                    XSSFCell cell = row.getCell(0);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        String name = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(name)) {
                            item.setName(name);
                        }
                    }

                    cell = row.getCell(1);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        String idCard = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(idCard)) {
                            item.setIdCard(idCard);
                        }
                    }

                    cell = row.getCell(4);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        String address = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(address)) {
                            item.setAddress(address);
                        }
                    }

                    cell = row.getCell(5);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        String phone = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(phone)) {
                            item.setPhone(phone);
                        }
                    }

                    cell = row.getCell(6);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        String community = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(community)) {
                            item.setCommunity(community);
                        }
                    }

                    cell = row.getCell(7);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        String examinationTime = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(examinationTime)) {
                            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                            item.setExaminationTime(fmt.parse(examinationTime));
                        }
                    }

                    cell = row.getCell(8);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        String remark = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(remark)) {
                            item.setRemark(remark);
                        }
                    }

                    final String name = item.getName();
                    final String idCard = item.getIdCard();
                    final String address = item.getAddress();
                    final String phone = item.getPhone();
                    final String community = item.getCommunity();
                    final Date examinationTime = item.getExaminationTime();
                    final String remark = item.getRemark();

                    if (StringUtils.isAnyBlank(name, idCard, address, phone, community)
                            || examinationTime == null) {
                        continue;
                    }


                    items.add(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return items;
    }
}

