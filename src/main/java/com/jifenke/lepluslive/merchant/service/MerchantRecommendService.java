package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantRecommend;
import com.jifenke.lepluslive.merchant.repository.MerchantRecommendRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
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
 * 商家推荐管理 Created by zhangwen on 2016/5/5.
 */
@Service
@Transactional(readOnly = true)
public class MerchantRecommendService {

  @Inject
  private MerchantRepository merchantRepository;

  @Inject
  private MerchantRecommendRepository recommendRepository;

  @Inject
  private DictionaryService dictionaryService;

  public MerchantRecommend findMerchantRecommendById(Integer id) {
    return recommendRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findAllMerchantRecommend(Integer offset) {
    Sort sort = new Sort(Sort.Direction.ASC, "sid");
    return recommendRepository.findAllByState(1, new PageRequest(offset - 1, 10, sort));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int editMerchantRecommend(MerchantRecommend merchantRec) throws Exception {
    Date date = new Date();
    MerchantRecommend origin = null;
    Merchant merchant = null;
    if (merchantRec.getId() != null) {
      origin = recommendRepository.findOne(merchantRec.getId());
      if (origin == null) {
        return 2; //未找到商家推荐
      }
    } else {
      origin = new MerchantRecommend();
    }
    if (merchantRec.getMerchant() != null && merchantRec.getMerchant().getId() != null) {
      merchant = merchantRepository.findOne(merchantRec.getMerchant().getId());
    }
    if (merchant == null) {
      return 1; //未找到商家
    }
    try {
      origin.setSid(merchantRec.getSid());
      origin.setMerchant(merchant);
      origin.setLastUpDate(date);
      recommendRepository.save(origin);
      dictionaryService.updateMerchantRecommend(date);
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void putOffMerchantRec(Integer id) throws Exception {
    Date date = new Date();
    MerchantRecommend merchantRecommend = recommendRepository.findOne(id);
    merchantRecommend.setState(0);
    merchantRecommend.setLastUpDate(date);
    dictionaryService.updateMerchantRecommend(date);
    recommendRepository.save(merchantRecommend);
  }

}
