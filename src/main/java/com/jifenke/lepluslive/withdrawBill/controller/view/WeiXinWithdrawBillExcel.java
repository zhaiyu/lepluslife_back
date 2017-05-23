package com.jifenke.lepluslive.withdrawBill.controller.view;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WeiXinWithdrawBill;
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
 * Created by lss on 2017/5/15.
 */
@Configuration
public class WeiXinWithdrawBillExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        List<WeiXinWithdrawBill> weiXinWithdrawBillList = (List<WeiXinWithdrawBill>) map.get("weiXinWithdrawBillList");
        setExcelRows(sheet, weiXinWithdrawBillList);
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
        excelHeader.createCell(0).setCellValue("提现单编号");
        excelHeader.createCell(1).setCellValue("申请时间");
        excelHeader.createCell(2).setCellValue("提现合伙人");
        excelHeader.createCell(3).setCellValue("合伙人ID");
        excelHeader.createCell(4).setCellValue("结算账号");
        excelHeader.createCell(5).setCellValue("提现金额");
        excelHeader.createCell(6).setCellValue("状态");
    }


    public void setExcelRows(HSSFSheet excelSheet, List<WeiXinWithdrawBill> weiXinWithdrawBillList) {
        int record = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (WeiXinWithdrawBill weiXinWithdrawBill :weiXinWithdrawBillList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(weiXinWithdrawBill.getWithdrawBillSid());

            if(weiXinWithdrawBill.getCreatedDate()!=null){
                excelRow.createCell(1).setCellValue(sdf.format(weiXinWithdrawBill.getCreatedDate()));
            }else {
                excelRow.createCell(1).setCellValue("--");
            }

            if(weiXinWithdrawBill.getPartner()!=null){
                excelRow.createCell(2).setCellValue(weiXinWithdrawBill.getPartner().getPartnerName());
                excelRow.createCell(3).setCellValue(weiXinWithdrawBill.getPartner().getPartnerSid());
            }else {
                excelRow.createCell(2).setCellValue("--");
                excelRow.createCell(3).setCellValue("--");
            }

            if(weiXinWithdrawBill.getWeiXinOtherUser()!=null){
                if(weiXinWithdrawBill.getWeiXinOtherUser().getWeiXinUser()!=null){
                    excelRow.createCell(4).setCellValue(weiXinWithdrawBill.getWeiXinOtherUser().getWeiXinUser().getNickname());
                }else {
                    excelRow.createCell(4).setCellValue("--");
                }
            }else {
                excelRow.createCell(4).setCellValue("--");
            }

            excelRow.createCell(5).setCellValue(weiXinWithdrawBill.getTotalPrice()/100.0);

            if(weiXinWithdrawBill.getState()==0){
                excelRow.createCell(6).setCellValue("申请中");
            }else if(weiXinWithdrawBill.getState()==1){
                excelRow.createCell(6).setCellValue("未接收");
            }else if(weiXinWithdrawBill.getState()==2){
                excelRow.createCell(6).setCellValue("已驳回");
            }else if(weiXinWithdrawBill.getState()==3){
                excelRow.createCell(6).setCellValue("已退款");
            }else if(weiXinWithdrawBill.getState()==4){
                excelRow.createCell(6).setCellValue("成功接收");
            }else if(weiXinWithdrawBill.getState()==5){
                excelRow.createCell(6).setCellValue("异常");
            }else {
                excelRow.createCell(6).setCellValue("--");
            }


        }
    }


}
