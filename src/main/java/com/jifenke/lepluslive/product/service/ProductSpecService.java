package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.product.controller.dto.ProductSpecDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductRebatePolicy;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductSpecLog;
import com.jifenke.lepluslive.product.repository.ProductSpecLogRepository;
import com.jifenke.lepluslive.product.repository.ProductSpecRepository;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Inject
  private ProductRebatePolicyService productRebatePolicyService;

  @Inject
  private DictionaryService dictionaryService;

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

  /**
   * 新增或编辑金币商品规格 2017/6/26
   *
   * @param productSpec 规格
   */
  @Transactional(propagation = Propagation.REQUIRED)
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
    origin.setProfit(productSpec.getProfit());
    origin.setToMerchant(productSpec.getToMerchant());
    origin.setToPartner(productSpec.getToPartner());
    origin.setPicture(productSpec.getPicture());
    origin.setProduct(productSpec.getProduct());

    productSpecRepository.save(origin);
  }

  /**
   * 新增或编辑规格 2017/6/26
   *
   * @param productSpecDto 规格包装类
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void editProductSpec(ProductSpecDto productSpecDto) throws Exception {

    ProductSpec origin = null;
    ProductRebatePolicy policy = null;
    if (productSpecDto.getId() != null) {
      origin = productSpecRepository.findOne(productSpecDto.getId());
      policy = productRebatePolicyService.findByProductSpec(origin);
    } else {
      origin = new ProductSpec();
      origin.setState(1);
      policy = new ProductRebatePolicy();
    }
    origin.setSpecDetail(productSpecDto.getSpecDetail());
    origin.setRepository(productSpecDto.getRepository());
    origin.setPicture(productSpecDto.getPicture());
    origin.setProduct(productSpecDto.getProduct());

    Map<String, Long> result = getRebateMap(productSpecDto);

    origin.setPrice(result.get("price"));
    origin.setCostPrice(result.get("costPrice").intValue());
    origin.setOtherPrice(result.get("otherPrice").intValue());
    origin.setPrice(result.get("price"));
    origin.setMinPrice(result.get("minPrice"));
    origin.setMinScore(result.get("minScore").intValue());
    origin.setProfit(result.get("toLePlusLife").intValue());
    origin.setToMerchant(result.get("toMerchant"));
    origin.setToPartner(result.get("toPartner"));
    ProductSpec save = productSpecRepository.save(origin);
    policy.setProductSpec(save);
    productRebatePolicyService.saveRebatePolicy(result, policy);
  }

  @Transactional(propagation = Propagation.REQUIRED)
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

  @Transactional(propagation = Propagation.REQUIRED)
  public LejiaResult putOnProductSpec(Integer id) {
    ProductSpec productSpec = productSpecRepository.findOne(id);
    if (productSpec == null) {
      return LejiaResult.build(500, "商品规格不存在");
    }
    productSpec.setState(1);
    productSpecRepository.save(productSpec);
    return LejiaResult.build(200, "成功上架商品规格");
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public LejiaResult pullOffProductSpec(Integer id) {
    ProductSpec productSpec = productSpecRepository.findOne(id);
    if (productSpec == null) {
      return LejiaResult.build(500, "商品规格不存在");
    }
    productSpec.setState(0);
    productSpecRepository.save(productSpec);
    return LejiaResult.build(200, "成功下架商品规格");
  }

  /**
   * 计算规格对应的分润等数据  2017/6/26
   */
  private Map<String, Long> getRebateMap(ProductSpecDto dto) throws Exception {

    Map<String, Long> result = new HashMap<>();
    BigDecimal X100 = new BigDecimal(100);

    BigDecimal totalPrice = new BigDecimal(dto.getPrice());
    BigDecimal costPrice = new BigDecimal(dto.getCostPrice());
    BigDecimal otherPrice = new BigDecimal(dto.getOtherPrice());
    long minScore = 0L;
    long minPrice = 0L;
    long rebateScore = 0L;
    long toMerchant = 0L;
    long toPartner = 0L;
    long toPartnerManager = 0L;
    long toLePlusLife = 0L;

    String[] split = dictionaryService.findDictionaryById(66L).getValue().split("_");

    //毛利润
    BigDecimal profit = totalPrice.subtract(costPrice).subtract(otherPrice);

    long price = totalPrice.multiply(X100).longValue();
    long commission = 0;
    if (profit.doubleValue() < 0) {
      throw new RuntimeException("金额填写有误");
    }

    try {
      if (profit.doubleValue() != 0) {
        //可用金币
        BigDecimal minScoreDecimal = profit.multiply(new BigDecimal(split[0]))
            .setScale(0, BigDecimal.ROUND_HALF_UP);
        minScore = minScoreDecimal.multiply(X100).longValue();
        minPrice = price - minScore; //实付
        //返鼓励金
        BigDecimal
            rebateScoreDecimal =
            profit.multiply(new BigDecimal(split[1])).setScale(2, BigDecimal.ROUND_HALF_UP);
        rebateScore = rebateScoreDecimal.multiply(X100).longValue();

        //分润总金额
        BigDecimal
            totalCommission =
            profit.subtract(minScoreDecimal).subtract(rebateScoreDecimal);
        commission = totalCommission.multiply(X100).longValue();
        //锁定商家合伙人
        toMerchant =
            totalCommission.multiply(new BigDecimal(split[2])).setScale(2, BigDecimal.ROUND_HALF_UP)
                .multiply(X100).longValue();
        //锁定商圈合伙人
        toPartner =
            totalCommission.multiply(new BigDecimal(split[3])).setScale(2, BigDecimal.ROUND_HALF_UP)
                .multiply(X100).longValue();
        //锁定城市合伙人
        toPartnerManager =
            totalCommission.multiply(new BigDecimal(split[4])).setScale(2, BigDecimal.ROUND_HALF_UP)
                .multiply(X100).longValue();
        //积分客
        toLePlusLife = commission - toMerchant - toPartner - toPartnerManager;
      } else {
        minPrice = price;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }

    result.put("price", price);
    result.put("minScore", minScore);
    result.put("minPrice", minPrice);
    result.put("costPrice", costPrice.multiply(X100).longValue());
    result.put("otherPrice", otherPrice.multiply(X100).longValue());

    result.put("rebateScore", rebateScore);
    result.put("commission", commission);
    result.put("toMerchant", toMerchant);
    result.put("toPartner", toPartner);
    result.put("toPartnerManager", toPartnerManager);
    result.put("toLePlusLife", toLePlusLife);

    return result;
  }

}
