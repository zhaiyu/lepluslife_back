//package com.jifenke.lepluslive.yibao;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
//import com.jifenke.lepluslive.order.service.ScanCodeOrderService;
//import com.jifenke.lepluslive.yibao.util.YbRequestUtils;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.Map;
//
//import javax.inject.Inject;
//
///**
// * Created by wcg on 16/4/15.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest
//@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
//public class RefundTest {
//
//
//  private static final Logger log = LoggerFactory.getLogger(RefundTest.class);
//
//  @Inject
//  private ScanCodeOrderService scanCodeOrderService;
//
//  /**
//   * 退款测试
//   */
//  @Test
//  public void refundTest() throws Exception {
//
//    System.out.println("===================主商户转账前余额===================");
//    Map<String, String> map = YbRequestUtils.queryBalance("");
//    log.info("主商户余额 = " + map.toString());
//
//    ScanCodeOrder order = scanCodeOrderService.findByOrderSid("17080518545153046");
//
//    Map<String, String> resultMap = YbRequestUtils.refund(order.getOrderSid(), order.getTruePay());
//
//    log.info("退款返回结果 = " + resultMap.toString());
//
//    Thread.sleep(5000);
//
//    System.out.println("===================主商户转账后余额===================");
//    Map<String, String> map2 = YbRequestUtils.queryBalance("");
//    log.info("主商户余额 = " + map2.toString());
//  }
//
//  /**
//   * 退款查询测试
//   */
//  @Test
//  public void queryRefundTest() throws Exception {
//
//    YbRequestUtils.queryRefund("17080518545153046");
//
//  }
//}
