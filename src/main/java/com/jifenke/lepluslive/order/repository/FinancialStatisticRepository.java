package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Created by wcg on 16/5/9.
 */
public interface FinancialStatisticRepository  extends JpaRepository<FinancialStatistic,Long>{


  Page findAll(Specification<FinancialStatistic> financialClause, Pageable pageRequest);

  List<FinancialStatistic> findAllByState(int i);

  Optional<FinancialStatistic> findByMerchantAndBalanceDate(Merchant merchant, Date end);
}
