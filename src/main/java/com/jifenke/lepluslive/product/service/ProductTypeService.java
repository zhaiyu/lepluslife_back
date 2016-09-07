package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.product.domain.entities.ProductType;
import com.jifenke.lepluslive.product.repository.ProductTypeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * 商品类别 Created by zhangwen on 2016/8/31.
 */
@Service
@Transactional(readOnly = true)
public class ProductTypeService {

  @Inject
  private ProductTypeRepository productTypeRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductType> findAllType() {
    return productTypeRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ProductType findProductTypeById(Integer id) {
    return productTypeRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int saveProductType(ProductType productType) throws Exception {
    ProductType DBType = null;
    try {
      if (productType.getId() != null) {
        DBType = productTypeRepository.findOne(productType.getId());
        if (DBType == null) {
          return 201;
        }
      }
      if (DBType == null) {
        DBType = new ProductType();
      }
      DBType.setType(productType.getType());
      DBType.setPicture(productType.getPicture());
      productTypeRepository.save(DBType);
      return 200;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

}
