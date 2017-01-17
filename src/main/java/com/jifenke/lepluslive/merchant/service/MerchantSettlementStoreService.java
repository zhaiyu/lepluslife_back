package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlement;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlementStore;
import com.jifenke.lepluslive.merchant.repository.MerchantSettlementStoreRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 门店对应的富友结算规则 Created by zhangwen on 2016/12/29.
 */
@Service
@Transactional(readOnly = true)
public class MerchantSettlementStoreService {


  @Inject
  private MerchantSettlementStoreRepository repository;

  @Inject
  private MerchantSettlementService merchantSettlementService;

  /**
   * 获取富友门店结算规则(如果没有则新建)  17/01/10
   *
   * @param merchantId 门店ID
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public MerchantSettlementStore findByMerchantId(Long merchantId) {
    MerchantSettlementStore store = repository.findByMerchantId(merchantId);
    if (store == null) {
      Date date = new Date();
      store = new MerchantSettlementStore();
      store.setMerchantId(merchantId);
      store.setCreateDate(date);
      store.setLastUpdate(date);
      repository.saveAndFlush(store);
    }
    return store;
  }

  /**
   * 获取使用某商户号的门店ID和NAME列表  16/12/29
   *
   * @param type     商户号类型 0=普通商户号|1=佣金商户号
   * @param settleId 商户号Id
   */
  public Map<String, String> findMerchantIdAndNameBySettleId(Integer type, Long settleId) {
    List<Object[]> list = null;
    Map<String, String> result = new HashMap<>();
    StringBuilder ids = new StringBuilder();
    StringBuilder names = new StringBuilder();
    if (type == 0) {
      list = repository.findMerchantListByCommon(settleId);
    } else {
      list = repository.findMerchantListByAlliance(settleId);
    }
    if (list != null && list.size() > 0) {
      for (Object[] o : list) {
        ids.append(o[0]).append("_");
        names.append(o[1]).append("_");
      }
    }
    if (ids.length() > 0) {
      result.put("ids", ids.toString().substring(0, ids.length() - 1));
    }
    if (names.length() > 0) {
      result.put("names", names.toString().substring(0, names.length() - 1));
    }
    return result;
  }

  /**
   * 新建/修改 门店对应的富友结算规则  2017/01/13
   *
   * @param store 富友结算规则
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveSettlementStore(MerchantSettlementStore store) throws Exception {
    MerchantSettlementStore db_store = null;
    Date date = new Date();
    try {
      if (store.getId() != null) {
        db_store = repository.findOne(store.getId());
      } else {
        db_store = new MerchantSettlementStore();
        db_store.setCreateDate(date);
        db_store.setMerchantId(store.getMerchantId());
      }
      db_store.setLastUpdate(date);
      if (store.getCommonSettlementId() != 0) {
        MerchantSettlement
            settlement =
            merchantSettlementService.findById(store.getCommonSettlementId());
        if (settlement == null) {
          throw new RuntimeException();
        }
      }
      if (store.getAllianceSettlementId() != 0) {
        MerchantSettlement
            settlement =
            merchantSettlementService.findById(store.getAllianceSettlementId());
        if (settlement == null) {
          throw new RuntimeException();
        }
      }
      db_store.setCommonSettlementId(store.getCommonSettlementId());
      db_store.setAllianceSettlementId(store.getAllianceSettlementId());
      repository.save(db_store);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

}
