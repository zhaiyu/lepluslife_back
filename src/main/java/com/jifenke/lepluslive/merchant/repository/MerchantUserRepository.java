package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by wcg on 16/5/11.
 */
public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {

  MerchantUser findByNameAndPasswordAndMerchant(String name, String password, Merchant merchant);

  List<MerchantUser> findAllByMerchant(Merchant merchant);

  Optional<MerchantUser> findByMerchantAndType(Merchant merchant, int i);

  List<MerchantUser> findByName(String name);

  Page findAll(Specification<MerchantUser> specification, Pageable pageRequest);

  @Query(value="SELECT new MerchantUser(id,merchantName) FROM MerchantUser mu where mu.type =?1 ")
  List<MerchantUser> findMerchantUserByType(Integer type);
}
