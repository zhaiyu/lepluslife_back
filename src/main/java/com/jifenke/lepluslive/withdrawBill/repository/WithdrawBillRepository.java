package com.jifenke.lepluslive.withdrawBill.repository;

import com.jifenke.lepluslive.withdrawBill.domain.entities.WithdrawBill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/8/26.
 */
public interface WithdrawBillRepository extends JpaRepository<WithdrawBill,Long> {

  Page findAll(Specification<WithdrawBill> whereClause, Pageable pageRequest);

}
