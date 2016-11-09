package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantRebatePolicy;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.order.domain.criteria.FinancialCriteria;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.FinancialRevise;
import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.PayWay;
import com.jifenke.lepluslive.order.repository.FinancialReviseRepository;
import com.jifenke.lepluslive.order.repository.FinancialStatisticRepository;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.order.repository.PosOrderRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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

  @Inject
  private PosOrderRepository posOrderRepository;

  @Inject
  private FinancialReviseRepository financialReviseRepository;

  @Inject
  private EntityManager entityManager;

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
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String starts = sdf.format(start);
    String ends = sdf.format(end);
//    String starts = "2016-10-18 00:32:16";
//    String ends = "2016-11-18 23:32:16";
    StringBuffer sql = new StringBuffer();
    sql.append(
        "select * from (select merchant_id id,sum(case when pay_way_id in (1,2)  then transfer_money else 0 end) wx,sum(case when pay_way_id in (3,4)  then transfer_money else 0 end)");
    sql.append(
        "app,sum(case when pay_way_id in (1,2)  then transfer_money_from_true_pay else 0 end)wx_true,sum(case when pay_way_id in (3,4)  then transfer_money_from_true_pay else 0");
    sql.append(" end)app_true from off_line_order where state = 1 and complete_date between '");
    sql.append(starts);
    sql.append("' and '");
    sql.append(ends);
    sql.append("' group by merchant_id )off_line_order left join (");
    sql.append(
        "select merchant_id sid,sum(transfer_money)pos,sum(transfer_by_bank)pos_true from pos_order where state = 1 and complete_date between '");
    sql.append(starts);
    sql.append("' and '");
    sql.append(ends);
    sql.append(
        "'group by merchant_id  ) pos_order on off_line_order.id = pos_order.sid union select * from (select merchant_id id,sum(case when pay_way_id in (1,2)  then");
    sql.append(
        " transfer_money else 0 end) wx,sum(case when pay_way_id in (3,4)  then transfer_money else 0 end) app,sum(case when pay_way_id in (1,2)  then transfer_money_from_true_pay ");
    sql.append(
        "else 0 end)wx_true,sum(case when pay_way_id in (3,4)  then transfer_money_from_true_pay else 0 end)app_true from off_line_order where state = 1 and complete_date between '");
    sql.append(starts);
    sql.append("' and '");
    sql.append(ends);
    sql.append("' group by merchant_id )off_line_order right join (");
    sql.append(
        "select merchant_id sid,sum(transfer_money)pos,sum(transfer_by_bank)pos_true from pos_order where state = 1 and complete_date between '");
    sql.append(starts);
    sql.append("' and '");
    sql.append(ends);
    sql.append("' group by merchant_id ) pos_order on off_line_order.id = pos_order.sid");
    List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
    return resultList;
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<FinancialStatistic> findAllNonTransferFinancialStatistic() {
    return financialStatisticRepository.findAllByState(0);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public OffLineOrder findOneByOrderSid(String orderSid) {
    return offLineOrderRepository.findOneByOrderSid(orderSid);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Optional<FinancialStatistic> findFinancialByMerchantAndDate(Merchant merchant, Date end) {
    return financialStatisticRepository.findByMerchantAndBalanceDate(merchant, end);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void createFinancialRevise(FinancialStatistic financialStatistic, Merchant merchant) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.SECOND, -1);

    Date end = calendar.getTime();
    FinancialRevise financialRevise = new FinancialRevise();
    financialRevise.setFinancialStatistic(financialStatistic);
    Object[]
        object =
        posOrderRepository.countPosTransferMoneyByMerchant(merchant.getId(), start, end);
    financialRevise.setRevisePosTransfer(Long.parseLong(object[0].toString()));
    financialRevise.setRevisePosTransTruePay(Long.parseLong(object[1].toString()));
    financialReviseRepository.save(financialRevise);
  }


}