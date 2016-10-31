package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wcg on 16/8/4.
 */
public interface MerchantPosRepository extends JpaRepository<MerchantPos, Long> {

  MerchantPos findByPosId(String posId);

  MerchantPos findById(Long id);

  List<MerchantPos> findAllByMerchant(Merchant merchant);

  Page findAll(Specification<MerchantPos> whereClause, Pageable pageable);

  @Query(value = "select ifnull(sum(case  when rebate_way = 1 or rebate_way = 2  then total_price end),0),ifnull(sum(case  when rebate_way = 3 then total_price end),0) from pos_order where merchant_pos_id  = ?1 and state = 1", nativeQuery = true)
  List<Object[]> countPosOrderFlow(Long id);

  @Query(value = "select count(1) from merchant_pos where merchant_id = ?1 ",nativeQuery = true)
  Long countByMerchant(Long merchantId);
}
