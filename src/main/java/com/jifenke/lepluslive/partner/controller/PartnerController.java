package com.jifenke.lepluslive.partner.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.partner.controller.dto.PartnerDto;
import com.jifenke.lepluslive.partner.controller.view.PartnerViewExcel;
import com.jifenke.lepluslive.partner.domain.criteria.PartnerCriteria;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManager;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnline;
import com.jifenke.lepluslive.partner.service.PartnerManagerService;
import com.jifenke.lepluslive.partner.service.PartnerService;

import com.jifenke.lepluslive.partner.service.PartnerWalletOnlineService;
import com.jifenke.lepluslive.partner.service.PartnerWalletService;
import com.jifenke.lepluslive.sMovie.domain.criteria.SMovieOrderCriteria;
import com.jifenke.lepluslive.sMovie.domain.entities.SMovieProduct;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.service.UserService;
import com.jifenke.lepluslive.user.service.WeiXinUserService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wcg on 16/7/21.
 */
@RestController
@RequestMapping("/manage")
public class PartnerController {

    @Inject
    private PartnerService partnerService;
    @Inject
    private PartnerManagerService partnerManagerService;

    @Inject
    private PartnerWalletService partnerWalletService;

    @Inject
    private PartnerWalletOnlineService partnerWalletOnlineService;

    @Inject
    private PartnerViewExcel partnerViewExcel;

    @Inject
    private UserService userService;


    @RequestMapping(value = "/partner")
    public ModelAndView goPartnerEditPage(Model model) {
//        model.addAttribute("partners", partnerService.findAll());
        model.addAttribute("partnerManagers", partnerService.findAllPartnerManagerByWallet());
        return MvUtil.go("/partner/partnerList");
    }

    @RequestMapping(value = "/partner/editUser")
    public LejiaResult editPartnerPassword(@RequestBody Partner partner, Model model) {
        model.addAttribute("partners", partnerService.findAll());
        partnerService.editPartnerPassword(partner);
        return LejiaResult.ok();
    }

    @RequestMapping(value = "/partner/edit", method = RequestMethod.GET)
    public ModelAndView goCreatePartnertPage(Long id, Model model) {
        if (id != null) {
            model.addAttribute("partnerInfo", partnerService
                    .findPartnerInfoByPartner(partnerService.findPartnerById(id)));
        }
        model.addAttribute("partnerManagers", partnerService.findAllPartnerManager());
        return MvUtil.go("/partner/createPartner");
    }

    @RequestMapping(value = "/partner", method = RequestMethod.POST)
    public LejiaResult createPartner(@RequestBody PartnerDto partnerDto) {
        partnerService.createPartner(partnerDto);
        return LejiaResult.ok();
    }

    @RequestMapping(value = "/partner", method = RequestMethod.PUT)
    public LejiaResult editPartner(@RequestBody PartnerDto partnerDto) {
        partnerService.editPartner(partnerDto);
        return LejiaResult.ok();
    }

    @RequestMapping(value = "/cityPartner/edit", method = RequestMethod.GET)
    public ModelAndView goCreateCityPartnertPage(Long id,Model model) {
        if(id!=null) {
            PartnerManager partnerManager = partnerManagerService.findPartnerManagerById(id);
            model.addAttribute("partnerManager",partnerManager);
        }
        return MvUtil.go("/partner/createCityPartner");
    }

    @RequestMapping(value = "/cityPartner/save", method = RequestMethod.PUT)
    public LejiaResult editCityPartner(@RequestBody PartnerManager partnerManager) {
        partnerService.editCityPartner(partnerManager);
        return LejiaResult.ok();
    }

    @RequestMapping(value="/partner/manager",method = RequestMethod.GET)
    public LejiaResult findPartnerManagerAccountById(Long id) {
        Partner account = partnerService.findPartnerById(id);
        return LejiaResult.ok(account);
    }


