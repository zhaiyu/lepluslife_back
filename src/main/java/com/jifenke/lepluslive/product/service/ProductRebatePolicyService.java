package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductRebatePolicy;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.repository.ProductRebatePolicyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 规格分润、返鼓励金规则
 * Created by zhangwen on 2017/6/26.
 */
@Service
@Transactional(readOnly = true)
public class ProductRebatePolicyService {

  @Inject
  private ProductRebatePolicyRepository repository;

  public ProductRebatePolicy findByProductSpec(ProductSpec productSpec) {

    return repository.findByProductSpec(productSpec);
  }

  public List<ProductRebatePolicy> listByProduct(Product product) {

    return repository.findAllByProductSpecProduct(product);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void saveRebatePolicy(Map<String, Long> result, ProductRebatePolicy policy) {

    policy.setDateUpdated(new Date());
    policy.setCommission(result.get("commission"));
    policy.setRebateScore(result.get("rebateScore"));
    policy.setToMerchant(result.get("toMerchant"));
    policy.setToPartner(result.get("toPartner"));
    policy.setToPartnerManager(result.get("toPartnerManager"));
    policy.setToLePlusLife(result.get("toLePlusLife"));

    repository.save(policy);
  }

}
