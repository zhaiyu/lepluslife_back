package com.jifenke.lepluslive.order.repository;


import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 富友扫码订单 Created by zhangwen on 16/12/6.
 */
public interface ScanCodeOrderRepository extends JpaRepository<ScanCodeOrder, String> {

  /**
   * 订单号查询订单  16/12/06
   *
   * @param orderSid 自有订单号
   */
  ScanCodeOrder findByOrderSid(String orderSid);

  Page findAll(Specification<ScanCodeOrder> whereClause, Pageable pageRequest);

  /**
   * 获取该商户使用的商户号今日可退款金额和红包  2016/12/22
   *
   * @param merchantNum 商户号
   * @param start       起始时间(今日零点)
   * @param end         截止时间(查询时间)
   */
  @Query(value = "SELECT SUM(total_price),SUM(true_pay),SUM(true_score) FROM scan_code_order WHERE state = 1 AND merchant_num = ?1 AND complete_date >= ?2 AND complete_date < ?3", nativeQuery = true)
  List<Object[]> countByMerchantNumToday(String merchantNum, Date start, Date end);

  /**
   * 统计某个门店的累计流水和累计收取红包  2017/02/10
   *
   * @param merchantId 门店ID
   */
  @Query(value = "SELECT SUM(total_price),SUM(true_score) FROM scan_code_order WHERE state = 1 AND merchant_id = ?1", nativeQuery = true)
  List<Object[]> countPriceByMerchant(Long merchantId);
}
