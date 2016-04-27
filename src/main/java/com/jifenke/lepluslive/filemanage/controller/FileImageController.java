package com.jifenke.lepluslive.filemanage.controller;

import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

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


  //获取文件后缀名


    @RequestMapping(value = "/file/saveImage")
    public @ResponseBody LejiaResult saveImage( @RequestParam("file") MultipartFile filedata,HttpServletRequest request,HttpServletResponse response) {
      String oriName =  filedata.getOriginalFilename();
      String extendName = MvUtil.getExtendedName(oriName);
      String filePath = MvUtil.getFilePath(extendName);
      try {
        fileImageService.saveImage(filedata, filePath);
        return LejiaResult.ok(filePath);
      } catch (IOException e) {
        LOG.error("图片上传失败"+e.getMessage());
        return LejiaResult.build(500,"上传失败");
    }

  }

  @RequestMapping(value = "/file/saveProductDetail")
  public @ResponseBody LejiaResult saveProductDetail( @RequestParam("file") MultipartFile filedata,HttpServletRequest request,HttpServletResponse response) {
    String oriName =  filedata.getOriginalFilename();
    String extendName = MvUtil.getExtendedName(oriName);
    String filePath = MvUtil.getFilePath(extendName);
    try {
      fileImageService.saveImage(filedata, filePath);
//        BufferedImage buff = ImageIO.read(filedata.getInputStream());
//        new ProductD
//        buff.getHeight();
//        buff.getWidth();


      return LejiaResult.ok(filePath);
    } catch (IOException e) {
      LOG.error("图片上传失败"+e.getMessage());
      return LejiaResult.build(500,"上传失败");
    }

  }


}
