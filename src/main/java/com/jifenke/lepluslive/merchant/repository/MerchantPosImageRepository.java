package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPosImage;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/6/7.
 */
public interface MerchantPosImageRepository extends JpaRepository<MerchantPosImage, Long> {

  public MerchantPosImage findByMerchant(Merchant merchant);

}
