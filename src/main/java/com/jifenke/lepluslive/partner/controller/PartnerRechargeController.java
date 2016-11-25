package com.jifenke.lepluslive.partner.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.partner.domain.criteria.PartnerRechargeCriteria;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerRecharge;
import com.jifenke.lepluslive.partner.domain.entities.PartnerScoreLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.service.PartnerRechargeService;
import com.jifenke.lepluslive.partner.service.PartnerScoreLogService;
import com.jifenke.lepluslive.partner.service.PartnerService;
import com.jifenke.lepluslive.partner.service.PartnerWalletService;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

  @Inject
  private PartnerWalletService partnerWalletService;

  @Inject
  private PartnerScoreLogService partnerScoreLogService;




  /**
   * 合伙人充值页
   */
  @RequestMapping(value = "/partnerFillingBill", method = RequestMethod.GET)
  public ModelAndView goPartnerEditPage(Model model) {
    return MvUtil.go("/partner/parterFillingBillPage");
  }

  /**
   *充值回显的ajax
   */
  @RequestMapping(value = "/partner/recharge/findById", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult findById(@RequestBody String fillId) {
    try {
        JSONObject jasonObject = new JSONObject(fillId);
        Long id=Long.parseLong(jasonObject.get("fillId").toString());
        PartnerRecharge partnerRecharge=partnerRechargeService.findById(id);
        return LejiaResult.ok(partnerRecharge);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(400, "系统异常,请与客服联系!");
    }
  }



  /**
   * 充值
   */
  @RequestMapping(value = "/partner/recharge/save", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult savePartnerRecharge(@RequestBody String idAndRechargeRemark) {
    try {
        JSONObject jasonObject = new JSONObject(idAndRechargeRemark);
        Long fillId=Long.parseLong(jasonObject.get("fillId").toString());
        String rechargeRemark=jasonObject.get("rechargeRemark").toString();
        PartnerRecharge partnerRecharge=partnerRechargeService.findById(fillId);
        if(partnerRecharge.getRechargeState()==0) {
            Partner partner = partnerRecharge.getPartner();
            PartnerWallet PartnerWallet = partnerWalletService.findOne(partner.getId());
            Long availableScoreA = PartnerWallet.getAvailableScoreA();
            Long availableScoreB = PartnerWallet.getAvailableScoreB();
            Long totalScoreA = PartnerWallet.getTotalScoreA();
            Long totalScoreB = PartnerWallet.getTotalScoreB();
            Long scorea = partnerRecharge.getScorea();
            Long scoreb = partnerRecharge.getScoreb();
            availableScoreA = availableScoreA + scorea;
            availableScoreB = availableScoreB + scoreb;
            totalScoreA = totalScoreA + scorea;
            totalScoreB = totalScoreB + scoreb;
            PartnerWallet.setAvailableScoreA(availableScoreA);
            PartnerWallet.setAvailableScoreB(availableScoreB);
            PartnerWallet.setTotalScoreA(totalScoreA);
            PartnerWallet.setTotalScoreB(totalScoreB);
            partnerWalletService.savePartnerWallet(PartnerWallet);

            partnerRecharge.setRemark(rechargeRemark);
            partnerRecharge.setRechargeState(1);
            partnerRechargeService.saveParterRecharge(partnerRecharge);

            if (scorea != 0) {
                PartnerScoreLog partnerScoreLog = new PartnerScoreLog();
                partnerScoreLog.setCreateDate(partnerRecharge.getCreateTime());
                partnerScoreLog.setPartnerId(partnerRecharge.getPartner().getId());
                partnerScoreLog.setDescription("合伙人充值");
                partnerScoreLog.setType(1);
                partnerScoreLog.setNumber(scorea);
                partnerScoreLog.setScoreAOrigin(2);
                partnerScoreLogService.savePartnerScoreLog(partnerScoreLog);
            }
            if (scoreb != 0) {
                PartnerScoreLog partnerScoreLog = new PartnerScoreLog();
                partnerScoreLog.setCreateDate(partnerRecharge.getCreateTime());
                partnerScoreLog.setPartnerId(partnerRecharge.getPartner().getId());
                partnerScoreLog.setDescription("合伙人充值");
                partnerScoreLog.setType(0);
                partnerScoreLog.setNumber(scoreb);
                partnerScoreLog.setScoreBOrigin(2);
                partnerScoreLogService.savePartnerScoreLog(partnerScoreLog);
            }
        }
      return LejiaResult.ok();
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(400, "系统异常,请与客服联系!");
    }
  }


    /**
     *驳回
     */
    @RequestMapping(value = "/partner/reject/save", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult savePartnerReject(@RequestBody String idAndRejectRemark) {
        System.out.println(idAndRejectRemark);
        try{
            JSONObject jasonObject = new JSONObject(idAndRejectRemark);
            Long rjId=Long.parseLong(jasonObject.get("rjId").toString());
            String rejectRemark=jasonObject.get("rejectRemark").toString();
            PartnerRecharge partnerRecharge=partnerRechargeService.findById(rjId);
            if(partnerRecharge.getRechargeState()==0){
                partnerRecharge.setRechargeState(2);
                partnerRecharge.setRemark(rejectRemark);
                partnerRechargeService.saveParterRecharge(partnerRecharge);
            }
        }catch (Exception e){

        }

        return LejiaResult.ok();
    }

  /**
   * 查询所有的充值请求
   */
  @RequestMapping("/partner/recharge/findByPage")
  @ResponseBody
  public LejiaResult findByPage(@RequestBody PartnerRechargeCriteria criteria) {
    Page<PartnerRecharge> page = partnerRechargeService.findByCriteria(criteria,10);
    return  LejiaResult.ok(page);
  }

}
