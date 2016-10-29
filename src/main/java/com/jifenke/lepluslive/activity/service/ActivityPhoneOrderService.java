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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
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


}
