package com.jifenke.lepluslive.yibao.controller.view;

import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;

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
 * 转账单报表
 * Created by xf on 17-7-14.
 */
@Configuration
public class LedgerTransferExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
                                    HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    HSSFSheet sheet = workbook.createSheet("list");
    setExcelHeader(sheet);
    List<LedgerTransfer> transferList = (List<LedgerTransfer>) model.get("ledgerTransferList");
    setExcelRows(sheet, transferList);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String filename = sdf.format(new Date()) + ".xls";//设置下载时客户端Excel的名称
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-disposition", "attachment;filename=" + filename);

    OutputStream ouputStream = response.getOutputStream();
    workbook.write(ouputStream);
    ouputStream.flush();
    ouputStream.close();
  }

  public void setExcelHeader(HSSFSheet excelSheet) {
    HSSFRow excelHeader = excelSheet.createRow(0);
    excelHeader.createCell(0).setCellValue("转账单号");
    excelHeader.createCell(1).setCellValue("清算日期");
    excelHeader.createCell(2).setCellValue("子商户号");
    excelHeader.createCell(3).setCellValue("转账金额");
    excelHeader.createCell(4).setCellValue("转账请求时间");
    excelHeader.createCell(5).setCellValue("转账成功时间");
    excelHeader.createCell(6).setCellValue("转账状态");
    excelHeader.createCell(7).setCellValue("有无补单");
    excelHeader.createCell(8).setCellValue("结算类型");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<LedgerTransfer> transferList) {
    int record = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    for (LedgerTransfer transfer : transferList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(transfer.getOrderSid());   // 转账单号

      excelRow.createCell(1).setCellValue(transfer.getTradeDate());         // 清算日期transfer
      excelRow.createCell(2).setCellValue(transfer.getLedgerNo());               // 子商户号
      excelRow.createCell(3).setCellValue(transfer.getActualTransfer() / 100.0);   // 转账金额
      excelRow.createCell(4).setCellValue(sdf.format(transfer.getDateCreated()));
      if (transfer.getDateCompleted() != null) {               // 成功日期
        excelRow.createCell(5).setCellValue(sdf.format(transfer.getDateCompleted()));
      } else {
        excelRow.createCell(5).setCellValue("--");
      }
      //转账状态 0=待转账，1=转账成功，其他为易宝错误码
      if (transfer.getState() == 0) {
        excelRow.createCell(6).setCellValue("待转账");
      } else if (transfer.getState() == 1) {
        excelRow.createCell(6).setCellValue("转账成功");
      } else if (transfer.getState() == 2) {
        excelRow.createCell(6).setCellValue("转账失败");
      } else if (transfer.getState() == 3) {
        excelRow.createCell(6).setCellValue("转账中（查询非终态）");
      } else {
        excelRow.createCell(6).setCellValue("未知");
      }
      if (transfer.getRepair() == 0) {
        excelRow.createCell(7).setCellValue("无");
      } else {
        excelRow.createCell(7).setCellValue("有");
      }
      if (transfer.getType() == 1) {
        excelRow.createCell(8).setCellValue("实时");
      } else {
        excelRow.createCell(8).setCellValue("合并");
      }
    }

  }
}
