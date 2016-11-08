package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.barcode.BarcodeConfig;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantInfo;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.partner.controller.dto.PartnerDto;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerInfo;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManager;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManagerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManagerWalletLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerScoreLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnline;
import com.jifenke.lepluslive.partner.repository.PartnerInfoRepository;
import com.jifenke.lepluslive.partner.repository.PartnerManagerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerManagerWalletLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerManagerWalletRepository;
import com.jifenke.lepluslive.partner.repository.PartnerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerScoreLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by wcg on 16/6/3.
 */
@Service
@Transactional(readOnly = true)
public class PartnerService {

  @Inject
  private EntityManager entityManager;

  @Inject
  private PartnerRepository partnerRepository;

  @Inject
  private PartnerManagerWalletRepository partnerManagerWalletRepository;

  @Inject
  private PartnerManagerWalletLogRepository partnerManagerWalletLogRepository;

  @Inject
  private PartnerManagerRepository partnerManagerRepository;

  @Inject
  private PartnerWalletRepository partnerWalletRepository;

  @Inject
  private PartnerWalletOnlineRepository walletOnlineRepository;

  @Inject
  private PartnerWalletLogRepository partnerWalletLogRepository;

  @Inject
  private PartnerScoreLogRepository partnerScoreLogRepository;

  @Inject
  private PartnerInfoRepository partnerInfoRepository;

  @Inject
  private MerchantService merchantService;

  @Inject
  private BarcodeService barcodeService;

  @Inject
  private FileImageService fileImageService;

