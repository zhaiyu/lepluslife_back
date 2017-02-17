package com.jifenke.lepluslive.fuyou.controller.view;

import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeSettleOrder;

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
 * 富友扫码结算单EXCEL Created by zhangwen on 17/01/03.
 */
@Configuration
public class ScanCodeSettleOrderViewExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);

    List<ScanCodeSettleOrder> orderList = (List<ScanCodeSettleOrder>) map.get("orderList");
    setExcelRows(sheet, orderList);
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
    excelHeader.createCell(0).setCellValue("结算单号");
    excelHeader.createCell(1).setCellValue("结算状态");
    excelHeader.createCell(2).setCellValue("账单日期");
    excelHeader.createCell(3).setCellValue("移动商户号");
    excelHeader.createCell(4).setCellValue("商户号类型");
    excelHeader.createCell(5).setCellValue("商户号费率");
    excelHeader.createCell(6).setCellValue("所在商户");
    excelHeader.createCell(7).setCellValue("结算卡号");
    excelHeader.createCell(8).setCellValue("开户行");
    excelHeader.createCell(9).setCellValue("收款人");
    excelHeader.createCell(10).setCellValue("实际应入账");
    excelHeader.createCell(11).setCellValue("扫码微信入账");
    excelHeader.createCell(12).setCellValue("补充结算红包");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<ScanCodeSettleOrder> orderList) {
    int record = 1;
    String[] s = new String[]{"待转账", "已转账", "已挂账", "未知", "佣金商户号", "普通商户号"};
    for (ScanCodeSettleOrder order : orderList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(order.getOrderSid());
      switch (order.getState()) {
        case 0:
          excelRow.createCell(1).setCellValue(s[0]);
          break;
        case 1:
          excelRow.createCell(1).setCellValue(s[1]);
          break;
        case 2:
          excelRow.createCell(1).setCellValue(s[2]);
          break;
        default:
          excelRow.createCell(1).setCellValue(s[3]);
      }
      excelRow.createCell(2).setCellValue(order.getTradeDate());
      excelRow.createCell(3).setCellValue(order.getMerchantNum());
      if (order.getType() == 1) {
        excelRow.createCell(4).setCellValue(s[4]);
      } else {
        excelRow.createCell(4).setCellValue(s[5]);
      }
      excelRow.createCell(5).setCellValue(String.valueOf(order.getCommission()));
      excelRow.createCell(6).setCellValue(order.getMerchantName());
      excelRow.createCell(7).setCellValue(order.getBankNumber());
      excelRow.createCell(8).setCellValue(order.getBankName());
      excelRow.createCell(9).setCellValue(order.getPayee());
      excelRow.createCell(10).setCellValue(order.getTransferMoney() / 100.0);
      excelRow.createCell(11).setCellValue(order.getTransferMoneyFromTruePay() / 100.0);
      excelRow.createCell(12).setCellValue(order.getTransferMoneyFromScore() / 100.0);
    }
  }
}
