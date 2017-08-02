//package com.jifenke.lepluslive.web.rest;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.global.util.MvUtil;
//import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
//import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
//import com.jifenke.lepluslive.yibao.domain.entities.LedgerRefundOrder;
//import com.jifenke.lepluslive.yibao.domain.entities.LedgerSettlement;
//import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
//import com.jifenke.lepluslive.yibao.domain.entities.StoreSettlement;
//import com.jifenke.lepluslive.yibao.repository.LedgerRefundOrderRepository;
//import com.jifenke.lepluslive.yibao.repository.LedgerSettlementRepository;
//import com.jifenke.lepluslive.yibao.repository.LedgerTransferRepository;
//import com.jifenke.lepluslive.yibao.repository.StoreSettlementRepository;
//import com.redis.M;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.inject.Inject;
//import java.util.Date;
//
///**
// * Created by xf on 17-7-18.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest
//@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
//public class TempTest {
//    @Inject
//    private LedgerRefundOrderRepository ledgerRefundOrderRepository;
//    @Inject
//    private LedgerSettlementRepository ledgerSettlementRepository;
//    @Inject
//    private StoreSettlementRepository storeSettlementRepository;
//    @Inject
//    private LedgerTransferRepository ledgerTransferRepository;
//    @Inject
//    private MerchantRepository merchantRepository;
//
//    //  添加测试数据
//    @Test
//    public void grouponOrder() {
//        for (int i = 0; i < 20; i++) {
//            StoreSettlement settlement = new StoreSettlement();
//            settlement.setState(4);
//            settlement.setTradeDate("2017-07-18");
//            settlement.setDateCreated(new Date());
//            settlement.setAliTruePayTransfer(870L);
//            settlement.setWxTruePayTransfer(100L);
//            settlement.setScoreTransfer(100L);
//            settlement.setLedgerSid("283140213");
//            settlement.setLedgerNo("23148021394");
//            settlement.setActualTransfer(800L);
//            settlement.setRefundExpend(20L);
//            settlement.setRefundNumber(21);
//            settlement.setLedgerNo("grown-nonull");
//            settlement.setTradeDate("2017-07-17");
//            settlement.setOrderSid(MvUtil.getOrderNumber());
//            Merchant two = merchantRepository.findOne(5L);
//            settlement.setMerchant(two);
//            if (i % 2 == 0) {//0=待查询，1=打款成功，2=已退回，3=无结算记录，4=已扣款未打款，5=打款中，-1=打款失败，-2=银行返回打款失败
//                if (i > 10) {
//                    Merchant one = merchantRepository.findOne(1L);
//                    settlement.setMerchant(one);
//                    settlement.setState(0);
//                    settlement.setLedgerNo("23149-ad");
//                    settlement.setTradeDate("2017-07-17");
//                } else if (i > 15) {
//                    Merchant one = merchantRepository.findOne(3L);
//                    settlement.setMerchant(one);
//                    settlement.setState(1);
//                    settlement.setLedgerNo("23149-asdp");
//                    settlement.setTradeDate("2017-07-19");
//                } else {
//                    Merchant one = merchantRepository.findOne(5L);
//                    settlement.setMerchant(one);
//                    settlement.setState(2);
//                    settlement.setLedgerNo("2seuwr-wen");
//                    settlement.setTradeDate("2017-07-17");
//                }
//            } else {
//                settlement.setState(3);
//                settlement.setLedgerNo("grown-wen");
//                settlement.setTradeDate("2017-07-17");
//            }
//            storeSettlementRepository.save(settlement);
//        }
//
//    }
//
//}
