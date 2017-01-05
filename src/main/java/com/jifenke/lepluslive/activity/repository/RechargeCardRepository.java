package com.jifenke.lepluslive.activity.repository;

import com.jifenke.lepluslive.activity.domain.entities.RechargeCard;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by tqy on 2017/1/4.
 */
public interface RechargeCardRepository extends JpaRepository<RechargeCard, Long> {

  Page findAll(Specification<RechargeCard> specification, Pageable pageRequest);

  @Query(value="SELECT new MerchantUser(id,merchantName) FROM MerchantUser mu where mu.type =?1 ")
  List<MerchantUser> findMerchantUserByType(Integer type);
}
