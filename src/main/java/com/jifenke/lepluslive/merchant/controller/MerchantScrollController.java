package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantScroll;
import com.jifenke.lepluslive.merchant.service.MerchantScrollService;
import com.jifenke.lepluslive.merchant.service.MerchantService;

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
 * Created by wcg on 16/6/8.
 */
@RestController
@RequestMapping("/manage/merchant")
public class MerchantScrollController {

  @Inject
  private MerchantScrollService merchantScrollService;

  @Inject
  private MerchantService merchantService;

  @RequestMapping(value = "/editContent/{id}", method = RequestMethod.GET)
  public ModelAndView goMerchantContentPage(@PathVariable Long id, Model model) {
    Merchant merchant = merchantService.findMerchantById(id);
    model.addAttribute("merchant", merchant);
    model.addAttribute("scrollPictures", merchantScrollService.findAllScorllPicture(merchant));
    return MvUtil.go("/merchant/merchantContent");
  }

  @RequestMapping("/scrollPicture/{id}")
  public
  @ResponseBody
  MerchantScroll findScrollPictureById(@PathVariable Long id) {
    return merchantScrollService.findScrollPictureById(id);

  }

  @RequestMapping(value = "/scrollPicture", method = RequestMethod.PUT)
  public
  @ResponseBody
  LejiaResult editScrollPicture(@RequestBody MerchantScroll merchantScroll) {
    merchantScrollService.editScrollPicture(merchantScroll);
    return LejiaResult.build(200, "修改成功");

  }

  @RequestMapping(value = "/scrollPicture", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult createScrollPicture(@RequestBody MerchantScroll merchantScroll) {
    merchantScrollService.editScrollPicture(merchantScroll);
    return LejiaResult.build(200, "保存成功");
  }

  @RequestMapping(value = "/scrollPicture/{id}", method = RequestMethod.DELETE)
  public
  @ResponseBody
  LejiaResult deleteScrollPicture(@PathVariable Long id) {
    merchantScrollService.deleteScrollPicture(id);
    return LejiaResult.build(200, "删除成功");
  }

}
