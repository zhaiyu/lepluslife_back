package com.jifenke.lepluslive.yinlian.controller.view;

import com.jifenke.lepluslive.yinlian.domain.entities.UnionPosOrder;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wcg on 16/5/9.
 */
@Configuration
public class UnionPosOrderViewExcel extends AbstractExcelView {


    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);

        List<UnionPosOrder> orderList = (List<UnionPosOrder>) map.get("orderList");
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
        excelHeader.createCell(0).setCellValue("订单号");
        excelHeader.createCell(1).setCellValue("订单类型");
        excelHeader.createCell(2).setCellValue("订单完成时间");
        excelHeader.createCell(3).setCellValue("消费门店");
        excelHeader.createCell(4).setCellValue("消费者");
        excelHeader.createCell(5).setCellValue("付款方式");
        excelHeader.createCell(6).setCellValue("消费金额");
        excelHeader.createCell(7).setCellValue("使用红包");
        excelHeader.createCell(8).setCellValue("使用储值");
        excelHeader.createCell(9).setCellValue("实际支付");
        excelHeader.createCell(10).setCellValue("佣金(手续费)"); //
        excelHeader.createCell(11).setCellValue("实际入账"); //
        excelHeader.createCell(12).setCellValue("发红包"); //
        excelHeader.createCell(13).setCellValue("发金币");
        excelHeader.createCell(14).setCellValue("终端号");
        excelHeader.createCell(15).setCellValue("状态");

    }

    public void setExcelRows(HSSFSheet excelSheet, List<UnionPosOrder> orderList) {

        int record = 1;
        for (UnionPosOrder order : orderList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(order.getOrderSid());
            if (order.getRebateWay() == null) {
                excelRow.createCell(1).setCellValue("--");
            } else {
                if (order.getRebateWay() == 0) {
                    excelRow.createCell(1).setCellValue("非会员普通订单");
                } else if (order.getRebateWay() == 1) {
                    excelRow.createCell(1).setCellValue("导流订单");
                } else if (order.getRebateWay() == 2) {
                    excelRow.createCell(1).setCellValue("会员普通订单");
                } else if (order.getRebateWay() == 3) {
                    excelRow.createCell(1).setCellValue("会员订单");
                }
            }
            if (order.getCompleteDate() != null) {
                excelRow.createCell(2).setCellValue(sdf.format(order.getCompleteDate()));
            } else {
                excelRow.createCell(2).setCellValue("--");
            }
            excelRow.createCell(3).setCellValue(order.getMerchant().getName() + "(" + order.getMerchant().getSid() + ")");
            if (order.getLeJiaUser() != null) {
                if (order.getLeJiaUser().getPhoneNumber() != null) {
                    excelRow.createCell(4).setCellValue(order.getLeJiaUser().getPhoneNumber() + "(" + order.getLeJiaUser().getUserSid() + ")");
                } else {
                    excelRow.createCell(4).setCellValue("--" + "(" + order.getLeJiaUser().getUserSid() + ")");
                }
            } else {
                excelRow.createCell(4).setCellValue("--");
            }

            if (order.getPaidType() == 1) {
                if (order.getChannel() == 0) {
                    excelRow.createCell(5).setCellValue("刷卡");
                } else if (order.getChannel() == 1) {
                    excelRow.createCell(5).setCellValue("微信");
                } else if (order.getChannel() == 2) {
                    excelRow.createCell(5).setCellValue("支付宝");
                }
            } else if (order.getPaidType() == 2) {
                excelRow.createCell(5).setCellValue("红包");
            } else if (order.getPaidType() == 3) {
                if (order.getChannel() == 0) {
                    excelRow.createCell(5).setCellValue("刷卡+红包");
                } else if (order.getChannel() == 1) {
                    excelRow.createCell(5).setCellValue("微信+红包");
                } else if (order.getChannel() == 2) {
                    excelRow.createCell(5).setCellValue("支付宝+红包");
                }
            }
            excelRow.createCell(6).setCellValue(order.getTotalPrice() / 100.0);
            excelRow.createCell(7).setCellValue(order.getTrueScore() / 100.0);
            excelRow.createCell(8).setCellValue(order.getTruePay() / 100.0);
            if (order.getLjCommission() == 0) {
                excelRow.createCell(9).setCellValue("--");
            } else {
                excelRow.createCell(9).setCellValue(order.getLjCommission() / 100.0);
            }
            if (order.getTransferMoney() == 0) {
                excelRow.createCell(10).setCellValue("--");
            } else {
                excelRow.createCell(10).setCellValue(order.getLjCommission() / 100.0);
            }
            excelRow.createCell(11).setCellValue(order.getTransferMoney() / 100.0);
            excelRow.createCell(12).setCellValue("--");
            excelRow.createCell(13).setCellValue("--");
            if (order.getOrderState() == 0) {
                excelRow.createCell(14).setCellValue("未支付");
            } else if (order.getOrderState() == 1) {
                excelRow.createCell(14).setCellValue("已支付(未对账)");
            } else if (order.getOrderState() == 2) {
                excelRow.createCell(14).setCellValue("已支付(已对账)");
            } else if (order.getOrderState() == 3) {
                excelRow.createCell(14).setCellValue("未支付(已冲正)");
            } else if (order.getOrderState() == 4) {
                excelRow.createCell(14).setCellValue("未支付(已撤销)");
            }
        }
    }
}
