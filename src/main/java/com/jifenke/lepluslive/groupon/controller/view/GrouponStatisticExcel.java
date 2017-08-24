package com.jifenke.lepluslive.groupon.controller.view;

import com.jifenke.lepluslive.groupon.domain.entities.GrouponStatistic;

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
 * 团购结算单导出
 * Created by zhangwen on 17/8/23.
 */
@Configuration
public class GrouponStatisticExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);

    List<GrouponStatistic> financialList = (List<GrouponStatistic>) map.get("list");
    setExcelRows(sheet, financialList);
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
    excelHeader.createCell(1).setCellValue("账单日期");
    excelHeader.createCell(2).setCellValue("转账日期");
    excelHeader.createCell(3).setCellValue("门店信息");
    excelHeader.createCell(4).setCellValue("绑定银行卡");
    excelHeader.createCell(5).setCellValue("开户行");
    excelHeader.createCell(6).setCellValue("收款人");
    excelHeader.createCell(7).setCellValue("核销笔数");
    excelHeader.createCell(8).setCellValue("交易总额");
    excelHeader.createCell(9).setCellValue("佣金/手续费");
    excelHeader.createCell(10).setCellValue("实际转账");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<GrouponStatistic> financialList) {
    int record = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for (GrouponStatistic statistic : financialList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(statistic.getSid());
      excelRow.createCell(1).setCellValue(sdf.format(statistic.getBalanceDate()));
      excelRow.createCell(2).setCellValue(statistic.getCompleteDate() == null ? "结算未完成"
                                                                              : sdf
                                              .format(statistic.getCompleteDate()));
      excelRow.createCell(3).setCellValue(
          statistic.getMerchant().getName() + "(" + statistic.getMerchant()
              .getMerchantSid() + ")");
      excelRow.createCell(4).setCellValue(
          statistic.getMerchant().getMerchantBank().getBankNumber());
      excelRow.createCell(5)
          .setCellValue(statistic.getMerchant().getMerchantBank().getBankName());
      excelRow.createCell(6).setCellValue(statistic.getMerchant().getPayee());
      excelRow.createCell(7).setCellValue(statistic.getCheckNum());
      excelRow.createCell(8).setCellValue(statistic.getTotalMoney() / 100);
      excelRow.createCell(9).setCellValue(statistic.getCommission() / 100.0);
      excelRow.createCell(10).setCellValue(statistic.getTransferMoney() / 100.0);
    }
  }
}
