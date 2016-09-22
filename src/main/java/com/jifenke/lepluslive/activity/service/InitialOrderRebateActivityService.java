package com.jifenke.lepluslive.activity.service;

import com.jifenke.lepluslive.activity.domain.criteria.RebateActivityCriteria;
import com.jifenke.lepluslive.activity.domain.criteria.RebateActivityLogCriteria;
import com.jifenke.lepluslive.activity.domain.entities.InitialOrderRebateActivity;
import com.jifenke.lepluslive.activity.domain.entities.InitialOrderRebateActivityLog;
import com.jifenke.lepluslive.activity.repository.InitialOrderRebateActivityLogRepository;
import com.jifenke.lepluslive.activity.repository.InitialOrderRebateActivityRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/9/12.
 */
@Service
@Transactional(readOnly = true)
public class InitialOrderRebateActivityService {

  @Inject
  private EntityManager em;

  @Inject
  private MerchantService merchantService;

  @Inject
  private InitialOrderRebateActivityRepository initialOrderRebateActivityRepository;

  @Inject
  private InitialOrderRebateActivityLogRepository initialOrderRebateActivityLogRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findMerchantOrderRebateActivityByCriteria(RebateActivityCriteria criteria) {
    int start = 10 * (criteria.getOffset() - 1);
    StringBuffer sql = new StringBuffer();
    sql.append(
        "select merchant.merchant_sid,merchant.name,initial_order_rebate_activity.state,merchant.partnership,merchant.bind_wx_user,initial_order_rebate_activity.total_rebate_times,initial_order_rebate_activity.total_rebate_money from (select id,merchant_sid,name,partnership,((select concat(head_image_url,'#=$(',nickname,')') from merchant_wx_user where merchant_wx_user.merchant_user_id = (select id from merchant_user where merchant_user.merchant_id = merchant.id and type =1) ))bind_wx_user from merchant where 1=1 ");
    if (criteria.getMerchant() != null) {
      sql.append(" and merchant.name like '%");
      sql.append(criteria.getMerchant());
      sql.append("%'");
    }
    if (criteria.getBindWxState() != null) {
      if (criteria.getBindWxState() == 1) {
        sql.append(
            "  and merchant.id  in (select merchant_user.merchant_id from merchant_wx_user,merchant_user where merchant_wx_user.merchant_user_id = merchant_user.id and merchant_user.type = 1 ) ");
      } else {
        sql.append(
            " and merchant.id not in (select merchant_user.merchant_id from merchant_wx_user,merchant_user where merchant_wx_user.merchant_user_id = merchant_user.id and merchant_user.type = 1 ) ");
      }
    }
    if (criteria.getState() != null) {
      if (criteria.getState() == 0) {
        sql.append(
            " and merchant.id not in (select merchant_id from initial_order_rebate_activity where state = 1)");
      } else {
        sql.append(" and merchant.id  in (select merchant_id from initial_order_rebate_activity where state = 1) ");
      }
    }
    sql.append("order by create_date desc limit ");
    sql.append(start);
    sql.append(",10 )merchant");
    sql.append(
        " left join  initial_order_rebate_activity on merchant.id =  initial_order_rebate_activity.merchant_id where 1=1 ");


    Query nativeQuery = em.createNativeQuery(sql.toString());
    List<Object[]> resultList = nativeQuery.getResultList();
    return resultList;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long countMerchantOrderRebateActivityByCriteria(RebateActivityCriteria criteria) {
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


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void merchantJoinActivity(Map<String, String> map) {
    String id = map.get("id");
    Merchant merchant = merchantService.findMerchantByMerchantSid(id);
    InitialOrderRebateActivity
        initialOrderRebateActivity =
        initialOrderRebateActivityRepository.findByMerchant(merchant);
    if (initialOrderRebateActivity == null) {
      initialOrderRebateActivity = new InitialOrderRebateActivity();
    }
    initialOrderRebateActivity.setState(1);
    BigDecimal ratio = new BigDecimal(100);
//    initialOrderRebateActivity
//        .setLimit(new BigDecimal(map.get("limit")).multiply(ratio).longValue());
    if (new Integer(map.get("rebateType")) == 1) {
      initialOrderRebateActivity
          .setMinRebate(new BigDecimal(map.get("minRebate")).multiply(ratio).longValue());
    }
    initialOrderRebateActivity
        .setMaxRebate(new BigDecimal(map.get("maxRebate")).multiply(ratio).longValue());
    initialOrderRebateActivity
        .setRebateType(new Integer(map.get("rebateType")));
    if (new Integer(map.get("dailyRebateType")) == 1) {
      initialOrderRebateActivity.setDailyRebateLimit(
          new BigDecimal(map.get("dailyRebateLimit")).multiply(ratio).longValue());
    }
    initialOrderRebateActivity
        .setDailyRebateType(new Integer(map.get("dailyRebateType")));
    initialOrderRebateActivity.setMerchant(merchant);
    initialOrderRebateActivityRepository.save(initialOrderRebateActivity);

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public InitialOrderRebateActivity findInitialOrderRebateActivityByMerchant(String sid) {
    return initialOrderRebateActivityRepository
        .findByMerchant(merchantService.findMerchantByMerchantSid(sid));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void quitActivity(String sid) {
    InitialOrderRebateActivity activity = initialOrderRebateActivityRepository
        .findByMerchant(merchantService.findMerchantByMerchantSid(sid));
    activity.setState(0);
    initialOrderRebateActivityRepository.save(activity);

  }


  public Page findAllRebateActivityLogByCriteria(RebateActivityLogCriteria criteria, int limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return initialOrderRebateActivityLogRepository
        .findAll(getWhereClause(criteria),
                 new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  public static Specification<InitialOrderRebateActivityLog> getWhereClause(
      RebateActivityLogCriteria criteria) {
    return new Specification<InitialOrderRebateActivityLog>() {
      @Override
      public Predicate toPredicate(Root<InitialOrderRebateActivityLog> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       criteria.getState()));
        }

        if (criteria.getStartDate() != null && criteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("createdDate"), new Date(criteria.getStartDate()),
                         new Date(criteria.getEndDate())));
        }

        if (criteria.getMerchant() != null && criteria.getMerchant() != "") {
          if (criteria.getMerchant().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<OffLineOrder>get("offLineOrder").<Merchant>get("merchant")
                            .get("merchantSid"),
                        "%" + criteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<OffLineOrder>get("offLineOrder").<Merchant>get("merchant").get("name"),
                        "%" + criteria.getMerchant() + "%"));
          }
        }
        return predicate;
      }
    };
  }
}
