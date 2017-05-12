package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnline;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnlineLog;
import com.jifenke.lepluslive.partner.repository.PartnerWalletLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lss on 16-11-21.
 */
@Service
@Transactional(readOnly = false)
public class PartnerWalletService {

  @Inject
  private PartnerWalletRepository partnerWalletRepository;

  @Inject
  private PartnerWalletLogRepository partnerWalletLogRepository;

  @Inject
  private PartnerWalletOnlineRepository partnerWalletOnlineRepository;

  @Inject
  private PartnerWalletOnlineLogRepository partnerWalletOnlineLogRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public PartnerWallet findOne(Long id) {
    return partnerWalletRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void savePartnerWallet(PartnerWallet partnerWallet) {
    partnerWalletRepository.save(partnerWallet);
  }

  /**
   * 根据合伙人查找合伙人钱包  2016/12/22
   *
   * @param partner 合伙人
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public PartnerWallet findByPartner(Partner partner) {
    return partnerWalletRepository.findByPartner(partner);
  }

  /**
   * 修改合伙人线下钱包
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changePartnerWallet(Long money, Partner partner, String sid, Long type) {
    if (money != 0) {
      PartnerWalletLog partnerWalletLog = new PartnerWalletLog();

      PartnerWallet partnerWallet = partnerWalletRepository.findByPartner(partner);
      Long availableBalance = partnerWallet.getAvailableBalance();

      long afterShareMoney = availableBalance + money;

      partnerWalletLog.setBeforeChangeMoney(availableBalance);

      partnerWalletLog.setAfterChangeMoney(afterShareMoney);

      partnerWalletLog.setPartnerId(partner.getId());

      partnerWalletLog.setOrderSid(sid);

      partnerWalletLog.setType(type);

      partnerWallet.setTotalMoney(partnerWallet.getTotalMoney() + money);

      partnerWallet.setAvailableBalance(afterShareMoney);

      partnerWalletLogRepository.save(partnerWalletLog);

      partnerWalletRepository.save(partnerWallet);
    }
  }

  public void changeOnlinePartnerWallet(Long money, Partner partner, String sid,
                                        long type) {
    if (money != 0) {
      PartnerWalletOnlineLog partnerWalletLog = new PartnerWalletOnlineLog();

      PartnerWalletOnline partnerWallet = partnerWalletOnlineRepository.findByPartner(partner);

      Long availableBalance = partnerWallet.getAvailableBalance();

      long afterShareMoney = availableBalance + money;

      partnerWalletLog.setBeforeChangeMoney(availableBalance);

      partnerWalletLog.setAfterChangeMoney(afterShareMoney);

      partnerWalletLog.setPartnerId(partner.getId());

      partnerWalletLog.setOrderSid(sid);

      partnerWalletLog.setType(type);

      partnerWallet.setTotalMoney(partnerWallet.getTotalMoney() + money);

      partnerWallet.setAvailableBalance(afterShareMoney);

      partnerWalletOnlineLogRepository.save(partnerWalletLog);

      partnerWalletOnlineRepository.save(partnerWallet);
    }
  }
}
