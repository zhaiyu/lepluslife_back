package com.jifenke.lepluslive.scoreAAccount.controller.view;


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
public class ScoreAAccountDetailViewExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);
    List<Map> scoreAAccountList = (List<Map>) map.get("scoreAAccountDetailList");
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
    excelHeader.createCell(0).setCellValue("红包变更时间");
    excelHeader.createCell(1).setCellValue("变更项目(订单 会员 编号)");
    excelHeader.createCell(2).setCellValue("消费者使用红包");
    excelHeader.createCell(3).setCellValue("积分客发放红包");
    excelHeader.createCell(4).setCellValue("佣金收入");
    excelHeader.createCell(5).setCellValue("分润后积分客收入");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<Map> scoreAAccountDetailList) {
    int record = 1;
    for (Map scoreAAccountDetail : scoreAAccountDetailList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
      excelRow.createCell(0).setCellValue(scoreAAccountDetail.get("changeDate").toString());
      if (scoreAAccountDetail.get("changeProject") != null) {
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 0)) {
          excelRow.createCell(1)
              .setCellValue("关注送红包" + "(" + scoreAAccountDetail.get("serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 1)) {
          excelRow.createCell(1).setCellValue("线上返还" + "(" + scoreAAccountDetail.get(
              "serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 2)) {
          excelRow.createCell(1).setCellValue("线上消费" + "(" + scoreAAccountDetail.get(
              "serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 3)) {
          excelRow.createCell(1).setCellValue("线下消费" + "(" + scoreAAccountDetail.get(
              "serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 4)) {
          excelRow.createCell(1)
              .setCellValue("线下返还" + "(" + scoreAAccountDetail.get("serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 5)) {
          excelRow.createCell(1)
              .setCellValue("活动返还" + "(" + scoreAAccountDetail.get("serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 6)) {
          excelRow.createCell(1)
              .setCellValue("运动" + "(" + scoreAAccountDetail.get("serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 7)) {
          excelRow.createCell(1)
              .setCellValue("摇一摇" + "(" + scoreAAccountDetail.get("serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 8)) {
          excelRow.createCell(1).setCellValue("APP分享" + "(" + scoreAAccountDetail.get(
              "serialNumber") + ")");
        }
        if ((Integer.parseInt(scoreAAccountDetail.get("changeProject").toString()) == 9)) {
          excelRow.createCell(1).setCellValue("线下支付完成页注册会员" + "(" + scoreAAccountDetail.get(
              "serialNumber") + ")");
        }
      } else {
        excelRow.createCell(1)
            .setCellValue(scoreAAccountDetail.get("operate") + "(" + scoreAAccountDetail.get(
                "serialNumber") + ")");
      }
      if (scoreAAccountDetail.get("useScoreA") != null) {
        excelRow.createCell(2).setCellValue(
            "￥" + (Double.valueOf(scoreAAccountDetail.get("useScoreA").toString().toString())) / 100);
      } else {
        excelRow.createCell(2).setCellValue("￥" + 0);
      }
      if (scoreAAccountDetail.get("issuedScoreA") != null) {
        excelRow.createCell(3).setCellValue(
            "￥" + (Double.valueOf(scoreAAccountDetail.get("issuedScoreA").toString().toString())) / 100);
      } else {
        excelRow.createCell(3).setCellValue("￥" + 0);
      }
      if (scoreAAccountDetail.get("commissionIncome") != null) {
        excelRow.createCell(4).setCellValue(
            "￥" + (Double.valueOf(scoreAAccountDetail.get("commissionIncome").toString().toString())) / 100);
      } else {
        excelRow.createCell(4).setCellValue("￥" + 0);
      }
      if (scoreAAccountDetail.get("jfkShare") != null) {
        excelRow.createCell(5).setCellValue(
            "￥" + (Double.valueOf(scoreAAccountDetail.get("jfkShare").toString().toString())) / 100);
      } else {
        excelRow.createCell(5).setCellValue("￥" + 0);
      }
    }
  }

}
