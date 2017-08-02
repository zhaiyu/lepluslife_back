//package com.jifenke.lepluslive.yibao;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.global.util.DataUtils;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.Date;
//
///**
// * Created by zhangwen on 2017/7/27.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest
//@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
//public class DataUtilTest {
//
//  @Test
//  public void getDay() throws Exception {
//    int day = DataUtils.getDay(new Date());
//
//    System.out.println(day);
//  }
//
//  @Test
//  public void dayDiff() throws Exception {
//    String date1 = "2012-23-23";
//    String date2 = "2012-23-33";
//    int day = DataUtils.dayDiff(date1, date2, null);
//
//    System.out.println(day);
//  }
//}
