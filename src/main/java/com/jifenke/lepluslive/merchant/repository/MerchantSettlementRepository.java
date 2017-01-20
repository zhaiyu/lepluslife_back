package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 富友结算规则、类型及结算账户 Created by zhangwen on 16/12/6.
 */
public interface MerchantSettlementRepository extends JpaRepository<MerchantSettlement, Long> {

  /**
   * 根据商户号查找对应结算信息  2016/12/29
   *
   * @param merchantNum 商户号
   */
  MerchantSettlement findByMerchantNum(String merchantNum);

  /**
   * 根据商户ID获取商户号列表  2017/01/09
   *
   * @param merchantUserId 商户ID
   */
  List<MerchantSettlement> findByMerchantUserId(Long merchantUserId);

  /**
   * 查询某一商户下富友商户号类型的数量  2017/01/04
   *
   * @param merchantUserId 商户ID
   */
  @Query(value = "SELECT COUNT(1),SUM(IF(type = 1,1,0)),SUM(IF(type = 1,0,1)) FROM merchant_settlement WHERE merchant_user_id = ?1", nativeQuery = true)
  List<Object[]> countByMerchantUser(Long merchantUserId);
}
