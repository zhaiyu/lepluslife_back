package com.jifenke.lepluslive.web.rest;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.barcode.BarcodeConfig;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.order.service.FinanicalStatisticService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.score.repository.ScoreARepository;
import com.jifenke.lepluslive.score.repository.ScoreBRepository;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;
import com.jifenke.lepluslive.user.repository.WeiXinUserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private FileImageService fileImageService;

  @Inject
  private OffLineOrderService offLineOrderService;


  @Inject
  private FinanicalStatisticService finanicalStatisticService;


  @Test
  public void tttt() {

    Merchant merchant = merchantService.findMerchantById(1L);

    Integer count = null;
    List<Object[]> scoreAs = null;
    List<Object[]> list = null;
    Map<String, Object> map = new HashMap<>();
    String subSource = "4_0_" + merchant.getId();  //关注来源
    //绑定会员数量
    count = leJiaUserRepository.countByBindMerchant(merchant.getId());
    map.put("bindM", count);
    //邀请会员数
    count = leJiaUserRepository.countBySubSourceAndState(subSource);
    map.put("inviteM", count);
    //邀请粉丝数
    count = leJiaUserRepository.countBySubSourceAndSubState(subSource);
    map.put("inviteU", count);
    //邀请会员的累计产生佣金
    count = leJiaUserRepository.countLJCommissionByMerchant(subSource);
    map.put("commission", count);
    //邀请会员的会员累计红包额和使用红包额
    scoreAs = leJiaUserRepository.countScoreAByMerchant(subSource);
    map.put("totalA", scoreAs.get(0)[0]);
    map.put("usedA", scoreAs.get(0)[1]);
    //邀请会员的各种订单类型数量
    list = leJiaUserRepository.countOrderByMerchant(subSource);
    for (Object[] o : list) {
      map.put("order_" + o[0], o[1]);
    }
    if (map.get("order_1") == null) {
      map.put("order_1", 0);
    }
    if (map.get("order_3") == null) {
      map.put("order_3", 0);
    }
    if (map.get("order_5") == null) {
      map.put("order_5", 0);
    }
    map.put("sid", merchant.getMerchantSid());
    map.put("name", merchant.getName());
    map.put("partnership", merchant.getPartnership());
    map.put("qrCode", merchant.getMerchantInfo().getQrCode());
    map.put("ticket", merchant.getMerchantInfo().getTicket());


  }

////  public static void main(String[] args) {
////    int x[][] = new int[9][9];
////    for(int i=0;i<9;i++){
////      for(int y=0;y<9;y++){
////        x[i][y]=new Random().nextInt(2);
////      }
////    }
////    Scanner input = new Scanner(System.in);
////    int a = input.nextInt();
////    int b = input.nextInt();
////    int n = input.nextInt();
////
////    for(int z=1;z<n;z++){
////      int m = x[a][b];
////      int a1 = x[a-1][b];
////      int a2 = x[a+1][b];
////      int a3 = x[a][b+1];
////      int a4 = x[a][b-1];
////
////
////
////    }
//
//
//
//  }


}
