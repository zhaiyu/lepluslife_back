package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPosImage;
import com.jifenke.lepluslive.merchant.service.MerchantPosImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xf on 2016/9/9. 图片文件 Controller
 */
@RestController
@RequestMapping("/manage")
public class MerchantImageController {

  private final Logger LOG = LoggerFactory.getLogger(MerchantImageController.class);

  @Inject
  private MerchantPosImageService merchantImageService;

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
      Long merchantId = new Long(request.getParameter("merchantId"));
      String fieldName = request.getParameter("fieldName");
      merchantImageService.saveImage(fileData, filePath, merchantId, fieldName);
      return LejiaResult.ok(filePath);
    } catch (Exception e) {
      LOG.error("图片上传失败: " + e.getMessage());
      return LejiaResult.build(500, "图片上传失败!");
    }
  }

  @RequestMapping(value = "/merchant/loadPosImg", method = RequestMethod.GET)
  @ResponseBody
  public MerchantPosImage loadProtImg(Long merchantId) {
    return merchantImageService.findByMerchant(merchantId);
  }


}

