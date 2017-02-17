package com.jifenke.lepluslive.withdrawBill.controller.view;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
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
 * Created by lss on 17-1-11.
 */
@Configuration
public class WithdrawExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);

        List<OffLineOrderShare> shareList = (List<OffLineOrderShare>) map.get("shareList");
        setExcelRows(sheet, shareList);
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
        excelHeader.createCell(2).setCellValue("会员ID");
        excelHeader.createCell(3).setCellValue("会员手机号");
        excelHeader.createCell(4).setCellValue("订单金额");
        excelHeader.createCell(5).setCellValue("分润金额");
        excelHeader.createCell(6).setCellValue("交易门店");
        excelHeader.createCell(7).setCellValue("交易天使合伙人");
        excelHeader.createCell(8).setCellValue("交易天使合伙人分润");
        excelHeader.createCell(9).setCellValue("锁定门店");
        excelHeader.createCell(10).setCellValue("锁定门店分润");
        excelHeader.createCell(11).setCellValue("锁定天使合伙人"); //
        excelHeader.createCell(12).setCellValue("锁定天使合伙人分润"); //
        excelHeader.createCell(13).setCellValue("锁定城市合伙人"); //
        excelHeader.createCell(14).setCellValue("锁定城市合伙人分润");
        excelHeader.createCell(15).setCellValue("积分客");

    }

    public void setExcelRows(HSSFSheet excelSheet, List<OffLineOrderShare> shareList) {
        int record = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < shareList.size(); i++) {
            OffLineOrderShare offLineOrderShare = shareList.get(i);
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(offLineOrderShare.getOffLineOrder().getOrderSid());
            excelRow.createCell(1).setCellValue(offLineOrderShare.getCreateDate());
            excelRow.createCell(2).setCellValue(offLineOrderShare.getOffLineOrder().getLeJiaUser().getUserSid());
            excelRow.createCell(3).setCellValue(offLineOrderShare.getOffLineOrder().getLeJiaUser().getPhoneNumber());
            excelRow.createCell(4).setCellValue(offLineOrderShare.getOffLineOrder().getTotalPrice() / 100.0);
            excelRow.createCell(5).setCellValue(offLineOrderShare.getShareMoney() / 100.0);

            excelRow.createCell(6).setCellValue(offLineOrderShare.getOffLineOrder().getMerchant().getName());
            if(offLineOrderShare.getTradePartner()==null){
                excelRow.createCell(7).setCellValue("--");
            }else {
                excelRow.createCell(7).setCellValue(offLineOrderShare.getTradePartner().getName());
            }

            excelRow.createCell(8).setCellValue(offLineOrderShare.getToTradePartner() / 100.0);
            if(offLineOrderShare.getLockMerchant()==null){
                excelRow.createCell(9).setCellValue("--");
            }else{
            excelRow.createCell(9).setCellValue(offLineOrderShare.getLockMerchant().getName());
            }


            excelRow.createCell(10).setCellValue(offLineOrderShare.getToLockMerchant() / 100.0);
            if(offLineOrderShare.getLockPartner()==null){
                excelRow.createCell(11).setCellValue("--");
            }else {
                excelRow.createCell(11).setCellValue(offLineOrderShare.getLockPartner().getName());
            }

            excelRow.createCell(12).setCellValue(offLineOrderShare.getToLockPartner() / 100.0);
            if(offLineOrderShare.getLockPartnerManager()==null){
                excelRow.createCell(13).setCellValue("--");
            }else{
                excelRow.createCell(13).setCellValue(offLineOrderShare.getLockPartnerManager().getName());
            }

            excelRow.createCell(14).setCellValue(offLineOrderShare.getToLockPartnerManager() / 100.0);
            excelRow.createCell(15).setCellValue(offLineOrderShare.getToLePlusLife() / 100.0);

        }
    }

}
