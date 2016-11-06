package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.OnLineOrderShare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zhangwen on 16/11/05.
 */
public interface OnLineOrderShareRepository extends JpaRepository<OnLineOrderShare, Long> {

  Page findAll(Specification<OnLineOrderShare> whereClause, Pageable pageRequest);

  @Query(value = "SELECT * FROM on_line_order_share WHERE on_line_order_id=?1", nativeQuery = true)
  OnLineOrderShare findByOrderId(Long orderId);
}
