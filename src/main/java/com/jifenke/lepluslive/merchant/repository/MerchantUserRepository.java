package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by wcg on 16/5/11.
 */
public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {

  MerchantUser findByNameAndPasswordAndMerchant(String name, String password, Merchant merchant);

  List<MerchantUser> findAllByMerchant(Merchant merchant);

  Optional<MerchantUser> findByMerchantAndType(Merchant merchant, int i);
}
