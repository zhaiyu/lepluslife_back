package com.jifenke.lepluslive.fuyou.controller.view;

import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeRefundOrder;
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
 * 富友扫码退款单EXCEL Created by zhangwen on 16/12/28.
 */
@Configuration
public class ScanCodeRefundOrderViewExcel extends AbstractExcelView {

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);

    List<ScanCodeRefundOrder> orderList = (List<ScanCodeRefundOrder>) map.get("orderList");
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
    excelHeader.createCell(0).setCellValue("退款单号");
    excelHeader.createCell(1).setCellValue("订单编号");
    excelHeader.createCell(2).setCellValue("富友商户号");
    excelHeader.createCell(3).setCellValue("商户号费率");
    excelHeader.createCell(4).setCellValue("交易门店");
    excelHeader.createCell(5).setCellValue("交易商户ID");
    excelHeader.createCell(6).setCellValue("退款完成时间");
    excelHeader.createCell(7).setCellValue("交易完成时间");
    excelHeader.createCell(8).setCellValue("微信渠道退款");
    excelHeader.createCell(9).setCellValue("红包渠道退款");
    excelHeader.createCell(10).setCellValue("消费者编号");
    excelHeader.createCell(11).setCellValue("消费者手机号");
    excelHeader.createCell(12).setCellValue("订单类型");
    excelHeader.createCell(13).setCellValue("追回红包");
    excelHeader.createCell(14).setCellValue("追回积分");
    excelHeader.createCell(15).setCellValue("追回总分润");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<ScanCodeRefundOrder> orderList) {
    int record = 1;
    String[]
        s =
        new String[]{"未完成的订单", "公众号", "APP", "未支付", "已支付", "已退款", "未知", "未绑定手机号", "非会员普通订单（普通商户）",
                     "会员普通订单（普通商户）", "非会员普通订单（联盟商户）", "导流订单", "会员订单（佣金费率）", "会员订单（普通费率）", "纯现金",
                     "纯红包", "混合支付"};
    LeJiaUser leJiaUser = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for (ScanCodeRefundOrder refundOrder : orderList) {
      ScanCodeOrder order = refundOrder.getScanCodeOrder();
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(refundOrder.getRefundOrderSid());
      excelRow.createCell(1).setCellValue(order.getOrderSid());
      excelRow.createCell(2).setCellValue(order.getScanCodeOrderExt().getMerchantNum());
      excelRow.createCell(3).setCellValue(order.getScanCodeOrderExt().getMerchantRate().toString());
      excelRow.createCell(4).setCellValue(order.getMerchant().getName());
      excelRow.createCell(5).setCellValue(order.getScanCodeOrderExt().getMerchantUserId());
      excelRow.createCell(6).setCellValue(sdf.format(refundOrder.getCompleteDate()));
      excelRow.createCell(7).setCellValue(sdf.format(order.getCompleteDate()));
      excelRow.createCell(8).setCellValue(order.getTruePay() / 100.0);
      excelRow.createCell(9).setCellValue(order.getTrueScore() / 100.0);
      leJiaUser = order.getLeJiaUser();
      excelRow.createCell(10).setCellValue(leJiaUser.getUserSid());
      if (leJiaUser.getPhoneNumber() != null) {
        excelRow.createCell(11).setCellValue(leJiaUser.getPhoneNumber());
      } else {
        excelRow.createCell(11).setCellValue(s[7]);
      }
      switch (order.getOrderType().intValue()) {
        case 12001:
          excelRow.createCell(12).setCellValue(s[8]);
          break;
        case 12002:
          excelRow.createCell(12).setCellValue(s[9]);
          break;
        case 12003:
          excelRow.createCell(12).setCellValue(s[10]);
          break;
        case 12004:
          excelRow.createCell(12).setCellValue(s[11]);
          break;
        case 12005:
          excelRow.createCell(12).setCellValue(s[12]);
          break;
        case 12006:
          excelRow.createCell(12).setCellValue(s[13]);
          break;
        default:
          excelRow.createCell(12).setCellValue(s[6]);
      }
      excelRow.createCell(13).setCellValue(refundOrder.getRealRebate() / 100.0);
      excelRow.createCell(14).setCellValue(refundOrder.getRealScoreB());
      Long
          total =
          refundOrder.getToLockMerchant() + refundOrder.getToLockPartner() + refundOrder
              .getToLockPartnerManager() + refundOrder.getToTradePartner() + refundOrder
              .getToTradePartnerManager();
      excelRow.createCell(15).setCellValue(total / 100.0);
    }
  }
}
