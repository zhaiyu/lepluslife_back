package com.jifenke.lepluslive.datacenter.domain.criteria;

import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;

/**
 * Created by xf on 2016/9/21.
 */
public class MerchantCriteriaEx extends MerchantCriteria {

  private Long validAmount;                // 有效金额 (大于有效金额为有效订单)

  public Long getValidAmount() {
    return validAmount;
  }

  public void setValidAmount(Long validAmount) {
    this.validAmount = validAmount * 100; //  1:100 (现金:积分)
  }
}
