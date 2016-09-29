package com.jifenke.lepluslive.global.util;

import java.math.BigDecimal;

/**
 * Created by xf on 2016/9/28.
 */
public class ParseUtil {
  //  判断是否可以转换为数字
  public static boolean isNum(String str) {
    try {
      new BigDecimal(str);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
