package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhangwen on 16/9/3.
 */
public interface MerchantDetailRepository extends JpaRepository<MerchantDetail, Long> {

  List<MerchantDetail> findAllByMerchant(Merchant merchant);

  Page findAll(Specification<MerchantDetail> whereClause, Pageable pageable);
}
