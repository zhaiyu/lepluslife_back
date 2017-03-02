package com.jifenke.lepluslive.yinlian.repository;

import com.jifenke.lepluslive.yinlian.domain.entities.UnionPayStore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 银联商务门店 Created by zhangwen on 16/12/19.
 */
public interface UnionPayStoreRepository extends JpaRepository<UnionPayStore, Long> {

  /**
   * 获取所有的门店号  2017/01/18
   *
   */
  @Query(value = "SELECT shop_number FROM union_pay_store WHERE merchant_num IS NOT NULL", nativeQuery = true)
  List<String> findAllMerchantNum();

}
