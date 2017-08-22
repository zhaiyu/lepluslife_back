package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.controller.dto.MerchantDto;
import com.jifenke.lepluslive.merchant.controller.view.MerchantViewExcel;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantScanPayWay;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.merchant.service.MerchantPosService;
import com.jifenke.lepluslive.merchant.service.MerchantScanPayWayService;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.merchant.service.MerchantWeiXinUserService;
import com.jifenke.lepluslive.partner.service.PartnerService;
import com.jifenke.lepluslive.sales.service.SalesService;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.service.MerchantLedgerService;
import com.jifenke.lepluslive.yibao.service.MerchantUserLedgerService;

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
  private MerchantUserLedgerService merchantUserLedgerService;

  @Inject
  private MerchantPosService merchantPosService;

  @Inject
  private MerchantUserService merchantUserService;

  @Inject
  private MerchantScanPayWayService merchantScanPayWayService;

  @Inject
  private MerchantLedgerService merchantLedgerService;

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
   * 创建门店
   */
  @RequestMapping(value = "/merchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult createMerchant(@RequestBody MerchantDto merchantDto) {
    Merchant merchant = merchantDto.getMerchant();
    try {
      Merchant dbMerchant = merchantService.createMerchant(merchant);
      //保存支付方式
      MerchantScanPayWay scanPayWay = merchantDto.getMerchantScanPayWay();
      scanPayWay.setMerchantId(dbMerchant.getId());
      merchantScanPayWayService.savePayWay(scanPayWay);
      //如果选择易宝支付，保存门店选择的子商编
      if (scanPayWay.getType() == 3) {
        merchantLedgerService.saveByMerchant(merchantDto.getMerchantLedger(), dbMerchant);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "创建异常");
    }
    return LejiaResult.build(200, "添加商户成功");
  }

  /**
   * 修改门店
   */
  @RequestMapping(value = "/merchant", method = RequestMethod.PUT)
  public LejiaResult editMerchant(@RequestBody MerchantDto merchantDto) {
    Merchant merchant = merchantDto.getMerchant();
    try {
      merchantService.editMerchant(merchant);
      //保存支付方式
      MerchantScanPayWay scanPayWay = merchantDto.getMerchantScanPayWay();
      merchantScanPayWayService.savePayWay(scanPayWay);
      //新加 如果选择易宝支付，保存门店选择的子商编
      if (scanPayWay.getType() == 3) {
        merchantLedgerService.saveByMerchant(merchantDto.getMerchantLedger(), merchant);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "修改异常");
    }
    return LejiaResult.build(200, "修改门店成功");
  }

  @RequestMapping(value = "/merchant/disable/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult deleteMerchant(@PathVariable Long id) {
    merchantService.disableMerchant(id);

    return LejiaResult.ok("成功停用商户");
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

  @RequestMapping(value = "/merchant/weiXinUser/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  LejiaResult unBindMerchantWeiXinUser(@PathVariable Long id) {
    merchantWeiXinUserService.unBindMerchantWeiXinUser(id);
    return LejiaResult.ok("成功解绑用户");
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

  /**
   * 查询商户下面所有门店
   */
  @RequestMapping(value = "/merchant/findByMU", method = RequestMethod.GET)
  @ResponseBody
  public LejiaResult findByMerchantUser(Long id) {
    MerchantUser merchantUser = merchantUserService.findById(id);
    List<Map<String, Object>> list = merchantService.findByMerchantUser(merchantUser);
    return LejiaResult.ok(list);
  }

  /**
   * 创建或编辑门店页面  2017/08/19
   *
   * @param id   商户ID或门店ID(type=0:商户ID|type=1:门店ID)
   * @param type //0=创建|1=修改
   */
  @RequestMapping(value = "/merchant/edit", method = RequestMethod.GET)
  public ModelAndView editPage(@RequestParam Long id, @RequestParam Integer type, Model model) {

    model.addAttribute("merchantTypes", merchantService.findAllMerchantTypes());
    model.addAttribute("sales", salesService.findAllSaleStaff());

    if (type == 0) { //创建门店
      model.addAttribute("createOrEdit", 0);

      MerchantUser merchantUser = merchantUserService.findById(id);
      model.addAttribute("merchantUser", merchantUser);
      //获取商户所有易宝子商户
      List<MerchantUserLedger>
          ledgerList =
          merchantUserLedgerService.findAllByMerchantUser(merchantUser);
      if (ledgerList == null || ledgerList.size() == 0) {
        model.addAttribute("display_show_yb", false);
      } else {
        model.addAttribute("ledgerList", ledgerList);
        model.addAttribute("display_show_yb", true);
      }

    } else {//编辑门店
      model.addAttribute("createOrEdit", 1);

      Merchant merchant = merchantService.findMerchantById(id);
      model.addAttribute("merchant", merchant);
      model.addAttribute("merchantUser", merchant.getMerchantUser());
      model.addAttribute("salesStaff", merchant.getSalesStaff());
      //获取门店结算方式 (没有就创建)
      MerchantScanPayWay scanPayWay = merchantScanPayWayService.findByMerchantId(id);
      model.addAttribute("scanPayWay", scanPayWay);
      //新加易宝 获取商户所有易宝子商户
      List<MerchantUserLedger>
          ledgerList =
          merchantUserLedgerService.findAllByMerchantUser(merchant.getMerchantUser());
      if (ledgerList == null || ledgerList.size() == 0) {
        model.addAttribute("display_show_yb", false);
      } else {
        model.addAttribute("ledgerList", ledgerList);
        model.addAttribute("display_show_yb", true);
      }
      if (scanPayWay.getType() == 3) {
        model.addAttribute("merchantLedger", merchantLedgerService.findByMerchant(merchant));
      }
    }

    return MvUtil.go("/merchant/edit");
  }

}
