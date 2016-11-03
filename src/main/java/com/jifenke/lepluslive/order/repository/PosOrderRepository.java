package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.PosOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by wcg on 16/8/2.
 */
public interface PosOrderRepository extends JpaRepository<PosOrder, Long> {

  PosOrder findByOrderSid(String orderNo);

  Page findAll(Specification<PosOrder> whereClause, Pageable pageRequest);

  @Query(value = "select ifnull(sum(transfer_money),0),ifnull(sum(transfer_by_bank),0) from pos_order where state = 1 and merchant_id = ?1 and complete_date between ?2 and ?3 ", nativeQuery = true)
  Object[] countPosTransferMoneyByMerchant(Long id, Date start, Date end);
}
