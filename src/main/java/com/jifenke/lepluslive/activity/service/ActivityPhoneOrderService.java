package com.jifenke.lepluslive.activity.service;


import com.jifenke.lepluslive.activity.domain.criteria.PhoneOrderCriteria;
import com.jifenke.lepluslive.activity.domain.entities.ActivityPhoneOrder;
import com.jifenke.lepluslive.activity.repository.ActivityPhoneOrderRepository;
import com.jifenke.lepluslive.user.service.WeiXinUserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * 手机话费订单相关 Created by zhangwen on 2016/10/26.
 */
@Service
public class ActivityPhoneOrderService {

  @Value("${telephone.appkey}")
  private String phoneKey;

  @Value("${telephone.secret}")
  private String phoneSecret;

  @Inject
  private ActivityPhoneOrderRepository repository;

  @Inject
  private WeiXinUserService weiXinUserService;

  @Inject
  private EntityManager em;

  /**
   * 话费订单数据统计  16/10/27
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map orderCount() {
    Map<Object, Object> result = new HashMap<>();
    String subSource = "5%";  //关注来源
    Object[] o = repository.orderCount().get(0);

    result.put("totalWorth", o[0]);
    result.put("totalPrice", o[1]);
    result.put("totalScore", o[2]);
    result.put("totalUser", o[3]);
    result.put("totalNumber", o[4]);
    result.put("totalBack", o[5]);

    Map map = weiXinUserService.subSourceCount(1, 1, 0, subSource);
    result.putAll(map);

    Integer todayUsedWorth = repository.todayUsedWorth();
    result.put("todayUsedWorth", todayUsedWorth);

    return result;
  }

  /**
   * 每一种话费产品的数据统计  16/11/04
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map ruleCount() {

    List<Object[]> list = repository.ruleCount();
    Map<Object, Object> map = new HashMap<>();

    for (Object[] o : list) {
      Map<Object, Object> result = new HashMap<>();
      result.put("totalWorth", o[1]);
      result.put("totalPrice", o[2]);
      result.put("totalScore", o[3]);
      result.put("totalNumber", o[4]);
      result.put("totalUser", o[5]);
      result.put("totalBack", o[6]);
      map.put(o[0], result);
    }
    return map;
  }

  /**
   * 按条件查询话费订单列表  16/10/27
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findByCriteria(PhoneOrderCriteria criteria) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return repository.findAll(getWhereClause(criteria),
                              new PageRequest(criteria.getCurrPage() - 1, 10, sort));
  }

  //封装查询条件   16/10/27  
  private Specification<ActivityPhoneOrder> getWhereClause(PhoneOrderCriteria criteria) {
    return new Specification<ActivityPhoneOrder>() {
      @Override
      public Predicate toPredicate(Root<ActivityPhoneOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getStatus() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("status"), criteria.getStatus()));
        }
        if (criteria.getStartDate() != null && !"".equals(criteria.getStartDate())) {
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(criteria.getStartDate()),
                         new Date(criteria.getEndDate())));
        }
        if (criteria.getPhone() != null && !"".equals(criteria.getPhone())) {
          predicate.getExpressions().add(cb.like(r.get("phone"), "%" + criteria.getPhone() + "%"));
        }
        if (criteria.getUserSid() != null && !"".equals(criteria.getUserSid())) {
          predicate.getExpressions().add(cb.equal(r.get("leJiaUser").get("userSid"),
                                                  criteria.getUserSid()));
        }
        if (criteria.getWorth() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("worth"), criteria.getWorth()));
        }
        if (criteria.getRuleId() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("phoneRule").get("id"), criteria.getRuleId()));
        }
        return predicate;
      }
    };
  }

  /**
   * 分天获取订单统计数量列表  16/10/31
   *
   * @param begin  开始时间  yyyy-MM-dd
   * @param end    结束时间  yyyy-MM-dd
   * @param worth  面值
   * @param ruleId 规则ID
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map<Object, Map<Object, Object>> orderByDayList(String begin, String end, Integer worth,
                                                         Long ruleId) {
    StringBuilder sql = new StringBuilder();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date currDate = calendar.getTime();
    try {
      Date beginDate = sdf.parse(begin);
      Date endDate = sdf.parse(end);
      int day1 = (int) (currDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24) - 1;
      int day2 = (int) (currDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24) - 1;
      sql.append(
          "SELECT DATE_FORMAT(pay_date, '%Y-%m-%d') AS currDate, SUM(worth) AS totalWorth, SUM(true_scoreb) AS totalScore,");
      sql.append(
          "SUM(true_price) AS totalPrice, COUNT(*) AS totalNumber, COUNT(DISTINCT(le_jia_user_id)) AS totalUser, SUM(pay_back_score) AS totalBack FROM activity_phone_order WHERE pay_state = 1");
      if (worth != null && worth != -1) {
        sql.append(" AND worth = ").append(worth);
      }
      if (ruleId != null && ruleId != -1) {
        sql.append(" AND phone_rule_id = ").append(ruleId);
      }
      sql.append(" AND pay_date BETWEEN DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL ")
          .append(day1);
      sql.append(" DAY) AND DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'),INTERVAL ").append(day2)
          .append(" DAY)");
      sql.append(" GROUP BY DATE_FORMAT(pay_date, '%Y-%m-%d')");

      Query query = em.createNativeQuery(sql.toString());
      List<Object[]> list = query.getResultList();

      Map<Object, Map<Object, Object>> result = new TreeMap<>();
      for (Object[] o : list) {
        Map<Object, Object> map = new HashMap<>();
        //map.put("currDate", o[0]);
        map.put("totalWorth", o[1]);
        map.put("totalScore", o[2]);
        map.put("totalPrice", o[3]);
        map.put("totalNumber", o[4]);
        map.put("totalUser", o[5]);
        map.put("totalBack", o[6]);
        result.put(o[0], map);
      }
      StringBuilder sql2 = new StringBuilder();
      sql2.append(
          "SELECT DATE_FORMAT(w.sub_date, '%Y-%m-%d'),SUM(IF(w.sub_state = 1, 1, 0)) AS subCount,SUM(IF(w.state = 1, 1, 0)) AS mCount FROM(SELECT sub_date, sub_state, state FROM wei_xin_user WHERE sub_source LIKE '4%') AS w WHERE ");
      sql2.append(" w.sub_date BETWEEN DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL ")
          .append(day1).append(
          " DAY)AND DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL ");
      sql2.append(day2).append(" DAY) GROUP BY DATE_FORMAT(w.sub_date, '%Y-%m-%d')");
      Query query2 = em.createNativeQuery(sql2.toString());
      List<Object[]> list2 = query2.getResultList();
      for (Object[] o : list2) {
        if (result.containsKey(o[0])) {
          Map<Object, Object> map = result.get(o[0]);
          map.put("subCount", o[0]);
          map.put("mCount", o[1]);
        } else {
          Map<Object, Object> map = new HashMap<>();
          map.put("subCount", o[0]);
          map.put("mCount", o[1]);
          result.put(o[0], map);
        }
      }
      return result;
    } catch (ParseException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

}
