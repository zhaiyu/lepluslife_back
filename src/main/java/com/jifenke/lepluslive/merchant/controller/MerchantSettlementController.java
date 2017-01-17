package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlement;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.MerchantSettlementService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import javax.inject.Inject;

/**
 * Created by zhangwen on 2017/1/9.
 */
@RestController
@RequestMapping("/manage/m_settlement")
public class MerchantSettlementController {

  @Inject
  private MerchantSettlementService service;

  @Inject
  private MerchantUserService merchantUserService;

  /**
   * 根据商户ID获取商户号列表信息  2016/01/09
   *
   * @param id 商户ID
   */
  @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
  public LejiaResult merchantList(@PathVariable Long id) {
    return LejiaResult.ok(service.findByMerchantUserId(id));
  }

  /**
   * 跳转到商户号编辑或创建页面  2016/01/09
   *
   * @param id             商户号ID
   * @param merchantUserId 商户ID
   */
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public ModelAndView information(@RequestParam Long merchantUserId,
                                  @RequestParam(required = false) Long id, Model model) {
    if (id != null) {
      model.addAttribute("m_number", service.findById(id));
    }
    model.addAttribute("m_user", merchantUserService.findById(merchantUserId));
    return MvUtil.go("/merchantUser/editMerchantNum");
  }

  /**
   * 新建或修改商户号  2017/01/10
   *
   * @param merchantSettlement 商户号信息
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public LejiaResult merchantList(@RequestBody MerchantSettlement merchantSettlement) {
    try {
      String msg = service.save(merchantSettlement);
      if (!"200".equals(msg)) {
        return LejiaResult.build(500, msg);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "save error");
    }
    return LejiaResult.ok();
  }

}
