package com.jifenke.lepluslive.web.rest;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.activity.service.ActivityPhoneOrderService;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.fuyou.service.ScanCodeOrderService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantRebatePolicy;
import com.jifenke.lepluslive.merchant.domain.entities.RebateStage;
import com.jifenke.lepluslive.merchant.repository.MerchantRebatePolicyRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.RebateStageRepository;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.domain.entities.PosErrorLog;
import com.jifenke.lepluslive.order.service.FinanicalStatisticService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.order.service.PosOrderService;
import com.jifenke.lepluslive.partner.repository.PartnerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;
import com.jifenke.lepluslive.score.domain.entities.ScoreC;
import com.jifenke.lepluslive.score.repository.ScoreARepository;
import com.jifenke.lepluslive.score.service.ScoreCService;
import com.jifenke.lepluslive.scoreAAccount.service.ScoreAAccountService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageSceneService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageService;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;
import com.jifenke.lepluslive.user.repository.WeiXinUserRepository;
import com.jifenke.lepluslive.user.service.UserService;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionPayStore;
import com.jifenke.lepluslive.yinlian.service.UnionPayStoreService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class tttt {


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

  //活动注册
  @Test
  public void qqqq() {
    Query
        query =
        entityManager.createNativeQuery(
            "select merchant_rebate_policy.id from merchant,merchant_rebate_policy where merchant.id = merchant_rebate_policy.merchant_id and merchant_rebate_policy.rebate_policy = 0 and merchant.partnership = 1");
    List<BigInteger> resultList = query.getResultList();
    for (BigInteger id : resultList) {
      MerchantRebatePolicy
          merchantRebatePolicy =
          merchantRebatePolicyRepository.findOne(id.longValue());
      RebateStage rebateStageOne = new RebateStage();
      rebateStageOne.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageOne.setEnd(600L);
      rebateStageOne.setRebateEnd(0L);
      rebateStageOne.setRebateStart(0L);
      rebateStageOne.setStart(0L);
      rebateStageRepository.save(rebateStageOne);

      RebateStage rebateStageTwo = new RebateStage();
      rebateStageTwo.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageTwo.setEnd(1500L);
      rebateStageTwo.setRebateEnd(15L);
      rebateStageTwo.setRebateStart(10L);
      rebateStageTwo.setStart(601L);
      rebateStageRepository.save(rebateStageTwo);

      RebateStage rebateStageThree = new RebateStage();
      rebateStageThree.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageThree.setEnd(3000L);
      rebateStageThree.setRebateEnd(25L);
      rebateStageThree.setRebateStart(15L);
      rebateStageThree.setStart(1501L);
      rebateStageRepository.save(rebateStageThree);

      RebateStage rebateStageFour = new RebateStage();
      rebateStageFour.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageFour.setEnd(5000L);
      rebateStageFour.setRebateEnd(35L);
      rebateStageFour.setRebateStart(25L);
      rebateStageFour.setStart(3001L);
      rebateStageRepository.save(rebateStageFour);

      RebateStage rebateStageFive = new RebateStage();
      rebateStageFive.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageFive.setEnd(10000L);
      rebateStageFive.setRebateEnd(80L);
      rebateStageFive.setRebateStart(40L);
      rebateStageFive.setStart(5001L);
      rebateStageRepository.save(rebateStageFive);

      RebateStage rebateStageSix = new RebateStage();
      rebateStageSix.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageSix.setEnd(20000L);
      rebateStageSix.setRebateEnd(150L);
      rebateStageSix.setRebateStart(90L);
      rebateStageSix.setStart(10001L);
      rebateStageRepository.save(rebateStageSix);

      RebateStage rebateStageSeven = new RebateStage();
      rebateStageSeven.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageSeven.setEnd(40000L);
      rebateStageSeven.setRebateEnd(280L);
      rebateStageSeven.setRebateStart(180L);
      rebateStageSeven.setStart(20001L);
      rebateStageRepository.save(rebateStageSeven);

      RebateStage rebateStageEight = new RebateStage();
      rebateStageEight.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageEight.setEnd(60000L);
      rebateStageEight.setRebateEnd(480L);
      rebateStageEight.setRebateStart(320L);
      rebateStageEight.setStart(40001L);
      rebateStageRepository.save(rebateStageEight);

      RebateStage rebateStageNine = new RebateStage();
      rebateStageNine.setMerchantRebatePolicy(merchantRebatePolicy);
      rebateStageNine.setEnd(9007199254740991L);
      rebateStageNine.setRebateEnd(550L);
      rebateStageNine.setRebateStart(500L);
      rebateStageNine.setStart(60000L);
      rebateStageRepository.save(rebateStageNine);
    }
  }

  @Test
  public void qqq() {
    Query
        query =
        entityManager.createNativeQuery(
            "select merchant.id from merchant,merchant_rebate_policy where merchant.id = merchant_rebate_policy.merchant_id and merchant_rebate_policy.rebate_policy = 0 and merchant.partnership = 1");
    List<BigInteger> resultList = query.getResultList();
    for (BigInteger id : resultList) {
      Merchant merchant = merchantRepository.findOne(id.longValue());
      MerchantRebatePolicy
          merchantRebatePolicy =
          merchantRebatePolicyRepository.findByMerchantId(merchant.getId());
      merchant.setScoreARebate(new BigDecimal(0));
      merchant.setScoreBRebate(new BigDecimal(0));
      merchantRebatePolicy.setImportScoreBScale(new BigDecimal(0));
      merchantRebatePolicy.setImportScoreCScale(merchant.getLjCommission());
      if (merchantRebatePolicy.getRebateFlag() == 0) {
        merchantRebatePolicy.setUserScoreCScale(merchant.getLjCommission());
        merchantRebatePolicy.setUserScoreAScale(new BigDecimal(0));
      } else if (merchantRebatePolicy.getRebateFlag() == 1) {
        merchantRebatePolicy.setUserScoreCScaleB(merchant.getLjCommission());
      }
      merchantRebatePolicy.setUserScoreBScale(new BigDecimal(0));
      merchantRebatePolicy.setUserScoreBScaleB(new BigDecimal(0));
      merchantRebatePolicy.setMemberShareScale(new BigDecimal(38));
      merchantRebatePolicy.setRebatePolicy(1);
      merchantRepository.save(merchant);
      merchantRebatePolicyRepository.save(merchantRebatePolicy);
    }
  }

  @Test
  public void testBill() {
    Calendar calendar = Calendar.getInstance();
    Date end = calendar.getTime();
    calendar.set(Calendar.MINUTE, -60);
    Date start = calendar.getTime();
    Long warningOrder = offLineOrderService.monitorOffLineOrder(start, end);
    int scoreCs = scoreCService.monitorScoreC().size();
    Long scoreCValue = offLineOrderService.monitorScorec();
    StringBuilder message = new StringBuilder();
    message.append("4月1日到现在发送了");
    message.append(scoreCValue/100.0);
    message.append("金币");
    if(warningOrder!=0){
      message.append(",出现可疑订单");
    }
    if(scoreCs!=0){
      message.append(",有用户金币大于100");
    }
    shortMessageService
        .sendOneMessage(userService.findUserById(50L), shortMessageSceneService.findSceneById(6L),
                        message.toString());

  }


}
