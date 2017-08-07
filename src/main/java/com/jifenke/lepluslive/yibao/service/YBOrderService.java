package com.jifenke.lepluslive.yibao.service;

import com.jifenke.lepluslive.global.util.DataUtils;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 易宝订单操作
 * Created by zhangwen on 2017/7/20.
 */
@Service("ybOrderService")
@Transactional(readOnly = true)
public class YBOrderService {


  @Inject
  private EntityManager entityManager;

  /**
   * 每日易宝订单统计(group by merchantNum)(用于易宝转账)  2017/7/20
   *
   * @param currDay 订单结算时间yyyy-MM-dd
   */
  public List<Map<String, Object>> countTransferGroupByMerchantNum(String currDay) {
    StringBuffer sql = new StringBuffer();
    //统一转账切点 目前为 23:30:00
    String beforeDate = currDay + " 23:30:00";
    sql.append("SELECT e.merchant_num AS ledgerNo,SUM(o.transfer_money) AS transferMoney");
    sql.append(
        " FROM scan_code_order o INNER JOIN scan_code_order_ext e ON o.scan_code_order_ext_id = e.id");
    sql.append(" WHERE o.settle_date = '").append(currDay).append("'");
    sql.append(" AND o.state IN (1, 2)");
    sql.append(" AND o.complete_date <= '").append(beforeDate).append("'");
    sql.append(" GROUP BY e.merchant_num");
    System.out.println(sql.toString());
    return findBySql(sql.toString());
  }

  /**
   * 每日易宝订单统计(group by merchantId)(用于门店结算单)  2017/7/25
   *
   * @param currDay 订单结算时间yyyy-MM-dd
   */
  public List<Map<String, Object>> countStoreStatementGroupByMerchantId(String currDay) {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT o.merchant_id AS merchantId,e.merchant_num AS ledgerNo,SUM(o.transfer_money) AS transferMoney,");
    sql.append("SUM(o.transfer_money_from_score) AS transferMoneyByScore,");
    sql.append("SUM(IF(e.pay_type = 1,o.transfer_money_from_true_pay,0)) AS transferMoneyByZfb,");
    sql.append("SUM(IF(e.pay_type = 0,o.transfer_money_from_true_pay,0)) AS transferMoneyByWx");
    sql.append(
        " FROM scan_code_order o INNER JOIN scan_code_order_ext e ON o.scan_code_order_ext_id = e.id");
    sql.append(" WHERE o.settle_date = '").append(currDay).append("'");
    sql.append(" AND o.state IN (1, 2)");
    sql.append(" GROUP BY o.merchant_id");
    System.out.println(sql.toString());
    return findBySql(sql.toString());
  }

  /**
   * 某日某子商编订单入账统计(用于易宝退款)  2017/8/6
   *
   * @param currDay  订单结算时间yyyy-MM-dd
   * @param ledgerNo 子商编
   */
  public Long countTransferByMerchantNum(String currDay, String ledgerNo) {
    StringBuffer sql = new StringBuffer();
    //统一转账切点 目前为 23:30:00
    String beforeDate = currDay + " 23:30:00";
    sql.append("SELECT SUM(o.transfer_money) AS transferMoney");
    sql.append(
        " FROM scan_code_order o INNER JOIN scan_code_order_ext e ON o.scan_code_order_ext_id = e.id");
    sql.append(" WHERE o.settle_date = '").append(currDay).append("'");
    sql.append(" AND e.merchant_num = '").append(ledgerNo).append("'");
    sql.append(" AND o.state IN (1, 2)");
    sql.append(" AND o.complete_date <= '").append(beforeDate).append("'");
    System.out.println(sql.toString());
    Query query = entityManager.createNativeQuery(sql.toString());
    Object singleResult = query.getSingleResult();
    if (singleResult != null) {
      return Long.valueOf("" + singleResult);
    }
    return 0L;
  }

