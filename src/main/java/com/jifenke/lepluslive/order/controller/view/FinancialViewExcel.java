package com.jifenke.lepluslive.order.controller.view;

import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wcg on 16/5/9.
 */
public class FinancialViewExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);

    List<FinancialStatistic> financialList = (List<FinancialStatistic>) map.get("financialList");
    setExcelRows(sheet, financialList);

  }

  public void setExcelHeader(HSSFSheet excelSheet) {
    HSSFRow excelHeader = excelSheet.createRow(0);
    excelHeader.createCell(0).setCellValue("结算单号");
    excelHeader.createCell(1).setCellValue("结算日期");
    excelHeader.createCell(2).setCellValue("商户信息");
    excelHeader.createCell(3).setCellValue("绑定银行卡");
    excelHeader.createCell(4).setCellValue("开户行");
    excelHeader.createCell(5).setCellValue("收款人");
    excelHeader.createCell(6).setCellValue("待转账金额");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<FinancialStatistic> financialList) {
    int record = 1;
    for (FinancialStatistic financialStatistic : financialList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(financialStatistic.getStatisticId());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      excelRow.createCell(1).setCellValue(sdf.format(financialStatistic.getBalanceDate()));
      excelRow.createCell(2).setCellValue(
          financialStatistic.getMerchant().getName() + "(" + financialStatistic.getMerchant()
              .getMerchantSid() + ")");
      excelRow.createCell(3).setCellValue(
          financialStatistic.getMerchant().getMerchantBanks().get(0).getBankNumber());
      excelRow.createCell(4)
          .setCellValue(financialStatistic.getMerchant().getMerchantBanks().get(0).getBankName());
      excelRow.createCell(5).setCellValue(financialStatistic.getMerchant().getPayee());
      excelRow.createCell(6).setCellValue(financialStatistic.getTransferPrice() / 100.0);

    }
  }
}
