package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.service.MerchantUserLedgerService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * 易宝子商户
 * Created by zhangwen on 2017/7/13.
 */
@RestController
@RequestMapping("/manage/ledger")
public class MerchantUserLedgerController {

  @Inject
  private MerchantUserLedgerService merchantUserLedgerService;

  @Inject
  private MerchantUserService merchantUserService;

  /**
   * 跳转到易宝子商户编辑页面  2017/7/13
   *
   * @param merchantUserId 乐加商户ID
   * @param ledgerId       易宝子商户ID
   */
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public ModelAndView edit(Model model, @RequestParam Long merchantUserId,
                           @RequestParam Long ledgerId) {

    model.addAttribute("m", merchantUserService.findById(merchantUserId));
    model.addAttribute("ledgerId", ledgerId);
    if (ledgerId != null && ledgerId != 0) {
      model.addAttribute("l", merchantUserLedgerService.findById(ledgerId));
    }

    return MvUtil.go("/yibao/ledger/edit");
  }

  /**
   * 易宝子商户编辑提交  2017/7/14
   *
   * @param merchantUserLedger 易宝子商户
   */
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public LejiaResult edit(@RequestBody MerchantUserLedger merchantUserLedger) {

    merchantUserLedgerService.edit(merchantUserLedger);
    return LejiaResult.ok();
  }

}
