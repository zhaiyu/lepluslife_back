package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wcg on 16/3/21.
 */
public interface OrderRepository extends JpaRepository<Order,Long>{

  Order findByOrderSid(String orderSid);

  @Query(value = "select sum(product_order.true_price) from product_order where product_order.state not in (0,4);",nativeQuery = true)
  Long countAllTurnover();

  @Query(value = "select count(*) from product_order where product_order.state not in (0,4);",nativeQuery = true)
  Long countOrder();

  @Query(value = "select count(*) from product_order where le_jia_user_id=?1 and product_order.state not in (0,4)",nativeQuery = true)
  Long countUserConsumptionTimes(Long id);
}
