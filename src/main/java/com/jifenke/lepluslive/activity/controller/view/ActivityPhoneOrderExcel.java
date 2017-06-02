package com.jifenke.lepluslive.activity.controller.view;

import com.jifenke.lepluslive.activity.domain.entities.ActivityPhoneOrder;
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
 * Created by lss on 17-5-22.
 */
@Configuration
public class ActivityPhoneOrderExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);

        List<ActivityPhoneOrder> phoneOrderList = (List<ActivityPhoneOrder>) map.get("phoneOrderList");
        setExcelRows(sheet, phoneOrderList);
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
        excelHeader.createCell(1).setCellValue("类型");
        excelHeader.createCell(2).setCellValue("话费金额");
        excelHeader.createCell(3).setCellValue("付款方式");
        excelHeader.createCell(4).setCellValue("充值手机号");
        excelHeader.createCell(5).setCellValue("充值会员");
        excelHeader.createCell(6).setCellValue("下单时间");
        excelHeader.createCell(7).setCellValue("充值时间");
        excelHeader.createCell(8).setCellValue("红包奖励");
        excelHeader.createCell(9).setCellValue("状态");
        excelHeader.createCell(10).setCellValue("现金");
        excelHeader.createCell(11).setCellValue("金币");
        excelHeader.createCell(12).setCellValue("手续费");
        excelHeader.createCell(13).setCellValue("乐加入账");
    }


    public void setExcelRows(HSSFSheet excelSheet, List<ActivityPhoneOrder> phoneOrderList) {

        int record = 1;
        for (ActivityPhoneOrder activityPhoneOrder : phoneOrderList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(activityPhoneOrder.getOrderSid());
            if(activityPhoneOrder.getType()==1){
                excelRow.createCell(1).setCellValue("积分");
            }else if(activityPhoneOrder.getType()==2){
                excelRow.createCell(1).setCellValue("金币");
            }else {
                excelRow.createCell(1).setCellValue("--");
            }
            excelRow.createCell(2).setCellValue(activityPhoneOrder.getWorth());
            if(activityPhoneOrder.getType()==1){
                excelRow.createCell(3).setCellValue(activityPhoneOrder.getTruePrice()/100.0+"+"+activityPhoneOrder.getTrueScoreB()/100.0+"金币");
            }else if(activityPhoneOrder.getType()==2){
                excelRow.createCell(3).setCellValue(activityPhoneOrder.getTruePrice()/100.0+"+"+activityPhoneOrder.getTrueScoreB()/100.0+"积分");
            }else {
                excelRow.createCell(3).setCellValue("--");
            }
            excelRow.createCell(4).setCellValue(activityPhoneOrder.getPhone());
            if(activityPhoneOrder.getLeJiaUser()!=null){
                excelRow.createCell(5).setCellValue(activityPhoneOrder.getLeJiaUser().getUserSid()+"("+activityPhoneOrder.getLeJiaUser().getPhoneNumber()+")");
            }else {
                excelRow.createCell(5).setCellValue("--");
            }

              excelRow.createCell(6).setCellValue(sdf.format(activityPhoneOrder.getCreateDate()));
            if(activityPhoneOrder.getCompleteDate()!=null){
                excelRow.createCell(7).setCellValue(sdf.format(activityPhoneOrder.getCompleteDate()));
            }else {
                excelRow.createCell(7).setCellValue("--");
            }
            excelRow.createCell(8).setCellValue(activityPhoneOrder.getPayBackScore()/100.0);
            if(activityPhoneOrder.getState()==0){
                excelRow.createCell(9).setCellValue("待支付");
            }else if(activityPhoneOrder.getState()==1){
                excelRow.createCell(9).setCellValue("已支付待充值");
            }else if(activityPhoneOrder.getState()==2){
                excelRow.createCell(9).setCellValue("已充值");
            }else if(activityPhoneOrder.getState()==3){
                excelRow.createCell(9).setCellValue("已支付充值失败");
            }else {
                excelRow.createCell(9).setCellValue("--");
            }
            excelRow.createCell(10).setCellValue(activityPhoneOrder.getTruePrice()/100.0);
            excelRow.createCell(11).setCellValue(activityPhoneOrder.getTrueScoreB()/100.0);
            excelRow.createCell(12).setCellValue(Math.ceil(activityPhoneOrder.getTruePrice()*0.006)/100.0);
            excelRow.createCell(13).setCellValue((activityPhoneOrder.getTruePrice()-Math.ceil(activityPhoneOrder.getTruePrice()*0.006))/100.0);
        }
    }





}
