package com.jifenke.lepluslive.activity.repository;

import com.jifenke.lepluslive.activity.domain.entities.InitialOrderRebateActivity;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/9/18.
 */
public interface InitialOrderRebateActivityRepository
    extends JpaRepository<InitialOrderRebateActivity, Long> {

  InitialOrderRebateActivity findByMerchant(Merchant merchant);
}
