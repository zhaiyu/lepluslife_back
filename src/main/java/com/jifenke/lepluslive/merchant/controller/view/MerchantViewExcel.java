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
    excelHeader.createCell(0).setCellValue("商户名称");
    excelHeader.createCell(1).setCellValue("商户编号");
    excelHeader.createCell(2).setCellValue("创建时间");
    excelHeader.createCell(3).setCellValue("负责销售");
    excelHeader.createCell(4).setCellValue("所属合伙人");
    excelHeader.createCell(5).setCellValue("乐店状态（1=已开启）");
    excelHeader.createCell(6).setCellValue("合约分类");
    excelHeader.createCell(7).setCellValue("普通订单费率");
    excelHeader.createCell(8).setCellValue("会员订单费率");
    excelHeader.createCell(9).setCellValue("导流订单费率");
    excelHeader.createCell(10).setCellValue("收取红包权限（1=可收取）");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<Merchant> merchantList) {
    int record = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (Merchant merchant : merchantList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(merchant.getName());
      excelRow.createCell(1).setCellValue(merchant.getMerchantSid());
      excelRow.createCell(2).setCellValue(sdf.format(merchant.getCreateDate()));
      excelRow.createCell(3).setCellValue(merchant.getSalesStaff() == null ? "无" :merchant.getSalesStaff().getName());
      excelRow.createCell(4).setCellValue(merchant.getPartner().getPartnerName());
      excelRow.createCell(5).setCellValue(merchant.getState());
      if(merchant.getPartnership() == 0){
        excelRow.createCell(6).setCellValue("普通商户");
        excelRow.createCell(7).setCellValue(merchant.getLjCommission().doubleValue());
        excelRow.createCell(8).setCellValue(0);
        excelRow.createCell(9).setCellValue(0);
      } else {
        excelRow.createCell(6).setCellValue("联盟商户");
        excelRow.createCell(7).setCellValue(merchant.getLjBrokerage().doubleValue());
        excelRow.createCell(8).setCellValue(merchant.getMemberCommission().doubleValue());
        excelRow.createCell(9).setCellValue(merchant.getLjCommission().doubleValue());
      }
      excelRow.createCell(10).setCellValue(merchant.getReceiptAuth());
    }
  }
}
