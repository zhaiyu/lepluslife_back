package com.jifenke.lepluslive.global.config;

import java.math.BigDecimal;

/**
 * 支付相关全局参数 (易宝&)
 * Created by zhangwen on 2017/7/12.
 */
public final class PaymentConstants {

  public static final BigDecimal WX_WEB_RATE = new BigDecimal(0.3);//微信WEB公众号成本费率

  public static final BigDecimal WX_APP_RATE = new BigDecimal(0.3);//微信APP成本费率

  public static final BigDecimal ALI_WEB_RATE = new BigDecimal(0.3);//支付宝WEB成本费率

  public static final BigDecimal ALI_APP_RATE = new BigDecimal(0.3);//支付宝APP成本费率

  public static final int SETTLEMENT_COST = 150;  //单笔结算费用

}
