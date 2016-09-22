package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.order.domain.criteria.FinancialCriteria;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.PayWay;
import com.jifenke.lepluslive.order.repository.FinancialStatisticRepository;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

  @Inject
  private MerchantService merchantService;

  @Inject
  private FinancialStatisticRepository financialStatisticRepository;

  public Page findOrderByPage(OLOrderCriteria orderCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return offLineOrderRepository
        .findAll(getWhereClause(orderCriteria),
                 new PageRequest(orderCriteria.getOffset() - 1, limit, sort));
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

        // zxf 2016/09/02
        if (!"".equals(orderCriteria.getOrderSid()) && orderCriteria.getOrderSid() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("orderSid"), orderCriteria.getOrderSid())
          );
        }
        if (orderCriteria.getRebateWay() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("rebateWay"), orderCriteria.getRebateWay())
          );
        }

        //lss 2016/07/19
        if (orderCriteria.getAmount() != null) {
          predicate.getExpressions().add(
              cb.greaterThanOrEqualTo(r.get("totalPrice"), orderCriteria.getAmount()));
        }

        if (orderCriteria.getPhoneNumber() != null && orderCriteria.getPhoneNumber() != "") {
          predicate.getExpressions().add(
              cb.like(r.<LeJiaUser>get("leJiaUser").get("phoneNumber"),
                      "%" + orderCriteria.getPhoneNumber() + "%"));
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

        if (orderCriteria.getPayWay() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<PayWay>get("payWay"),
                       new PayWay(orderCriteria.getPayWay())));
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

  public List<Object[]> countTransferMoney(Date start, Date end) {
    return offLineOrderRepository.countTransferMoney(start, end);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeOrderStateToPaid(Long id) {
    OffLineOrder offLineOrder = offLineOrderRepository.findOne(id);
    offLineOrder.setState(1);
    offLineOrder.setCompleteDate(new Date());
    offLineOrderRepository.save(offLineOrder);
  }

  public Page findFinancialByCirterial(FinancialCriteria financialCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "balanceDate");
    return financialStatisticRepository
        .findAll(getFinancialClause(financialCriteria),
                 new PageRequest(financialCriteria.getOffset() - 1, limit, sort));
  }


  public static Specification<FinancialStatistic> getFinancialClause(
      FinancialCriteria financialCriteria) {
    return new Specification<FinancialStatistic>() {
      @Override
      public Predicate toPredicate(Root<FinancialStatistic> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (financialCriteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       financialCriteria.getState()));
        }

        if (financialCriteria.getStartDate() != null && financialCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("balanceDate"), new Date(financialCriteria.getStartDate()),
                         new Date(financialCriteria.getEndDate())));
        }

        if (financialCriteria.getTransferEndDate() != null
            && financialCriteria.getTransferStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("transferDate"), new Date(financialCriteria.getTransferStartDate()),
                         new Date(financialCriteria.getTransferEndDate())));
        }

        if (financialCriteria.getMerchant() != null && financialCriteria.getMerchant() != "") {
          if (financialCriteria.getMerchant().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                        "%" + financialCriteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("name"),
                        "%" + financialCriteria.getMerchant() + "%"));
          }
        }
        return predicate;
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public FinancialStatistic changeFinancialStateToTransfer(Long id) {
    FinancialStatistic financialStatistic = financialStatisticRepository.findOne(id);
    financialStatistic.setState(1);
    financialStatistic.setTransferDate(new Date());
    financialStatisticRepository.save(financialStatistic);
    merchantService.changeMerchantWalletTotalTransferMoney(financialStatistic);
    return financialStatistic;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeFinancialStateToHover(Long id) {
    FinancialStatistic financialStatistic = financialStatisticRepository.findOne(id);
    financialStatistic.setState(2);
    financialStatisticRepository.save(financialStatistic);
  }

  public List<FinancialStatistic> findAllNonTransferFinancialStatistic() {
    return financialStatisticRepository.findAllByState(0);
  }
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public OffLineOrder findOneByOrderSid(String orderSid) {
    return offLineOrderRepository.findOneByOrderSid(orderSid);
  }
}