package com.jifenke.lepluslive.web.rest;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponCode;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponRefund;
import com.jifenke.lepluslive.groupon.repository.GrouponCodeRepository;
import com.jifenke.lepluslive.groupon.repository.GrouponOrderRepository;
import com.jifenke.lepluslive.groupon.repository.GrouponRefundRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantRebatePolicy;
import com.jifenke.lepluslive.merchant.domain.entities.RebateStage;
import com.jifenke.lepluslive.merchant.repository.MerchantRebatePolicyRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.RebateStageRepository;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.score.service.ScoreCService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageSceneService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.service.UserService;
import com.jifenke.lepluslive.withdrawBill.repository.WeixinWithdrawBillRepository;
import com.jifenke.lepluslive.withdrawBill.service.WeiXinWithdrawBillService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by wcg on 16/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
public class ttttt {


    @Inject
    private MerchantRepository merchantRepository;

    @Inject
    private EntityManager entityManager;

    @Inject
    private MerchantRebatePolicyRepository merchantRebatePolicyRepository;

    @Inject
    private RebateStageRepository rebateStageRepository;

    @Inject
    private OffLineOrderService offLineOrderService;

    @Inject
    private ScoreCService scoreCService;

    @Inject
    private ShortMessageService shortMessageService;

    @Inject
    private ShortMessageSceneService shortMessageSceneService;

    @Inject
    private UserService userService;

    @Inject
    private WeixinWithdrawBillRepository weixinWithdrawBillRepository;

    @Inject
    private WeiXinWithdrawBillService weiXinWithdrawBillService;

    //活动注册
    @Test
    public void qqqq() {
        weiXinWithdrawBillService.withdraw(1L);
    }

    @Test
    public void qq() {
        weiXinWithdrawBillService.checkRedPackInfo("1358860502201705116581849585");
    }


    @Inject
    private GrouponOrderRepository grouponOrderRepository;
    @Inject
    private GrouponCodeRepository grouponCodeRepository;
    @Inject
    private GrouponRefundRepository grouponRefundRepository;

    //  添加测试数据
    @Test
    public void grouponOrder() {
        for (int i = 0; i < 20; i++) {
            GrouponOrder grouponOrder = new GrouponOrder();
            GrouponProduct grouponProduct = new GrouponProduct();
            grouponProduct.setId(1L);
            grouponOrder.setGrouponProduct(grouponProduct);
            LeJiaUser lejiaUser = new LeJiaUser();
            lejiaUser.setId(50L);
            grouponOrder.setLeJiaUser(lejiaUser);
            if (i % 2 == 0) {
                if (i > 10) {
                    // 0=待使用  1=已使用  2=退款   //0 未付款 1 已完成 /0 公众号 1 app
                    // 订单类型 0 普通订单 1 乐加订单
                    grouponOrder.setOrderState(1);
                    grouponOrder.setCreateDate(new Date());
                    grouponOrder.setCompleteDate(new Date());
                    grouponOrder.setState(1L);
                    grouponOrder.setOrderType(1L);
                    grouponOrder.setScorea(1000L);
                    grouponOrder.setOrderType(0L);
                    grouponOrder.setScorea(2000L);
                    grouponOrder.setTotalPrice(20000L);
                    grouponOrder.setRebateScorec(1800L);
                    grouponOrder.setRebateScorea(10000L);
                    grouponOrder.setTruePay(6600L);
                    grouponOrder.setPayOrigin(1);
                } else if (i > 15) {
                    grouponOrder.setOrderState(0);
                    grouponOrder.setCreateDate(new Date());
                    grouponOrder.setState(0L);
                    grouponOrder.setScorea(1000L);
                    grouponOrder.setOrderType(0L);
                    grouponOrder.setScorea(860L);
                    grouponOrder.setTotalPrice(120000L);
                    grouponOrder.setRebateScorec(1900L);
                    grouponOrder.setRebateScorea(6000L);
                    grouponOrder.setTruePay(7700L);
                    grouponOrder.setPayOrigin(1);
                } else if (i > 5) {
                    grouponOrder.setOrderState(2);
                    grouponOrder.setCreateDate(new Date());
                    grouponOrder.setCompleteDate(new Date());
                    grouponOrder.setState(1L);
                    grouponOrder.setScorea(1000L);
                    grouponOrder.setOrderType(0L);
                    grouponOrder.setScorea(2121L);
                    grouponOrder.setTotalPrice(120000L);
                    grouponOrder.setRebateScorec(3000L);
                    grouponOrder.setRebateScorea(10000L);
                    grouponOrder.setTruePay(9900L);
                    grouponOrder.setPayOrigin(0);
                }
            } else {
                if (i > 10) {
                    // 0=待使用  1=已使用  2=退款   //0 未付款 1 已完成 /0 公众号 1 app
                    // 订单类型 0 普通订单 1 乐加订单
                    grouponOrder.setOrderState(0);
                    grouponOrder.setCreateDate(new Date());
                    grouponOrder.setCompleteDate(new Date());
                    grouponOrder.setState(1L);
                    grouponOrder.setOrderType(1L);
                    grouponOrder.setScorea(1000L);
                    grouponOrder.setOrderType(0L);
                    grouponOrder.setScorea(2000L);
                    grouponOrder.setTotalPrice(20000L);
                    grouponOrder.setRebateScorec(1800L);
                    grouponOrder.setRebateScorea(10000L);
                    grouponOrder.setTruePay(6600L);
                    grouponOrder.setPayOrigin(0);
                } else if (i > 15) {
                    grouponOrder.setOrderState(0);
                    grouponOrder.setCreateDate(new Date());
                    grouponOrder.setState(0L);
                    grouponOrder.setScorea(1000L);
                    grouponOrder.setOrderType(1L);
                    grouponOrder.setScorea(860L);
                    grouponOrder.setTotalPrice(120000L);
                    grouponOrder.setRebateScorec(1900L);
                    grouponOrder.setRebateScorea(6000L);
                    grouponOrder.setTruePay(7700L);
                    grouponOrder.setPayOrigin(1);
                } else if (i > 5) {
                    grouponOrder.setOrderState(0);
                    grouponOrder.setCreateDate(new Date());
                    grouponOrder.setCompleteDate(new Date());
                    grouponOrder.setState(1L);
                    grouponOrder.setScorea(1000L);
                    grouponOrder.setOrderType(0L);
                    grouponOrder.setScorea(2121L);
                    grouponOrder.setTotalPrice(120000L);
                    grouponOrder.setRebateScorec(3000L);
                    grouponOrder.setRebateScorea(10000L);
                    grouponOrder.setTruePay(9900L);
                    grouponOrder.setPayOrigin(0);
                }
            }
            grouponOrderRepository.save(grouponOrder);
        }
    }

