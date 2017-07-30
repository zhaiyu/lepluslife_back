package com.jifenke.lepluslive.yibao.repository;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhangwen on 2017/7/12.
 */
public interface MerchantUserLedgerRepository extends JpaRepository<MerchantUserLedger, Long> {

  Page findAll(Specification<MerchantUserLedger> whereClause, Pageable pageRequest);

  MerchantUserLedger findByLedgerNo(String ledgerNo);

  List<MerchantUserLedger> findAllByMerchantUser(MerchantUser merchantUser);
}
