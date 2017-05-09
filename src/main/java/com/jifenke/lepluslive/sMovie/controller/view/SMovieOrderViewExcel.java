package com.jifenke.lepluslive.sMovie.controller.view;

import com.jifenke.lepluslive.sMovie.domain.entities.SMovieOrder;
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
 * Created by lss on 17-5-5.
 */
@Configuration
public class SMovieOrderViewExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);

        List<SMovieOrder> orderList = (List<SMovieOrder>) map.get("orderList");
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
        excelHeader.createCell(1).setCellValue("会员ID");
        excelHeader.createCell(2).setCellValue("会员手机号");
        excelHeader.createCell(3).setCellValue("购买产品");
        excelHeader.createCell(4).setCellValue("订单金额");
        excelHeader.createCell(5).setCellValue("使用金币");
        excelHeader.createCell(6).setCellValue("实际之付");
        excelHeader.createCell(7).setCellValue("红包奖励");
        excelHeader.createCell(8).setCellValue("下单时间");
        excelHeader.createCell(9).setCellValue("支付时间");
        excelHeader.createCell(10).setCellValue("核销时间");
        excelHeader.createCell(11).setCellValue("核销影院");
        excelHeader.createCell(12).setCellValue("状态");
        excelHeader.createCell(13).setCellValue("手续费成本");
        excelHeader.createCell(14).setCellValue("实际入账");
    }


    public void setExcelRows(HSSFSheet excelSheet, List<SMovieOrder> orderList) {
        int record = 1;
        for (SMovieOrder order : orderList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(order.getOrderSid());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            if (order.getLeJiaUser() != null) {
                excelRow.createCell(1).setCellValue(order.getLeJiaUser().getUserSid());
                excelRow.createCell(2).setCellValue(order.getLeJiaUser().getPhoneNumber());
            } else {
                excelRow.createCell(1).setCellValue("--");
                excelRow.createCell(2).setCellValue("--");
            }
            if (order.getsMovieProduct() != null) {
                excelRow.createCell(3).setCellValue(order.getsMovieProduct().getName());
            }
            excelRow.createCell(4).setCellValue(order.getTotalPrice() / 100.0);
            excelRow.createCell(5).setCellValue(order.getTrueScore() / 100.0);
            excelRow.createCell(6).setCellValue(order.getTotalPrice() / 100.0);
            excelRow.createCell(7).setCellValue(order.getPayBackA() / 100.0);
            excelRow.createCell(8).setCellValue(sdf.format(order.getDateCreated()));
            excelRow.createCell(9).setCellValue(sdf.format(order.getDateCompleted()));
            excelRow.createCell(10).setCellValue(sdf.format(order.getDateUsed()));
            if (order.getsMovieTerminal() != null) {
                excelRow.createCell(11).setCellValue(order.getsMovieTerminal().getMovieName());
            } else {
                excelRow.createCell(11).setCellValue("--");
            }
            if (order.getState() == 0) {
                excelRow.createCell(12).setCellValue("待付款");
            } else if (order.getState() == 1) {
                excelRow.createCell(12).setCellValue("已付款待核销");
            } else if (order.getState() == 2) {
                excelRow.createCell(12).setCellValue("已付款已核销");
            } else if (order.getState() == 3) {
                excelRow.createCell(12).setCellValue("已退款");
            } else {
                excelRow.createCell(12).setCellValue("--");
            }

            excelRow.createCell(13).setCellValue(order.getCommission() / 100.0);
            excelRow.createCell(14).setCellValue(order.getTrueIncome() / 100.0);


        }
    }

}
