package com.jifenke.lepluslive.user.domain.entities;

import com.jifenke.lepluslive.user.controller.dto.LeJiaUserDto;
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
 * Created by xf on 17-2-22.
 * 会员信息导出
 */
@Configuration
public class UserViewExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        List<LeJiaUserDto> userList = (List<LeJiaUserDto>) map.get("userList");
        setExcelRows(sheet, userList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String filename = "userInfo" + sdf.format(new Date()) + ".xls";//设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);

        OutputStream ouputStream = response.getOutputStream();
        hssfWorkbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();

    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("会员ID");
        excelHeader.createCell(1).setCellValue("会员类型");
        excelHeader.createCell(2).setCellValue("注册来源");
        excelHeader.createCell(3).setCellValue("注册时间");
        excelHeader.createCell(4).setCellValue("注册日期");
        excelHeader.createCell(5).setCellValue("是否关注");
        excelHeader.createCell(6).setCellValue("微信昵称");
        excelHeader.createCell(7).setCellValue("手机号");
        excelHeader.createCell(8).setCellValue("创建时间");
        excelHeader.createCell(9).setCellValue("运营城市");
        excelHeader.createCell(10).setCellValue("锁定门店");
        excelHeader.createCell(11).setCellValue("锁定门店时间");
        excelHeader.createCell(12).setCellValue("锁定天使合伙人");
        excelHeader.createCell(13).setCellValue("锁定天使合伙人时间");
        excelHeader.createCell(14).setCellValue("红包余额");
        excelHeader.createCell(15).setCellValue("累计红包");
        excelHeader.createCell(16).setCellValue("积分余额");
        excelHeader.createCell(17).setCellValue("累计积分");
        excelHeader.createCell(18).setCellValue("金币余额");
        excelHeader.createCell(19).setCellValue("累计金币");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<LeJiaUserDto> userList) {
        for (LeJiaUserDto lejiaUserDto : userList) {
            HSSFRow excelRow = excelSheet.createRow(excelSheet.getLastRowNum() + 1);
            excelRow.createCell(0).setCellValue(lejiaUserDto.getId());
            if (lejiaUserDto.getState() == 0) {
                excelRow.createCell(1).setCellValue("普通消费者");
            } else {
                excelRow.createCell(1).setCellValue("乐+会员");
            }
            excelRow.createCell(2).setCellValue(lejiaUserDto.getRegisterOrigin().getDescription());
            String registSeconds = "未记录";
            String registDays = "未记录";
            if(lejiaUserDto.getPhoneBindDate()!=null) {
                registSeconds = new SimpleDateFormat("HH:mm:ss").format(lejiaUserDto.getPhoneBindDate());
                registDays = new SimpleDateFormat("yyyy/MM/dd").format(lejiaUserDto.getPhoneBindDate());
            }
            excelRow.createCell(3).setCellValue(registSeconds);
            excelRow.createCell(4).setCellValue(registDays);
            if (lejiaUserDto.getSubState() == 1) {
                excelRow.createCell(5).setCellValue("已关注");
            } else {
                excelRow.createCell(5).setCellValue("未关注");
            }
            excelRow.createCell(6).setCellValue(lejiaUserDto.getNickname());
            excelRow.createCell(7).setCellValue(lejiaUserDto.getPhoneNumber());
            String createDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(lejiaUserDto.getCreateDate());
            excelRow.createCell(8).setCellValue(createDate);
            excelRow.createCell(9).setCellValue(lejiaUserDto.getCity());
            excelRow.createCell(10).setCellValue(lejiaUserDto.getMerchantName());
            String merchantBindDate = "未记录";
            if(lejiaUserDto.getBindMerchantDate()!=null)
                    merchantBindDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(lejiaUserDto.getBindMerchantDate());
            excelRow.createCell(11).setCellValue(merchantBindDate);
            excelRow.createCell(12).setCellValue(lejiaUserDto.getPartnerName());
            String parterBindDate = "未记录";
            if(lejiaUserDto.getBindPartnerDate()!=null)
                parterBindDate =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(lejiaUserDto.getBindPartnerDate());
            excelRow.createCell(13).setCellValue(parterBindDate);
            excelRow.createCell(14).setCellValue(lejiaUserDto.getScoreA());
            excelRow.createCell(15).setCellValue(lejiaUserDto.getTotalScoreA());
            excelRow.createCell(16).setCellValue(lejiaUserDto.getScoreB());
            excelRow.createCell(17).setCellValue(lejiaUserDto.getTotalScoreB());
            excelRow.createCell(18).setCellValue(lejiaUserDto.getScoreC()!=null?lejiaUserDto.getTotalScoreC():0);
            excelRow.createCell(19).setCellValue(lejiaUserDto.getTotalScoreC()!=null?lejiaUserDto.getTotalScoreC():0);
        }
    }
}