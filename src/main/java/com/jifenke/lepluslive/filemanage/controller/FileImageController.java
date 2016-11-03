package com.jifenke.lepluslive.filemanage.controller;

import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.util.ImageLoad;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;
import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.order.service.PosOrderService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wcg on 16/3/10.
 */
@RestController
@RequestMapping("/manage")
public class FileImageController {

  private final Logger LOG = LoggerFactory.getLogger(FileImageController.class);

  @Inject
  private FileImageService fileImageService;

  @Inject
  private PosOrderService posOrderService;

  @Inject
  private OffLineOrderService offLineOrderService;

  @RequestMapping(value = "/file/saveImage")
  public
  @ResponseBody
  LejiaResult saveImage(@RequestParam("file") MultipartFile filedata, HttpServletRequest request,
                        HttpServletResponse response) {
    String oriName = filedata.getOriginalFilename();
    String extendName = MvUtil.getExtendedName(oriName);
    String filePath = MvUtil.getFilePath(extendName);
    try {
      fileImageService.saveImage(filedata, filePath);
      return LejiaResult.ok(filePath);
    } catch (IOException e) {
      LOG.error("图片上传失败" + e.getMessage());
      return LejiaResult.build(500, "上传失败");
    }

  }

  @RequestMapping(value = "/file/saveProductDetail")
  public
  @ResponseBody
  LejiaResult saveProductDetail(@RequestParam("file") MultipartFile filedata,
                                HttpServletRequest request, HttpServletResponse response) {
    String oriName = filedata.getOriginalFilename();
    String extendName = MvUtil.getExtendedName(oriName);
    String filePath = MvUtil.getFilePath(extendName);
    try {
      fileImageService.saveImage(filedata, filePath);
      return LejiaResult.ok(filePath);
    } catch (IOException e) {
      LOG.error("图片上传失败" + e.getMessage());
      return LejiaResult.build(500, "上传失败");
    }

  }


  @RequestMapping(value = "/file/downloadPicture")
  public void getPhotoById(String url, HttpServletResponse response) {
    response.setContentType("application/x-msdownload;");
    response.setHeader("Content-disposition", "attachment; filename=image.jpg");
    response.setCharacterEncoding("UTF-8");
    OutputStream outputSream = null;
    try {
      outputSream = response.getOutputStream();
      InputStream in = ImageLoad.returnStream(url);
      int len = 0;
      byte[] buf = new byte[1024];
      while ((len = in.read(buf, 0, 1024)) != -1) {
        outputSream.write(buf, 0, len);
      }
      outputSream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/file/pos_excel_handle")
  public void posExcelUploadAndHandle(@RequestParam MultipartFile file, @RequestParam String path,
                                      @RequestParam String verify) {
    String name = file.getOriginalFilename();
    try {
      PosDailyBill posDailyBill = fileImageService.saveExcel(file, name);
      HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
      Set<Merchant> set = new HashSet<Merchant>();
      for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
        if (hssfSheet == null) {
          continue;
        }
        for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
          HSSFRow hssfRow = hssfSheet.getRow(rowNum);
          if (hssfRow != null) {
            String posId = getValue(hssfRow.getCell(3));
            String orderSid = getValue(hssfRow.getCell(6));
            String paidMoney = getValue(hssfRow.getCell(7));
            String transferMoney = getValue(hssfRow.getCell(8));
            String paidResult = getValue(hssfRow.getCell(9));
            String completeDate = getValue(hssfRow.getCell(11));
            Merchant merchant = posOrderService
                .checkOrder(posId, orderSid, paidMoney, transferMoney, paidResult, completeDate,
                            posDailyBill);
            if (merchant != null) {
              set.add(merchant);
            }
          }
        }
      }
      if (set.size() != 0) {//代表有冲突订单，对有冲突订单对商户生成对账单差错单
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        Date end = calendar.getTime();
        set.forEach(merchant -> {
          Optional<FinancialStatistic>
              optional =
              offLineOrderService.findFinancialByMerchantAndDate(merchant, end);
          if(optional.isPresent()){
            offLineOrderService.createFinancialRevise(optional.get(),merchant);
          }
        });
      }
    } catch (Exception e) {
      LOG.error("文件上传失败" + e.getMessage());
      try {
        BufferedOutputStream
            buf =
            new BufferedOutputStream(new FileOutputStream("/app/logs" + name));
        buf.write(file.getBytes());
        buf.flush();
        buf.close();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
  }

  @SuppressWarnings("static-access")
  private String getValue(HSSFCell hssfCell) {
    if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
      return String.valueOf(hssfCell.getBooleanCellValue());
    } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
      return String.valueOf(hssfCell.getNumericCellValue());
    } else {
      return String.valueOf(hssfCell.getStringCellValue());
    }
  }

}
