package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPos;
import com.jifenke.lepluslive.merchant.service.MerchantPosService;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.criteria.PosOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.domain.entities.PosErrorLog;
import com.jifenke.lepluslive.order.domain.entities.PosOrder;
import com.jifenke.lepluslive.order.repository.PosDailyBillRepository;
import com.jifenke.lepluslive.order.repository.PosErrorLogRepository;
import com.jifenke.lepluslive.order.repository.PosOrderRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

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

  @Inject
  private PosDailyBillRepository posDailyBillRepository;

  @Inject
  private MerchantPosService merchantPosService;

  @Inject
  private PosErrorLogRepository posErrorLogRepository;


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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void savePosDailyBill(PosDailyBill posDailyBill) {
    posDailyBillRepository.save(posDailyBill);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Merchant checkOrder(String posId, String orderSid, String paidMoney, String transferMoney,
                             String paidResult, String completeDate, PosDailyBill posDailyBill)
      throws ParseException {
    Merchant merchant = null;
    PosOrder posOrder = posOrderRepository.findByOrderSid(orderSid);
    BigDecimal truePay = new BigDecimal(paidMoney).multiply(new BigDecimal(100));
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = simpleDateFormat.parse(completeDate);
    Long
        truePayCommission =
        truePay.subtract(new BigDecimal(transferMoney).multiply(new BigDecimal(100))).longValue();
    if (posOrder != null) {//订单存在
      if (posOrder.getState() == 1 && posOrder.getTruePay() == truePay.longValue()
          && truePayCommission
          .equals(posOrder.getTruePayCommission())) {
        return merchant;
      } else {
        posOrder.setTruePay(truePay.longValue());
        posOrder.setTruePayCommission(truePayCommission);
        posOrder.setState(1);
        posOrderRepository.save(posOrder);
        //记录差错日志
        PosErrorLog posErrorLog = new PosErrorLog();
        posErrorLog.setCreateDate(date);
        posErrorLog.setOrderSid(orderSid);
        posErrorLog.setLocalState(posOrder.getState());
        posErrorLog.setLocalCommission(posOrder.getTruePayCommission());
        posErrorLog.setLocalTruePay(posOrder.getTruePay());
        posErrorLog.setRemoteCommission(truePayCommission);
        posErrorLog.setRemoteTruePay(truePay.longValue());
        posErrorLog.setPosDailyBill(posDailyBill);
        posErrorLogRepository.save(posErrorLog);
        return posOrder.getMerchant();
      }
    } else {//订单不存在
      posOrder.setTruePay(truePay.longValue());
      posOrder.setTruePayCommission(truePayCommission);
      posOrder.setTotalPrice(truePay.longValue());
      posOrder.setTrueScore(0L);
      posOrder.setLjCommission(truePayCommission);
      posOrder.setRebateWay(1);
      posOrder.setTransferByBank(
          new BigDecimal(transferMoney).multiply(new BigDecimal(100)).longValue());
      posOrder.setCreatedDate(date);
      posOrder.setCompleteDate(date);
      posOrder.setState(1);
      MerchantPos merchantPos = merchantPosService.findPosByPosId(posId);
      merchant = merchantPos.getMerchant();
      posOrder.setMerchant(merchant);
      posOrder.setMerchantPos(merchantPos);
      posOrderRepository.save(posOrder);
      PosErrorLog posErrorLog = new PosErrorLog();
      posErrorLog.setCreateDate(date);
      posErrorLog.setOrderSid(orderSid);
      posErrorLog.setLocalState(2);
      posErrorLog.setLocalCommission(posOrder.getTruePayCommission());
      posErrorLog.setLocalTruePay(posOrder.getTruePay());
      posErrorLog.setRemoteCommission(truePayCommission);
      posErrorLog.setRemoteTruePay(truePay.longValue());
      posErrorLog.setPosDailyBill(posDailyBill);
      posErrorLogRepository.save(posErrorLog);
      return merchant;
    }
  }

  /**
   *  分页查找 订单文件(有错误记录)
   */
  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public List<PosDailyBill> findErrorPosDailyBill(Integer offset, Integer pageSize) {
    Integer startIndex = (offset-1) * pageSize;
    return  posDailyBillRepository.findErrorBillByPage(startIndex,pageSize);
  }
  /**
   *  分页查找 订单文件-总记录数  (有错误记录)
   */
  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public Long countErroPosDailyBill() {
    return  posDailyBillRepository.countErrorBill();
  }

  /**
   *  分页查找 订单文件-总页数  (有错误记录)
   */
  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public Integer pageCountErroPosDailyBill(Integer pageSize) {
    Long countErrorBill = posDailyBillRepository.countErrorBill();
    Integer pageCount = new Double(Math.ceil((countErrorBill/(pageSize*1.0)))).intValue();
    return  pageCount;
  }


  /***
   *  根据订单文件查找相应的错误记录
   * @param dailyBills
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public List<List<PosErrorLog>> findPosErrorLogByBill(List<PosDailyBill> dailyBills) {
    List<List<PosErrorLog>> errlogsList  =  new ArrayList<>();
    for (PosDailyBill dailyBill : dailyBills) {
      List<PosErrorLog> errorLogs = posErrorLogRepository.findByPosDailyBill(dailyBill);
      errlogsList.add(errorLogs);
    }
    return errlogsList;
  }

}
