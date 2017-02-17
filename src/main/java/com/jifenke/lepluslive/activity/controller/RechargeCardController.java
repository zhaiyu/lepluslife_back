package com.jifenke.lepluslive.activity.controller;

import com.jifenke.lepluslive.activity.domain.criteria.RechargeCardCriteria;
import com.jifenke.lepluslive.activity.domain.entities.RechargeCard;
import com.jifenke.lepluslive.activity.service.RechargeCardService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantUserCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.partner.service.PartnerService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;

/**
 * 充值卡记录
 * Created by tqy on 2017-1-4.
 */
@RestController
@RequestMapping("/manage")
public class RechargeCardController {

    @Inject
    private RechargeCardService rechargeCardService;


    @RequestMapping("/rechargeCard/list")
    public ModelAndView listPage(Model model) {
      return MvUtil.go("/activity/rechargeCardList");
    }
    /**
     * 条件查询
     * @param
     */
    @RequestMapping(value="/rechargeCard/find_page",method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody RechargeCardCriteria rechargeCardCriteria) {
        Page page = rechargeCardService.findByCriteria(rechargeCardCriteria);
        List<RechargeCard> list = page.getContent();
        if (list.size()>0){
          for (RechargeCard item : list){
            LeJiaUser leJiaUser = item.getWeiXinUser().getLeJiaUser();
            if (leJiaUser!=null){
//              System.out.println(leJiaUser.getPhoneNumber()+"------"+leJiaUser.getUserSid());
              LeJiaUser leJiaUser222 = new LeJiaUser();
              leJiaUser222.setPhoneNumber(leJiaUser.getPhoneNumber());
              leJiaUser222.setUserSid(leJiaUser.getUserSid());
              item.getWeiXinUser().setLeJiaUser(leJiaUser222);
            }
          }
        }
        return LejiaResult.ok(page);
    }

    /**
     * 修改充值状态
     * @param
     */
    @RequestMapping(value="/rechargeCard/rechargeStatus/{id}", method = RequestMethod.POST)
    public LejiaResult changeStatus(@PathVariable Long id,Integer rechargeStatus) {
      if(id==null || rechargeStatus==null){
        return LejiaResult.build(499, "参数错误!");
      }
      return rechargeCardService.editStatus(id,rechargeStatus);
    }

}