    @RequestMapping(value = "/partner/getPartnerByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getPartnerByAjax(@RequestBody PartnerCriteria partnerCriteria) {
        Page page = partnerService.findPartnerByPage(partnerCriteria, 10);
        if (partnerCriteria.getOffset() == null) {
            partnerCriteria.setOffset(1);
        }
        List<Partner> partnerList = page.getContent();
        List<Integer> partnerBindMerchantCountList = new ArrayList<Integer>();
        List<Integer> partnerBindUserCountList = new ArrayList<Integer>();
        List<Long> partnerWalletAvailableBalanceList = new ArrayList<Long>();
        List<Long> partnerWalletTotalMoneyList = new ArrayList<Long>();
        List<Long> partnerWalletOnlineAvailableBalanceList = new ArrayList<Long>();
        List<Long> partnerWalletOnlineTotalMoneyList = new ArrayList<Long>();
        List<String> leJiaUserSidList = new ArrayList<String>();
        for (Partner partner : partnerList) {
            int partnerBindMerchant = 0;
            int partnerBindUser = 0;
            long partnerWalletAvailableBalance = 0;
            long partnerWalletTotalMoney = 0;

            long partnerWalletOnlineAvailableBalance = 0;
            long partnerWalletOnlineTotalMoney = 0;
            String leJiaUserSid="";

            Long partnerId = partner.getId();
            partnerBindMerchant = partnerService.findPartnerBindMerchantCount(partnerId);
            partnerBindUser = partnerService.findPartnerBindUserCount(partnerId);
            PartnerWallet partnerWallet=partnerWalletService.findByPartner(partner);
            PartnerWalletOnline partnerWalletOnline=partnerWalletOnlineService.findByPartner(partner);

            if(partner.getWeiXinUser()!=null){
                WeiXinUser weiXinUser=partner.getWeiXinUser();
                if(weiXinUser.getLeJiaUser()!=null){
                    Long id=weiXinUser.getId();
                    leJiaUserSid=userService.findUserByWeiXinId(id);
                }
            }
            leJiaUserSidList.add(leJiaUserSid);
            partnerWalletOnlineAvailableBalance=partnerWalletOnline.getAvailableBalance();
            partnerWalletOnlineTotalMoney=partnerWalletOnline.getTotalMoney();
            partnerWalletAvailableBalance=partnerWallet.getAvailableBalance();
            partnerWalletTotalMoney=partnerWallet.getTotalMoney();
            partnerWalletOnlineAvailableBalanceList.add(partnerWalletOnlineAvailableBalance);
            partnerWalletOnlineTotalMoneyList.add(partnerWalletOnlineTotalMoney);
            partnerWalletAvailableBalanceList.add(partnerWalletAvailableBalance);
            partnerWalletTotalMoneyList.add(partnerWalletTotalMoney);
            partnerBindMerchantCountList.add(partnerBindMerchant);
            partnerBindUserCountList.add(partnerBindUser);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("partnerBindMerchantCountList", partnerBindMerchantCountList);
        map.put("partnerBindUserCountList", partnerBindUserCountList);
        map.put("partnerWalletAvailableBalanceList", partnerWalletAvailableBalanceList);
        map.put("partnerWalletTotalMoneyList", partnerWalletTotalMoneyList);
        map.put("partnerWalletOnlineAvailableBalanceList", partnerWalletOnlineAvailableBalanceList);
        map.put("partnerWalletOnlineTotalMoneyList", partnerWalletOnlineTotalMoneyList);
        map.put("leJiaUserSidList", leJiaUserSidList);
        return LejiaResult.ok(map);
    }

    @RequestMapping(value = "/partner/editPartnerPage/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult editPartnerPage(@PathVariable Long id) {
        Partner partner = partnerService.findPartnerById(id);
        return LejiaResult.ok(partner);
    }

    @RequestMapping(value = "/partner/exportExcel", method = RequestMethod.POST)
    public ModelAndView exportExcel(PartnerCriteria partnerCriteria) {
        if (partnerCriteria.getOffset() == null) {
            partnerCriteria.setOffset(1);
        }
        Page page = partnerService.findPartnerByPage(partnerCriteria, 10000);
        Map map = new HashMap();
        map.put("partnerList", page.getContent());
        return new ModelAndView(partnerViewExcel, map);
    }


}
