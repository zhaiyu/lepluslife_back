package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantDetail;

import com.jifenke.lepluslive.merchant.service.MerchantDetailService;


import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.inject.Inject;

/**
 * 商家详情图 Created by zhangwen on 16/9/2.
 */
@RestController
@RequestMapping("/manage/merchant")
public class MerchantDetailController {

  @Inject
  private MerchantDetailService detailService;


  //获取某个商家的某个详情图  06/09/03
  @RequestMapping(value = "/findDetail/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult findProductType(@PathVariable Long id) {
    return LejiaResult.ok(detailService.findDetailPictureById(id));
  }

  //新建或修改 06/09/03
  @RequestMapping(value = "/detailPicture", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult save(@RequestBody MerchantDetail merchantDetail) {
    detailService.editDetailPicture(merchantDetail);
    return LejiaResult.build(200, "修改成功");
  }

  //异步获取详情图列表 06/09/03
  @RequestMapping(value = "/detailList", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult ajaxList(@RequestParam Integer offset, @RequestParam Long merchantId) {

    if (offset != null) { //当前页码
      offset = 1;
    }
    Page page = detailService.findDetailPicByPage(offset, merchantId);

    return LejiaResult.ok(page);
  }

  //删除某个详情图  06/09/03
  @RequestMapping(value = "/delDetail/{id}", method = RequestMethod.DELETE)
  public
  @ResponseBody
  LejiaResult delDetailPicture(@PathVariable Long id) {
    detailService.deleteDetailPicture(id);
    return LejiaResult.build(200, "删除成功");
  }

}
