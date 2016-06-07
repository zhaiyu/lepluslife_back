package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductRecommend;
import com.jifenke.lepluslive.product.repository.ProductRecommendRepository;
import com.jifenke.lepluslive.product.repository.ProductRepository;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import javax.inject.Inject;

/**
 * 商品推荐管理 Created by zhangwen on 2016/6/6.
 */
@Service
@Transactional(readOnly = true)
public class ProductRecommendService {

  @Inject
  private ProductRepository productRepository;

  @Inject
  private ProductRecommendRepository recommendRepository;

  @Inject
  private DictionaryService dictionaryService;

  public ProductRecommend findProductRecommendById(Integer id) {
    return recommendRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findAllProductRecommend(Integer offset) {
    Sort sort = new Sort(Sort.Direction.ASC, "sid");
    return recommendRepository.findAllByState(1, new PageRequest(offset - 1, 10, sort));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int editProductRecommend(ProductRecommend productRec) throws Exception {

    ProductRecommend origin = null;
    Product product = null;
    Date date = new Date();
    if (productRec.getId() != null) {
      origin = recommendRepository.findOne(productRec.getId());
      if (origin == null) {
        return 2; //未找到商品推荐
      }
    } else {
      origin = new ProductRecommend();
    }
    if (productRec.getProduct() != null && productRec.getProduct().getId() != null) {
      product = productRepository.findOne(productRec.getProduct().getId());
    }
    if (product == null) {
      return 1; //未找到商品
    }

    try {
      origin.setSid(productRec.getSid());
      origin.setPicture(productRec.getPicture());
      origin.setProduct(product);
      origin.setLastUpDate(date);
      recommendRepository.save(origin);
      dictionaryService.updateProductRecommend(date);
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void putOffProductRec(Integer id) throws Exception {
    Date date = new Date();
    ProductRecommend productRecommend = recommendRepository.findOne(id);
    productRecommend.setState(0);
    productRecommend.setLastUpDate(date);
    dictionaryService.updateProductRecommend(date);
    recommendRepository.save(productRecommend);
  }

}
