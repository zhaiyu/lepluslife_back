package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;

import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.user.service.WeiXinUserService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 商户邀请码 Created by zhangwen on 16/9/6.
 */
@RestController
@RequestMapping("/manage/merchant")
public class MerchantInfoController {

  @Inject
  private MerchantService merchantService;

  @Inject
  private WeiXinUserService weiXinUserService;

  //商户邀请码列表页  16/09/07
  @RequestMapping(value = "codePage", method = RequestMethod.GET)
  public ModelAndView goMerchantCodePage() {
    return MvUtil.go("/activity/merchantCodeList");
  }

  //商户邀请码列表页统计数据  16/09/07
  @RequestMapping(value = "/ajaxTotalData", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult getTotalData() {
    //获取所有商户锁定会员，邀请粉丝，会员，红包，佣金
    Map map = weiXinUserService.getTotalData();
    return LejiaResult.ok(map);
  }

  //商户邀请码列表页数据  16/09/06
  @RequestMapping(value = "/ajaxCodeList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getMerchants(@RequestBody MerchantCriteria merchantCriteria) {
    if (merchantCriteria.getOffset() == null) {
      merchantCriteria.setOffset(1);
    }
    Page page = merchantService.findMerchantInfoByPage(merchantCriteria);
    //获取每个商户锁定会员，邀请粉丝，会员，红包，佣金，订单
    List<Merchant> merchants = page.getContent();
    List list = merchantService.findMerchantCodeData(merchants);
    return LejiaResult.build(200, page.getTotalPages() + "_" + page.getTotalElements(), list);
  }

  //创建商户邀请码(永久二维码)  16/09/06
  @RequestMapping(value = "/createQrCode/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult createQrCode(@PathVariable Long id) {
    try {
      int status = merchantService.createQrCode(id);
      return LejiaResult.build(status, "ok");
    } catch (Exception e) {
      return LejiaResult.build(500, "服务器异常");
    }
  }

}
