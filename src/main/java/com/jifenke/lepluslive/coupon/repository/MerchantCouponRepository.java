package com.jifenke.lepluslive.coupon.repository;

import com.jifenke.lepluslive.coupon.domain.entities.MerchantCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商家优惠券 Created by zhangwen on 16/11/29.
 */
public interface MerchantCouponRepository extends JpaRepository<MerchantCoupon, Long> {

}
