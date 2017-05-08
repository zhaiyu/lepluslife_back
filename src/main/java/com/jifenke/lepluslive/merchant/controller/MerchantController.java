package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.controller.dto.MerchantDto;
import com.jifenke.lepluslive.merchant.controller.dto.MerchantUserDto;
import com.jifenke.lepluslive.merchant.controller.view.MerchantViewExcel;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.*;
import com.jifenke.lepluslive.merchant.service.*;
import com.jifenke.lepluslive.partner.service.PartnerService;

import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;
import com.jifenke.lepluslive.sales.service.SalesService;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

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

import springfox.documentation.spring.web.json.Json;

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

  @Inject
  private MerchantRebatePolicyService merchantReBatePolicyService;

  @Inject
  private MerchantUserService merchantUserService;

  @Inject
  private MerchantScanPayWayService merchantScanPayWayService;

  @Inject
  private MerchantSettlementStoreService merchantSettlementStoreService;

  @Inject
  private MerchantSettlementService merchantSettlementService;

  @Inject
  private DictionaryService dictionaryService;

  @RequestMapping(value = "/merchant", method = RequestMethod.GET)
  public ModelAndView goShowMerchantPage(Model model) {
    model.addAttribute("merchantTypes", merchantService.findAllMerchantTypes());
    model.addAttribute("cities", cityService.findAllCity());
    model.addAttribute("partners", partnerService.findAllParter());
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


  /**
   * 创建门店页面  2017/01/10
   *
   * @param merchantUserId 商户ID
   */
  @RequestMapping(value = "/merchant/edit", method = RequestMethod.GET)
  public ModelAndView goCreateMerchantPage(@RequestParam Long merchantUserId, Model model) {
    model.addAttribute("merchantUser", merchantUserService.findById(merchantUserId));
    model.addAttribute("merchantTypes", merchantService.findAllMerchantTypes());
    model.addAttribute("partners", partnerService.findAllParter());
    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
    model.addAttribute("sales", salesStaffList);
    model.addAttribute("rebateStage", dictionaryService.findDictionaryById(50L).getValue());
    //新加内容 01/10
    //获取商户所有的商户号
    model.addAttribute("settlementList",
                       merchantSettlementService.findByMerchantUser(merchantUserId));
    return MvUtil.go("/merchant/edit");
  }

  /**
   * 修改门店页面  2017/01/10
   *
   * @param id 门店ID
   */
  @RequiresPermissions("merchant:edit")
  @RequestMapping(value = "/merchant/edit/{id}", method = RequestMethod.GET)
  public ModelAndView goEditMerchantPage(@PathVariable Long id, Model model) {
    Merchant merchant = merchantService.findMerchantById(id);
    model.addAttribute("merchant", merchant);
    model.addAttribute("merchantTypes", merchantService.findAllMerchantTypes());
    model.addAttribute("merchantUser", merchant.getMerchantUser());
    model.addAttribute("partners", partnerService.findAllParter());
    model.addAttribute("merchantRebatePolicy", merchantReBatePolicyService.findByMerchant(id));
    SalesStaff salesStaff = merchant.getSalesStaff();
    model.addAttribute("salesStaff", salesStaff);
    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
//    salesStaffList.remove(salesStaff);
    model.addAttribute("sales", salesStaffList);
    //新加内容 01/10
    //获取商户所有的商户号
    if (merchant.getMerchantUser() != null) {
      model.addAttribute("settlementList", merchantSettlementService
          .findByMerchantUser(merchant.getMerchantUser().getId()));
    }
    //获取门店结算方式和使用商户号信息 (没有就创建)
    model.addAttribute("scanPayWay", merchantScanPayWayService.findByMerchantId(id));
    model.addAttribute("store", merchantSettlementStoreService.findByMerchantId(id));
    model.addAttribute("rebateStage", dictionaryService.findDictionaryById(50L).getValue());
    return MvUtil.go("/merchant/edit");
  }

  @RequestMapping(value = "/merchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult createMerchant(@RequestBody MerchantDto merchantDto) {
    Merchant merchant = merchantDto.getMerchant();
    merchantService.createMerchant(merchant);
    MerchantRebatePolicy policy = merchantDto.getMerchantRebatePolicy();
    policy.setMerchantId(merchant.getId());
    merchantReBatePolicyService.saveMerchantRebatePolicy(policy);
    //新加内容 01/13
    //保存支付方式和商户号使用信息
    MerchantScanPayWay scanPayWay = merchantDto.getMerchantScanPayWay();
    scanPayWay.setMerchantId(merchant.getId());
    MerchantSettlementStore store = merchantDto.getMerchantSettlementStore();
    store.setMerchantId(merchant.getId());
    try {
      merchantScanPayWayService.savePayWay(scanPayWay);
      merchantSettlementStoreService.saveSettlementStore(store);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "创建异常");
    }

    return LejiaResult.ok("添加商户成功");
  }

  @RequestMapping(value = "/merchant", method = RequestMethod.PUT)
  public LejiaResult editMerchant(@RequestBody MerchantDto merchantDto) {
    Merchant merchant = merchantDto.getMerchant();
    MerchantUser merchantUser = null;
    if(merchant.getMerchantUser()!=null&&merchant.getMerchantUser().getName()!=null) {
      merchantUser = merchantUserService.findMerchantManagerByName(merchant.getMerchantUser().getName());
    }
    merchantService.editMerchant(merchant,merchantUser);
    MerchantRebatePolicy policy = merchantDto.getMerchantRebatePolicy();
    if (policy.getId() == null) {
      policy.setMerchantId(merchant.getId());
      merchantReBatePolicyService.saveMerchantRebatePolicy(policy);
    } else {
      merchantReBatePolicyService.editMerchantRebatePolicy(policy);
    }
    //新加内容 01/13
    //保存支付方式和商户号使用信息
    MerchantScanPayWay scanPayWay = merchantDto.getMerchantScanPayWay();
    MerchantSettlementStore store = merchantDto.getMerchantSettlementStore();
    try {
      merchantScanPayWayService.savePayWay(scanPayWay);
      merchantSettlementStoreService.saveSettlementStore(store);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "修改异常");
    }
    return LejiaResult.build(200,"修改门店成功",merchantUser.getId());
  }

  @RequestMapping(value = "/merchant/disable/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult deleteMerchant(@PathVariable Long id) {
    merchantService.disableMerchant(id);

    return LejiaResult.ok("成功停用商户");
  }

  /**
   * fixme:待删除  2017/02/07 转移到merchantUserController
   */
  @RequestMapping(value = "/merchant/user/{id}", method = RequestMethod.GET)
  public ModelAndView goMerchantUserPage(@PathVariable Long id, Model model) {
    Merchant merchant = merchantService.findMerchantById(id);
    model.addAttribute("merchant", merchantService.findMerchantById(id));
    List<MerchantUser>
        merchantUsers =
        merchantService.findMerchantUsersByMerchant(merchant);
    model.addAttribute("merchantUsers", merchantUsers);
    model.addAttribute("merchantWeiXinUsers",
                       merchantService.findmerchantWeixinUserByMerchanUsers(merchantUsers));
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

  /**
   * fixme:待删除  2017/02/09 转移到merchantUserController
   */
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

  /**
   * fixme:待删除  2017/02/09
   */
  @RequestMapping(value = "/merchant/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult createMerchantUser(@RequestBody MerchantUserDto merchantUserDto) {
    MerchantUser merchantUser = merchantUserDto.getMerchantUser();
    Merchant merchant = merchantUserDto.getMerchant();
    merchantUser.setMerchant(merchant);
    merchantService.editMerchantUser(merchantUser);

    return LejiaResult.ok("成功创建用户");
  }

  /**
   * fixme:待删除  2017/02/09
   */
  @RequestMapping(value = "/merchant/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult editMerchantUser(@RequestBody MerchantUserDto merchantUserDto) {
    MerchantUser merchantUser = merchantUserDto.getMerchantUser();
    Merchant merchant = merchantUserDto.getMerchant();
    merchantUser.setMerchant(merchant);
    merchantService.editMerchantUser(merchantUser);

    return LejiaResult.ok("成功修改用户");
  }

  /**
   * fixme:待删除  2017/02/08 转移到merchantUserController
   */
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
    if (!"0".equals(city)) {
      merchantCriteria.setCity(Long.parseLong(city));
    } else {
      merchantCriteria.setCity(null);
    }
    Page page = merchantService.findMerchantsByPage(merchantCriteria, 10000);
    List<Merchant> merchants = page.getContent();
    List<Integer> binds = merchantService.findBindLeJiaUsers(merchants);
    Map map = new HashMap();
    map.put("merchantList", page.getContent());
    map.put("binds", binds);
    return new ModelAndView(merchantViewExcel, map);
  }

  @RequestMapping(value = "/merchant/pos_manage/{id}", method = RequestMethod.GET)
  public ModelAndView posManage(@PathVariable Long id, Model model) {
    Merchant merchant = merchantService.findMerchantById(id);
    List list = merchantService.findAllPosByMerchant(merchant);
    model.addAttribute(list);
    model.addAttribute("posList", list);
    model.addAttribute("merchantId", id);
    model.addAttribute("merchantName", merchant.getName());
    return MvUtil.go("/merchant/merchantPosManage");
  }


}
