package com.jifenke.lepluslive.withdrawBill.repository;

import com.jifenke.lepluslive.withdrawBill.domain.entities.WeiXinWithdrawBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/8/26.
 */
public interface WeixinWithdrawBillRepository extends JpaRepository<WeiXinWithdrawBill,Long> {

  WeiXinWithdrawBill findByMchBillno(String mchBillno);

  Page findAll(Specification<WeiXinWithdrawBill> whereClause, Pageable pageRequest);

}
