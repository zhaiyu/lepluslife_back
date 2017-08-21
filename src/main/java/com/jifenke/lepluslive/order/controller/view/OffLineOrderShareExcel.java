package com.jifenke.lepluslive.order.controller.view;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
 * Created by sunxingfei on 2017/5/18.
 */
@Configuration
public class OffLineOrderShareExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);

        List<OffLineOrderShare> offLineOrderShareList = (List<OffLineOrderShare>) map.get("offLineOrderShareList");
        setExcelRows(sheet, offLineOrderShareList);
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
        // 合并单元格
        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 6, 7);
        CellRangeAddress cra2 = new CellRangeAddress(0, 0, 8, 9);
        CellRangeAddress cra3 = new CellRangeAddress(0, 0, 10, 11);
        CellRangeAddress cra4 = new CellRangeAddress(0, 0, 12, 13);
        CellRangeAddress cra5 = new CellRangeAddress(0, 0, 14, 15);
        //在sheet里增加合并单元格
        excelSheet.addMergedRegion(cra1);
        excelSheet.addMergedRegion(cra2);
        excelSheet.addMergedRegion(cra3);
        excelSheet.addMergedRegion(cra4);
        excelSheet.addMergedRegion(cra5);
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("订单号");
        excelHeader.createCell(1).setCellValue("交易完成时间");
        excelHeader.createCell(2).setCellValue("消费者信息");
        excelHeader.createCell(3).setCellValue("消费金额");
        excelHeader.createCell(4).setCellValue("分润金额");
        excelHeader.createCell(5).setCellValue("交易商户");
        excelHeader.createCell(6).setCellValue("交易商户所在合伙人分润");
        excelHeader.createCell(8).setCellValue("交易合伙人管理员分润");
        excelHeader.createCell(10).setCellValue("会员绑定商户分润");
        excelHeader.createCell(12).setCellValue("会员绑定合伙人分润");
        excelHeader.createCell(14).setCellValue("绑定合伙人管理员分润");
        excelHeader.createCell(16).setCellValue("积分客分润");
        excelHeader.createCell(17).setCellValue("发放金币");
        excelHeader.createCell(18).setCellValue("分润金额");
        excelHeader.createCell(19).setCellValue("发放鼓励金");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<OffLineOrderShare> offLineOrderShareList) {
        int record = 1;
        for (OffLineOrderShare share : offLineOrderShareList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            if (share.getOffLineOrder() != null) {
                excelRow.createCell(0).setCellValue(share.getOffLineOrder().getOrderSid());
            } else {
                excelRow.createCell(0).setCellValue("--");
            }

            excelRow.createCell(1).setCellValue(sdf.format(share.getCreateDate()));


            if (share.getOffLineOrder() != null) {
                if (share.getOffLineOrder().getLeJiaUser() != null) {
                    excelRow.createCell(2).setCellValue(share.getOffLineOrder().getLeJiaUser().getPhoneNumber() + "(" + share.getOffLineOrder().getLeJiaUser().getUserSid() + ")");
                } else {
                    excelRow.createCell(2).setCellValue("--");
                }

            } else {
                excelRow.createCell(2).setCellValue("--");
            }
            if (share.getOffLineOrder() != null) {
                excelRow.createCell(3).setCellValue(share.getOffLineOrder().getTotalPrice() / 100.0);
            } else {
                excelRow.createCell(3).setCellValue("--");
            }

            excelRow.createCell(4).setCellValue(share.getShareMoney() / 100.0);
            if (share.getOffLineOrder() != null) {
                excelRow.createCell(5).setCellValue(share.getOffLineOrder().getMerchant().getName());
            } else {
                excelRow.createCell(5).setCellValue("--");
            }
            excelRow.createCell(6).setCellValue(share.getTradePartner().getName());
            excelRow.createCell(7).setCellValue(share.getToTradePartner() / 100.0);
            excelRow.createCell(8).setCellValue(share.getTradePartnerManager().getName());
            excelRow.createCell(9).setCellValue(share.getToTradePartnerManager() / 100.0);
            if (share.getLockMerchant() != null) {
                excelRow.createCell(10).setCellValue(share.getLockMerchant().getName());
                excelRow.createCell(11).setCellValue(share.getToLockMerchant() / 100.0);
            } else {
                excelRow.createCell(10).setCellValue("--");
                excelRow.createCell(11).setCellValue("--");
            }
            if (share.getLockPartner() != null) {
                excelRow.createCell(12).setCellValue(share.getLockPartner().getName());
                excelRow.createCell(13).setCellValue(share.getToLockPartner() / 100.0);
            } else {
                excelRow.createCell(12).setCellValue("--");
                excelRow.createCell(13).setCellValue("--");
            }
            if (share.getLockPartnerManager() != null) {
                excelRow.createCell(14).setCellValue(share.getLockPartnerManager().getName());
                excelRow.createCell(15).setCellValue(share.getToLockPartnerManager() / 100.0);
            } else {
                excelRow.createCell(14).setCellValue("--");
                excelRow.createCell(15).setCellValue("--");
            }
            excelRow.createCell(16).setCellValue(share.getToLePlusLife() / 100.0);
            if (share.getOffLineOrder() != null) {
                excelRow.createCell(17).setCellValue(share.getOffLineOrder().getScoreC() / 100.0);
                excelRow.createCell(18).setCellValue(share.getOffLineOrder().getShareMoney() / 100.0);
                excelRow.createCell(19).setCellValue(share.getOffLineOrder().getRebate() / 100.0);
            } else {
                excelRow.createCell(17).setCellValue(0L);
                excelRow.createCell(18).setCellValue(share.getShareMoney()/100.0);
                excelRow.createCell(19).setCellValue(0L);
            }
        }
    }
}
