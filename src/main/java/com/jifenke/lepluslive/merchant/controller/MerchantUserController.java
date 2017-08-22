package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.controller.dto.MerchantUserDto;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantUserCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.merchant.service.MerchantUserShopService;
import com.jifenke.lepluslive.merchant.service.MerchantWeiXinUserService;
import com.jifenke.lepluslive.partner.service.PartnerService;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
    @Inject
    private PartnerService partnerService;

    @Inject
    private MerchantService merchantService;

    @Inject
    private MerchantUserShopService merchantUserShopService;

    @Inject
    private MerchantWeiXinUserService merchantWeiXinUserService;


    @RequestMapping("/merchantUser/list")
    public ModelAndView listPage(Model model) {
        List<City> allCity = cityService.findAllCity();
        model.addAttribute("citys", allCity);
        model.addAttribute("partners", partnerService.findAllParter());
        return MvUtil.go("/merchantUser/list");
    }

    @RequestMapping(value = "/merchantUser/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult loadEditPage(@PathVariable Long id) {
        MerchantUser merchantUser = merchantUserService.findById(id);
        return LejiaResult.ok(merchantUser);
    }

    @RequestMapping(value = "/merchantUser/edit", method = RequestMethod.PUT)
    @ResponseBody
    public LejiaResult saveEdit(@RequestBody MerchantUser merchantUser) {
        merchantUserService.updateMerchantUser(merchantUser);
        return LejiaResult.ok("商户信息修改成功！");
    }


    @RequestMapping(value = "/merchantUser/create", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult createMerchantUser(@RequestBody MerchantUser merchantUser) {
        merchantUserService.createMerchantUserManager(merchantUser);
        return LejiaResult.ok("商户账号创建成功！");
    }

    @RequestMapping(value = "/merchantUser/check_username", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult checkUserName(@RequestBody String name) {
        List<MerchantUser> list = merchantUserService.findByName(name);
        if (list != null && list.size() > 0) {
            return LejiaResult.build(400, "用户名已存在,换个试下！");
        } else {
            return LejiaResult.ok();
        }
    }

    @RequestMapping(value = "/merchantUser/check_exist", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult checkExist(String name) {
        MerchantUser manager = merchantUserService.findMerchantManagerByName(name);
        if (manager == null) {
            return LejiaResult.build(400, "商户不存在.");
        } else {
            return LejiaResult.ok();
        }
    }


    //已废弃，待删除  2017/01/04
    @RequestMapping(value = "/merchantUser/find_page", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody MerchantUserCriteria merchantUserCriteria) {
        if (merchantUserCriteria.getType() == null) {
            merchantUserCriteria.setType(8);               // 默认搜索商户账号
        }
        Page page = merchantUserService.findByCriteria(merchantUserCriteria, 10);
        return LejiaResult.ok(page);
    }

    @RequestMapping(value = "/merchantUser/ambiguitySearch", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult findByKeyWord(String keyword) {
        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        merchantUserCriteria.setKeyword(keyword);
        Page page = merchantUserService.findByCriteria(merchantUserCriteria, 10);
        return LejiaResult.ok(page);
    }

    /**
     * 分页查询商户列表信息  2017/01/04
     *
     * @param merchantUserCriteria 查询条件
     */
    @RequestMapping(value = "/merchantUser/ajaxList", method = RequestMethod.POST)
    public LejiaResult ajaxList(@RequestBody MerchantUserCriteria merchantUserCriteria) {
        if (merchantUserCriteria.getType() == null) {
            merchantUserCriteria.setType(8);               // 默认搜索商户账号
        }
        Map<String, Object> result = new HashMap<>();
        Page page = merchantUserService.findByCriteria(merchantUserCriteria, 10);
        result.put("totalPages", page.getTotalPages());
        result.put("totalElements", page.getTotalElements());
        List<MerchantUser> list = page.getContent();
        List<Map<String, Object>> content = merchantUserService.getMerchantUserInfo(list);
        result.put("content", content);
        return LejiaResult.ok(result);
    }

    /**
     * 商户基本信息编辑页面 2017/01/06
     *
     * @param id 商户ID
     */
    @RequestMapping(value = "/merchantUser/edit", method = RequestMethod.GET)
    public ModelAndView editPage(@RequestParam(required = false) Long id, Model model) {
        List<City> allCity = cityService.findAllCity();
        if (id != null) {
            model.addAttribute("m", merchantUserService.findById(id));
            model.addAttribute("hasLock", merchantUserService.getBindNumber(id));
        }
        model.addAttribute("citys", allCity);
        model.addAttribute("partners", partnerService.findAllParter());
        //该商户已经绑定的会员数量
        return MvUtil.go("/merchantUser/edit");
    }

    /**
     * 跳转到商户详细信息页面  2016/01/09
     *
     * @param id 商户ID
     * @param li 页面默认显示的分类
     */
    @RequestMapping(value = "/merchantUser/info/{id}", method = RequestMethod.GET)
    public ModelAndView information(@PathVariable Long id, @RequestParam(required = false) Integer li,
                                    Model model) {
        MerchantUser merchantUser = merchantUserService.findById(id);
        model.addAttribute("m", merchantUser);
        model.addAttribute("merchants", merchantService.countByMerchantUser(merchantUser));
        if (li != null) {
            model.addAttribute("li", li);
        }
        return MvUtil.go("/merchantUser/information");
    }

    /**
     * 商户详细页上部分统计信息  2017/02/10
     *
     * @param id 商户ID
     */
    @RequestMapping(value = "/merchantUser/data/{id}", method = RequestMethod.GET)
    public Map<String, Object> infoData(@PathVariable Long id) {
        return merchantUserService.collection(id);
    }

    /**
     * 获取商户下的所有门店信息  2016/01/09
     *
     * @param id 商户ID
     */
    @RequestMapping(value = "/merchantUser/merchantList/{id}", method = RequestMethod.GET)
    public LejiaResult merchantList(@PathVariable Long id) {
        MerchantUser merchantUser = merchantUserService.findById(id);
        return LejiaResult.ok(merchantService.findByMerchantUser(merchantUser));
    }

    /**
     * 获取商户下所有的账户和绑定微信号  2017/02/07
     *
     * @param id 商户ID
     */
    @RequestMapping(value = "/merchantUser/user/{id}", method = RequestMethod.GET)
    public LejiaResult goMerchantUserPage(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        List<MerchantUser>
                merchantUsers = merchantUserService.findMerchantUsersByCreateUser(id);
        result.put("merchantUsers", merchantUsers);
        result.put("shops", merchantUserShopService.countByMerchantUserList(merchantUsers));
        result.put("merchantWeiXinUsers",
                merchantService.findmerchantWeixinUserByMerchanUsers(merchantUsers));
        return LejiaResult.ok(result);
    }

    /**
     * 编辑账户时获取账户信息  2017/02/08
     *
     * @param accountId 账户ID
     */
    @RequestMapping(value = "/merchantUser/account", method = RequestMethod.GET)
    public LejiaResult editMerchantUser(@RequestParam Long accountId) {
        Map<String, Object> result = new HashMap<>();
        MerchantUser account = merchantUserService.findById(accountId);
        result.put("user", account);
        result.put("shops", merchantUserShopService.countByMerchantUser(account));
        result.put("wxList", merchantWeiXinUserService.findMerchantWeiXinUserByMerchantUser(account));

        return LejiaResult.ok(result);
    }

    /**
     * 保存账户  2017/02/08
     */
    @RequestMapping(value = "/merchantUser/account", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public LejiaResult saveMerchantUser(@RequestBody MerchantUserDto merchantUserDto) {
        Map<String, String> result = merchantUserService
                .editMerchantUser(merchantUserDto.getMerchantUser(), merchantUserDto.getShopList());

        return LejiaResult.build(Integer.valueOf(result.get("status")), result.get("msg"));
    }

    /**
     * 删除账户  2017/02/09
     */
    @RequestMapping(value = "/merchantUser/delete/{id}", method = RequestMethod.DELETE)
    public LejiaResult deleteMerchantUser(@PathVariable Long id) {
        merchantUserService.deleteMerchantUser(id);
        return LejiaResult.ok();
    }

    /**
     *  获取所有商户  2017/06/26
     */
    @RequestMapping(value="/merchantUser/findAll",method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult findAllMerchantUser() {
        List<MerchantUser> merchantUserList = merchantUserService.findAllManager();
        return LejiaResult.ok(merchantUserList);
    }
}
