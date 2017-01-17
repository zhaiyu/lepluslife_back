package com.jifenke.lepluslive.global.config;

/**
 * Application constants.
 */
public final class Constants {

  // Spring profile for development, production and "fast", see http://jhipster.github.io/profiles.html
  public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
  public static final String SPRING_PROFILE_PRODUCTION = "prod";
  public static final String SPRING_PROFILE_FAST = "fast";
  // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
  public static final String SPRING_PROFILE_CLOUD = "cloud";
  // Spring profile used when deploying to Heroku
  public static final String SPRING_PROFILE_HEROKU = "heroku";

  public static final String SYSTEM_ACCOUNT = "system";
  public static final Long ORDER_EXPIRED = 864000000L;
  // public static final Long ORDER_EXPIRED =60000L ;

  public static final String PRODUCT_URL = "http://www.lepluslife.com/weixin/product/";

  public static final String MERCHANT_URL = "http://www.lepluslife.com/lepay/merchant/";

  public static final String PARTNER_URL = "http://www.lepluslife.com/weixin/partner/bind_wx_user/";

  // Poster
  public static final String
      PARTNER_HB_URL =
      "http://www.lepluspay.com/poster/partner/downloadPage/";

  public static final String BAR_CODE_EXT = "png";

  public static final Integer COOKIE_DISABLE_TIME = 7200;
  public static final String
      POS_BILL_URL =
      "http://lepluslive-pos-excel.oss-cn-beijing.aliyuncs.com/";

  public static final String
      PHONE_CHECK =
      "http://api.huafeiduo.com/gateway.cgi?mod=order.phone.check&";//充值前查询是否可充接口

  public static final String
      PHONE_STATUS =
      "http://api.huafeiduo.com/gateway.cgi?mod=order.phone.get&"; //检查是否完成了充值

  public static final String
      PHONE_BALANCE =
      "http://api.huafeiduo.com/gateway.cgi?mod=account.balance&";//查询账户余额接口

  public static final String
      PHONE_SUBMIT =
      "http://api.huafeiduo.com/gateway.cgi?mod=order.phone.submit&";//手机号充值接口

  public static final String
      PHONE_NOTIFY_URL =
      "http://www.lepluslife.com/front/phone/afterPay";//充值回调


  public static final String FUYOU_INS_CD = "08M0063365"; //富友分配的机构号
  public static final String
      FUYOU_REFUND_URL =
      "http://weixinpay.fuiou.com/commonRefund";//富友退款接口地址

  public static final String
      FUYOU_PRI_KEY =
      "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKrADjicUSALdyt/LxqFkNTury0MF92O84xdlVfvGbTyXk+PeJ3N2/LYcIH4HxfHVtEibCBCu4gTsM5hpp6tpsBfBYwnwxY0peSEOZ9EQmz3+9gCThG12m1yNjLYeraU/Gx7hqPX4/doS8urtR0Asum+FV5W3kAgjcukIsGn0kLnAgMBAAECgYAcbXMwq526Bw6lGDygXsJZIQ/bIRtMEMOth9sYU79k58EZ39oF88L9sFky2jta+x4SHXgA+vs70YNrKMjTvDd5o5aTQZpB48TKpJ8c206Up/Gm50HwWjqJHgC1tOnIWRq8qF7AU3zfKjaishz1nAt58z4PEMW+TzWHGe4v9vxz4QJBAOhlyj6QrtIuZ4dvmD/B6itvahTLQKY/WfdXRqbf8kPemxkcWscQT7+bnK+DrfDZhtRo2i7q6POvun0dIxOn3JcCQQC8F3eTzfj4HlIil9RsJjpVlg15rhc7ydQwVZRh2wZR14GV8+yGFogHh1Ba02EB+xQn03T0zoCce5BbDh2H1oYxAkAhDJi+XQT/junaMNyN9J3An4+OdXk0Kz44FolNoftp+3ZDE+008fTlYtPdgfRyk/zAqEie83k9bngu4r3iRbTxAkAfIv9fj2xUnqhYI6w9jwJ/IozuhLxB4IJo0fHzVQ+xwqwoB64y8E3qeSL7NhzL+CV5Bk9JK1otDWNzP13yG7gxAkBDabCAaVQKnhaSQCkUOE3YUFBbSNfkLElctxla/mMoxSJWU/J5ZCXQxd2LnuZmRfK5Txg0rECynJYnvbGldICp";


  private Constants() {
  }
}
