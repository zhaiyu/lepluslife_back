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

  //随机生成没有文件后缀有前缀的文件名
  public static String getFileName(String prefix) {
    String
        randomStr =
        RandomStringUtils
            .random(2, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");

    return prefix + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + randomStr;
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

  //生成合伙人随机号
  public static String getPartnerSid() {
    return RandomStringUtils.random(7, "1234567890");
  }

  /**
   * 生成n位随机数
   */
  public static String getRandomNumber(Integer number) {
    return RandomStringUtils.random(number, "0123456789");

  }

  /**
   * 生成时间戳+n位随机数
   *
   * @param random 随机位数
   */
  public static String getOrderNumber(int random) {
    String randomStr = RandomStringUtils.random(random, "1234567890");
    return new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + randomStr;
  }

  public static String getMerchantUserSid() {
    return RandomStringUtils.randomAlphanumeric(7);                 // 字母和数字的随机组合字符串 [7位]
  }


  /**
   * 生成随机字符串
   */
  public static String getRandomStr() {
    return RandomStringUtils
        .random(16, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
  }

  /**
   * 生成n位随机字符串
   */
  public static String getRandomStr(int count) {
    return RandomStringUtils
        .random(count, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
  }

  /**
   * 生成条形码
   */
  public static String getBarCodeStr() {
    return RandomStringUtils.random(13, "1234567890");
  }

//  public static void main(String[] args) {
//    for(int i = 0;i<7;i++){
//      System.out.println(getPartnerSid());
//    }
//  }

  //短信随机号
  public static String getShortMessageSceneNumber() {
    String randomStr = RandomStringUtils.random(5, "1234567890");
    return new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + randomStr;
  }

}
