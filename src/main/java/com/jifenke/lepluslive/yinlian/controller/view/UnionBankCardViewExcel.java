package com.jifenke.lepluslive.yinlian.controller.view;

import com.jifenke.lepluslive.user.domain.entities.BankCard;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionBankCard;
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
 * Created by lss on 2017/5/26.
 */
@Configuration
public class UnionBankCardViewExcel extends AbstractExcelView {


    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);

        Map resultMap = (Map) map.get("resultMap");
        setExcelRows(sheet, resultMap);
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
        excelHeader.createCell(0).setCellValue("银行卡号");
        excelHeader.createCell(1).setCellValue("会员ID");
        excelHeader.createCell(2).setCellValue("手机号");
        excelHeader.createCell(3).setCellValue("开户行");
        excelHeader.createCell(4).setCellValue("卡类型");
        excelHeader.createCell(5).setCellValue("卡名称");
        excelHeader.createCell(6).setCellValue("注册方式");
        excelHeader.createCell(7).setCellValue("绑定时间");
        excelHeader.createCell(8).setCellValue("状态");
    }
    public void setExcelRows(HSSFSheet excelSheet, Map resultMap) {

        int record = 1;
        List<UnionBankCard> unionBankCardList= (List<UnionBankCard>)resultMap.get("unionBankCardList");
        List<BankCard> bankCards=(List<BankCard>)resultMap.get("bankCards");
        for (int i=0;i<unionBankCardList.size();i++) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(unionBankCardList.get(i).getNumber());
            excelRow.createCell(1).setCellValue(unionBankCardList.get(i).getUserSid());
            excelRow.createCell(2).setCellValue(unionBankCardList.get(i).getPhoneNumber());
            if(bankCards.get(i)!=null&&bankCards.get(i).getBankName()!=null){
                excelRow.createCell(3).setCellValue(bankCards.get(i).getBankName());
            }else {
                excelRow.createCell(3).setCellValue("--");
            }
            if(bankCards.get(i)!=null&&bankCards.get(i).getCardType()!=null){
                excelRow.createCell(4).setCellValue(bankCards.get(i).getCardType());
            }else {
                excelRow.createCell(4).setCellValue("--");
            }
            if(bankCards.get(i)!=null&&bankCards.get(i).getCardName()!=null){
                excelRow.createCell(5).setCellValue(bankCards.get(i).getCardName());
            }else {
                excelRow.createCell(5).setCellValue("--");
            }

            if(unionBankCardList.get(i).getRegisterWay()==1){
                excelRow.createCell(6).setCellValue("APP注册");
            }else if(unionBankCardList.get(i).getRegisterWay()==2){
                excelRow.createCell(6).setCellValue("POS注册");
            }else if(unionBankCardList.get(i).getRegisterWay()==3){
                excelRow.createCell(6).setCellValue("手动注册");
            }else {
                excelRow.createCell(6).setCellValue("--");
            }

            excelRow.createCell(7).setCellValue(sdf.format(unionBankCardList.get(i).getCreateDate()));

            if(unionBankCardList.get(i).getState()==0){
                excelRow.createCell(8).setCellValue("未注册");
            }else if(unionBankCardList.get(i).getState()==1){
                excelRow.createCell(8).setCellValue("已注册");
            }else if(unionBankCardList.get(i).getState()==2){
                excelRow.createCell(8).setCellValue("取消注册");
            }else {
                excelRow.createCell(8).setCellValue("--");
            }
        }
    }

}
