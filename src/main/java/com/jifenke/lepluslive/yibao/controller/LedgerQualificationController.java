package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerQualification;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.service.LedgerQualificationService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zhangwen on 2017/7/18.
 */
@RestController
@RequestMapping("/manage/qualification")
public class LedgerQualificationController {

  @Inject
  private LedgerQualificationService ledgerQualificationService;

  /**
   * 跳转到易宝子商户资质上传页面  2017/7/18
   *
   * @param ledgerId 易宝子商户ID
   */
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public ModelAndView edit(Model model, @RequestParam Long ledgerId) {
    MerchantUserLedger ledger = new MerchantUserLedger();
    ledger.setId(ledgerId);
    LedgerQualification
        qualification =
        ledgerQualificationService.findByMerchantUserLedger(ledger);
    model.addAttribute("q", qualification);

    return MvUtil.go("/yibao/qualification/edit");
  }

  /**
   * 分账方资质上传 2017/7/18
   *
   * @param qualificationId 易宝子商户资质记录ID
   * @param picPath         图片保存完整路径
   * @param type            图片所属类型在易宝上传接口fileType数组属性(从0开始)
   */
  @RequestMapping(value = "/uploadQualification", method = RequestMethod.POST)
  public Map uploadQualification(@RequestParam Long qualificationId, @RequestParam String picPath,
                                 @RequestParam Integer type) {
    return ledgerQualificationService.uploadQualification(qualificationId, picPath, type);
  }

}
