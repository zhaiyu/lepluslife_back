package com.jifenke.lepluslive.global.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wcg on 16/3/9.
 */
public class MvUtil {

  //新建视图
  public static ModelAndView go(String uri) {
    return new ModelAndView(uri);
  }


  public static String getExtendedName(String fullName) {
    if (fullName != null) {
      String[] arr = fullName.split("\\.");
      return arr[arr.length - 1];
    }
    return null;
  }

  //随机生成文件名
  public static String getFilePath(String extendName) {
    String
        randomStr =
        RandomStringUtils
            .random(10, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");

    return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + randomStr + "." + extendName;
  }


  //生成订单号
  public static String getOrderNumber() {
    String randomStr = RandomStringUtils.random(5, "1234567890");
    return new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + randomStr;
  }

  //生成商户随即号
  public static String getMerchantSid() {
    return RandomStringUtils.random(7, "1234567890");
  }


  /**
   * 生成随机字符串
   */
  public static String getRandomStr() {
    return RandomStringUtils
        .random(16, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
  }

  /**
   * 生成条形码
   */
  public static String getBarCodeStr() {
    return RandomStringUtils.random(13, "1234567890");
  }

  public static void main(String[] args) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.SECOND, -1);

    Date end = calendar.getTime();

    System.out.println(start);
    System.out.println(end);
  }

}
