//package com.jifenke.lepluslive.yibao;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.global.util.DataUtils;
//import com.jifenke.lepluslive.yibao.service.LedgerSettlementService;
//import com.jifenke.lepluslive.yibao.service.YBOrderService;
//import com.jifenke.lepluslive.yibao.util.YbRequestUtils;
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
//import javax.inject.Inject;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by wcg on 16/4/15.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest
//@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
//public class LedgerStatementTest {
//
//
//  private static final Logger log = LoggerFactory.getLogger(LedgerStatementTest.class);
//
//  @Inject
//  private YBOrderService ybOrderService;
//
//
//  //结算单查询接口测试
//  @Test
//  public void ledgerSettlementTest() {
//
//    String ledgers = "10015388496|10015389723";
////    String ledgers = "10014282523";
//    String date = "2017-07-27";
//    Map<String, String> map = YbRequestUtils.querySettlement(ledgers, date);
//
//    System.out.println("" + map);
//
//  }
//
//
//  //获取所有的子商户号测试
//  @Test
//  public void findAllLedgersTest() {
//
//    List<Map<String, Object>> allLedgers = ybOrderService.findAllLedgers();
//
//    System.out.println("" + allLedgers);
//
//  }
//
//  @Test
//  public void getActualTransfer() throws Exception {
//
//    Long transfer = ybOrderService.findActualTransfer(3, "2016-07-1", "10014282423");
//
//    System.out.println("转账金额为=====" + transfer);
//
//  }
//
//  @Inject
//  private LedgerSettlementService ledgerSettlementService;
//
//  @Test
//  public void jobTest() throws Exception {
//
//    //周六周日不生成通道结算单
//    int day = DataUtils.getDay(new Date());
//    if (day == 1 || day == 7) {
//      System.out.println("周六周日无结算单");
//    }
//
////    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////    String tradeDate = sdf.format(new Date());
//    String tradeDate = "2017-07-27";
//
//
//    //获取所有的审核通过的易宝子商户号
//    List<Map<String, Object>> list = ybOrderService.findAllLedgers();
//
//    //第一次统计
//    for (Map<String, Object> map : list) {
//      try {
//        Map<String, String>
//            queryMap =
//            YbRequestUtils.querySettlement("" + map.get("ledgerNo"), tradeDate);
//        if (!"1".equals(queryMap.get("code"))) {
//          System.out.println("没有订单");
//        }
//        ledgerSettlementService.createLedgerSettlement(queryMap, map);
//      } catch (Exception e) {
//        log.error("易宝-商户号为" + map.get("ledgerNo") + "的通道结算单统计出现问题");
//      }
//    }
//  }
//}
