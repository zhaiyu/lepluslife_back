package com.jifenke.lepluslive.coupon.repository;

import com.jifenke.lepluslive.coupon.domain.entities.CashCouponProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CashCouponProductRepository
 *  满减券产品
 * @author XF
 * @date 2017/7/6
 */
public interface CashCouponProductRepository extends JpaRepository<CashCouponProduct,Long>{
    Page findAll(Specification<CashCouponProduct> whereClause, Pageable pageRequest);
}
