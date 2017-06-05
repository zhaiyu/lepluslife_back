package com.jifenke.lepluslive.score.controller.view;

import com.jifenke.lepluslive.score.domain.entities.ScoreCDetail;
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
 * Created by lss on 2017/6/5.
 */
@Configuration
public class ScoreCDetailExcel  extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        List<ScoreCDetail> scoreCDetailList = (List<ScoreCDetail>) map.get("scoreCDetailList");
        setExcelRows(sheet, scoreCDetailList);
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
        excelHeader.createCell(0).setCellValue("金币变更时间");
        excelHeader.createCell(1).setCellValue("变更项目");
        excelHeader.createCell(2).setCellValue("变更数目");
        excelHeader.createCell(3).setCellValue("对应单号");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<ScoreCDetail> scoreCDetailList) {
        int record = 1;
        for (ScoreCDetail scoreCDetail : scoreCDetailList) {
            HSSFRow excelRow = excelSheet.createRow(record++);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(sdf.format(scoreCDetail.getDateCreated()));

            if(scoreCDetail.getOrigin()==1){
                excelRow.createCell(1).setCellValue("线上返还");
            }else if(scoreCDetail.getOrigin()==2){
                excelRow.createCell(1).setCellValue("线上消费");
            }else if(scoreCDetail.getOrigin()==3){
                excelRow.createCell(1).setCellValue("线下消费");
            }else if(scoreCDetail.getOrigin()==4){
                excelRow.createCell(1).setCellValue("线下返还");
            }else if(scoreCDetail.getOrigin()==0){
                excelRow.createCell(1).setCellValue("注册有礼");
            }else if(scoreCDetail.getOrigin()==8){
                excelRow.createCell(1).setCellValue("分享得金币");
            }else if(scoreCDetail.getOrigin()==15003){
                excelRow.createCell(1).setCellValue("充话费消耗");
            }else {
                excelRow.createCell(1).setCellValue("--");
            }
            excelRow.createCell(2).setCellValue(scoreCDetail.getNumber()/100.0);

            if(scoreCDetail.getOrderSid()==null){
                excelRow.createCell(3).setCellValue("--");
            }else {
                excelRow.createCell(3).setCellValue(scoreCDetail.getOrderSid());
            }


        }
    }


}
