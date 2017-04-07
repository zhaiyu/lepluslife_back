package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductType;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.service.GoldProductService;
import com.jifenke.lepluslive.product.service.ProductService;
import com.jifenke.lepluslive.product.service.ProductSpecService;
import com.jifenke.lepluslive.product.service.ScrollPictureService;
import com.jifenke.lepluslive.weixin.service.CategoryService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;

/**
 * 金币商品 Created by zhangwen on 17/2/20.
 */
@RestController
@RequestMapping("/manage/gold")
public class GoldProductController {

  @Inject
  private ProductService productService;

  @Inject
  private GoldProductService goldProductService;

  @Inject
  private ScrollPictureService scrollPictureService;

  @Inject
  private ProductSpecService productSpecService;

  @Inject
  private CategoryService categoryService;

  /**
   * 金币商品列表页   17/2/20
   */
  @RequestMapping(value = "/product", method = RequestMethod.GET)
  public ModelAndView findPageProduct(Model model) {

    List<ProductType> productTypes = productService.findAllProductType();
    model.addAttribute("productTypes", productTypes);
    model.addAttribute("markList", categoryService.findAllByCategory(1));
    return MvUtil.go("/product/gold/index");
  }

  /**
   * 分页获取金币商品列表  17/2/20
   */
  @RequestMapping(value = "/product", method = RequestMethod.POST)
  public LejiaResult ajaxList(@RequestBody ProductCriteria criteria) {
    return LejiaResult.ok(goldProductService.findGoldProductByPage(criteria));
  }

  /**
   * 金币商品编辑或新建页面  17/2/20
   *
   * @param id 商品ID
   */
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public ModelAndView goEditProductPage(@RequestParam(required = false) Long id, Model model) {

    Product product = null;
    List<ProductDetail> detailList = null;
    List<ScrollPicture> scrollPictureList = null;
    model.addAttribute("productTypes", productService.findAllProductType());
    model.addAttribute("markList", categoryService.findAllByCategory(1));
    if (id != null) {
      product = productService.findOneProduct(id);
    } else {
      model.addAttribute("sid", productService.getTotalCount());
      return MvUtil.go("/product/gold/productCreate");
    }
    if (product != null) {
      model.addAttribute("product", product);
      detailList = productService.findAllProductDetailsByProduct(product);
      scrollPictureList = scrollPictureService.findAllScorllPicture(product);
      model.addAttribute("detailList", detailList);
      if (detailList == null) {
        model.addAttribute("detailSize", 1);
      } else {
        model.addAttribute("detailSize", detailList.size());
      }
      model.addAttribute("scrollList", scrollPictureList);
      if (scrollPictureList == null) {
        model.addAttribute("scrollSize", 1);
      } else {
        model.addAttribute("scrollSize", scrollPictureList.size());
      }
    }

    return MvUtil.go("/product/gold/productCreate");
  }

  /**
   * 金币商品规格管理页面  17/2/20
   *
   * @param id 商品ID
   */
  @RequestMapping(value = "/specManage", method = RequestMethod.GET)
  public ModelAndView goSpecManagePage(@RequestParam Long id, Model model) {
    Product product = productService.findOneProduct(id);
    model.addAttribute("product", product);
    model.addAttribute("productSpecs", productSpecService.findProductSpecsByProduct(product));
    return MvUtil.go("/product/gold/productSpec");
  }

  /**
   * 金币商品保存 17/2/20
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public LejiaResult ajaxSave(@RequestBody LimitProductDto productDto) {
    try {
      goldProductService.saveGoldProduct(productDto);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(303, "保存异常");
    }
    return LejiaResult.ok();
  }
}
