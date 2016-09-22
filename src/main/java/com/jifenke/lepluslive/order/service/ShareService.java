package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.criteria.ShareCriteria;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
import com.jifenke.lepluslive.order.repository.OffLineOrderShareRepository;

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
 * Created by wcg on 16/7/19.
 */
@Service
@Transactional(readOnly = true)
public class ShareService {

  @Inject
  private OffLineOrderShareRepository offLineOrderShareRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Page findShareByPage(ShareCriteria orderCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return offLineOrderShareRepository
        .findAll(getWhereClause(orderCriteria),
                 new PageRequest(orderCriteria.getOffset() - 1, limit, sort));

  }

  public static Specification<OffLineOrderShare> getWhereClause(ShareCriteria shareCriteria) {
    return new Specification<OffLineOrderShare>() {
      @Override
      public Predicate toPredicate(Root<OffLineOrderShare> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (shareCriteria.getStartDate() != null && shareCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(shareCriteria.getStartDate()),
                         new Date(shareCriteria.getEndDate())));
        }

        if (shareCriteria.getUserSid() != null && shareCriteria.getUserSid() != "") {
          predicate.getExpressions().add(
              cb.like(r.get("offLineOrder").get("leJiaUser").get("userSid"),
                      "%" + shareCriteria.getUserSid() + "%"));
        }
        if (shareCriteria.getUserSid() != null && shareCriteria.getUserSid() != "") {
          predicate.getExpressions().add(
              cb.like(r.get("offLineOrder").get("leJiaUser").get("phoneNumber"),
                      "%" + shareCriteria.getUserPhone() + "%"));
        }
        if (shareCriteria.getBindMerchant() != null && shareCriteria.getBindMerchant() != "") {
          predicate.getExpressions().add(
              cb.like(r.get("lockMerchant").get("name"),  "%" +shareCriteria.getBindMerchant()+ "%"));
        }

        if (shareCriteria.getBindPartner() != null && shareCriteria.getBindPartner() != "") {
          predicate.getExpressions().add(
              cb.like(r.get("lockPartner").get("name"),  "%" +shareCriteria.getBindPartner()+ "%"));
        }
        if (shareCriteria.getTradeMerchant() != null && shareCriteria.getTradeMerchant() != "") {
          predicate.getExpressions().add(
              cb.like(r.get("offLineOrder").get("merchant").get("name"),  "%" +shareCriteria.getTradeMerchant()+ "%"));
        }

        if (shareCriteria.getTradePartner() != null && shareCriteria.getTradePartner() != "") {
          predicate.getExpressions().add(
              cb.like(r.get("tradePartner").get("name"),  "%" +shareCriteria.getTradePartner()+ "%"));
        }

        if (shareCriteria.getOrderSid() != null && shareCriteria.getOrderSid() != "") {
          predicate.getExpressions().add(
              cb.like(r.get("offLineOrder").get("orderSid"),  "%" +shareCriteria.getOrderSid()+ "%"));
        }

        return predicate;
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public OffLineOrderShare findOneByOrderId(Long id) {
    return offLineOrderShareRepository.findOneByOrderId(id);

  }
}
