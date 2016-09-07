package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.criteria.PosOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.PosOrder;
import com.jifenke.lepluslive.order.repository.PosOrderRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/9/5.
 */
@Service
@Transactional(readOnly = true)
public class PosOrderService {

  @Inject
  private PosOrderRepository posOrderRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findOrderByPage(PosOrderCriteria posOrderCriteria, int limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return posOrderRepository
        .findAll(getWhereClause(posOrderCriteria),
                 new PageRequest(posOrderCriteria.getOffset() - 1, limit, sort));
  }

  public static Specification<PosOrder> getWhereClause(PosOrderCriteria orderCriteria) {
    return new Specification<PosOrder>() {
      @Override
      public Predicate toPredicate(Root<PosOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (orderCriteria.getUserPhone() != null && orderCriteria.getUserPhone() != "") {
          predicate.getExpressions().add(
              cb.like(r.<LeJiaUser>get("leJiaUser").get("phoneNumber"),
                      "%" + orderCriteria.getUserPhone() + "%"));
        }
        if (orderCriteria.getUserSid() != null && orderCriteria.getUserSid() != "") {
          predicate.getExpressions().add(
              cb.like(r.<LeJiaUser>get("leJiaUser").get("userSid"),
                      "%" + orderCriteria.getUserSid() + "%"));
        }

        if (orderCriteria.getStartDate() != null && orderCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("completeDate"), new Date(orderCriteria.getStartDate()),
                         new Date(orderCriteria.getEndDate())));
        }

        if (orderCriteria.getTradeFlag() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("tradeFlag"),
                       orderCriteria.getTradeFlag()));
        }
        if (orderCriteria.getRebateWay() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("rebateWay"),
                       orderCriteria.getRebateWay()));
        }
        if (orderCriteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       orderCriteria.getState()));
        }
        if (orderCriteria.getMerchantLocation() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("merchant").<City>get("city"),
                       new City(orderCriteria.getMerchantLocation())));
        }
        if (orderCriteria.getMerchant() != null && orderCriteria.getMerchant() != "") {
          if (orderCriteria.getMerchant().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                        "%" + orderCriteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("name"),
                        "%" + orderCriteria.getMerchant() + "%"));
          }
        }
        return predicate;
      }
    };
  }
}
