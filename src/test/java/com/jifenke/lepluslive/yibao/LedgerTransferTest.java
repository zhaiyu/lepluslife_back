//package com.jifenke.lepluslive.yibao;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.global.util.MvUtil;
//import com.jifenke.lepluslive.yibao.service.LedgerTransferService;
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
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
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
//public class LedgerTransferTest {
//
//
//  private static final Logger log = LoggerFactory.getLogger(LedgerTransferTest.class);
//
//
//  @Inject
//  private YBOrderService ybOrderService;
//
//
//  @Inject
//  private LedgerTransferService ledgerTransferService;
//
//  //转账定时任务测试
//  @Test
//  public void transferTest() {
//
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    String tradeDate = sdf.format(new Date());
//
//    //每日易宝订单统计
//    List<Map<String, Object>> list = new ArrayList<>();
//    Map<String, Object> map1 = new HashMap<>();
//    map1.put("ledgerNo", "10015388496"); //-张文
//    map1.put("transferMoney", 1111);
//    list.add(map1);
//    Map<String, Object> map2 = new HashMap<>();
//    map2.put("ledgerNo", "10015389723"); //-张强
//    map2.put("transferMoney", 1);
//    list.add(map2);
////    Map<String, Object> map3 = new HashMap<>();
////    map3.put("ledgerNo", "10015343947");
////    map3.put("transferMoney", 10);
////    list.add(map3);
//    System.out.println("===================子商户转账前余额===================");
//    YbRequestUtils.queryBalance("10015388496,10015389723");
//    System.out.println("===================主商户转账前余额===================");
//    YbRequestUtils.queryBalance("");
//
//    //每日退款完成统计
//    Map<String, Object> objectMap = new HashMap<>();
////    objectMap.put("10014282423",2);
////    objectMap.put("10015331444",1);
////
////    for (Map<String, Object> map : list) {
////      try {
////        //获取商户号今日退款金额
////        String ledgerNo = "" + map.get("ledgerNo");
////        Long transferMoney = Long.valueOf("" + map.get("transferMoney"));
////        if (objectMap.get(ledgerNo) != null) {
////          transferMoney -= Long.valueOf("" + objectMap.get(ledgerNo));
////        }
////        if (transferMoney > 0) {
////          ledgerTransferService.transfer(ledgerNo, transferMoney, tradeDate, 2);
////        } else {
////          log.error("商户号========" + map.get("ledgerNo") + "的统计出现负数问题");
////        }
////      } catch (Exception e) {
////        e.printStackTrace();
////        log.error("商户号为" + map.get("ledgerNo") + "的统计出现问题");
////      }
////    }
//
//
//  }
//
//  //转账接口测试
//  @Test
//  public void singleTransferTest() throws Exception {
//    YbRequestUtils.transfer("10015388496", 11042L, MvUtil.getOrderNumber());
//  }
//}
