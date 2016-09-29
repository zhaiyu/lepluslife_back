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
    excelHeader.createCell(10).setCellValue("佣金");
    excelHeader.createCell(11).setCellValue("商户应入账");
    excelHeader.createCell(12).setCellValue("手续费");
    excelHeader.createCell(13).setCellValue("发放红包");
    excelHeader.createCell(14).setCellValue("分润金额");
    excelHeader.createCell(15).setCellValue("发放积分");
    excelHeader.createCell(16).setCellValue("状态");
    excelHeader.createCell(17).setCellValue("订单类型");
    excelHeader.createCell(18).setCellValue("城市");
    excelHeader.createCell(19).setCellValue("销售姓名");
    excelHeader.createCell(20).setCellValue("消费者类别");
    excelHeader.createCell(21).setCellValue("交易完成日期");
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
      excelRow.createCell(10).setCellValue(order.getLjCommission() / 100.0);
      excelRow.createCell(11)
          .setCellValue(order.getTransferMoney() / 100.0);
      excelRow.createCell(12).setCellValue(order.getWxCommission() / 100.0);
      excelRow.createCell(13).setCellValue(order.getRebate() / 100.0);
      if (order.getRebateWay() != 1) {
        excelRow.createCell(14).setCellValue(0);
      } else {
        excelRow.createCell(14).setCellValue(
            (order.getLjCommission() - order.getWxCommission() - order.getRebate()) / 100.0);
      }
      excelRow.createCell(15).setCellValue(order.getScoreB());
      if (order.getState() == 0) {
        excelRow.createCell(16).setCellValue("未支付");
      } else {
        excelRow.createCell(16).setCellValue("已支付");
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
      excelRow.createCell(17).setCellValue(orderType);
      LeJiaUser leJiaUser=order.getLeJiaUser();
      if(order.getMerchant()!=null&&order.getMerchant().getSalesStaff()!=null&&order.getMerchant().getSalesStaff().getName()!=null)
      {
        excelRow.createCell(19).setCellValue(order.getMerchant().getSalesStaff().getName());
      }
      else{
        excelRow.createCell(19).setCellValue("----");
      }
      if(order.getMerchant()!=null&&order.getMerchant().getCity()!=null&&order.getMerchant().getCity().getName()!=null)
      {
        excelRow.createCell(18).setCellValue(order.getMerchant().getCity().getName());
      }
      else{
        excelRow.createCell(18).setCellValue("----");
      }
      Long  leJiaUserId =leJiaUser.getId();
      int count=offLineOrderRepository.findOffLineOrderCountOfLeJiaUser(leJiaUserId);
      if(count==0){excelRow.createCell(20).setCellValue("未消费过");}
      if(count==1){excelRow.createCell(20).setCellValue("微信新用户");}
      if(count>1){excelRow.createCell(20).setCellValue("微信老用户");}
      excelRow.createCell(21).setCellValue(order.getCompleteDate() == null ? "未完成的订单" : sdf2.format(order.getCompleteDate()));
    }
  }
}
