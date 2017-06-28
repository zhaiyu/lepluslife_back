package com.jifenke.lepluslive.yinlian.controller.view;

import com.jifenke.lepluslive.yinlian.domain.entities.UnionPayStore;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.Lifecycle;
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
public class UnionPayStoreViewExcel  extends AbstractExcelView {


    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        HSSFSheet sheet = hssfWorkbook.createSheet("list");
        setExcelHeader(sheet);
        List<UnionPayStore> unionPayStoreList= ( List<UnionPayStore>) map.get("unionPayStoreList");
        setExcelRows(sheet, unionPayStoreList);
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
        excelHeader.createCell(0).setCellValue("银商门店号");
        excelHeader.createCell(1).setCellValue("银商门店名称");
        excelHeader.createCell(2).setCellValue("银商门店地址");
        excelHeader.createCell(3).setCellValue("营销联盟商户号");
        excelHeader.createCell(4).setCellValue("拥有终端");
        excelHeader.createCell(5).setCellValue("对应乐加门店");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<UnionPayStore> unionPayStoreList) {

        int record = 1;
        for (UnionPayStore unionPayStore : unionPayStoreList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            excelRow.createCell(0).setCellValue(unionPayStore.getShopNumber());
            excelRow.createCell(1).setCellValue(unionPayStore.getShopName());
            excelRow.createCell(2).setCellValue(unionPayStore.getAddress());
            excelRow.createCell(3).setCellValue(unionPayStore.getMerchantNum());
            excelRow.createCell(4).setCellValue(unionPayStore.getTermNos());
            if(unionPayStore.getMerchant()!=null){
                excelRow.createCell(5).setCellValue(unionPayStore.getMerchant().getMerchantSid());
            }else {
                excelRow.createCell(5).setCellValue("--");
            }
        }
    }

}
