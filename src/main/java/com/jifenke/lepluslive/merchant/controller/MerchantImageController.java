package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.controller.dto.BussineLicenseImgDto;
import com.jifenke.lepluslive.merchant.controller.dto.BussineProtocalDto;
import com.jifenke.lepluslive.merchant.service.MerchantImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zxf on 2016/9/9. 图片文件 Controller
 */
@RestController
@RequestMapping("/manage")
public class MerchantImageController {

  private final Logger LOG = LoggerFactory.getLogger(MerchantImageController.class);

  @Inject
  private MerchantImageService merchantImageService;

  @RequestMapping("/merchant/saveImage")
  @ResponseBody
  public LejiaResult saveImage(@RequestParam("file") MultipartFile fileData,
                               HttpServletRequest request, HttpServletResponse response) {
    try {
      //  获取文件名和文件路径
      String oriName = fileData.getOriginalFilename();
      String extendName = MvUtil.getExtendedName(oriName);
      String filePath = MvUtil.getFilePath(extendName);
      //  获取文件参数
      Integer type = new Integer(request.getParameter("type"));
      Long merchantId = new Long(request.getParameter("merchantId"));
      Long imgType = new Long(request.getParameter("imgType"));
      Long index = new Long(request.getParameter("index"));
      merchantImageService.saveImage(fileData, filePath, type, imgType, merchantId, index);
      return LejiaResult.ok(filePath);
    } catch (Exception e) {
      LOG.error("图片上传失败: " + e.getMessage());
      return LejiaResult.build(500, "图片上传失败!");
    }
  }

  @RequestMapping(value = "/merchant/loadProtImg", method = RequestMethod.GET)
  @ResponseBody
  public BussineProtocalDto loadProtImg(Long merchantId) {
    return merchantImageService.getProtImg(merchantId);
  }

  @RequestMapping(value = "/merchant/loadLiceImg", method = RequestMethod.GET)
  @ResponseBody
  public BussineLicenseImgDto loadLiceImg(Long merchantId) {
    return merchantImageService.getLiceImg(merchantId);
  }

}

