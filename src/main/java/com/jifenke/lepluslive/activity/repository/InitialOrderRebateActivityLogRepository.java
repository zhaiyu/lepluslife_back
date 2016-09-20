package com.jifenke.lepluslive.activity.repository;

import com.jifenke.lepluslive.activity.domain.entities.InitialOrderRebateActivity;
import com.jifenke.lepluslive.activity.domain.entities.InitialOrderRebateActivityLog;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/9/18.
 */
public interface InitialOrderRebateActivityLogRepository
    extends JpaRepository<InitialOrderRebateActivityLog, Long> {

  Page findAll(Specification<InitialOrderRebateActivityLog> whereClause, Pageable pageable);
}
