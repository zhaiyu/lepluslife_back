package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlementStore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 富友门店结算规则 Created by zhangwen on 16/12/6.
 */
public interface MerchantSettlementStoreRepository
    extends JpaRepository<MerchantSettlementStore, Long> {

  /**
   * 获取富友门店结算规则  16/12/6
   *
   * @param merchantId 门店ID
   */
  MerchantSettlementStore findByMerchantId(Long merchantId);

  /**
   * 获取使用某普通商户号的门店ID和NAME列表  16/12/29
   *
   * @param settleId 普通商户号Id
   */
  @Query(value = "SELECT m.id,m.`name` FROM merchant_settlement_store s INNER JOIN merchant m ON s.merchant_id = m.id WHERE s.common_settlement_id = ?1", nativeQuery = true)
  List<Object[]> findMerchantListByCommon(Long settleId);

  /**
   * 获取使用某佣金商户号的门店ID和NAME列表  16/12/29
   *
   * @param settleId 佣金商户号Id
   */
  @Query(value = "SELECT m.id,m.`name` FROM merchant_settlement_store s INNER JOIN merchant m ON s.merchant_id = m.id WHERE s.alliance_settlement_id = ?1", nativeQuery = true)
  List<Object[]> findMerchantListByAlliance(Long settleId);

}
