package com.douguo.ndc.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/6
 */
public class ProfileExcelUtil extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        // 设置文件名称
        String jobName = model.get("exclName").toString();
        String excelName = jobName + ".xlsx";

        // 设置sheet名称
        String sheetName = jobName;

        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));

        // 设置标题
        Sheet sheet = workbook.createSheet(sheetName);
        Row header = sheet.createRow(0);
        // 设置表头
        List<String> headerNamesList = (List<String>)model.get("colTitleList");
        for (int i = 0; i < headerNamesList.size(); i++) {
            header.createCell(i).setCellValue(headerNamesList.get(i));
        }

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));

        int rowIndex = 1;
        // 填充数据
        List<List> stuList = (List)model.get("list");
        for (List tmpList : stuList) {
            for (int i = 0; i < tmpList.size(); i++) {
                Row rowItem = sheet.createRow(rowIndex++);
                String[] values = (String[])tmpList.get(i);
                for (int j = 0; j < values.length; j++) {
                    rowItem.createCell(j).setCellValue(values[j]);
                }
            }
        }
    }
}
