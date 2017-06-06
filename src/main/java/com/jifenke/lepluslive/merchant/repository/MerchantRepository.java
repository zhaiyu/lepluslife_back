package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
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
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

  Page<Merchant> findAll(Pageable pageable);

  @Query(value = "select count(*) from merchant group by ?1", nativeQuery = true)
  int getMerchantSid(String location);


  @Query(value = "SELECT COUNT(*) FROM merchant WHERE sales_staff_id=?1", nativeQuery = true)
  int findSalesMerchantCount(String id);

  @Query(value = "SELECT * FROM merchant WHERE sales_staff_id=?1", nativeQuery = true)
  List<Merchant> findMerchantBySaleId(String id);

  @Query(value = "SELECT id,name FROM merchant WHERE partnership in (0,1)",nativeQuery = true)
  List<Object[]> findAllMerchant();

  @Query(value = "SELECT * FROM merchant WHERE id=?1",nativeQuery = true)
  Merchant findById(String name);

  Page findAll(Specification<Merchant> whereClause, Pageable pageable);

  Optional<Merchant> findByMerchantSid(String merchantSid);

  Merchant findByPartnerAndPartnership(Partner origin, int i);

  /**
   * 查询某一商户下所有门店  2017/01/04
   *
   * @param id 商户ID
   */
  @Query(value = "SELECT id,partnership,user_limit FROM merchant WHERE merchant_user_id = ?1", nativeQuery = true)
  List<Object[]> countByMerchantUser(Long id);

  /**
   * 查询某一商户下所有门店的详细信息  2017/01/09
   *
   * @param merchantUser 商户
   */
  List<Merchant> findByMerchantUser(MerchantUser merchantUser);

  @Query(value = "SELECT * FROM merchant WHERE name=?1",nativeQuery = true)
  List<Merchant> findByName(String name);
}
