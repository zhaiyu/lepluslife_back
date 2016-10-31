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
  public static final String PARTNER_HB_URL = "http://www.lepluspay.com/poster/partner/downloadPage/";

  public static final String BAR_CODE_EXT = "png";

  public static final Integer COOKIE_DISABLE_TIME = 7200;
  public static final String POS_BILL_URL = "http://lepluslive-pos-excel.oss-cn-beijing.aliyuncs.com/";

  private Constants() {
  }
}
