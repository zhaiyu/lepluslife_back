//package com.jifenke.lepluslive.yibao;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.yibao.service.StoreSettlementService;
//import com.jifenke.lepluslive.yibao.service.YBOrderService;
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
//import java.util.List;
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
//public class StoreStatementTest {
//
//
//  private static final Logger log = LoggerFactory.getLogger(StoreStatementTest.class);
//
//
//  @Inject
//  private YBOrderService ybOrderService;
//
//
//  @Inject
//  private StoreSettlementService storeSettlementService;
//
//  //门店结算单任务测试
//  @Test
//  public void transferTest() {
//
//    String tradeDate = "2017-05-26";
//
//    //每日易宝订单统计
//    List<Map<String, Object>> list = ybOrderService.countStoreStatementGroupByMerchantId(tradeDate);
//    //每日退款完成统计
//    Map<String, Object> refundMap = ybOrderService.countRefundGroupByMerchantId(tradeDate);
//
//    for (Map<String, Object> map : list) {
//      try {
//        storeSettlementService.createStoreSettlement(map, refundMap, tradeDate);
//      } catch (Exception e) {
//        log.error("易宝-门店ID为" + map.get("merchantId") + "的统计出现问题");
//      }
//    }
//
//
//
//
//  }
//
//}
