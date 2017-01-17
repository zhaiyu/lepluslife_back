package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantScanPayWay;
import com.jifenke.lepluslive.merchant.repository.MerchantScanPayWayRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import javax.inject.Inject;

/**
 * 富友结算规则、类型及结算账户 Created by zhangwen on 2017/1/9.
 */
@Service
@Transactional(readOnly = true)
public class MerchantScanPayWayService {

  @Inject
  private MerchantScanPayWayRepository repository;

  @Inject
  private MerchantService merchantService;

  /**
   * 门店扫码支付方式(没有就创建)  17/01/10
   *
   * @param merchantId 门店ID
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public MerchantScanPayWay findByMerchantId(Long merchantId) {
    MerchantScanPayWay scanPayWay = repository.findByMerchantId(merchantId);
    if (scanPayWay == null) {
      Merchant merchant = merchantService.findMerchantById(merchantId);
      Date date = new Date();
      scanPayWay = new MerchantScanPayWay();
      scanPayWay.setCreateDate(date);
      scanPayWay.setLastUpdate(date);
      scanPayWay.setMerchantId(merchantId);
      scanPayWay.setCommission(merchant.getLjCommission());
      repository.saveAndFlush(scanPayWay);
    }
    return scanPayWay;
  }

  /**
   * 新建/修改 支付方式  2017/01/13
   *
   * @param scanPayWay 支付方式
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void savePayWay(MerchantScanPayWay scanPayWay) throws Exception {
    MerchantScanPayWay db_scanPayWay = null;
    Date date = new Date();
    try {
      if (scanPayWay.getId() != null) {
        db_scanPayWay = repository.findOne(scanPayWay.getId());
      } else {
        db_scanPayWay = new MerchantScanPayWay();
        db_scanPayWay.setCreateDate(date);
        db_scanPayWay.setMerchantId(scanPayWay.getMerchantId());
      }
      db_scanPayWay.setCommission(scanPayWay.getCommission());
      db_scanPayWay.setLastUpdate(date);
      db_scanPayWay.setType(scanPayWay.getType());
      repository.save(db_scanPayWay);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

}
