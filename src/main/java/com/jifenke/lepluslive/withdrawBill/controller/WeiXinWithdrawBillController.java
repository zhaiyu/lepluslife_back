package com.jifenke.lepluslive.withdrawBill.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnline;
import com.jifenke.lepluslive.partner.service.PartnerService;
import com.jifenke.lepluslive.partner.service.PartnerWalletOnlineService;
import com.jifenke.lepluslive.partner.service.PartnerWalletService;
import com.jifenke.lepluslive.withdrawBill.controller.view.WeiXinWithdrawBillExcel;
import com.jifenke.lepluslive.withdrawBill.domain.criteria.WeiXinWithdrawBillCriteria;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WeiXinWithdrawBill;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WithdrawBill;
import com.jifenke.lepluslive.withdrawBill.service.WeiXinWithdrawBillService;
import com.jifenke.lepluslive.withdrawBill.service.WithdrawBillService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lss on 2017/5/15.
 */
@RestController
@RequestMapping("/manage/weiXinWithdrawBill")
public class WeiXinWithdrawBillController {
    @Inject
    private WeiXinWithdrawBillService weiXinWithdrawBillService;

    @Inject
    private WeiXinWithdrawBillExcel weiXinWithdrawBillExcel;

    @Inject
    private PartnerService partnerService;

    @Inject
    private PartnerWalletOnlineService partnerWalletOnlineService;

    @Inject
    private PartnerWalletService partnerWalletService;

    @Inject
    private WithdrawBillService withdrawBillService;


    @RequestMapping("/weiXinWithdrawBillList")
    public ModelAndView weiXinWithdrawBillList() {
        return MvUtil.go("/withdraw/weiXinWithdrawBillList");
    }


    @RequestMapping(value = "/getWeiXinWithdrawBillByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getWeiXinWithdrawBillByAjax(@RequestBody WeiXinWithdrawBillCriteria weiXinWithdrawBillCriteria) {
        Page page = weiXinWithdrawBillService.findWeiXinWithdrawBillByPage(weiXinWithdrawBillCriteria, 10);
        if (weiXinWithdrawBillCriteria.getOffset() == null) {
            weiXinWithdrawBillCriteria.setOffset(1);
        }
        return LejiaResult.ok(page);
    }


    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public ModelAndView exportExcel(WeiXinWithdrawBillCriteria weiXinWithdrawBillCriteria) {
        if (weiXinWithdrawBillCriteria.getOffset() == null) {
            weiXinWithdrawBillCriteria.setOffset(1);
        }
        Page page = weiXinWithdrawBillService.findWeiXinWithdrawBillByPage(weiXinWithdrawBillCriteria, 10000);
        Map map = new HashMap();
        map.put("weiXinWithdrawBillList", page.getContent());
        return new ModelAndView(weiXinWithdrawBillExcel, map);
    }


    @RequestMapping(value = "/getOneWeiXinWithdrawBill/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult reject(@PathVariable Long id) {

        WeiXinWithdrawBill weiXinWithdrawBill = weiXinWithdrawBillService.findById(id);
        return LejiaResult.ok(weiXinWithdrawBill);
    }


    @RequestMapping(value = "/rejectConfirm/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult rejectConfirm(@PathVariable Long id) {
        WeiXinWithdrawBill weiXinWithdrawBill=weiXinWithdrawBillService.findById(id);
        if(weiXinWithdrawBill.getState()==0){
            try {
                weiXinWithdrawBillService.rejectConfirm(id);
            } catch (Exception e) {
                return LejiaResult.build(500, "error");
            }
            return LejiaResult.ok();

        }else {
            return LejiaResult.build(500, "error");
        }

    }

    @RequestMapping(value = "/withdrawConfirm/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult withdrawConfirm(@PathVariable Long id) {
        WeiXinWithdrawBill weiXinWithdrawBill=weiXinWithdrawBillService.findById(id);
        if(weiXinWithdrawBill.getState()==0){
            try {
                Map<String, String> map = weiXinWithdrawBillService.withdraw(id);
                if ("SUCCESS".equals(map.get("result_code").toString()) && "SUCCESS".equals(map.get(
                        "return_code").toString())) {
                    return LejiaResult.ok(map);
                } else {
                    return LejiaResult.build(500, "error", map);
                }
            } catch (Exception e) {
                return LejiaResult.build(500, "serverError");
            }
        }else {
            return LejiaResult.build(500, "serverError");
        }



    }


    @RequestMapping(value = "/shareDetailsPage/{id}", method = RequestMethod.GET)
    public ModelAndView shareDetailsPage(Model model, @PathVariable Long id) {
        Partner partner = partnerService.findPartnerById(id);
        model.addAttribute("partner", partner);
        model.addAttribute("goBackUrl", "/manage/weiXinWithdrawBill/weiXinWithdrawBillList");
        PartnerWalletOnline partnerWalletOnline =partnerWalletOnlineService.findByPartner(partner);
        PartnerWallet partnerWallet=partnerWalletService.findByPartner(partner);
        model.addAttribute("partnerWalletOnline", partnerWalletOnline);
        model.addAttribute("partnerWallet", partnerWallet);
        Long weiXinWithdrawBillOnWithdrawal=weiXinWithdrawBillService.findPartnerOnWithdrawalByPartnerId(partner.getId());
        Long withdrawBillOnWithdrawal=withdrawBillService.findPartnerOnWithdrawalByPartnerId(partner.getId());
        if(weiXinWithdrawBillOnWithdrawal==null){
            weiXinWithdrawBillOnWithdrawal=0l;
        }
        if(withdrawBillOnWithdrawal==null){
            withdrawBillOnWithdrawal=0l;
        }
        Long onWithdrawal=weiXinWithdrawBillOnWithdrawal+withdrawBillOnWithdrawal;
        model.addAttribute("onWithdrawal", onWithdrawal);
        return MvUtil.go("/withdraw/weiXinWithdrawBillDetails");
    }


}
