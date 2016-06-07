package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.domain.entities.ProductRecommend;
import com.jifenke.lepluslive.product.service.ProductRecommendService;

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
 * Created by zhangwen on 16/6/6.
 */
@RestController
@RequestMapping("/manage")
public class ProductRecommendController {

  @Inject
  private ProductRecommendService recommendService;

  //分页
  @RequestMapping(value = "/productRec", method = RequestMethod.GET)
  public ModelAndView findPageProductRec(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(required = false) Integer state, Model model) {
    if (offset == null) {
      offset = 1;
    }
    if (state == null) {
      state = 0;
    }

    Page page = recommendService.findAllProductRecommend(offset);
    model.addAttribute("recommends", page.getContent());
    model.addAttribute("pages", page.getTotalPages());
    model.addAttribute("state", state);
    model.addAttribute("currentPage", offset);
    return MvUtil.go("/product/productRecommend");
  }

  @RequestMapping(value = "/productRec", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult createProductSpec(@RequestBody ProductRecommend productRecommend) {
    try {
      int status = recommendService.editProductRecommend(productRecommend);
      if (status != 0) {
        return LejiaResult.build(404, "未找到商品或推荐");
      }
      return LejiaResult.build(200, "保存成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "保存失败");
    }

  }


  @RequestMapping(value = "/productRec/putOff/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult putOffProductRec(@PathVariable Integer id) {
    try {
      recommendService.putOffProductRec(id);
      return LejiaResult.build(200, "ok");
    } catch (Exception e) {
      return new LejiaResult(500, "下架推荐商品失败", null);
    }
  }
}
