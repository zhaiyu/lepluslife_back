package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wcg on 16/3/17.
 */
public interface MerchantRepository extends JpaRepository<Merchant,Long>{
  Page<Merchant> findAll(Pageable pageable);

  @Query(value = "select count(*) from merchant group by ?1",nativeQuery = true)
  int getMerchantSid(String location);

}
