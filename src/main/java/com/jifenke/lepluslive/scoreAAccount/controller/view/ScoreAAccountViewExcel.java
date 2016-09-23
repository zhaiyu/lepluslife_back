package com.jifenke.lepluslive.scoreAAccount.controller.view;

import com.jifenke.lepluslive.scoreAAccount.domain.entities.ScoreAAccount;

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
 * Created by lss on 2016/9/18.
 */
@Configuration
public class ScoreAAccountViewExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);
    List<ScoreAAccount> scoreAAccountList = (List<ScoreAAccount>) map.get("scoreAAccountList");
    setExcelRows(sheet, scoreAAccountList);
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
    excelHeader.createCell(0).setCellValue("日期");
    excelHeader.createCell(1).setCellValue("使用红包金额(应结算金额)");
    excelHeader.createCell(2).setCellValue("发送红包金额");
    excelHeader.createCell(3).setCellValue("佣金收入");
    excelHeader.createCell(4).setCellValue("分润后积分客收入");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<ScoreAAccount> scoreAAccountList) {
    int record = 1;
    for (ScoreAAccount scoreAAccount : scoreAAccountList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      excelRow.createCell(0).setCellValue(sdf.format(scoreAAccount.getChangeDate()));
      excelRow.createCell(1).setCellValue("￥" + (double)scoreAAccount.getUseScoreA() / 100 + "(" + "￥"
                                          + (double)scoreAAccount.getSettlementAmount() / 100 + ")");
      excelRow.createCell(2).setCellValue("￥" + (double)scoreAAccount.getIssuedScoreA() / 100);
      excelRow.createCell(3).setCellValue("￥" + (double)scoreAAccount.getCommissionIncome() / 100);
      excelRow.createCell(4).setCellValue("￥" + (double)scoreAAccount.getJfkShare() / 100);
    }

  }


}
