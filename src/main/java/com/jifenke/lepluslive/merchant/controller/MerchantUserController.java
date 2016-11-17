package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantUserCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by xf on 16-11-16.
 */
@RestController
@RequestMapping("/manage")
public class MerchantUserController {

    @Inject
    private MerchantUserService merchantUserService;
    @Inject
    private CityService cityService;


    @RequestMapping("/merchantUser/list")
    public ModelAndView listPage(Model model) {
        List<City> allCity = cityService.findAllCity();
        model.addAttribute("citys",allCity);
        return MvUtil.go("/merchant/merchantUserList");
    }


    @RequestMapping(value = "/merchantUser/create",method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult createMerchantUser(@RequestBody MerchantUser merchantUser) {
        merchantUserService.createMerchantUserManager(merchantUser);
        return LejiaResult.ok("商户账号创建成功！");
    }

    @RequestMapping(value="/merchantUser/check_username",method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult checkUserName(@RequestBody String name) {
        List<MerchantUser> list = merchantUserService.findByName(name);
        if(list!=null && list.size()>0) {
            return LejiaResult.build(400,"用户名已存在,换个试下！");
        }else {
            return LejiaResult.ok();
        }
    }

    @RequestMapping(value="/merchantUser/find_page",method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody  MerchantUserCriteria merchantUserCriteria) {
        Page page = merchantUserService.findByCriteria(merchantUserCriteria,10);
        return LejiaResult.ok(page);
    }
}
