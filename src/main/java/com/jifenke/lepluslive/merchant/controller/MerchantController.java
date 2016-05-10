package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.PaginationUtil;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.merchant.service.MerchantService;

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

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/17.
 */
@RestController
@RequestMapping("/manage")
public class MerchantController {

  @Inject
  private MerchantService merchantService;

  @Inject
  private CityService cityService;

  @RequestMapping(value = "/merchant",method = RequestMethod.GET)
  public ModelAndView goShowMerchantPage(Model model){
    model.addAttribute("merchantTypes",merchantService.findAllMerchantTypes());
    return MvUtil.go("/merchant/merchantList");
  }

  @RequestMapping(value = "/merchant/search", method = RequestMethod.POST)
  public @ResponseBody
  LejiaResult getMerchants(@RequestBody MerchantCriteria merchantCriteria) {
    if (merchantCriteria.getOffset() == null) {
      merchantCriteria.setOffset(1);
    }
    Page page = merchantService.findMerchantsByPage(merchantCriteria, 10);
    return LejiaResult.ok(page);
  }


  @RequestMapping(value = "/merchant/edit",method = RequestMethod.GET)
  public ModelAndView goCreateMerchantPage(Model model){
     model.addAttribute("merchantTypes",merchantService.findAllMerchantTypes());
    return MvUtil.go("/merchant/merchantEdit");
  }

  @RequestMapping(value = "/merchant/edit/{id}",method = RequestMethod.GET)
  public ModelAndView goEditMerchantPage(@PathVariable Long id,Model model){
      model.addAttribute("merchant", merchantService.findMerchantById(id));
    model.addAttribute("merchantTypes",merchantService.findAllMerchantTypes());
    return MvUtil.go("/merchant/merchantEdit");
  }

  @RequestMapping(value = "/merchant",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult createMerchant(@RequestBody Merchant merchant){
    merchantService.createMerchant(merchant);

    return LejiaResult.ok("添加商户成功");
  }

  @RequestMapping(value = "/merchant",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult eidtMerchant(@RequestBody Merchant merchant){
    merchantService.editMerchant(merchant);

    return LejiaResult.ok("修改商户成功");
  }

  @RequestMapping(value = "/merchant/disable/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
   public LejiaResult deleteMerchant(@PathVariable Long id){
    merchantService.disableMerchant(id);

    return LejiaResult.ok("成功停用商户");
  }








}
