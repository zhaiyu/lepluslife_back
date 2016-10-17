package com.jifenke.lepluslive.partner.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.partner.domain.criteria.PartnerRechargeCriteria;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerRecharge;
import com.jifenke.lepluslive.partner.service.PartnerRechargeService;
import com.jifenke.lepluslive.partner.service.PartnerService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by xf on 2016/10/9.
 * 合伙人充值 Controller
 */
@RestController
@RequestMapping("/manage")
public class PartnerRechargeController {

  @Inject
  private PartnerService partnerService;

  @Inject
  private PartnerRechargeService partnerRechargeService;

  /**
   * 新增合伙人充值请求
   */
  @RequestMapping(value = "/partner/recharge/save", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult savePartnerRecharge(@RequestBody PartnerRecharge partnerRecharge) {
    try {
      Partner
          partner =
          partnerService.findPartnerById(4L);
      // .findByPartnerSid(SecurityUtils.getCurrentUserLogin());
      partnerRecharge.setPartnerPhoneNumber(partner.getPhoneNumber());
      partnerRecharge.setPartner(partner);
      partnerRechargeService.saveParterRecharge(partnerRecharge);
      return LejiaResult.ok("您的充值申请已提交, 乐加客服会尽快联系您 !");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(400, "系统异常,请与客服联系!");
    }
  }

  /**
   * 审核通过充值请求 :   -----> TODO
   */
  @RequestMapping(value = "/partner/recharge/doChcek", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult checkPartnerRecharge(@RequestBody PartnerRecharge partnerRecharge) {
    try {
      if (partnerRecharge.getRechargeState() == 1) {                 //  审核
        partnerRechargeService.doCheck(partnerRecharge);
        return LejiaResult.ok("该充值申请已审核通过 !");
      } else {
        throw new RuntimeException();
      }
    } catch (Exception e) {
      return LejiaResult.build(400, "操作异常,请与管理员联系!");
    }
  }

  /**
   * 驳回充值请求
   */
  @RequestMapping(value = "/partner/recharge/doReject", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult rejectPartnerRecharge(@RequestBody PartnerRecharge partnerRecharge) {
    try {
      if (partnerRecharge.getRechargeState() == 2) {           //  驳回
        partnerRechargeService.doReject(partnerRecharge);
        return LejiaResult.ok("该充值申请已被驳回 !");
      } else {
        throw new RuntimeException();
      }
    } catch (Exception e) {
      return LejiaResult.build(400, "操作异常,请与管理员联系!");
    }
  }


  /**
   * 查询所有的充值请求
   */
  @RequestMapping("/partner/recharge/findByPartner")
  @ResponseBody
  public LejiaResult findByPage(@RequestBody PartnerRechargeCriteria criteria) {
    Page<PartnerRecharge> page = partnerRechargeService.findByCriteria(criteria,10);
    return  LejiaResult.ok(page);
  }

}
