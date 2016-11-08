package com.jifenke.lepluslive.web.rest;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.domain.entities.PosErrorLog;
import com.jifenke.lepluslive.order.service.FinanicalStatisticService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.order.service.PosOrderService;
import com.jifenke.lepluslive.partner.repository.PartnerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;
import com.jifenke.lepluslive.score.repository.ScoreARepository;
import com.jifenke.lepluslive.scoreAAccount.service.ScoreAAccountService;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;
import com.jifenke.lepluslive.user.repository.WeiXinUserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
public class ttt {


  @Inject
  private WeiXinUserRepository weiXinUserRepository;

  @Inject
  private LeJiaUserRepository leJiaUserRepository;

  @Inject
  private ScoreARepository scoreARepository;

  @Inject
  private MerchantRepository merchantRepository;

  @Inject
  private MerchantService merchantService;

  @Inject
  private BarcodeService barcodeService;

  private String barCodeRootUrl = "http://lepluslive-barcode.oss-cn-beijing.aliyuncs.com";

  @Inject
  private PartnerWalletRepository partnerWalletRepository;

  @Inject
  private ScoreAAccountService scoreAAccountService;

  @Inject
  private PartnerRepository partnerRepository;

  @Inject
  private OffLineOrderService offLineOrderService;

  @Inject
  private FinanicalStatisticService finanicalStatisticService;

  //添加所有红包账户
  @Test
  public void qqqq() {
    scoreAAccountService.addAllScoreAAccount();
  }


  @Test
  public void tttt() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.SECOND, -1);

    Date end = calendar.getTime();

    List<Object[]> objects = offLineOrderService.countTransferMoney(start, end);

    for (Object[] object : objects) {
      finanicalStatisticService.createFinancialstatistic(object, end);
    }
  }


  @Inject
  private PosOrderService posOrderService;

  @Test
  public void testBill() {
    List<PosDailyBill> erroPosDailyBill = posOrderService.findErrorPosDailyBill(1, 3);
    /*for (PosDailyBill posDailyBill : erroPosDailyBill) {
      System.out.println(posDailyBill.getFilename());
    }*/
    System.out.println(erroPosDailyBill);
  }

  @Test
  public void testErrlog() {
    List<PosDailyBill> erroPosDailyBill = posOrderService.findErrorPosDailyBill(1, 3);
    List<List<PosErrorLog>> posErrorLogByBill = posOrderService.findPosErrorLogByBill(erroPosDailyBill);
    for (List<PosErrorLog> posErrorLog : posErrorLogByBill) {
      System.out.println(posErrorLog.size());
    }
  }

  @Test
  public void testDouble() {
    System.out.println((5/2.0));
    System.out.println((5/2));
    System.out.println((5.0/2));
    System.out.println((5.0/2.0));
    System.out.println(Math.ceil(5/2.0));
    System.out.println(new Double(Math.ceil(5/2.0)).intValue());
  }

  @Test
  public void testPage() {
    Long count = posOrderService.countErroPosDailyBill();
    Integer pageCount = posOrderService.pageCountErroPosDailyBill(3);
    System.out.println(count  + "---------"  + pageCount);
  }
}
