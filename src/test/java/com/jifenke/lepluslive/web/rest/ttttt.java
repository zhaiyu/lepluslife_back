package com.jifenke.lepluslive.web.rest;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.global.config.Constants;
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




}
