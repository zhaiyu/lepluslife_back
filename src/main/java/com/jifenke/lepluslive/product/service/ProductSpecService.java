package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductSpecLog;
import com.jifenke.lepluslive.product.repository.ProductSpecLogRepository;
import com.jifenke.lepluslive.product.repository.ProductSpecRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;

/**
 * 商品规格管理 Created by zhangwen on 2016/5/5.
 */
@Service
@Transactional(readOnly = true)
public class ProductSpecService {

  @Inject
  private ProductSpecRepository productSpecRepository;

  @Inject
  private ProductSpecLogRepository productSpecLogRepository;

  public ProductSpec findProductSpecById(Integer id) {

    return productSpecRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductSpec> findProductSpecsByProduct(Product product) {
    return productSpecRepository.findAllByProduct(product);
  }

  /**
   * 获取所有的上线的规格 16/09/19
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductSpec> findProductSpecsByProductAndState(Product product) {
    return productSpecRepository.findAllByProductAndState(product, 1);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editProductSpec(ProductSpec productSpec) {

    ProductSpec origin = null;
    if (productSpec.getId() != null) {
      origin = productSpecRepository.findOne(productSpec.getId());
    } else {
      origin = new ProductSpec();
      origin.setState(1);
    }
    origin.setSpecDetail(productSpec.getSpecDetail());
    origin.setRepository(productSpec.getRepository());
    origin.setPrice(productSpec.getPrice());
    origin.setMinPrice(productSpec.getMinPrice());
    origin.setMinScore(productSpec.getMinScore());
    origin.setToMerchant(productSpec.getToMerchant());
    origin.setToPartner(productSpec.getToPartner());
    origin.setPicture(productSpec.getPicture());
    origin.setProduct(productSpec.getProduct());

    productSpecRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public LejiaResult editRepository(ProductSpecLog productSpecLog) {
    ProductSpec spec = productSpecRepository.findOne(productSpecLog.getProductSpec().getId());
    if (spec == null) {
      return LejiaResult.build(500, "商品规格不存在");
    }
    if (productSpecLog.getState() == 1) {
      spec.setRepository(spec.getRepository() + productSpecLog.getNumber());
    }
    if (productSpecLog.getState() == 0) {
      if (spec.getRepository() < productSpecLog.getNumber()) {
        return LejiaResult.build(500, "库存不能为负");
      }
      spec.setRepository(spec.getRepository() - productSpecLog.getNumber());
    }
    //保存记录和商品规格
    productSpecLogRepository.save(productSpecLog);
    productSpecRepository.save(spec);
    return LejiaResult.build(200, "成功修改规格数量");
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public LejiaResult putOnProductSpec(Integer id) {
    ProductSpec productSpec = productSpecRepository.findOne(id);
    if (productSpec == null) {
      return LejiaResult.build(500, "商品规格不存在");
    }
    productSpec.setState(1);
    productSpecRepository.save(productSpec);
    return LejiaResult.build(200, "成功上架商品规格");
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public LejiaResult pullOffProductSpec(Integer id) {
    ProductSpec productSpec = productSpecRepository.findOne(id);
    if (productSpec == null) {
      return LejiaResult.build(500, "商品规格不存在");
    }
    productSpec.setState(0);
    productSpecRepository.save(productSpec);
    return LejiaResult.build(200, "成功下架商品规格");
  }

}
