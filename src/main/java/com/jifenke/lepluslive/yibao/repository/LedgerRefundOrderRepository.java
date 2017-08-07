package com.jifenke.lepluslive.yibao.repository;

import com.jifenke.lepluslive.yibao.domain.entities.LedgerRefundOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhangwen on 2017/7/12.
 */
public interface LedgerRefundOrderRepository extends JpaRepository<LedgerRefundOrder, Long> {

    Page findAll(Specification<LedgerRefundOrder> whereClause, Pageable pageRequest);

    LedgerRefundOrder findByOrderSid(String orderSid);

}
