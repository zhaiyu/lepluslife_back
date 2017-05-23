package com.jifenke.lepluslive.partner.controller.view;

import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnlineLog;
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
 * Created by lss on 2017/5/17.
 */
@Configuration
public class PartnerWalletOnlineLogExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        List<PartnerWalletOnlineLog> partnerWalletOnlineLogList = (List<PartnerWalletOnlineLog>) map.get("partnerWalletOnlineLogList");
        setExcelRows(sheet, partnerWalletOnlineLogList);
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
        excelHeader.createCell(0).setCellValue("变更流水号");
        excelHeader.createCell(1).setCellValue("变更时间");
        excelHeader.createCell(2).setCellValue("变更值");
        excelHeader.createCell(3).setCellValue("变更来源");
        excelHeader.createCell(4).setCellValue("关联单号");
        excelHeader.createCell(5).setCellValue("变更前余额");
        excelHeader.createCell(6).setCellValue("变更后余额");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<PartnerWalletOnlineLog> partnerWalletOnlineLogList) {
        int record = 1;
        for (PartnerWalletOnlineLog partnerWalletOnlineLog : partnerWalletOnlineLogList) {
            HSSFRow excelRow = excelSheet.createRow(record++);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(partnerWalletOnlineLog.getId());

            if(partnerWalletOnlineLog.getCreateDate()!=null){
                excelRow.createCell(1).setCellValue(sdf.format(partnerWalletOnlineLog.getCreateDate()));
            }else {
                excelRow.createCell(1).setCellValue("--");
            }
            excelRow.createCell(2).setCellValue(partnerWalletOnlineLog.getChangeMoney()/100.0);
            if(partnerWalletOnlineLog.getType()==16001l){
                excelRow.createCell(3).setCellValue("锁定会员线上消费");
            }else if(partnerWalletOnlineLog.getType()==16002l){
                excelRow.createCell(3).setCellValue("锁定会员成为合伙人");
            } else if(partnerWalletOnlineLog.getType()==16003l){
                excelRow.createCell(3).setCellValue("提现");
            }else if(partnerWalletOnlineLog.getType()==16004l){
                excelRow.createCell(3).setCellValue("用户未接受微信红包");
            }else if(partnerWalletOnlineLog.getType()==16005l){
                excelRow.createCell(3).setCellValue("驳回提现请求");
            }else {
                excelRow.createCell(3).setCellValue("--");
            }

            excelRow.createCell(4).setCellValue(partnerWalletOnlineLog.getOrderSid());
            excelRow.createCell(5).setCellValue(partnerWalletOnlineLog.getBeforeChangeMoney()/100.0);
            excelRow.createCell(6).setCellValue(partnerWalletOnlineLog.getAfterChangeMoney()/100.0);
        }
    }

}
