package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.yibao.service.MerchantUserLedgerService;

import org.springframework.ui.Model;
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
  private MerchantUserService merchantUserServicel;

  /**
   * 跳转到易宝子商户编辑页面  2017/7/13
   *
   * @param merchantUserId 乐加商户ID
   * @param ledgerId       易宝子商户ID
   */
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public ModelAndView edit(Model model, @RequestParam Long merchantUserId,
                           @RequestParam Long ledgerId) {

    model.addAttribute("m", merchantUserServicel.findById(merchantUserId));
    if (ledgerId != null && ledgerId != 0) {
      model.addAttribute("ledger", merchantUserLedgerService.findById(ledgerId));
    }

    return MvUtil.go("/yibao/ledger/edit");
  }

}
