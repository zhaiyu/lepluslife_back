package com.jifenke.lepluslive.coupon.repository;

import com.jifenke.lepluslive.coupon.domain.entities.CashCouponLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CashCouponLogRepository
 *   领券记录
 * @author XF
 * @date 2017/7/5
 */
public interface CashCouponLogRepository  extends JpaRepository<CashCouponLog,Long>{
    Page findAll(Specification<CashCouponLog> whereClause, Pageable pageRequest);
}
