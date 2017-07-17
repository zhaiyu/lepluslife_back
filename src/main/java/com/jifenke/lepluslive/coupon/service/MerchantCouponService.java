package com.jifenke.lepluslive.coupon.service;

import com.jifenke.lepluslive.coupon.repository.MerchantCouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * 商户优惠券 Created by zhangwen on 2016/11/29.
 */
@Service
@Transactional(readOnly = true)
public class MerchantCouponService {

  @Inject
  private MerchantCouponRepository merchantCouponRepository;

}
