package com.jifenke.lepluslive.order.controller.view;

import com.jifenke.lepluslive.Address.domain.entities.Address;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.order.domain.entities.OrderDetail;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 线上订单导出 Created by zhangwen on 16/10/13.
 */
@Configuration
public class OnLineOrderViewExcel extends AbstractExcelView {


  @Override
  protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    HSSFSheet sheet = hssfWorkbook.createSheet("list");
    setExcelHeader(sheet);
    List<OnLineOrder> orderList = (List<OnLineOrder>) map.get("orderList");
    setExcelRows(sheet, orderList);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String filename = "onLineOrder" + sdf.format(new Date()) + ".xls";//设置下载时客户端Excel的名称
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
    excelHeader.createCell(1).setCellValue("下单渠道");
    excelHeader.createCell(2).setCellValue("订单状态");
    excelHeader.createCell(3).setCellValue("下单日期");
    excelHeader.createCell(4).setCellValue("下单时间");
    excelHeader.createCell(5).setCellValue("支付日期");
    excelHeader.createCell(6).setCellValue("支付时间");
    excelHeader.createCell(7).setCellValue("商品信息");
    excelHeader.createCell(8).setCellValue("买家姓名");
    excelHeader.createCell(9).setCellValue("买家手机号");
    excelHeader.createCell(10).setCellValue("买家地址");
    excelHeader.createCell(11).setCellValue("订单总价");
    excelHeader.createCell(12).setCellValue("实付金额");
    excelHeader.createCell(13).setCellValue("使用积分");
    excelHeader.createCell(14).setCellValue("红包返还");
    excelHeader.createCell(15).setCellValue("微信手续费");
    excelHeader.createCell(16).setCellValue("微信平台入账");
    excelHeader.createCell(17).setCellValue("是否已付款(1=是)");
  }

  public void setExcelRows(HSSFSheet excelSheet, List<OnLineOrder> orderList) {

    int record = 1;
    int payState = 0;
    for (OnLineOrder order : orderList) {
      HSSFRow excelRow = excelSheet.createRow(record++);
      excelRow.createCell(0).setCellValue(order.getOrderSid());
      if (order.getPayOrigin().getPayFrom() == 1) {
        excelRow.createCell(1).setCellValue("APP");
      } else {
        excelRow.createCell(1).setCellValue("公众号");
      }
      String state = null;
      switch (order.getState()) {
        case 0:
          payState = 0;
          state = "未支付";
          break;
        case 1:
          state = "已支付";
          payState = 1;
          break;
        case 2:
          state = "已发货";
          payState = 1;
          break;
        case 3:
          state = "已收货";
          payState = 1;
          break;
        case 4:
          state = "订单取消";
          payState = 0;
          break;
      }
      excelRow.createCell(2).setCellValue(state);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
      excelRow.createCell(3).setCellValue(sdf2.format(order.getCreateDate()));
      excelRow.createCell(4).setCellValue(sdf.format(order.getCreateDate()));
      if (order.getPayDate() == null) {
        excelRow.createCell(5).setCellValue("未支付");
        excelRow.createCell(6).setCellValue("未支付");
      } else {
        excelRow.createCell(5).setCellValue(sdf2.format(order.getPayDate()));
        excelRow.createCell(6).setCellValue(sdf2.format(order.getPayDate()));
      }
      //商品信息
      List<OrderDetail> details = order.getOrderDetails();
      String info = "";
      for (OrderDetail detail : details) {
        info +=
            "+" + detail.getProduct().getName() + "(" + detail.getProductSpec().getSpecDetail()
            + ")X" + detail.getProductNumber();
      }
      excelRow.createCell(7).setCellValue(info);
      String name = null;
      String phone = null;
      String location = null;
      Address address = order.getAddress();
      LeJiaUser user = order.getLeJiaUser();
      if (address != null) {
        name = address.getName();
        phone = address.getPhoneNumber();
        location = address.getLocation();
      } else {
        name = user.getUserSid();
        phone = user.getPhoneNumber();
      }
      excelRow.createCell(8).setCellValue(name);
      excelRow.createCell(9).setCellValue(phone);
      excelRow.createCell(10).setCellValue(location);
      excelRow.createCell(11)
          .setCellValue(order.getTotalPrice() / 100.0 + "+" + order.getTotalScore() + "积分");
      excelRow.createCell(12).setCellValue(order.getTruePrice() / 100.0);
      excelRow.createCell(13).setCellValue(order.getTrueScore() == null ? 0 : order.getTrueScore());
      excelRow.createCell(14)
          .setCellValue(order.getPayBackA() == null ? 0 : order.getPayBackA() / 100.0);
      if (order.getTruePrice() != null) {
        BigDecimal decimal = new BigDecimal(order.getTruePrice() * 6 / 100000.0);
        double pay = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        excelRow.createCell(15).setCellValue(pay);
        excelRow.createCell(16).setCellValue(order.getTruePrice() / 100.0 - pay);
      } else {
        excelRow.createCell(15).setCellValue(0);
        excelRow.createCell(16).setCellValue(0);
      }
      excelRow.createCell(17).setCellValue(payState);
    }
  }
}
