package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wcg on 16/5/5.
 */
public interface OffLineOrderShareRepository extends JpaRepository<OffLineOrderShare, Long> {

  Page findAll(Specification<OffLineOrderShare> whereClause, Pageable pageRequest);

  @Query(value = "SELECT * FROM off_line_order_share WHERE off_line_order_id=?1", nativeQuery = true)
  OffLineOrderShare findOneByOrderId(Long orderId);
}