  /**
   * 每日退款完成统计(group by merchantNum)(用于易宝转账)  2017/7/21
   *
   * @param currDay 退款结算时间（订单结算时间）yyyy-MM-dd
   */
  public Map<String, Object> countRefundGroupByMerchantNum(String currDay) {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT ledger_no AS ledgerNo,SUM(transfer_money) AS transferMoney FROM yb_ledger_refund_order");
    sql.append(" WHERE trade_date = '").append(currDay).append("'");
    sql.append(" AND state = 2 AND order_from = 1 GROUP BY ledger_no");

    System.out.println(sql.toString());
    List<Map<String, Object>> list = findBySql(sql.toString());
    Map<String, Object> map = new HashMap<>();
    if (list != null && list.size() > 0) {
      for (Map<String, Object> m : list) {
        map.put("" + m.get("ledgerNo"), m.get("transferMoney"));
      }
    }
    return map;
  }

  /**
   * 每日退款完成统计(group by merchantId)(用于门店结算单)  2017/7/25
   *
   * @param currDay 退款结算时间（订单结算时间）yyyy-MM-dd
   */
  public Map<String, Object> countRefundGroupByMerchantId(String currDay) {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT merchant_id AS merchantId,COUNT(1) AS refundNumber,SUM(transfer_money) AS transferMoney FROM yb_ledger_refund_order");
    sql.append(" WHERE trade_date = '").append(currDay).append("'");
    sql.append(" AND state = 2 AND order_from = 1 GROUP BY merchant_id");

    System.out.println(sql.toString());
    List<Map<String, Object>> list = findBySql(sql.toString());
    Map<String, Object> map = new HashMap<>();
    if (list != null && list.size() > 0) {
      for (Map<String, Object> m : list) {
        map.put("" + m.get("merchantId"), m.get("transferMoney") + "_" + m.get("refundNumber"));
      }
    }
    return map;
  }

  /**
   * 某日某子商编退款入账统计(用于易宝退款)  2017/8/6
   *
   * @param currDay  退款结算时间（订单结算时间）yyyy-MM-dd
   * @param ledgerNo 子商编
   */
  public Long countRefundByMerchantNum(String currDay, String ledgerNo) {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT SUM(transfer_money) AS transferMoney FROM yb_ledger_refund_order");
    sql.append(" WHERE trade_date = '").append(currDay).append("'");
    sql.append(" AND ledger_no = '").append(ledgerNo).append("'");
    sql.append(" AND state = 2 AND order_from = 1");

    System.out.println(sql.toString());
    Query query = entityManager.createNativeQuery(sql.toString());
    Object singleResult = query.getSingleResult();
    if (singleResult != null) {
      return Long.valueOf("" + singleResult);
    }
    return 0L;
  }

  /**
   * 获取所有的审核成功的商户号及商户ID(用于通道结算单)  2017/7/26
   */
  public List<Map<String, Object>> findAllLedgers() {
    String
        sql =
        "SELECT ledger_no AS ledgerNo,merchant_user_id AS merchantUserId,account_name AS accountName,bank_account_number AS bankNo FROM yb_merchant_user_ledger WHERE state = 1";
    return findBySql(sql);
  }

  /**
   * 获取某个子商户号某日的转账成功金额(用于通道结算单)  2017/7/26
   *
   * @param days     转账起止天数
   * @param date     起始日期
   * @param ledgerNo 商户号
   */
  public Long findActualTransfer(int days, String date, String ledgerNo) {
    StringBuffer s = new StringBuffer();
    String currDate = null;
    if (days == 1) {
      s.append("SELECT SUM(actual_transfer) FROM yb_ledger_transfer");
      s.append(" WHERE ledger_no = '").append(ledgerNo).append("' AND state = 1");
      s.append(" AND trade_date = '").append(date).append("'");
    } else {
      StringBuffer dates = new StringBuffer();
      dates.append(" ('").append(date).append("'");
      while (days > 1) {
        days--;
        currDate = DataUtils.getNextDateByDay(date, days);
        dates.append(",'").append(currDate).append("'");
      }
      dates.append(")");
      s.append("SELECT SUM(actual_transfer) FROM yb_ledger_transfer");
      s.append(" WHERE ledger_no = '").append(ledgerNo).append("' AND state = 1");
      s.append(" AND trade_date IN");
      // ('2016-07-20','2017-07-19')
      s.append(dates);
    }
    System.out.println(s.toString());
    Query query = entityManager.createNativeQuery(s.toString());
    Object singleResult = query.getSingleResult();
    if (singleResult != null) {
      return Long.valueOf("" + singleResult);
    }
    return 0L;
  }

