package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wcg on 16/3/21.
 */
public interface OrderRepository extends JpaRepository<OnLineOrder, Long> {

  OnLineOrder findByOrderSid(String orderSid);

  @Query(value = "select sum(on_line_order.true_price) from on_line_order where on_line_order.state not in (0,4);", nativeQuery = true)
  Long countAllTurnover();

  @Query(value = "select count(*) from on_line_order where on_line_order.state not in (0,4);", nativeQuery = true)
  Long countOrder();

  @Query(value = "select count(*) from on_line_order where le_jia_user_id=?1 and on_line_order.state not in (0,4)", nativeQuery = true)
  Long countUserConsumptionTimes(Long id);


  /**
   * 用户当前持有积分总额
   */
  @Query(value = "SELECT SUM(score) FROM scoreb", nativeQuery = true)
  Long sumCurrScoreb();

  /**
   * 用户当前持有红包总额
   */
  @Query(value = "SELECT  SUM(score) FROM scorea", nativeQuery = true)
  Long sumCurrScoreA();

  /**
   * 用户累计获取积分总额
   */
  @Query(value = "SELECT SUM(total_score) FROM scoreb", nativeQuery = true)
  Long sumTotalScoreB();

  /**
   * 用户累计获取红包总额
   */
  @Query(value = "SELECT  SUM(total_score) FROM scorea", nativeQuery = true)
  Long sumTotalScoreA();

  /**
   * 导流订单总数和总金额
   */
  @Query(value = "SELECT COUNT(*),SUM(total_price) FROM off_line_order WHERE rebate_Way=1 AND  state=1", nativeQuery = true)
  List<Object[]> importOrderCount();

  /**
   * 分润总金额
   */
  @Query(value = "SELECT SUM(share_money) FROM off_line_order_share", nativeQuery = true)
  Long totalShare();

  @Query(value = "SELECT COUNT(*) FROM merchant WHERE partnership=1", nativeQuery = true)
  Long unionMerchantCount();

  @Query(value = "SELECT COUNT(*) FROM wei_xin_user WHERE state=1", nativeQuery = true)
  Long membersCount();

  Page findAll(Specification<OnLineOrder> whereClause, Pageable pageRequest);
}
