package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by wcg on 16/5/5.
 */
public interface OffLineOrderRepository extends JpaRepository<OffLineOrder, Long> {

  Page findAll(Specification<OffLineOrder> whereClause, Pageable pageRequest);

  @Query(value = "select merchant_id,sum(transfer_money),sum(transfer_money_from_true_pay) from off_line_order where state = 1 and complete_date between ?1 and ?2 group by `merchant_id`  ", nativeQuery = true)
  List<Object[]> countTransferMoney(Date start, Date end);

  @Query(value = "SELECT "
                 + " (SELECT count(w.le_jia_user_id) FROM wei_xin_user w "
                 + " WHERE w.le_jia_user_id NOT IN (SELECT off_line_order.le_jia_user_id "
                 + " FROM off_line_order WHERE rebate_way = 1 AND off_line_order.state = 1) "
                 + "AND w.state = 1 "
                 + " ) total0, "
                 + " sum(total = 1) total1,sum(total = 2) total2,sum(total = 3) total3,sum(total > 3) total4 "
                 + "FROM wei_xin_user w, "
                 + " (SELECT le_jia_user_id,count(le_jia_user_id) total "
                 + "  FROM off_line_order WHERE off_line_order.rebate_way = 1 "
                 + "  AND off_line_order.state = 1  GROUP BY le_jia_user_id "
                 + " ) o "
                 + "WHERE w.le_jia_user_id = o.le_jia_user_id AND w.state = 1 ", nativeQuery = true)
  List<Object[]> countUserByOffLineOrder();

  @Query(value = "SELECT * FROM off_line_order WHERE order_sid=?1", nativeQuery = true)
  OffLineOrder findOneByOrderSid(String orderSid);

  @Query(value = "select count(1) from off_line_order where merchant_id=?1 and complete_date between ?2 and ?3 and state=1 and total_price>=?4", nativeQuery = true)
  Long countOrderNumByMerchant(Long merchantId, String startDate, String endDate,Long validAmount);

  @Query(value = "select sum(total_price) from off_line_order where merchant_id=?1 and complete_date between ?2 and ?3 and state=1 and total_price>=?4", nativeQuery = true)
  Long countOrderTotalByMerchant(Long merchantId, String startDate, String endDate,Long validAmount);

  @Query(value = "select count(1),sum(total_price),sum(lj_commission) from off_line_order where merchant_id=?1 and  state=1 and rebate_way = ?2 and complete_date  between ?3 and ?4 and total_price>=?5 ", nativeQuery = true)
  List<Object[]> countLeadOrderByMidAndWay(Long merchantId, Long rebateWay, String startDate,
                                           String endDate,Long validAmount);

  @Query(value = "SELECT COUNT(*) FROM off_line_order WHERE le_jia_user_id=?1", nativeQuery = true)
  int findOffLineOrderCountOfLeJiaUser(Long leJiaUserId);



  @Query(value = "SELECT SUM(true_pay) ,DATE_FORMAT(complete_date,'%Y%m%d')    FROM off_line_order WHERE DATE_FORMAT(complete_date,'%Y%m%d') IS NOT NULL GROUP BY  DATE_FORMAT(complete_date,'%Y%m%d')", nativeQuery = true)
  List<Object[]> findOfflineWxTruePayAndDate();



  @Query(value = "SELECT SUM(true_price) ,DATE_FORMAT(create_date,'%Y%m%d') FROM on_line_order WHERE    state NOT IN (0,4) GROUP BY  DATE_FORMAT(create_date,'%Y%m%d')", nativeQuery = true)
  List<Object[]> findOnlineWxTruePayAndDate();



  @Query(value = "SELECT SUM(wx_commission) ,DATE_FORMAT(complete_date,'%Y%m%d') FROM off_line_order WHERE DATE_FORMAT(complete_date,'%Y%m%d') IS NOT NULL  GROUP BY  DATE_FORMAT(complete_date,'%Y%m%d')", nativeQuery = true)
  List<Object[]> findOfflineWxCommissionAndDate();



  @Query(value = "SELECT true_price ,DATE_FORMAT(create_date,'%Y%m%d') FROM on_line_order WHERE    state NOT IN (0,4) ", nativeQuery = true)
  List<Object[]> findOnlineWxCommissionAndDate();
}
