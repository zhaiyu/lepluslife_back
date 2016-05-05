package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.order.domain.entities.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.order.domain.entities.OrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.PayWay;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/5/5.
 */
@Service
@Transactional(readOnly = true)
public class OffLineOrderService {

  @Inject
  private OffLineOrderRepository offLineOrderRepository;

  public Page findOrderByPage(Integer offset, OLOrderCriteria orderCriteria) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return offLineOrderRepository
        .findAll(getWhereClause(orderCriteria), new PageRequest(offset - 1, 10, sort));
  }

  public static Specification<OffLineOrder> getWhereClause(OLOrderCriteria orderCriteria) {
    return new Specification<OffLineOrder>() {
      @Override
      public Predicate toPredicate(Root<OffLineOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (orderCriteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       orderCriteria.getState()));
        }
        if (orderCriteria.getPhoneNumber() != null) {
          predicate.getExpressions().add(
              cb.like(r.<LeJiaUser>get("leJiaUser").get("phoneNumber"),
                      "%" + orderCriteria.getPhoneNumber() + "%"));
        }

        if (orderCriteria.getStartDate() != null) {
          predicate.getExpressions().add(
              cb.between(r.get("completeDate"), orderCriteria.getStartDate(),
                         orderCriteria.getEndDate()));
        }

        if (orderCriteria.getPayWay() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<PayWay>get("payWay"),
                       orderCriteria.getPayWay()));
        }
        if(orderCriteria.getMerchant()!=null){
          if(orderCriteria.getMerchant().matches("^\\d{1,6}$")){
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                        "%" + orderCriteria.getMerchant() + "%"));
          }else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("Merchant").get("name"),
                        "%" + orderCriteria.getMerchant() + "%"));
          }
        }
        return predicate;
      }
    };
  }

  public List<Object[]> countTransferMoney(Date start, Date end) {
    return offLineOrderRepository.countTransferMoney(start,end);
  }
}