    @Test
    public void grouponCode() {
        List<GrouponOrder> all = grouponOrderRepository.findAll();
        Merchant merchant = new Merchant();
        merchant.setId(1L);
        for (int i = 0; i < all.size(); i++) {
            GrouponCode groupCode = new GrouponCode();
            groupCode.setGrouponOrder(all.get(i));
            groupCode.setShareToLockMerchant(0L);
            groupCode.setShareToLockPartner(0L);
            groupCode.setShareToLockPartnerManager(0L);
            groupCode.setShareToTradePartner(0L);
            groupCode.setShareToTradePartnerManager(0L);
            groupCode.setCreateDate(new Date());
            groupCode.setCommission(100L);
            groupCode.setLeJiaUser(all.get(i).getLeJiaUser());
            groupCode.setMerchant(merchant);
            groupCode.setSid(MvUtil.getMerchantSid());
            groupCode.setExpiredDate(new Date());
            groupCode.setStartDate(new Date());
            if (i % 2 == 0) {
                groupCode.setCodeType(0);
                groupCode.setCheckDate(new Date());
                groupCode.setMerchantUser("AngelaBaby");
            } else {
                groupCode.setCodeType(1);
                groupCode.setMerchantUser("范冰冰");
            }
            grouponCodeRepository.save(groupCode);
        }
    }

    @Test
    public void grouponRefund() {
        List<GrouponOrder> all = grouponOrderRepository.findAll();
        for (int i = 0; i < all.size(); i++) {
            if(i%2!=0) {
                GrouponOrder order = all.get(i);
                GrouponRefund r1= new GrouponRefund();
                if(i<10) {
                    r1.setState(0);
                }else {
                    r1.setState(2);
                    r1.setCompleteDate(new Date());
                }
                r1.setGrouponOrder(order);
                r1.setRecycleScorea(100L);
                r1.setRecycleScorec(100L);
                r1.setRefundNum(100);
                r1.setReturnScorea(1000L);
                r1.setReturnTruePay(1000L);
                grouponRefundRepository.save(r1);
            }else {
                //0 退款中 1 退款完成 2 退款驳回
                GrouponOrder order = all.get(i);
                GrouponRefund r2= new GrouponRefund();
                r2.setState(1);
                r2.setCompleteDate(new Date());
                r2.setGrouponOrder(order);
                r2.setRecycleScorea(100L);
                r2.setRecycleScorec(100L);
                r2.setRefundNum(100);
                r2.setReturnScorea(1000L);
                r2.setReturnTruePay(1000L);
                grouponRefundRepository.save(r2);
            }
        }
    }

}
