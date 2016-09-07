package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantScroll;
import com.jifenke.lepluslive.merchant.service.MerchantScrollService;

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
 * 商家详情图 Created by zhangwen on 16/9/3.
 */
@RestController
@RequestMapping("/manage/merchant")
public class MerchantScrollController {

  @Inject
  private MerchantScrollService merchantScrollService;


  //获取某个轮播图  06/09/02
  @RequestMapping(value = "/findScroll/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult findProductType(@PathVariable Long id) {
    return LejiaResult.ok(merchantScrollService.findScrollPictureById(id));
  }

  //新建或修改 06/09/02
  @RequestMapping(value = "/scrollPicture", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult save(@RequestBody MerchantScroll merchantScroll) {
    merchantScrollService.editScrollPicture(merchantScroll);
    return LejiaResult.build(200, "修改成功");
  }

  //轮播图页 06/09/02
  @RequestMapping(value = "/editContent", method = RequestMethod.GET)
  public ModelAndView goMerchantContentPage(@RequestParam Long id, @RequestParam Integer type,
                                            Model model) {
    model.addAttribute("merchantId", id);
    model.addAttribute("type", type);
    return MvUtil.go("/merchant/list");
  }

  //异步获取轮播图数据 06/09/02
  @RequestMapping(value = "/scrollList", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult ajaxList(@RequestParam Integer offset, @RequestParam Long merchantId) {

    if (offset != null) { //当前页码
      offset = 1;
    }
    Page page = merchantScrollService.findScorllPicByPage(offset, merchantId);
    return LejiaResult.ok(page);
  }

  @RequestMapping(value = "/delScroll/{id}", method = RequestMethod.DELETE)
  public
  @ResponseBody
  LejiaResult deleteScrollPicture(@PathVariable Long id) {
    merchantScrollService.deleteScrollPicture(id);
    return LejiaResult.build(200, "删除成功");
  }


}
