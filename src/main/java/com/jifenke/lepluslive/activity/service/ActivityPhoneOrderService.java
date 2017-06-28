package com.jifenke.lepluslive.activity.service;


import com.jifenke.lepluslive.activity.domain.criteria.PhoneOrderCriteria;
import com.jifenke.lepluslive.activity.domain.entities.ActivityPhoneOrder;
import com.jifenke.lepluslive.activity.repository.ActivityPhoneOrderRepository;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.service.WeiXinUserService;
import com.jifenke.lepluslive.weixin.service.WxTemMsgService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
  private WxTemMsgService wxTemMsgService;

  @Inject
  private RechargeService rechargeService;

  @Inject
  private EntityManager em;

  /**
   * 将订单设为已充值(其他平台充值的)  16/12/09
   *
   * @param orderSid 自有订单号
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void setState(String orderSid) throws Exception {
    ActivityPhoneOrder order = repository.findByOrderSid(orderSid);
    try {
      Date date = new Date();
      order.setState(2);
      order.setPayState(1);
      if (order.getPayDate() == null) {
        order.setPayDate(date);
      }
      order.setCompleteDate(date);
      order.setPlatform(3);
      repository.save(order);
      //给用户发充值模板通知
      String[] keys = new String[4];
      keys[0] = order.getPhone();
      keys[1] = order.getWorth() + ".00元";
      keys[2] = order.getTruePrice() / 100.0 + "元+" + order.getTrueScoreB() + "积分";
      keys[3] = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getPayDate());
      wxTemMsgService.sendTemMessage(
          weiXinUserService.findWeiXinUserByLeJiaUser(order.getLeJiaUser()).getOpenId(), 6L, keys);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  /**
   * 将订单重新充值  16/12/09
   *
   * @param orderSid 自有订单号
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Map<String, Object> recharge(String orderSid) throws Exception {
    Map<String, Object> result = new HashMap<>();
    //查询该笔订单是否已经充值
    try {
      Map map = rechargeService.status(orderSid);
      System.out.println(map);
      ActivityPhoneOrder order = repository.findByOrderSid(orderSid);
      String status = String.valueOf(((Map<String, Object>) map.get("data")).get("status"));
      Date date = new Date();
      if ("success".equals(status)) {
        //掉单，将订单设为已支付，并发送模板消息
        Map map2 = (Map) map.get("data");
        order.setState(2);
        order.setWorth(Integer.valueOf(map2.get("card_worth").toString()));
        order.setUsePrice(
            new BigDecimal(map2.get("price").toString()).multiply(new BigDecimal(100)).intValue());
        order.setOrderId(map2.get("order_id").toString());
        order.setCompleteDate(date);
        order.setPlatform(2);
        repository.save(order);
        //给用户发充值模板通知
        String[] keys = new String[4];
        keys[0] = order.getPhone();
        keys[1] = order.getWorth() + ".00元";
        keys[2] = order.getTruePrice() / 100.0 + "元+" + order.getTrueScoreB() / 100.0 + "金币";
        keys[3] =
            new SimpleDateFormat("yyyy-MM-dd HH:mm")
                .format(order.getPayDate() == null ? date : order.getPayDate());
        wxTemMsgService.sendTemMessage(
            weiXinUserService.findWeiXinUserByLeJiaUser(order.getLeJiaUser()).getOpenId(), 6L,
            keys);
        result.put("status", 101);
        result.put("msg", "充值回调丢失,该订单已经充值成功");
      } else if ("recharging".equals(status) || "init".equals(status)) {
        result.put("status", 102);
        result.put("msg", "该订单正在充值中,请稍后查询");
      } else if ("failure".equals(status)) {
        //调充值接口充值
        orderSid = MvUtil.getOrderNumber();
        Map<Object, Object>
            result2 =
            rechargeService.submit(order.getPhone(), order.getWorth(), orderSid);
        if (result2.get("status") == null || "failure"
            .equalsIgnoreCase("" + result2.get("status"))) {
          //充值失败
          order.setState(3);
          order.setErrorDate(new Date());
          order.setMessage(result.get("message") == null ? "未知错误" : ("" + result.get("message")));
          repository.save(order);
          result.put("status", 103);
          result.put("msg", result.get("message"));
        } else {
          order.setOrderSid(orderSid);
          order.setPlatform(2);
          repository.saveAndFlush(order);
          result.put("status", 100);
          result.put("msg", "重新充值成功，请稍后查询");
        }
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }


  /**
   * 话费订单数据统计  16/10/27
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map orderCount() {
    Map<Object, Object> result = new HashMap<>();
    Object[] o = repository.orderCount().get(0);

    result.put("totalWorth", o[0]);
    result.put("totalPrice", o[1]);
    result.put("totalScore", o[2]);
    result.put("totalUser", o[3]);
    result.put("totalNumber", o[4]);
    result.put("totalBack", o[5]);
    result.put("totalCost", o[6]);

    //活动带来粉丝/会员
//    String subSource = "5%";  //关注来源
//    Map map = weiXinUserService.subSourceCount(1, 1, 0, subSource);
//    result.putAll(map);
    //今日已使用的话费
//    Integer todayUsedWorth = repository.todayUsedWorth();
//    result.put("todayUsedWorth", todayUsedWorth);

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
  public Page findByCriteria(PhoneOrderCriteria criteria,Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return repository.findAll(getWhereClause(criteria),
                              new PageRequest(criteria.getCurrPage() - 1, limit, sort));
  }

  //封装查询条件   16/10/27  
  private Specification<ActivityPhoneOrder> getWhereClause(PhoneOrderCriteria criteria) {
    return new Specification<ActivityPhoneOrder>() {
      @Override
      public Predicate toPredicate(Root<ActivityPhoneOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getState() != null) {
          predicate.getExpressions().add(cb.equal(r.get("state"), criteria.getState()));
        }
        if (criteria.getType() != null) {
          predicate.getExpressions().add(cb.equal(r.get("type"), criteria.getType()));
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
        if (criteria.getOrderId() != null && !"".equals(criteria.getOrderId())) {
          predicate.getExpressions().add(cb.equal(r.get("orderId"), criteria.getOrderId()));
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
      int day1 = (int) ((currDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24) - 1);
      int day2 = (int) ((currDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24) - 1);
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
      long j = endDate.getTime();
      Map<Object, Map<Object, Object>> result = new TreeMap<>();
      for (long i = beginDate.getTime(); i <= j; i += 86400000) {
        result.put(sdf.format(new Date(i)), new HashMap<>());
      }

      for (Object[] o : list) {
        Map<Object, Object> map = result.get(o[0]);
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
          "SELECT DATE_FORMAT(w.sub_date, '%Y-%m-%d'),SUM(IF(w.sub_state = 1, 1, 0)) AS subCount,SUM(IF(w.state = 1, 1, 0)) AS mCount FROM(SELECT sub_date, sub_state, state FROM wei_xin_user WHERE sub_source LIKE '5%') AS w WHERE ");
      sql2.append(" w.sub_date BETWEEN DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL ")
          .append(day1).append(
          " DAY)AND DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d'), INTERVAL ");
      sql2.append(day2).append(" DAY) GROUP BY DATE_FORMAT(w.sub_date, '%Y-%m-%d')");
      Query query2 = em.createNativeQuery(sql2.toString());
      List<Object[]> list2 = query2.getResultList();
      for (Object[] o : list2) {
        Map<Object, Object> map = result.get(o[0]);
        map.put("subCount", o[1]);
        map.put("mCount", o[2]);
      }
      return result;
    } catch (ParseException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

}
