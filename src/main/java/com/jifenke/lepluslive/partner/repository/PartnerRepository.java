package com.jifenke.lepluslive.partner.repository;

import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wcg on 16/6/3.
 */
public interface PartnerRepository extends JpaRepository<Partner, Long> {

  List<Partner> findByPartnerManager(PartnerManager partnerManager);

  @Query(value = "select count(*) from (select count(*) from off_line_order,le_jia_user where off_line_order.le_jia_user_id = le_jia_user.id and le_jia_user.bind_partner_id = ?1  group by off_line_order.le_jia_user_id )a",nativeQuery = true)
  Long countLeJiaUserByPartner(Long id);

  Page findAll(Specification<Partner> whereClause, Pageable pageRequest);

  @Query(value = "SELECT  count(*) from merchant where partner_id=?1",nativeQuery = true)
  int findPartnerBindMerchantCount(Long id);


  @Query(value = "SELECT count(*) from le_jia_user where bind_partner_id=?1",nativeQuery = true)
  int findPartnerBindUserCount(Long id);


}
