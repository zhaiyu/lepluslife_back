package com.jifenke.lepluslive.yibao.controller.view;

import com.jifenke.lepluslive.yibao.domain.entities.LedgerRefundOrder;
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
 * 退款单报表
 * Created by xf on 17-7-14.
 */
@Configuration
public class LedgerRefundOrderExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet("list");
        setExcelHeader(sheet);
        List<LedgerRefundOrder> refundOrderList = (List<LedgerRefundOrder>) model.get("ledgerRefundOrderList");
        setExcelRows(sheet, refundOrderList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String filename = sdf.format(new Date()) + ".xls";//设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);

        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("退款单号");
        excelHeader.createCell(1).setCellValue("订单编号");
        excelHeader.createCell(2).setCellValue("退款通道");
        excelHeader.createCell(3).setCellValue("清算日期");
        excelHeader.createCell(4).setCellValue("退款完成时间");
        excelHeader.createCell(5).setCellValue("退款金额");
        excelHeader.createCell(6).setCellValue("手续费");
        excelHeader.createCell(7).setCellValue("手续费承担方");
        excelHeader.createCell(8).setCellValue("应收门店金额");
        excelHeader.createCell(9).setCellValue("订单类型");
        excelHeader.createCell(10).setCellValue("追回虚拟货币");
        excelHeader.createCell(11).setCellValue("追回分润");
        excelHeader.createCell(12).setCellValue("状态");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<LedgerRefundOrder> refundOrderList) {
        int record = 1;
        for (LedgerRefundOrder refundOrder : refundOrderList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(refundOrder.getRefundOrderSid());   // 退款单号
            excelRow.createCell(1).setCellValue(refundOrder.getOrderSid());         // 订单编号
            if (refundOrder.getOrderFrom() == 1) {                                  // 退款通道 1=易宝，2=富有，3=微信
                excelRow.createCell(2).setCellValue("易宝");
            } else if (refundOrder.getOrderFrom() == 2) {
                excelRow.createCell(2).setCellValue("富有");
            } else if (refundOrder.getOrderFrom() == 3) {
                excelRow.createCell(2).setCellValue("微信");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");             // 清算日期
            if (refundOrder.getTradeDate() != null) {
                excelRow.createCell(3).setCellValue(refundOrder.getTradeDate());
            } else {
                excelRow.createCell(3).setCellValue("--");
            }
            if (refundOrder.getDateCompleted() != null) {                         //  退款完成时间
                excelRow.createCell(4).setCellValue(sdf.format(refundOrder.getDateCompleted()));
            } else {
                excelRow.createCell(4).setCellValue("--");
            }
            excelRow.createCell(5).setCellValue("￥" + (refundOrder.getTotalAmount() / 100.0));// 退款金额
            excelRow.createCell(6).setCellValue("￥" + ((refundOrder.getThirdTrueCommission() + refundOrder.getScoreCommission()) / 100.0));// 手续费
            //结算费用承担方  0=积分客|1=子商户
            if (refundOrder.getRateCostSide() == 0) {
                excelRow.createCell(7).setCellValue("积分客");
            } else if (refundOrder.getRateCostSide() == 1) {
                excelRow.createCell(7).setCellValue("商户");
            }
            excelRow.createCell(8).setCellValue(refundOrder.getTrueAmount() / 100.0); // 应收门店金额
            //  订单类型 1=普通订单,2=乐加订单
            if (refundOrder.getOrderType() == 1) {
                excelRow.createCell(9).setCellValue("普通订单");
            } else if (refundOrder.getOrderType() == 2) {
                excelRow.createCell(9).setCellValue("乐加订单");
            }
            //  追回补贴金额
            excelRow.createCell(10).setCellValue((refundOrder.getRealScoreA()/100.0) + "元" + (refundOrder.getRealScoreC() / 100.0) + "金币");
            //  追回分润
            excelRow.createCell(11).setCellValue(refundOrder.getShareBack() / 100.0);
            // 退款状态 0=待退款，1=未开始退款，2=退款成功，3=退款失败，其他为通道返回码
            if (refundOrder.getState() == 0) {
                excelRow.createCell(12).setCellValue("待退款");
            } else if (refundOrder.getState() == 1) {
                excelRow.createCell(12).setCellValue("未开始退款");
            } else if (refundOrder.getState() == 2) {
                excelRow.createCell(12).setCellValue("退款成功");
            } else if (refundOrder.getState() == 3) {
                excelRow.createCell(12).setCellValue("退款失败");
            } else {
                excelRow.createCell(12).setCellValue("通道返回码");
            }
        }

    }
}
