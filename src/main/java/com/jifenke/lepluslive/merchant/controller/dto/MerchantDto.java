package com.jifenke.lepluslive.merchant.controller.dto;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantScanPayWay;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantLedger;

/**
 * 新建或修改门店包装类
 * Created by xf on 16-11-9.
 */
public class MerchantDto {

  private Merchant merchant;

  private MerchantScanPayWay merchantScanPayWay;

  private MerchantLedger merchantLedger;


  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }


  public MerchantScanPayWay getMerchantScanPayWay() {
    return merchantScanPayWay;
  }

  public void setMerchantScanPayWay(MerchantScanPayWay merchantScanPayWay) {
    this.merchantScanPayWay = merchantScanPayWay;
  }

  public MerchantLedger getMerchantLedger() {
    return merchantLedger;
  }

  public void setMerchantLedger(MerchantLedger merchantLedger) {
    this.merchantLedger = merchantLedger;
  }
}
