package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.service.ProductDetailService;
import com.jifenke.lepluslive.product.service.ScrollPictureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/6.
 */
@RestController
@RequestMapping("/manage")
public class ProductDetailController {

  private Logger LOG = LoggerFactory.getLogger(ProductDetailController.class);
  @Inject
  private ProductDetailService productDetailService;

  @RequestMapping("/productDetail/{id}")
  public @ResponseBody
  ProductDetail findProductDetailById(@PathVariable Integer id ){
    return productDetailService.findProductDetailById(id);

  }

  @RequestMapping(value = "/productDetail",method = RequestMethod.PUT)
  public @ResponseBody
  LejiaResult editProductDetail(@RequestBody ProductDetail productDetail){
    productDetailService.editProductDetail(productDetail);
    return LejiaResult.build(200,"修改成功");

  }

  @RequestMapping(value = "/productDetail",method = RequestMethod.POST)
  public @ResponseBody LejiaResult createProductDetail(@RequestBody ProductDetail productDetail){
    productDetailService.editProductDetail(productDetail);
    return LejiaResult.build(200,"保存成功");
  }

  @RequestMapping(value = "/productDetail/{id}",method = RequestMethod.DELETE)
  public @ResponseBody LejiaResult deleteProductDetail(@PathVariable Integer id){
    productDetailService.deleteProductDetail(id);
    return LejiaResult.build(200,"删除成功");
  }

}
