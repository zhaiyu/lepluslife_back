package com.jifenke.lepluslive.merchant.controller.dto;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUserShop;

import java.util.List;

/**
 * Created by xf on 16-11-30.
 */
public class MerchantUserDto {

  private MerchantUser merchantUser;

  private Merchant merchant;

  private List<MerchantUserShop> shopList;  //账号管理的门店ID列表   id1_id2

  public MerchantUser getMerchantUser() {
    return merchantUser;
  }

  public void setMerchantUser(MerchantUser merchantUser) {
    this.merchantUser = merchantUser;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public List<MerchantUserShop> getShopList() {
    return shopList;
  }

  public void setShopList(List<MerchantUserShop> shopList) {
    this.shopList = shopList;
  }
}
