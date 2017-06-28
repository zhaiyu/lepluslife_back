package com.jifenke.lepluslive.sales.controller.view;

import com.jifenke.lepluslive.partner.domain.entities.PartnerManagerWallet;
import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;
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
public class SalesStaffViewExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        List<SalesStaff> salesStaffList = (List<SalesStaff>) map.get("salesStaffList");
        setExcelRows(sheet, salesStaffList);
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
        excelHeader.createCell(0).setCellValue("销售人员编号");
        excelHeader.createCell(1).setCellValue("姓名");
        excelHeader.createCell(2).setCellValue("负责商户数量");
    }
    public void setExcelRows(HSSFSheet excelSheet, List<SalesStaff> salesStaffList) {
        int record = 1;
        for (SalesStaff salesStaff : salesStaffList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(salesStaff.getId());
            excelRow.createCell(1).setCellValue(salesStaff.getName());
            excelRow.createCell(2).setCellValue(salesStaff.getMerchant().size());
        }
    }

}
