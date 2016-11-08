package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductSpecLog;
import com.jifenke.lepluslive.product.service.ProductSpecService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 商品规格管理 Created by zhangwen on 2016/5/5.
 */
@RestController
@RequestMapping("/manage")
public class ProductSpecController {

  @Inject
  private ProductSpecService productSpecService;

  @RequestMapping("/productSpec/{id}")
  public ProductSpec findProductSpecById(@PathVariable Integer id) {
    return productSpecService.findProductSpecById(id);
  }

  /**
   * 新增或修改商品规格
   */
  @RequestMapping(value = "/productSpec", method = RequestMethod.POST)
  public LejiaResult createProductSpec(@RequestBody ProductSpec productSpec) {
    productSpecService.editProductSpec(productSpec);
    return LejiaResult.build(200, "保存成功");
  }

  @RequestMapping(value = "/productSpec/editRepository", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult editRepository(@RequestBody ProductSpecLog productSpecLog) {
    try {
      return productSpecService.editRepository(productSpecLog);
    } catch (Exception e) {
      e.printStackTrace();
      return new LejiaResult(500, "修改商品规格数量失败", null);
    }
  }

  @RequestMapping(value = "/productSpec/putOn/{id}")
  public LejiaResult putProduct(@PathVariable Integer id) {
    return productSpecService.putOnProductSpec(id);
  }

  @RequestMapping(value = "/productSpec/putOff/{id}")
  public LejiaResult pullProduct(@PathVariable Integer id) {
    return productSpecService.pullOffProductSpec(id);
  }

}
