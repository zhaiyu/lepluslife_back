package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wcg on 16/3/21.
 */
public interface OrderRepository extends JpaRepository<OnLineOrder,Long>{

  OnLineOrder findByOrderSid(String orderSid);

  @Query(value = "select sum(on_line_order.true_price) from on_line_order where on_line_order.state not in (0,4);",nativeQuery = true)
  Long countAllTurnover();

  @Query(value = "select count(*) from on_line_order where on_line_order.state not in (0,4);",nativeQuery = true)
  Long countOrder();

  @Query(value = "select count(*) from on_line_order where le_jia_user_id=?1 and on_line_order.state not in (0,4)",nativeQuery = true)
  Long countUserConsumptionTimes(Long id);

  Page findAll(Specification<OnLineOrder> whereClause, Pageable pageRequest);
}
