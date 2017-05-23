package com.jifenke.lepluslive.partner.controller.view;

import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnline;
import com.jifenke.lepluslive.partner.repository.PartnerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;
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
 * Created by lss on 2017/5/14.
 */
@Configuration
public class PartnerViewExcel  extends AbstractExcelView {
    @Inject
    private LeJiaUserRepository leJiaUserRepository;
    @Inject
    private PartnerRepository partnerRepository;
    @Inject
    private PartnerWalletRepository partnerWalletRepository;

    @Inject
    private PartnerWalletOnlineRepository partnerWalletOnlineRepository;





    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);

        List<Partner> orderList = (List<Partner>) map.get("partnerList");
        setExcelRows(sheet, orderList);
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
        excelHeader.createCell(0).setCellValue("合伙人ID");
        excelHeader.createCell(1).setCellValue("合伙人姓名");
        excelHeader.createCell(2).setCellValue("联系电话");
        excelHeader.createCell(3).setCellValue("绑定店铺");
        excelHeader.createCell(4).setCellValue("绑定会员");
        excelHeader.createCell(5).setCellValue("佣金金额");
        excelHeader.createCell(6).setCellValue("累计佣金收入");
        excelHeader.createCell(7).setCellValue("线上佣金收入");
        excelHeader.createCell(8).setCellValue("线下佣金收入");
        excelHeader.createCell(9).setCellValue("注册来源");
        excelHeader.createCell(10).setCellValue("注册时间");
        excelHeader.createCell(11).setCellValue("对应会员ID");
    }
    public void setExcelRows(HSSFSheet excelSheet, List<Partner> orderList) {
        int record = 1;
        for (Partner partner : orderList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(partner.getId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            if (partner.getPartnerName() != null) {
                excelRow.createCell(1).setCellValue(partner.getPartnerName());
            } else {
                excelRow.createCell(1).setCellValue("--");
            }
            if (partner.getPhoneNumber() != null) {
                excelRow.createCell(2).setCellValue(partner.getPhoneNumber());
            } else {
                excelRow.createCell(2).setCellValue("--");
            }

            int partnerBindMerchant = 0;
            partnerBindMerchant =partnerRepository.findPartnerBindMerchantCount(partner.getId());
            Long merchantLimit=0l;
            if(partner.getMerchantLimit()!=null){
                merchantLimit=partner.getMerchantLimit();
            }
            excelRow.createCell(3).setCellValue( partnerBindMerchant+"/"+merchantLimit);

            int partnerBindUser = 0;
            Long userLimit=0l;
            partnerBindUser = partnerRepository.findPartnerBindUserCount(partner.getId());
            if(partner.getUserLimit()!=null){
                userLimit=partner.getUserLimit();
            }
            excelRow.createCell(4).setCellValue( partnerBindUser +"/"+userLimit);

            long partnerWalletAvailableBalance = 0;
            long partnerWalletTotalMoney = 0;
            long partnerWalletOnlineAvailableBalance = 0;
            long partnerWalletOnlineTotalMoney = 0;
            PartnerWallet partnerWallet=partnerWalletRepository.findByPartner(partner);
            PartnerWalletOnline partnerWalletOnline=partnerWalletOnlineRepository.findByPartner(partner);

            partnerWalletAvailableBalance=partnerWallet.getAvailableBalance();
            partnerWalletTotalMoney=partnerWallet.getTotalMoney();
            partnerWalletOnlineAvailableBalance=partnerWalletOnline.getAvailableBalance();
            partnerWalletOnlineTotalMoney=partnerWalletOnline.getTotalMoney();

            excelRow.createCell(5).setCellValue( (partnerWalletAvailableBalance+partnerWalletOnlineAvailableBalance)/100.0);
            excelRow.createCell(6).setCellValue( (partnerWalletTotalMoney+partnerWalletOnlineTotalMoney)/100.0);
            excelRow.createCell(7).setCellValue( partnerWalletOnlineTotalMoney/100.0);
            excelRow.createCell(8).setCellValue( partnerWalletTotalMoney/100.0);
            if(partner.getOrigin()!=null){
                if(partner.getOrigin()==0){
                    excelRow.createCell(9).setCellValue( "后台添加");
                }else if(partner.getOrigin()==1){
                    excelRow.createCell(9).setCellValue( "公众号注册");
                }else {
                    excelRow.createCell(9).setCellValue( "--");
                }
            }else {
                excelRow.createCell(9).setCellValue( "--");
            }

            if(partner.getRegisterDate()!=null){
                excelRow.createCell(10).setCellValue(sdf.format(partner.getRegisterDate()));
            }else {
                excelRow.createCell(10).setCellValue( "--");
            }
            if(partner.getWeiXinUser()!=null){
                WeiXinUser weiXinUser=partner.getWeiXinUser();
                if(weiXinUser.getLeJiaUser()!=null){
                    Long id=weiXinUser.getId();
                  String  leJiaUserSid=leJiaUserRepository.findUserByWeiXinId(id);
                    excelRow.createCell(11).setCellValue(leJiaUserSid);
                }else {
                    excelRow.createCell(11).setCellValue( "--");
                }
            }else {
                excelRow.createCell(11).setCellValue( "--");
            }

        }
    }

}
