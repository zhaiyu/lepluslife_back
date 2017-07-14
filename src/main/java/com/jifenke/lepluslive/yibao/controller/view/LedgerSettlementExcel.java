package com.jifenke.lepluslive.yibao.controller.view;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerSettlement;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通道结算单报表
 * Created by xf on 17-7-14.
 */
@Configuration
public class LedgerSettlementExcel extends AbstractExcelView {
    @Inject
    private MerchantUserService merchantUserService;
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet("list");
        setExcelHeader(sheet);
        List<LedgerSettlement> settlementList = (List<LedgerSettlement>) model.get("ledgerSettlementList");
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
        excelHeader.createCell(0).setCellValue("通道结算单号");
        excelHeader.createCell(1).setCellValue("易宝商户编号");
        excelHeader.createCell(2).setCellValue("商户名称");
        excelHeader.createCell(3).setCellValue("清算日期");
        excelHeader.createCell(4).setCellValue("日交易转账金额");
        excelHeader.createCell(5).setCellValue("费用承担方");
        excelHeader.createCell(6).setCellValue("实际转账金额");
        excelHeader.createCell(7).setCellValue("账户余额");
        excelHeader.createCell(8).setCellValue("起结金额");
        excelHeader.createCell(9).setCellValue("应结算金额");
        excelHeader.createCell(10).setCellValue("实际结算金额");
        excelHeader.createCell(11).setCellValue("转账状态");
        excelHeader.createCell(12).setCellValue("结算状态");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<LedgerSettlement> settlementList) {
        int record = 1;
        for (LedgerSettlement settlement : settlementList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(settlement.getOrderSid());
            excelRow.createCell(1).setCellValue(settlement.getLedgerNo());
            if(settlement.getMerchantUserId()!=null) {
                MerchantUser user = merchantUserService.findById(settlement.getMerchantUserId());
                excelRow.createCell(2).setCellValue(user.getName());
            }else {
                excelRow.createCell(2).setCellValue("--");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            if (settlement.getTradeDate() != null) {
                excelRow.createCell(3).setCellValue(sdf.format(settlement.getTradeDate()));
            } else {
                excelRow.createCell(3).setCellValue("--");
            }
            excelRow.createCell(4).setCellValue(settlement.getTotalTransfer() / 100.0);   // 日交易转账金额
            excelRow.createCell(5).setCellValue(settlement.getSettlementCost() / 100.0);// 单笔结算费用
            //结算费用承担方  0=积分客|1=子商户
            if (settlement.getCostSide() == 0) {
                excelRow.createCell(6).setCellValue("积分客");
            } else if (settlement.getCostSide() == 1) {
                excelRow.createCell(6).setCellValue("子商户");
            }
            excelRow.createCell(7).setCellValue(settlement.getActualTransfer() / 100.0); // 实际转账金额
            //  当前子账户余额(转账前查询)
            excelRow.createCell(8).setCellValue(settlement.getAccountBalance() / 100.0);
            //  起结金额
            excelRow.createCell(9).setCellValue(settlement.getMinSettleAmount() / 100.0);
            //  应结算金额
            excelRow.createCell(10).setCellValue(settlement.getSettlementAmount() / 100.0);
            //  实际结算金额
            excelRow.createCell(11).setCellValue(settlement.getSettlementTrueAmount() / 100.0);
            //转账状态 0=待转账，1=转账成功，2=转账失败
            if (settlement.getTransferState() == 0) {
                excelRow.createCell(11).setCellValue("待转账");
            } else if (settlement.getTransferState() == 1) {
                excelRow.createCell(11).setCellValue("转账成功");
            } else {
                excelRow.createCell(11).setCellValue("转账失败");
            }
            // 结算状态 0=待查询，1=打款成功，2=已退回，3=无结算记录，4=已扣款未打款，5=打款中，-1=打款失败，-2=银行返回打款失败
            if (settlement.getState() == 0) {
                excelRow.createCell(11).setCellValue("待查询");
            } else if (settlement.getState() == 1) {
                excelRow.createCell(11).setCellValue("打款成功");
            } else if (settlement.getState() == 2) {
                excelRow.createCell(11).setCellValue("已退回");
            } else if (settlement.getState() == 3) {
                excelRow.createCell(11).setCellValue("无结算记录");
            } else if (settlement.getState() == 4) {
                excelRow.createCell(11).setCellValue("已扣款未打款");
            } else if (settlement.getState() == 5) {
                excelRow.createCell(11).setCellValue("打款中");
            } else if (settlement.getState() == -1) {
                excelRow.createCell(11).setCellValue("打款失败");
            } else if (settlement.getState() == -2) {
                excelRow.createCell(11).setCellValue("银行返回打款失败");
            }
        }

    }
}