  /**
   * 获取某个子商户号某日的门店应结算金额(用于通道结算单)  2017/7/26
   *
   * @param days     转账起止天数
   * @param date     起始日期
   * @param ledgerNo 商户号
   */
  public Long findTotalTransfer(int days, String date, String ledgerNo) {
    StringBuffer s = new StringBuffer();
    String currDate = null;
    if (days == 1) {
      s.append("SELECT SUM(actual_transfer) FROM yb_store_settlement");
      s.append(" WHERE ledger_no = '").append(ledgerNo).append("'");
      s.append(" AND trade_date = '").append(date).append("'");
    } else {
      StringBuffer dates = new StringBuffer();
      dates.append(" ('").append(date).append("'");
      while (days > 1) {
        days--;
        currDate = DataUtils.getNextDateByDay(date, days);
        dates.append(",'").append(currDate).append("'");
      }
      dates.append(")");
      s.append("SELECT SUM(actual_transfer) FROM yb_store_settlement");
      s.append(" WHERE ledger_no = '").append(ledgerNo).append("'");
      s.append(" AND trade_date IN");
      s.append(dates);
    }
    System.out.println(s.toString());
    Query query = entityManager.createNativeQuery(s.toString());
    Object singleResult = query.getSingleResult();
    if (singleResult != null) {
      return Long.valueOf("" + singleResult);
    }
    return 0L;
  }

  /**
   * 查询一定时间内结算状态非终态的结算单(用于通道结算单)  2017/8/1
   *
   * @param beginDate 结算起始日期
   * @param endDate   结算终止日期
   */
  public List<Map<String, Object>> findSettlementByStateAndTradeDate(String beginDate,
                                                                     String endDate) {
    StringBuffer s = new StringBuffer();
    s.append("SELECT id,ledger_no AS ledgerNo,trade_date AS tradeDate FROM yb_ledger_settlement");
    s.append(" WHERE state != 1 AND trade_date BETWEEN '");
    s.append(beginDate).append("' AND '");
    s.append(endDate).append("'");
    System.out.println(s.toString());
    return findBySql(s.toString());
  }

  /**
   * 重置门店结算单状态(用于通道结算单)  2017/7/27
   *
   * @param days     转账起止天数
   * @param date     起始日期
   * @param ledgerNo 商户号
   * @param state    结算状态
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void resetStoreSettlementState(int days, String date, String ledgerNo, int state) {
    StringBuffer s = new StringBuffer();
    String currDate = null;
    if (days == 1) {
      s.append("UPDATE yb_store_settlement SET state = ").append(state);
      s.append(" WHERE ledger_no = '").append(ledgerNo).append("'");
      s.append(" AND trade_date = '").append(date).append("'");
    } else {
      StringBuffer dates = new StringBuffer();
      dates.append(" ('").append(date).append("'");
      while (days > 1) {
        days--;
        currDate = DataUtils.getNextDateByDay(date, days);
        dates.append(",'").append(currDate).append("'");
      }
      dates.append(")");
      s.append("UPDATE yb_store_settlement SET state = ").append(state);
      s.append(" WHERE ledger_no = '").append(ledgerNo).append("'");
      s.append(" AND trade_date IN");
      s.append(dates);
    }
    System.out.println(s.toString());
    Query query = entityManager.createNativeQuery(s.toString());
    int i = query.executeUpdate();
    System.out.println("更新记录===" + i);
  }

  /**
   * 获取各种List数据 2017/4/7
   */
  @SuppressWarnings(value = "unchecked")
  public List<Map<String, Object>> findBySql(String sql) {

    Query query = entityManager.createNativeQuery(sql);
    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    return query.getResultList();
  }

}
