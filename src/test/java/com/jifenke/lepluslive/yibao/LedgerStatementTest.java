//package com.jifenke.lepluslive.yibao;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.global.util.DataUtils;
//import com.jifenke.lepluslive.yibao.service.LedgerSettlementService;
//import com.jifenke.lepluslive.yibao.service.YBOrderService;
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
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
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
//    String ledgers = "10015501161";
////    String ledgers = "10014282523";
//    String date = "2017-08-13";
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
////    //周六周日不生成通道结算单
////    int day = DataUtils.getDay(new Date());
////    if (day == 1 || day == 7) {
////      System.out.println("周六周日无结算单");
////    }
////
////    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////    String tradeDate = sdf.format(new Date());
//    String tradeDate = "2017-08-13";
//
//    //获取所有的审核通过的易宝子商户号
//    List<Map<String, Object>> list = ybOrderService.findAllLedgers();
//
//    for (Map<String, Object> map : list) {
//      try {
//        Map<String, String>
//            queryMap =
//            YbRequestUtils.querySettlement("" + map.get("ledgerNo"), tradeDate);
//        ledgerSettlementService.createLedgerSettlement(queryMap, map);
//      } catch (Exception e) {
//        log.error("易宝-商户号为" + map.get("ledgerNo") + "的通道结算单统计出现问题");
//      }
//    }
//
//
////    //获取所有的审核通过的易宝子商户号
////    List<Map<String, Object>> list = ybOrderService.findAllLedgers();
////
////    //第一次统计
////    for (Map<String, Object> map : list) {
////      try {
////        Map<String, String>
////            queryMap =
////            YbRequestUtils.querySettlement("" + map.get("ledgerNo"), tradeDate);
////        if (!"1".equals(queryMap.get("code"))) {
////          System.out.println("没有订单");
////        }
////        ledgerSettlementService.createLedgerSettlement(queryMap, map);
////      } catch (Exception e) {
////        log.error("易宝-商户号为" + map.get("ledgerNo") + "的通道结算单统计出现问题");
////      }
////    }
//  }
//
//  //查询最近十日结算状态非终态的结算单，如果成功，修改状态并修改对应门店结算单状态，并发送消息
//  @Test
//  public void updateSettlementTest() throws Exception {
//
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    Date date = new Date();
//    String tradeDate = sdf.format(date);
//    Calendar calendar = Calendar.getInstance();
//    calendar.setTime(date);
//    calendar.add(Calendar.DAY_OF_MONTH, -11);
//    String beginDate = sdf.format(calendar.getTime());
//    calendar.add(Calendar.DAY_OF_MONTH, 10);
//    String endDate = sdf.format(calendar.getTime());
//    //查询最近十日结算状态非终态的结算单，如果成功，修改状态并修改对应门店结算单状态，并发送消息
//    List<Map<String, Object>>
//        settlementList =
//        ybOrderService.findSettlementByStateAndTradeDate(beginDate, endDate);
//    for (Map<String, Object> map : settlementList) {
//      try {
//        Map<String, String>
//            queryMap =
//            YbRequestUtils.querySettlement("" + map.get("ledgerNo"), "" + map.get("tradeDate"));
//        ledgerSettlementService
//            .updateLedgerSettlement(queryMap, Long.valueOf("" + map.get("id")), tradeDate);
//      } catch (Exception e) {
//        log.error(
//            "易宝-商户号为" + map.get("ledgerNo") + "结算日期为" + map.get("tradeDate") + "的通道结算单查询出现问题");
//      }
//    }
//  }
//}
