package com.jifenke.lepluslive.yibao.controller.view;

import com.jifenke.lepluslive.yibao.domain.entities.StoreSettlement;
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
 * 门店结算单报表
 * Created by xf on 17-7-14.
 */
@Configuration
public class StoreSettlementExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet("list");
        setExcelHeader(sheet);
        List<StoreSettlement> settlementList = (List<StoreSettlement>) model.get("storeSettlementList");
        setExcelRows(sheet, settlementList);
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
        excelHeader.createCell(0).setCellValue("结算单编号");
        excelHeader.createCell(1).setCellValue("门店SID");
        excelHeader.createCell(2).setCellValue("门店名称");
        excelHeader.createCell(3).setCellValue("清算日期");
        excelHeader.createCell(4).setCellValue("易宝商户号");
        excelHeader.createCell(5).setCellValue("微信应入账");
        excelHeader.createCell(6).setCellValue("支付宝应入账");
        excelHeader.createCell(7).setCellValue("鼓励金应入账");
        excelHeader.createCell(8).setCellValue("退款笔数");
        excelHeader.createCell(9).setCellValue("退款支出");
        excelHeader.createCell(10).setCellValue("实际应转账");
        excelHeader.createCell(11).setCellValue("通道结算单");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<StoreSettlement> settlementList) {
        int record = 1;
        for (StoreSettlement settlement : settlementList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(settlement.getOrderSid());
            excelRow.createCell(1).setCellValue(settlement.getMerchant().getMerchantSid());
            excelRow.createCell(2).setCellValue(settlement.getMerchant().getName());
            if (settlement.getTradeDate() != null) {
                excelRow.createCell(3).setCellValue(settlement.getTradeDate());
            } else {
                excelRow.createCell(3).setCellValue("--");
            }
            excelRow.createCell(4).setCellValue(settlement.getLedgerNo());              // 易宝的子商户号
            excelRow.createCell(5).setCellValue(settlement.getWxTruePayTransfer() / 100.0);// 微信应入账
            excelRow.createCell(6).setCellValue(settlement.getAliTruePayTransfer() / 100.0);// 支付宝应入账
            excelRow.createCell(7).setCellValue(settlement.getScoreTransfer() / 100.0);     // 鼓励金应入账
            excelRow.createCell(8).setCellValue(settlement.getRefundNumber());              // 退款笔数
            excelRow.createCell(9).setCellValue(settlement.getRefundExpend() / 100.0);        // 退款支出
            excelRow.createCell(10).setCellValue(settlement.getActualTransfer() / 100.0);   // 实际应转账金额
            excelRow.createCell(11).setCellValue(settlement.getLedgerSid());   // 通道结算单
        }
    }
}
