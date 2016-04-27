package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.PaginationUtil;
import com.jifenke.lepluslive.merchant.domain.entities.City;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
  public ModelAndView goShowMerchantPage(@RequestParam(value = "page", required = false) Integer offset,
                                     @RequestParam(value = "per_page", required = false) Integer limit,
                                     Model model){
    Page page = merchantService
        .findMerchantsByPage(PaginationUtil.generatePageRequest(offset, limit));
    model.addAttribute("merchants", page.getContent());
    model.addAttribute("pages", page.getTotalPages());
    if (offset == null) {
      offset = 1;
    }
    model.addAttribute("currentPage", offset);
    return MvUtil.go("/merchant/merchantList");
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

  @RequestMapping(value = "/merchant/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult deleteMerchant(@PathVariable Long id){
    merchantService.deleteMerchant(id);

    return LejiaResult.ok("删除商户成功");
  }





}
