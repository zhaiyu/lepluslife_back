package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wcg on 16/8/4.
 */
public interface MerchantPosRepository extends JpaRepository<MerchantPos, Long> {

  MerchantPos findByPosId(String posId);

  List<MerchantPos> findAllByMerchant(Merchant merchant);

}