  @Value("${bucket.ossBarCodeReadRoot}")
  private String barCodeRootUrl;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Partner> findAllParter() {
    return partnerRepository.findAll();
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findAll() {

    Query
        query =
        entityManager.createNativeQuery(
            "select partner.id ,partner.name,partner.user_limit,partner.merchant_limit,partner.available,partner.total,partner.partner_name partner_name,partner.phone_number phone_number,partner.password password,ifnull(bindUser.bind ,0)userBind,ifnull(bindMerchant.bindMerchant,0) as merchantBind from (select partner.id id,partner.merchant_limit merchant_limit,partner.name name,partner.partner_name partner_name,partner.phone_number phone_number,partner.password password,partner.user_limit user_limit,partner_wallet.available_balance available,partner_wallet.total_money total from partner,partner_wallet where partner_wallet.partner_id = partner.id )partner left join (select count(*) bind,partner.id ids from le_jia_user,partner where partner.id = le_jia_user.bind_partner_id group by partner.id )bindUser on bindUser.ids = partner.id left join (select count(*)bindMerchant,partner.id id from merchant,partner where partner.id = merchant.partner_id group by merchant.partner_id)bindMerchant on bindMerchant.id = partner.id");

    List<Object[]> details = query.getResultList();

    return details;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findAllPartnerManagerByWallet() {
    return partnerManagerWalletRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editPartnerPassword(Partner partner) {
    Partner origin = partnerRepository.findOne(partner.getId());
    origin.setName(partner.getName());
    origin.setPassword(MD5Util.MD5Encode(partner.getPassword(), "utf-8"));
    partnerRepository.save(origin);

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Partner findPartnerById(Long id) {
    return partnerRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findAllPartnerManager() {
    return partnerManagerRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void createPartner(PartnerDto partnerDto) {
    Partner partner = partnerDto.getPartner();
    partnerRepository.save(partner);
    PartnerWallet partnerWallet = new PartnerWallet();
    partnerWallet.setPartner(partner);
    Long scoreA = partnerDto.getScoreA() * 100;
    Long scoreB = partnerDto.getScoreB();
    partnerWallet.setAvailableScoreA(scoreA);
    partnerWallet.setAvailableScoreB(scoreB);
    partnerWallet.setTotalScoreA(scoreA);
    partnerWallet.setTotalScoreB(scoreB);
    partnerWalletRepository.save(partnerWallet);
    //创建线上钱包
    PartnerWalletOnline walletOnline = new PartnerWalletOnline();
    walletOnline.setPartner(partner);
    walletOnlineRepository.save(walletOnline);
    PartnerScoreLog partnerScoreLog = new PartnerScoreLog();
    partnerScoreLog.setDescription("关注送红包");
    partnerScoreLog.setType(1);
    partnerScoreLog.setPartnerId(partner.getId());
    partnerScoreLog.setNumber(scoreA);
    partnerScoreLog.setScoreAOrigin(0);
    partnerScoreLogRepository.save(partnerScoreLog);
    PartnerScoreLog partnerScoreBLog = new PartnerScoreLog();
    partnerScoreBLog.setDescription("关注送积分");
    partnerScoreBLog.setType(0);
    partnerScoreBLog.setPartnerId(partner.getId());
    partnerScoreBLog.setNumber(scoreB);
    partnerScoreBLog.setScoreBOrigin(0);
    partnerScoreLogRepository.save(partnerScoreBLog);
    //设置发红包机制
    PartnerInfo partnerInfo = new PartnerInfo();
    partnerInfo.setMaxScoreA(300);
    partnerInfo.setMinScoreA(100);
    partnerInfo.setScoreAType(1);
    partnerInfo.setMaxScoreB(5);
    partnerInfo.setScoreBType(0);
    partnerInfo.setPartner(partner);
    partnerInfo.setInviteLimit(partnerDto.getInviteLimit());
    byte[]
        bytes =
        new byte[0];
    byte[]
        bytes2 =
        new byte[0];
    try {
      bytes = barcodeService.qrCode(Constants.PARTNER_URL + partner.getPartnerSid(),
                                    BarcodeConfig.QRCode.defaultConfig());
      bytes2 =
          barcodeService
              .qrCode(Constants.PARTNER_HB_URL + partner.getPartnerSid(),          // 海报二维码
                      BarcodeConfig.QRCode.defaultConfig());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String filePath = MvUtil.getFilePath(Constants.BAR_CODE_EXT);
    String
        filePath2 =
        MvUtil.getFilePath(Constants.BAR_CODE_EXT);                                // 地址
    partnerInfo.setQrCodeUrl(barCodeRootUrl + "/" + filePath);
    partnerInfo.setHbQrCodeUrl(barCodeRootUrl + "/" + filePath2);
    partnerInfoRepository.save(partnerInfo);
    final byte[] finalBytes = bytes;
    final byte[] finalBytes2 = bytes2;
    fileImageService.SaveBarCode(finalBytes, filePath);
    fileImageService.SaveBarCode(finalBytes2, filePath2);
    Merchant merchant = new Merchant();//天使合伙人虚拟商户
    merchant.setPartner(partner);
    merchant.setName(partner.getPartnerName() + "(合伙人)");
    merchant.setPartnership(2);
    MerchantInfo merchantInfo = new MerchantInfo();
    merchant.setMerchantInfo(merchantInfo);
    merchantService.saveMerchant(merchant);
    //为商户开通商户邀请码
    try {
      merchantService.createQrCode(merchant.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editPartner(PartnerDto partnerDto) {
    Partner partner = partnerDto.getPartner();
    Partner origin = partnerRepository.findOne(partner.getId());
    origin.setName(partner.getName());
    origin.setBankName(partner.getBankName());
    origin.setBankNumber(partner.getBankNumber());
    origin.setMerchantLimit(partner.getMerchantLimit());
    origin.setUserLimit(partner.getUserLimit());
    origin.setPartnerManager(partner.getPartnerManager());
    origin.setPayee(partner.getPayee());
    origin.setPhoneNumber(partner.getPhoneNumber());
    origin.setPartnerName(partner.getPartnerName());
    origin.setBenefitTime(partner.getBenefitTime());
    merchantService.editPartnerVirtualMerchant(origin);
    partnerRepository.save(origin);
    PartnerInfo partnerInfo = partnerInfoRepository.findByPartner(origin);
    partnerInfo.setInviteLimit(partnerDto.getInviteLimit());
    partnerInfoRepository.save(partnerInfo);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public PartnerWallet findPartnerWalletByPartner(Partner partner) {
    return partnerWalletRepository.findByPartner(partner);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public PartnerManagerWallet findPartnerManagerWalletByPartnerManager(
      PartnerManager PartnerManager) {
    return partnerManagerWalletRepository.findByPartnerManager(PartnerManager);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void savePartnerWallet(PartnerWallet partnerWallet) {
    partnerWalletRepository.saveAndFlush(partnerWallet);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void savePartnerManagerWallet(PartnerManagerWallet partnerManagerWallet) {
    partnerManagerWalletRepository.saveAndFlush(partnerManagerWallet);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void partnerWalletLog(PartnerWalletLog PartnerWalletLog) {
    partnerWalletLogRepository.saveAndFlush(PartnerWalletLog);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void savePartnerManagerWalletLog(PartnerManagerWalletLog partnerManagerWalletLog) {
    partnerManagerWalletLogRepository.saveAndFlush(partnerManagerWalletLog);
  }

  public PartnerInfo findPartnerInfoByPartner(Partner partner) {
    return partnerInfoRepository.findByPartner(partner);
  }
}
