package com.jifenke.lepluslive.web.rest;

import com.jifenke.lepluslive.Application;
import com.jifenke.lepluslive.barcode.BarcodeConfig;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantInfo;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.partner.domain.entities.PartnerInfo;
import com.jifenke.lepluslive.partner.domain.entities.PartnerScoreLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.repository.PartnerInfoRepository;
import com.jifenke.lepluslive.partner.repository.PartnerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerScoreLogRepository;
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

import java.io.IOException;

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
  private PartnerScoreLogRepository partnerScoreLogRepository;

  @Inject
  private PartnerInfoRepository partnerInfoRepository;

  @Inject
  private FileImageService fileImageService;

  //添加所有红包账户
  @Test
  public void qqqq() {
    scoreAAccountService.addAllScoreAAccount();
  }


  @Test
  public void tttt() {
//    partnerRepository.findAll().forEach(partner -> {
//      PartnerWallet partnerWallet = partnerWalletRepository.findByPartner(partner);
//      PartnerInfo partnerInfo = partnerInfoRepository.findByPartner(partner);
//      byte[]
//          bytes =
//          new byte[0];
//      byte[]
//          bytes2 =
//          new byte[0];
//      try {
//        bytes = barcodeService.qrCode(Constants.PARTNER_URL + partner.getPartnerSid(),
//                                      BarcodeConfig.QRCode.defaultConfig());
//        bytes2 =
//            barcodeService
//                .qrCode(Constants.PARTNER_HB_URL + partner.getPartnerSid(),          // 海报二维码
//                        BarcodeConfig.QRCode.defaultConfig());
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//      String filePath = MvUtil.getFilePath(Constants.BAR_CODE_EXT);
//      String
//          filePath2 =
//          MvUtil.getFilePath(Constants.BAR_CODE_EXT);                                // 地址
//      partnerInfo.setQrCodeUrl(barCodeRootUrl + "/" + filePath);
//      partnerInfo.setHbQrCodeUrl(barCodeRootUrl + "/" + filePath2);
//      partnerInfoRepository.save(partnerInfo);
//      final byte[] finalBytes = bytes;
//      final byte[] finalBytes2 = bytes2;
//      fileImageService.SaveBarCode(finalBytes, filePath);
//      fileImageService.SaveBarCode(finalBytes2, filePath2);
//    });
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
