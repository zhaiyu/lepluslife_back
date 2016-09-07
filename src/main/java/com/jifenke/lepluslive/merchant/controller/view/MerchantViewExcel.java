package com.jifenke.lepluslive.merchant.controller.view;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lss on 2016/8/19.
 */
@Configuration
public class MerchantViewExcel extends AbstractExcelView {
  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);

    List<Merchant> merchantList = (List<Merchant>) map.get("merchantList");
    setExcelRows(sheet, merchantList);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String filename =sdf.format(new Date())+".xls";//设置下载时客户端Excel的名称
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-disposition", "attachment;filename=" + filename);

    OutputStream ouputStream = response.getOutputStream();
    hssfWorkbook.write(ouputStream);
    ouputStream.flush();
    ouputStream.close();

  }

  public void setExcelHeader(HSSFSheet excelSheet) {
    HSSFRow excelHeader = excelSheet.createRow(0);
    excelHeader.createCell(0).setCellValue("商户名称");
    excelHeader.createCell(1).setCellValue("商户编号");

  }

  public void setExcelRows(HSSFSheet excelSheet, List<Merchant> merchantList) {
    int record = 1;
    for (Merchant merchant : merchantList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(merchant.getMerchantSid());
      excelRow.createCell(1).setCellValue(merchant.getName());

  }
  }
}
