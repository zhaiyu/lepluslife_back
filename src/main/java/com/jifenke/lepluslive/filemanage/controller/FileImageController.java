package com.jifenke.lepluslive.filemanage.controller;

import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.util.ImageLoad;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;

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
      fileImageService.saveImage(file, name);
    } catch (IOException e) {
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

}
