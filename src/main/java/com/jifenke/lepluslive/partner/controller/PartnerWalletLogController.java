package com.jifenke.lepluslive.partner.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.partner.controller.view.PartnerWalletLogExcel;
import com.jifenke.lepluslive.partner.controller.view.PartnerWalletOnlineLogExcel;
import com.jifenke.lepluslive.partner.domain.criteria.PartnerWalletLogCriteria;
import com.jifenke.lepluslive.partner.domain.criteria.PartnerWalletOnlineLogCriteria;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnline;
import com.jifenke.lepluslive.partner.service.*;
import com.jifenke.lepluslive.sMovie.domain.criteria.SMovieOrderCriteria;
import com.jifenke.lepluslive.withdrawBill.domain.criteria.WeiXinWithdrawBillCriteria;
import com.jifenke.lepluslive.withdrawBill.service.WeiXinWithdrawBillService;
import com.jifenke.lepluslive.withdrawBill.service.WithdrawBillService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lss on 2017/5/17.
 */
@RestController
@RequestMapping("/manage/partnerWalletLog")
public class PartnerWalletLogController {

    @Inject
    private PartnerWalletOnlineLogService partnerWalletOnlineLogService;

    @Inject
    private PartnerWalletLogService partnerWalletLogService;

    @Inject
    private PartnerWalletOnlineLogExcel partnerWalletOnlineLogExcel;

    @Inject
    private PartnerWalletLogExcel partnerWalletLogExcel;

    @Inject
    private PartnerService partnerService;

    @Inject
    private PartnerWalletOnlineService partnerWalletOnlineService;

    @Inject
    private PartnerWalletService partnerWalletService;

    @Inject
    private WithdrawBillService withdrawBillService;

    @Inject
    private WeiXinWithdrawBillService weiXinWithdrawBillService;

    @RequestMapping(value = "/getPartnerWalletOnLineLogByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getPartnerWalletOnLineLogByAjax(@RequestBody PartnerWalletOnlineLogCriteria partnerWalletOnlineLogCriteria) {

        if (partnerWalletOnlineLogCriteria.getOffset() == null) {
            partnerWalletOnlineLogCriteria.setOffset(1);
        }
        Page page = partnerWalletOnlineLogService.findByPage(partnerWalletOnlineLogCriteria, 10);
        return LejiaResult.ok(page);
    }


    @RequestMapping(value = "/exportOnLineExcel", method = RequestMethod.POST)
    public ModelAndView exportOnLineExcel(PartnerWalletOnlineLogCriteria partnerWalletOnlineLogCriteria) {
        if (partnerWalletOnlineLogCriteria.getOffset() == null) {
            partnerWalletOnlineLogCriteria.setOffset(1);
        }
        Page page = partnerWalletOnlineLogService.findByPage(partnerWalletOnlineLogCriteria, 10000);
        Map map = new HashMap();
        map.put("partnerWalletOnlineLogList", page.getContent());
        return new ModelAndView(partnerWalletOnlineLogExcel, map);
    }



    @RequestMapping(value = "/getPartnerWalletOffLineLogByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getPartnerWalletOffLineLogByAjax(@RequestBody PartnerWalletLogCriteria partnerWalletLogCriteria) {

        if (partnerWalletLogCriteria.getOffset() == null) {
            partnerWalletLogCriteria.setOffset(1);
        }
        Page page = partnerWalletLogService.findByPage(partnerWalletLogCriteria, 10);
        return LejiaResult.ok(page);
    }



    @RequestMapping(value = "/exportOffLineExcel", method = RequestMethod.POST)
    public ModelAndView exportOffLineExcel( PartnerWalletLogCriteria partnerWalletLogCriteria) {
        if (partnerWalletLogCriteria.getOffset() == null) {
            partnerWalletLogCriteria.setOffset(1);
        }
        Page page = partnerWalletLogService.findByPage(partnerWalletLogCriteria, 10000);
        Map map = new HashMap();
        map.put("partnerWalletOfflineLogList", page.getContent());
        return new ModelAndView(partnerWalletLogExcel, map);
    }


    @RequestMapping(value = "/partnerDetailsPage/{id}", method = RequestMethod.GET)
    public ModelAndView partnerDetailsPage(Model model, @PathVariable Long id) {
        Partner partner = partnerService.findPartnerById(id);
        model.addAttribute("partner", partner);
        model.addAttribute("goBackUrl", "/manage/partner");
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
