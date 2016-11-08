package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.PaginationUtil;
import com.jifenke.lepluslive.global.util.ParseUtil;
import com.jifenke.lepluslive.merchant.controller.view.MerchantViewExcel;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.BankName;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantBank;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.*;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.service.PartnerService;

import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;
import com.jifenke.lepluslive.sales.service.SalesService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/17.
 */
@RestController
@RequestMapping("/manage")
public class MerchantController {

  @Inject
  private MerchantViewExcel merchantViewExcel;

  @Inject
  private MerchantService merchantService;

  @Inject
  private CityService cityService;

  @Inject
  private PartnerService partnerService;

  @Inject
  private SalesService salesService;

  @Inject
  private MerchantWeiXinUserService merchantWeiXinUserService;

  @Inject
  private BankNameService bankNameService;

  @Inject
  private MerchantPosService merchantPosService;

  @RequestMapping(value = "/merchant", method = RequestMethod.GET)
  public ModelAndView goShowMerchantPage(Model model) {
    model.addAttribute("merchantTypes", merchantService.findAllMerchantTypes());
    model.addAttribute("cities", cityService.findAllCity());
    return MvUtil.go("/merchant/merchantList");
  }

  @RequestMapping(value = "/merchant/search", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getMerchants(@RequestBody MerchantCriteria merchantCriteria) {
    if (merchantCriteria.getOffset() == null) {
      merchantCriteria.setOffset(1);
    }
    Page page = merchantService.findMerchantsByPage(merchantCriteria, 10);
    //获取每个合伙人的锁定会员数
    List<Merchant> merchants = page.getContent();
    List<Integer> binds = merchantService.findBindLeJiaUsers(merchants);
    //获取每个商户的pos机数量
    List<Long> posCounts = merchantPosService.countPosByMerchants(merchants);
    List<Object> list = new ArrayList<>();
    list.add(page);
    list.add(binds);
    list.add(posCounts);
    return LejiaResult.ok(list);
  }


  @RequestMapping(value = "/merchant/edit", method = RequestMethod.GET)
  public ModelAndView goCreateMerchantPage(Model model) {
    model.addAttribute("merchantTypes", merchantService.findAllMerchantTypes());
    model.addAttribute("partners", partnerService.findAllParter());
    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
    model.addAttribute("sales", salesStaffList);
    return MvUtil.go("/merchant/merchantCreate");
  }

  @RequiresPermissions("merchant:edit")
  @RequestMapping(value = "/merchant/edit/{id}", method = RequestMethod.GET)
  public ModelAndView goEditMerchantPage(@PathVariable Long id, Model model) {
    model.addAttribute("merchant", merchantService.findMerchantById(id));
    model.addAttribute("merchantTypes", merchantService.findAllMerchantTypes());
    model.addAttribute("partners", partnerService.findAllParter());
    Merchant merchant=merchantService.findMerchantById(id);
    SalesStaff salesStaff=merchant.getSalesStaff();
    model.addAttribute("salesStaff",salesStaff);
    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
    salesStaffList.remove(salesStaff);
    model.addAttribute("sales", salesStaffList);
    return MvUtil.go("/merchant/merchantCreate");
  }

  @RequestMapping(value = "/merchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult createMerchant(@RequestBody Merchant merchant) {
    merchantService.createMerchant(merchant);

    return LejiaResult.ok("添加商户成功");
  }

  @RequestMapping(value = "/merchant", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult eidtMerchant(@RequestBody Merchant merchant) {
    merchantService.editMerchant(merchant);

    return LejiaResult.ok("修改商户成功");
  }

  @RequestMapping(value = "/merchant/disable/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult deleteMerchant(@PathVariable Long id) {
    merchantService.disableMerchant(id);

    return LejiaResult.ok("成功停用商户");
  }


  @RequestMapping(value = "/merchant/user/{id}", method = RequestMethod.GET)
  public ModelAndView goMerchantUserPage(@PathVariable Long id, Model model) {
    Merchant merchant = merchantService.findMerchantById(id);
    model.addAttribute("merchant", merchantService.findMerchantById(id));
    List<MerchantUser>
        merchantUsers =
        merchantService.findMerchantUsersByMerchant(merchant);
    model.addAttribute("merchantUsers", merchantUsers);
    model.addAttribute("merchantWeiXinUsers",merchantService.findmerchantWeixinUserByMerchanUsers(merchantUsers));
    return MvUtil.go("/merchant/merchantUser");
  }

  @RequestMapping(value = "/merchant/openStore", method = RequestMethod.POST)
  public LejiaResult openStore(@RequestBody Merchant merchant) {
    merchantService.openStore(merchant);
    return LejiaResult.build(200, "开启成功");
  }

  @RequestMapping(value = "/merchant/closeStore/{id}", method = RequestMethod.GET)
  public LejiaResult closeStore(@PathVariable Long id) {
    merchantService.closeStore(id);
//    model.addAttribute("merchant", merchant);
    return LejiaResult.build(200, "成功关闭乐店");
  }

  @RequestMapping(value = "/merchant/openStore/{id}", method = RequestMethod.GET)
  public ModelAndView goOpenShopPage(@PathVariable Long id, Model model) {
    Merchant merchant = merchantService.findMerchantById(id);
    model.addAttribute("merchant", merchant);
    return MvUtil.go("/merchant/openStore");
  }

  @RequestMapping(value = "/merchant/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult deleteMerchantUser(@PathVariable Long id) {
    merchantService.deleteMerchantUser(id);
    return LejiaResult.ok("成功停用商户");
  }

  @RequestMapping(value = "/merchant/weiXinUser/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult unBindMerchantWeiXinUser(@PathVariable Long id) {
    merchantWeiXinUserService.unBindMerchantWeiXinUser(id);
    return LejiaResult.ok("成功解绑用户");
  }


  @RequestMapping(value = "/merchant/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult createMerchantUser(@RequestBody MerchantUser merchantUser) {
    merchantService.editMerchantUser(merchantUser);

    return LejiaResult.ok("成功创建用户");
  }


  @RequestMapping(value = "/merchant/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult editMerchantUser(@RequestBody MerchantUser merchantUser) {
    merchantService.editMerchantUser(merchantUser);

    return LejiaResult.ok("成功修改用户");
  }

  @RequestMapping(value = "/merchant/user/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult getMerchantUserById(@PathVariable Long id) {

    return LejiaResult.ok(merchantService.getMerchantUserById(id));
  }

  @RequestMapping(value = "/merchant/qrCode", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult merchantQrCode(@RequestParam Long id) {
    return LejiaResult.ok(merchantService.qrCodeManage(id));
  }

  @RequestMapping(value = "/merchant/pureQrCode", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult pureQrCode(@RequestParam Long id) {
    return LejiaResult.ok(merchantService.pureQrCodeManage(id));
  }

  @RequestMapping(value = "/merchant/merchantExport", method = RequestMethod.GET)
  public ModelAndView exporeExcel(MerchantCriteria merchantCriteria, String city) {
    if (merchantCriteria.getOffset() == null) {
      merchantCriteria.setOffset(1);
    }
    if(!"0".equals(city)){
      merchantCriteria.setCity(Long.parseLong(city));
    }else {
      merchantCriteria.setCity(null);
    }
    Page page = merchantService.findMerchantsByPage(merchantCriteria, 10000);
    Map map = new HashMap();
    map.put("merchantList", page.getContent());
    return new ModelAndView(merchantViewExcel, map);
  }

  @RequestMapping(value = "/merchant/pos_manage/{id}", method = RequestMethod.GET)
  public ModelAndView posManage(@PathVariable Long id, Model model) {
    Merchant merchant = merchantService.findMerchantById(id);
    List list = merchantService.findAllPosByMerchant(merchant);
    model.addAttribute(list);
    model.addAttribute("posList",list);
    model.addAttribute("merchantId",id);
    model.addAttribute("merchantName",merchant.getName());
    return MvUtil.go("/merchant/merchantPosManage");
  }


}
