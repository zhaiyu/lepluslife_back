package com.jifenke.lepluslive.product.controller.view;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sunxingfei on 2017/5/26.
 */
@Configuration
public class GoldProductViewExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        Map<String, Object> result = (Map<String, Object>) map.get("goldResult");
        setExcelRows(sheet, result);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String filename = sdf.format(new Date()) + ".xls";//设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream = response.getOutputStream();
        hssfWorkbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("商品分类");
        excelHeader.createCell(1).setCellValue("商品序号");
        excelHeader.createCell(2).setCellValue("商户ID");
        excelHeader.createCell(3).setCellValue("商户名称");
        excelHeader.createCell(4).setCellValue("所需金币");
        excelHeader.createCell(5).setCellValue("市场价");
        excelHeader.createCell(6).setCellValue("实际销量");
        excelHeader.createCell(7).setCellValue("库存");
    }

    public void setExcelRows(HSSFSheet excelSheet, Map<String, Object> result ) {

        int record = 1;
        List<Map> productList=(List<Map>)result.get("productList");
        for (Map map : productList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(map.get("typeName").toString());
            excelRow.createCell(1).setCellValue(map.get("sid").toString());
            excelRow.createCell(2).setCellValue(map.get("id").toString());
            excelRow.createCell(3).setCellValue(map.get("name").toString());
            excelRow.createCell(4).setCellValue(Double.valueOf(map.get("minScore").toString())/100.0);
            excelRow.createCell(5).setCellValue("￥"+Double.valueOf(map.get("price").toString())/100.0);
            excelRow.createCell(6).setCellValue(map.get("saleNumber").toString());
            excelRow.createCell(7).setCellValue(map.get("repository").toString());
        }
    }

}
