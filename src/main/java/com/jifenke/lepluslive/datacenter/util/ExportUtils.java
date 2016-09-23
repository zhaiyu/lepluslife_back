package com.jifenke.lepluslive.datacenter.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Created by xf on 2016/9/22.
 */
public class ExportUtils {

  private HSSFWorkbook workbook;
  private HSSFSheet sheet;

  public ExportUtils(HSSFWorkbook workbook, HSSFSheet sheet) {
    this.sheet = sheet;
    this.workbook = workbook;
  }

  /**
   *  设置表头样式
   */
  public HSSFCellStyle getHeadStyle() {
    // 创建表格样式
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    // 设置表格样式为淡蓝色
    cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    // 设置水平居中
    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    // 设置垂直居中
    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    // 设置字体样式
    HSSFFont font = workbook.createFont();
    font.setFontName("宋体");
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeight((short)300);
    cellStyle.setFont(font);
    // 设置线条样式
    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    return cellStyle;
  }


  /**
   *  设置表体样式
   */
  public HSSFCellStyle getBodyStyle() {
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    // 背景颜色
    // 居中对齐
    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    // 字体样式
    HSSFFont font = workbook.createFont();
    font.setFontName("微软雅黑");
    font.setFontHeight((short)200);
    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
    cellStyle.setFont(font);
    // 线条
    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    cellStyle.setLeftBorderColor(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    return cellStyle;
  }


}
