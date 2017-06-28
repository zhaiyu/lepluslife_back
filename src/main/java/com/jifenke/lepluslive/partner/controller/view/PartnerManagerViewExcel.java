package com.jifenke.lepluslive.partner.controller.view;

import com.jifenke.lepluslive.partner.domain.entities.PartnerManager;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManagerWallet;
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
public class PartnerManagerViewExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        List<PartnerManagerWallet> partnerManagerWalletList = (List<PartnerManagerWallet>) map.get("partnerManagerWalletList");
        setExcelRows(sheet, partnerManagerWalletList);
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
        excelHeader.createCell(1).setCellValue("合伙人名称");
        excelHeader.createCell(2).setCellValue("账户余额");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<PartnerManagerWallet> partnerManagerWalletList) {
        int record = 1;
        for (PartnerManagerWallet partnerManagerWallet : partnerManagerWalletList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(partnerManagerWallet.getPartnerManager().getId());
            excelRow.createCell(1).setCellValue(partnerManagerWallet.getPartnerManager().getName());
            excelRow.createCell(2).setCellValue(partnerManagerWallet.getAvailableBalance()/100.0);
        }
    }

}
