//package com.jifenke.lepluslive.yibao;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.global.util.MvUtil;
//import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
//import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransferLog;
//import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
//import com.jifenke.lepluslive.yibao.service.LedgerTransferLogService;
//import com.jifenke.lepluslive.yibao.service.LedgerTransferService;
//import com.jifenke.lepluslive.yibao.service.MerchantUserLedgerService;
//import com.jifenke.lepluslive.yibao.util.YbRequestUtils;
//import com.jifenke.lepluslive.yibao.util.ZGTUtils;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
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
//public class MerchantUserLedgerTest {
//
//
//  @Inject
//  private MerchantUserLedgerService merchantUserLedgerService;
//
//  //子商户注册
//  @Test
//  public void registerTest() {
//
//    MerchantUserLedger ledger = new MerchantUserLedger();
//    ledger.setId(0L);
//    ledger.setAccountName("张文");
//    ledger.setBankAccountNumber("6228481108572810978");
//    ledger.setBankAccountType(1);
//    ledger.setBankCity("北京市");
//    ledger.setBankName("北京农业银行西三旗支行");
//    ledger.setBankProvince("北京市");
//    ledger.setBindMobile("15501066812");
//    ledger.setIdCard("370481199201076437");
//    ledger.setLinkman("张文");
//    ledger.setMinSettleAmount(10000);
//    ledger.setSignedName("张文");
//
//    ledger.setCustomerType(1);
//    ledger.setBusinessLicence("fksdfdierwer3323242");
//    ledger.setLegalPerson("张文");
//
//    merchantUserLedgerService.edit(ledger);
//  }
//
//  //配置文件加载测试
//  @Test
//  public void getConfigureTest() {
//
//    String keyForHmac = ZGTUtils.getKeyForHmac();
//
//    System.out.println("keyForHmac=" + keyForHmac);
//  }
//
//  @Inject
//  private LedgerTransferService transferService;
//
//  //转账测试
//  @Test
//  public void TransferTest() {
//
//    String ledgerNo = "10014282423"; //测试子商号
////    String ledgerNo = "10015331444"; //测试子商号(冻结)
//
//    Map<String, String> before = YbRequestUtils.queryBalance(ledgerNo);
//
//    System.out.println("转账前子商户==" + before);
//
//    Map<String, String> manBefore = YbRequestUtils.queryBalance("");
//
//    System.out.println("转账前主商户==" + manBefore);
//
//    Long amount = 9900L;  //分
//
//    String tradeDate = "2017-07-19";
//
//    transferService.transfer(ledgerNo, amount, tradeDate,1);
//
//    Map<String, String> after = YbRequestUtils.queryBalance(ledgerNo);
//
//    System.out.println("转账后子商户==" + after);
//
//    Map<String, String> manAfter = YbRequestUtils.queryBalance("");
//
//    System.out.println("转账后主商户==" + manAfter);
//
//  }
//
//  //long 单位转换测试 分-->元
//  @Test
//  public void unitTest() {
//
//    Long a = 1234L;
//
//    String b = "" + a / 100.0;
//    System.out.println("b===" + b);
//
//    Long a2 = 1230L;
//
//    String b2 = "" + a2 / 100.0;
//    System.out.println("b2===" + b2);
//
//    Long a3 = 1200L;
//
//    String b3 = "" + a3 / 100.0;
//    System.out.println("b3===" + b3);
//
//    Long a4 = 5L;
//
//    String b4 = "" + a4 / 100.0;
//    System.out.println("b4===" + b4);
//
//    Long a5 = 70L;
//
//    String b5 = "" + a5 / 100.0;
//    System.out.println("b5===" + b5);
//  }
//
//  @Inject
//  private LedgerTransferLogService ledgerTransferLogService;
//
//
//  //临时测试
//  @Test
//  public void tempTest() {
//
//    LedgerTransferLog log = new LedgerTransferLog();
//
//    ledgerTransferLogService.saveLog(log);
//
//
//  }
//
//}
