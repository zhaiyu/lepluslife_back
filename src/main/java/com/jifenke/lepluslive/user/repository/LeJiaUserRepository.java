package com.jifenke.lepluslive.user.repository;

import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by wcg on 16/3/24.
 */
public interface LeJiaUserRepository extends JpaRepository<LeJiaUser, Long> {

  Page findAll(Specification<LeJiaUser> whereClause, Pageable pageRequest);

  @Query(value = "SELECT COUNT(*) FROM le_jia_user WHERE le_jia_user.bind_merchant_id = ?1", nativeQuery = true)
  int countByBindMerchant(Long merchantId);
}
