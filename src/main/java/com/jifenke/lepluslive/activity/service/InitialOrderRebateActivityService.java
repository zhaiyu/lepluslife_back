package com.jifenke.lepluslive.activity.service;

import com.jifenke.lepluslive.activity.domain.criteria.RebateOrderCriteria;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by wcg on 16/9/12.
 */
@Service
@Transactional(readOnly = true)
public class InitialOrderRebateActivityService {

  @Inject
  private EntityManager em;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findMerchantOrderRebateActivityByCriteria(RebateOrderCriteria criteria) {
    int start = 10 * (criteria.getOffset() - 1);
    StringBuffer sql = new StringBuffer();
    sql.append(
        "select merchant.merchant_sid,merchant.name,initial_order_rebate_activity.state,merchant.partnership,merchant.bind_wx_user,initial_order_rebate_activity.total_rebate_times,initial_order_rebate_activity.total_rebate_money from (select id,merchant_sid,name,partnership,((select concat(head_image_url,'#=$(',nickname,')') from merchant_wx_user where merchant_wx_user.merchant_user_id = (select id from merchant_user where merchant_user.merchant_id = merchant.id and type =1) ))bind_wx_user from merchant ");
    if (criteria.getMerchant() != null) {
      sql.append(" where merchant.name like '%");
      sql.append(criteria.getMerchant());
      sql.append("%'");
    }
    sql.append("order by create_date desc limit ");
    sql.append(start);
    sql.append(",10 )merchant");
    sql.append(
        " left join  initial_order_rebate_activity on merchant.id =  initial_order_rebate_activity.merchant_id where 1=1");
    if (criteria.getBindWxState() != null) {
      if (criteria.getBindWxState() == 1) {
        sql.append(" and merchant.bind_wx_user is not null ");
      } else {
        sql.append(" and merchant.bind_wx_user is null ");
      }
    }
    if (criteria.getState() != null) {
      if (criteria.getState() == 0) {
        sql.append(
            " and initial_order_rebate_activity.state is null or initial_order_rebate_activity.state = 0 ");
      } else {
        sql.append(" and initial_order_rebate_activity.state = 1  ");
      }
    }
    Query nativeQuery = em.createNativeQuery(sql.toString());
    List<Object[]> resultList = nativeQuery.getResultList();
    return resultList;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long countMerchantOrderRebateActivityByCriteria(RebateOrderCriteria criteria) {
    StringBuffer sql = new StringBuffer();
    sql.append("select count(*) from (select  merchant.id mid  from (select id from merchant");
    if (criteria.getMerchant() != null) {
      sql.append(" where merchant.name like '%");
      sql.append(criteria.getMerchant());
      sql.append("%'");
    }
    sql.append(
        ")merchant  left join initial_order_rebate_activity on merchant.id =  initial_order_rebate_activity.merchant_id ");
    if (criteria.getState() != null) {
      if (criteria.getState() == 0) {
        sql.append(
            " where initial_order_rebate_activity.state is null or initial_order_rebate_activity.state = 0 ");
      } else {
        sql.append(" where initial_order_rebate_activity.state = 1  ");
      }
    }
    sql.append(" )merchant");
    if (criteria.getBindWxState() != null) {
      if (criteria.getBindWxState() == 0) {
        sql.append(
            " where merchant.mid not in (select merchant_user.merchant_id from merchant_wx_user,merchant_user where merchant_wx_user.merchant_user_id = merchant_user.id and merchant_user.type = 1 )");
      } else {
        sql.append(
            " where merchant.mid  in (select merchant_user.merchant_id from merchant_wx_user,merchant_user where merchant_wx_user.merchant_user_id = merchant_user.id and merchant_user.type = 1 )");
      }
    }
    Query nativeQuery = em.createNativeQuery(sql.toString());
    List<BigInteger> details = nativeQuery.getResultList();
    return details.get(0).longValue();
  }


}
