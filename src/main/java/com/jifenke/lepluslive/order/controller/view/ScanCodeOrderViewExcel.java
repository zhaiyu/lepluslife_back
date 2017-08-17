package com.jifenke.lepluslive.order.controller.view;

import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

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
 * 富友扫码订单EXCEL Created by zhangwen on 16/12/21.
 */
@Configuration
public class ScanCodeOrderViewExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);

    List<ScanCodeOrder> orderList = (List<ScanCodeOrder>) map.get("orderList");
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
    excelHeader.createCell(0).setCellValue("订单编号");
    excelHeader.createCell(1).setCellValue("状态");
    excelHeader.createCell(2).setCellValue("订单创建时间");
    excelHeader.createCell(3).setCellValue("订单完成时间");
    excelHeader.createCell(4).setCellValue("订单完成日期");
    excelHeader.createCell(5).setCellValue("支付渠道");
    excelHeader.createCell(6).setCellValue("交易门店");
    excelHeader.createCell(7).setCellValue("交易商户ID");
    excelHeader.createCell(8).setCellValue("消费者编号");
    excelHeader.createCell(9).setCellValue("消费者手机号");
    excelHeader.createCell(10).setCellValue("订单类型");
    excelHeader.createCell(11).setCellValue("支付方式");
    excelHeader.createCell(12).setCellValue("订单金额");
    excelHeader.createCell(13).setCellValue("使用红包");
    excelHeader.createCell(14).setCellValue("实际支付");
    excelHeader.createCell(15).setCellValue("乐加佣金");
    excelHeader.createCell(16).setCellValue("乐加佣金（实付）");
    excelHeader.createCell(17).setCellValue("乐加佣金（红包）");
    excelHeader.createCell(18).setCellValue("商户总应入账");
    excelHeader.createCell(19).setCellValue("商户应入账（实付）");
    excelHeader.createCell(20).setCellValue("商户应入账（红包）");
    excelHeader.createCell(21).setCellValue("发放金币");
    excelHeader.createCell(22).setCellValue("发放鼓励金");
    excelHeader.createCell(23).setCellValue("分润金额");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<ScanCodeOrder> orderList) {
    int record = 1;
    String[]
        s =
        new String[]{"未完成的订单", "公众号", "APP", "未支付", "已支付", "已退款", "未知", "未绑定手机号", "非会员普通订单（普通商户）",
                     "会员普通订单（普通商户）", "非会员普通订单（联盟商户）", "导流订单", "会员订单（佣金费率）", "会员订单（普通费率）", "纯现金",
                     "纯红包", "混合支付"};
    LeJiaUser leJiaUser = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    for (ScanCodeOrder order : orderList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(order.getOrderSid());
      if (order.getState() == 0) {
        excelRow.createCell(1).setCellValue(s[3]);
      } else if (order.getState() == 1) {
        excelRow.createCell(1).setCellValue(s[4]);
      } else if (order.getState() == 2) {
        excelRow.createCell(1).setCellValue(s[5]);
      } else {
        excelRow.createCell(1).setCellValue(s[6]);
      }
      excelRow.createCell(2).setCellValue(sdf.format(order.getCreatedDate()));
      if (order.getCompleteDate() == null) {
        excelRow.createCell(3).setCellValue(s[0]);
        excelRow.createCell(4).setCellValue(s[0]);
      } else {
        excelRow.createCell(3).setCellValue(sdf.format(order.getCompleteDate()));
        excelRow.createCell(4).setCellValue(sdf2.format(order.getCompleteDate()));
      }
      if (order.getScanCodeOrderExt().getSource() == 0) {
        excelRow.createCell(5).setCellValue(s[1]);
      } else {
        excelRow.createCell(5).setCellValue(s[2]);
      }
      excelRow.createCell(6).setCellValue(order.getMerchant().getName());
      excelRow.createCell(7).setCellValue(order.getScanCodeOrderExt().getMerchantUserId());
      leJiaUser = order.getLeJiaUser();
      excelRow.createCell(8).setCellValue(leJiaUser.getUserSid());
      if (leJiaUser.getPhoneNumber() != null) {
        excelRow.createCell(9).setCellValue(leJiaUser.getPhoneNumber());
      } else {
        excelRow.createCell(9).setCellValue(s[7]);
      }
      switch (order.getOrderType().intValue()) {
        case 12001:
          excelRow.createCell(10).setCellValue(s[8]);
          break;
        case 12002:
          excelRow.createCell(10).setCellValue(s[9]);
          break;
        case 12003:
          excelRow.createCell(10).setCellValue(s[10]);
          break;
        case 12004:
          excelRow.createCell(10).setCellValue(s[11]);
          break;
        case 12005:
          excelRow.createCell(10).setCellValue(s[12]);
          break;
        case 12006:
          excelRow.createCell(10).setCellValue(s[13]);
          break;
        default:
          excelRow.createCell(10).setCellValue(s[6]);
      }
      //付款方式 0=不用（纯通道） 1=使用（纯鼓励金）  2=混合
      if (order.getScanCodeOrderExt().getPayment() == 0) {
        excelRow.createCell(11).setCellValue("纯通道");
      } else if (order.getScanCodeOrderExt().getPayment() == 2) {
        excelRow.createCell(11).setCellValue("混合");
      } else {
        excelRow.createCell(11).setCellValue("纯鼓励金");
      }
      excelRow.createCell(12).setCellValue(order.getTotalPrice() / 100.0);
      excelRow.createCell(13).setCellValue(order.getTrueScore() / 100.0);
      excelRow.createCell(14).setCellValue(order.getTruePay() / 100.0);
      excelRow.createCell(15).setCellValue(order.getCommission() / 100.0);
      excelRow.createCell(16).setCellValue(order.getTruePayCommission() / 100.0);
      excelRow.createCell(17).setCellValue(order.getScoreCommission() / 100.0);
      excelRow.createCell(18).setCellValue(order.getTransferMoney() / 100.0);
      excelRow.createCell(19).setCellValue(order.getTransferMoneyFromTruePay() / 100.0);
      excelRow.createCell(20).setCellValue(order.getTransferMoneyFromScore() / 100.0);
      excelRow.createCell(21).setCellValue(order.getScoreC() / 100.0);
      excelRow.createCell(22).setCellValue(order.getRebate() / 100.0);
      excelRow.createCell(23).setCellValue(order.getShare() / 100.0);
    }
  }
}
