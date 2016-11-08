package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.partner.domain.entities.Partner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by wcg on 16/3/17.
 */
public interface MerchantRepository extends JpaRepository<Merchant,Long>{
  Page<Merchant> findAll(Pageable pageable);

  @Query(value = "select count(*) from merchant group by ?1",nativeQuery = true)
  int getMerchantSid(String location);


  @Query(value = "SELECT COUNT(*) FROM merchant WHERE sales_staff_id=?1",nativeQuery = true)
  int findSalesMerchantCount(String id);

  @Query(value = "SELECT * FROM merchant WHERE sales_staff_id=?1",nativeQuery = true)
  List<Merchant> findMerchantBySaleId(String id);

  Page findAll(Specification<Merchant> whereClause, Pageable pageable);

  Optional<Merchant> findByMerchantSid(String merchantSid);

  Merchant findByPartnerAndPartnership(Partner origin, int i);
}
