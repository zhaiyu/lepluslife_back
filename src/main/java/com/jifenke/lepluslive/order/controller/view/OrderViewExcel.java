package com.jifenke.lepluslive.order.controller.view;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wcg on 16/5/9.
 */
@Configuration
public class OrderViewExcel extends AbstractExcelView {

  @Inject
  OffLineOrderRepository offLineOrderRepository;

  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);

    List<OffLineOrder> orderList = (List<OffLineOrder>) map.get("orderList");
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
    excelHeader.createCell(1).setCellValue("交易完成时间");
    excelHeader.createCell(2).setCellValue("商户名称");
    excelHeader.createCell(3).setCellValue("商户编号");
    excelHeader.createCell(4).setCellValue("消费者手机号");
    excelHeader.createCell(5).setCellValue("消费者编号");
    excelHeader.createCell(6).setCellValue("订单金额");
    excelHeader.createCell(7).setCellValue("红包使用");
    excelHeader.createCell(8).setCellValue("支付方式");
    excelHeader.createCell(9).setCellValue("实际支付");
    excelHeader.createCell(10).setCellValue("货币手续费"); //
    excelHeader.createCell(11).setCellValue("红包手续费"); //
    excelHeader.createCell(12).setCellValue("红包补贴"); //
    excelHeader.createCell(13).setCellValue("佣金");
    excelHeader.createCell(14).setCellValue("商户应入账");
    //15
    excelHeader.createCell(15).setCellValue("第三方手续费");
    excelHeader.createCell(16).setCellValue("手续费补贴"); //
    excelHeader.createCell(17).setCellValue("佣金纯收入"); //
    excelHeader.createCell(18).setCellValue("会员高签收入"); //
    excelHeader.createCell(19).setCellValue("发放红包");
    excelHeader.createCell(20).setCellValue("分润金额");
    excelHeader.createCell(21).setCellValue("发放积分");
    excelHeader.createCell(22).setCellValue("状态");
    excelHeader.createCell(23).setCellValue("订单类型");
    excelHeader.createCell(24).setCellValue("城市");
    excelHeader.createCell(25).setCellValue("销售姓名");
    excelHeader.createCell(26).setCellValue("消费者类别");
    excelHeader.createCell(27).setCellValue("交易完成日期");
    excelHeader.createCell(28).setCellValue("商户应入账(微信渠道)");
    excelHeader.createCell(29).setCellValue("商户应入账(红包渠道)");

  }

  public void setExcelRows(HSSFSheet excelSheet, List<OffLineOrder> orderList) {

    int record = 1;
    for (OffLineOrder order : orderList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(order.getOrderSid());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
      excelRow.createCell(1).setCellValue(order.getCompleteDate() == null ? "未完成的订单"
                                                                          : sdf
                                              .format(order.getCompleteDate()));
      excelRow.createCell(2).setCellValue(order.getMerchant().getName());
      excelRow.createCell(3).setCellValue(order.getMerchant().getMerchantSid());
      if (order.getLeJiaUser().getPhoneNumber() == null) {
        excelRow.createCell(4).setCellValue("未绑定手机号");
        excelRow.createCell(5).setCellValue(order.getLeJiaUser().getUserSid());
      } else {
        excelRow.createCell(4).setCellValue(
            order.getLeJiaUser().getPhoneNumber());
        excelRow.createCell(5).setCellValue(order.getLeJiaUser().getUserSid());
      }
      excelRow.createCell(6).setCellValue(order.getTotalPrice() / 100.0);
      excelRow.createCell(7).setCellValue(order.getTrueScore() / 100.0);
      excelRow.createCell(8).setCellValue(order.getPayWay().getPayWay());
      excelRow.createCell(9).setCellValue(order.getTruePay() / 100.0);
      //货币手续费
      Integer partnership = order.getMerchant().getPartnership();
      double currencyCommissionCharge = 0;
      double totalPrice = order.getTotalPrice().doubleValue() / 100.0;
      double truePay = order.getTruePay().doubleValue() / 100.0;
      if (partnership == 0) {
        BigDecimal ljCommission = order.getMerchant().getLjCommission().divide(new BigDecimal(100));
        double result = ljCommission.multiply(new BigDecimal(truePay)).doubleValue();
        currencyCommissionCharge = Math.round(result * 100) / 100.0;
        excelRow.createCell(10).setCellValue(currencyCommissionCharge);

      }
      if (partnership == 1) {
        BigDecimal ljBrokerage = order.getMerchant().getLjBrokerage().divide(new BigDecimal(100));
        double result = ljBrokerage.multiply(new BigDecimal(truePay)).doubleValue();
        currencyCommissionCharge = Math.round(result * 100) / 100.0;
        excelRow.createCell(10).setCellValue(currencyCommissionCharge);

      }
      //红包手续费
      double scoreaCommissionCharge = 0;
      Long ljCommission=order.getLjCommission();
      Long truePayCommission=order.getTruePayCommission();
      if(ljCommission==null){
        ljCommission=0L;
      }
      if(truePayCommission==null){
        truePayCommission=0L;
      }
      double dLjCommission = ljCommission.doubleValue() / 100.0;
      double dtruePayCommission = truePayCommission.doubleValue() / 100.0;
         scoreaCommissionCharge =dLjCommission-dtruePayCommission;
      scoreaCommissionCharge= Math.round(scoreaCommissionCharge*100)/100.0;
      excelRow.createCell(11).setCellValue(scoreaCommissionCharge);
      //红包补贴
      double trueScore = order.getTrueScore().doubleValue() / 100.0;
      double scoreaSubsidy = trueScore - scoreaCommissionCharge;
      excelRow.createCell(12).setCellValue(scoreaSubsidy);

      excelRow.createCell(13).setCellValue(order.getLjCommission() / 100.0);
      excelRow.createCell(14)
          .setCellValue(order.getTransferMoney() / 100.0);
      excelRow.createCell(15).setCellValue(order.getWxCommission() / 100.0);
      //手续费补贴
        BigDecimal memberCommission=new BigDecimal(0);
        BigDecimal ljBrokerage=new BigDecimal(0);
        if(order.getMerchant().getMemberCommission()!=null){
            memberCommission=order.getMerchant().getMemberCommission();
        }
        if(order.getMerchant().getLjBrokerage()!=null){
            ljBrokerage=order.getMerchant().getLjBrokerage();
        }
        int a8= memberCommission.compareTo(ljBrokerage);

      double wxCommission = order.getWxCommission().doubleValue() / 100.0;
      double commissionSubsidy = currencyCommissionCharge - wxCommission;
      if(order.getRebateWay()==1){
        excelRow.createCell(16).setCellValue(0);
      }  if(a8==1&&order.getRebateWay()==3){
        excelRow.createCell(16).setCellValue(0);
      } else {
        excelRow.createCell(16).setCellValue(commissionSubsidy);
      }

      //佣金纯收入
      if (order.getRebateWay() == 1) {
        double dljCommission = new Double(ljCommission.toString()) / 100.0;

        double a = Math.round(dljCommission * 100) / 100.0;

        double b = Math.round(scoreaCommissionCharge * 100) / 100.0;
        double c = Math.round(wxCommission * 100) / 100.0;
        double d = (a * 100 - b * 100 - c * 100) / 100.0;
        excelRow.createCell(17).setCellValue(d);
      } else {
        excelRow.createCell(17).setCellValue(0);
      }
      //会员高签收入
      if (order.getRebateWay() == 3) {
        double dljCommission = new Double(ljCommission.toString()) / 100.0;

        double a = Math.round(dljCommission * 100) / 100.0;

        double b = Math.round(scoreaCommissionCharge * 100) / 100.0;
        double c = Math.round(wxCommission * 100) / 100.0;
        double d = (a * 100 - b * 100 - c * 100) / 100.0;
        if (d < 0) {
          excelRow.createCell(18).setCellValue("--");
        } else {
          excelRow.createCell(18).setCellValue(d);
        }

      } else {
        excelRow.createCell(18).setCellValue("--");
      }
      excelRow.createCell(19).setCellValue(order.getRebate() / 100.0);
      if (order.getRebateWay() != 1) {
        excelRow.createCell(20).setCellValue(0);
      } else {
        excelRow.createCell(20).setCellValue(
            (order.getLjCommission() - order.getWxCommission() - order.getRebate()) / 100.0);
      }
      excelRow.createCell(21).setCellValue(order.getScoreB());
      if (order.getState() == 0) {
        excelRow.createCell(22).setCellValue("未支付");
      } else {
        excelRow.createCell(22).setCellValue("已支付");
      }
      String orderType = null;
      switch (order.getRebateWay()) {
        case 0:
          orderType = "非会员普通订单";
          break;
        case 1:
          orderType = "导流订单";
          break;
        case 2:
          orderType = "会员普通订单";
          break;
        case 3:
          orderType = "会员订单";
          break;
        case 4:
          orderType = "非会员扫纯支付码";
          break;
        case 5:
          orderType = "会员扫纯支付码";
          break;
      }
      excelRow.createCell(23).setCellValue(orderType);
      LeJiaUser leJiaUser = order.getLeJiaUser();
      if (order.getMerchant() != null && order.getMerchant().getCity() != null
          && order.getMerchant().getCity().getName() != null) {
        excelRow.createCell(24).setCellValue(order.getMerchant().getCity().getName());
      } else {
        excelRow.createCell(24).setCellValue("----");
      }
      if (order.getMerchant() != null && order.getMerchant().getSalesStaff() != null
          && order.getMerchant().getSalesStaff().getName() != null) {
        excelRow.createCell(25).setCellValue(order.getMerchant().getSalesStaff().getName());
      } else {
        excelRow.createCell(25).setCellValue("----");
      }
      Long leJiaUserId = leJiaUser.getId();
      int count = offLineOrderRepository.findOffLineOrderCountOfLeJiaUser(leJiaUserId);
      if (count == 0) {
        excelRow.createCell(26).setCellValue("未消费过");
      }
      if (count == 1) {
        excelRow.createCell(26).setCellValue("微信新用户");
      }
      if (count > 1) {
        excelRow.createCell(26).setCellValue("微信老用户");
      }
      excelRow.createCell(27).setCellValue(
          order.getCompleteDate() == null ? "未完成的订单" : sdf2.format(order.getCompleteDate()));
      //商户应入账(微信渠道)
           double dtruePay = order.getTruePay().doubleValue();
           double ddtruePay=order.getTruePay().doubleValue()/100.0;
           double wxMerchantRecorded=Math.round((ddtruePay-dtruePayCommission))*100/100.0;
      excelRow.createCell(28).setCellValue(wxMerchantRecorded);
      // 商户应入账(红包渠道)
      Long transferMoney=order.getTransferMoney();
      double dtransferMoney=transferMoney.doubleValue() / 100.0;
      double scoreaMerchantRecorded=Math.round((dtransferMoney-(truePay-dtruePayCommission)))*100/100.0;
      excelRow.createCell(29).setCellValue(scoreaMerchantRecorded);
    }
  }
}
