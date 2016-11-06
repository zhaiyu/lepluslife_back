package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.banner.domain.criteria.BannerCriteria;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.controller.dto.ProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductType;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.service.ProductDetailService;
import com.jifenke.lepluslive.product.service.ProductService;
import com.jifenke.lepluslive.product.service.ProductSpecService;
import com.jifenke.lepluslive.product.service.ScrollPictureService;
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

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 爆品 Created by zhangwen on 16/9/18.
 */
@RestController
@RequestMapping("/manage")
public class LimitProductController {

  @Inject
  private ProductService productService;

  @Inject
  private ScrollPictureService scrollPictureService;

  @Inject
  private ProductSpecService productSpecService;

  /**
   * 爆品页面 16/09/18
   *
   * @param type product类型
   */
  @RequestMapping(value = "/limit", method = RequestMethod.GET)
  public ModelAndView goLimitPage(@RequestParam Integer type, Model model) {
    if (type == null) {
      type = 1;
    }
    model.addAttribute("type", type);
    return MvUtil.go("/product/limitProductList");
  }

  /**
   * 编辑爆品 16/09/18
   *
   * @param productId 商品id
   */
  @RequestMapping(value = "/limit/edit", method = RequestMethod.GET)
  public ModelAndView goBannerPage(@RequestParam(required = false) Long productId, Model model) {
    Product product = null;
    List<ProductDetail> detailList = null;
    List<ProductSpec> specList = null;
    List<ScrollPicture> scrollPictureList = null;
    if (productId != null) {
      product = productService.findOneProduct(productId);
    }
    if (product != null) {
      model.addAttribute("product", product);
      detailList = productService.findAllProductDetailsByProduct(product);
      specList = productSpecService.findProductSpecsByProductAndState(product);
      scrollPictureList = scrollPictureService.findAllScorllPicture(product);
      model.addAttribute("detailList", detailList);
      if (detailList == null) {
        model.addAttribute("detailSize", 1);
      } else {
        model.addAttribute("detailSize", detailList.size());
      }
      model.addAttribute("specList", specList);
      if (specList == null) {
        model.addAttribute("specSize", 1);
      } else {
        model.addAttribute("specSize", specList.size());
      }
      model.addAttribute("scrollList", scrollPictureList);
      if (scrollPictureList == null) {
        model.addAttribute("scrollSize", 1);
      } else {
        model.addAttribute("scrollSize", scrollPictureList.size());
      }
    }
    return MvUtil.go("/product/editLimit");
  }

  @RequestMapping(value = "/limit/ajaxList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult ajaxList(@RequestBody ProductCriteria criteria) {

    if (criteria.getOffset() == null) {
      criteria.setOffset(1);
    }
    criteria.setType(2);
    Map result = productService.findLimitProductByPage(criteria);

    return LejiaResult.ok(result);
  }

  /**
   * 上架或下架 16/09/18
   *
   * @param id 商品ID
   */
  @RequestMapping(value = "/limit/state/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult changeStatus(@PathVariable Long id) {
    productService.changeState(id);
    return LejiaResult.ok("ok");
  }

  /**
   * 爆款设置 16/09/19
   *
   * @param id    商品id
   * @param thumb 爆款图片(type=1是必须)
   * @param type  1=设为爆款|0=取消爆款
   */
  @RequestMapping(value = "/limit/changeHot", method = RequestMethod.GET)
  public LejiaResult changeHot(@RequestParam(required = true) Long id,
                               @RequestParam(required = false) String thumb,
                               @RequestParam(required = true) Integer type) {
    try {
      productService.changeHot(id, thumb, type);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(202, "保存异常");
    }
    return LejiaResult.ok("ok");
  }

  /**
   * 秒杀商品保存 16/09/19
   */
  @RequestMapping(value = "/limit/save", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult ajaxSave(@RequestBody LimitProductDto productDto) {
    try {
      productService.saveLimitProduct(productDto);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(303, "保存异常");
    }
    return LejiaResult.ok();
  }

}
