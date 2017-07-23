package com.jifenke.lepluslive.yibao.service;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
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
@Service
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
   * 获取各种List数据 2017/4/7
   */
  @SuppressWarnings(value = "unchecked")
  public List<Map<String, Object>> findBySql(String sql) {

    Query query = entityManager.createNativeQuery(sql);
    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    return query.getResultList();
  }

}
