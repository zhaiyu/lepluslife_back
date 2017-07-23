package com.jifenke.lepluslive.fuyou.service;

import com.jifenke.lepluslive.fuyou.domain.criteria.ScanCodeRefundOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeRefundLog;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeRefundOrder;
import com.jifenke.lepluslive.fuyou.repository.ScanCodeRefundLogRepository;
import com.jifenke.lepluslive.fuyou.repository.ScanCodeRefundOrderRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.weixin.domain.entities.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 富友退款单 Created by zhangwen on 2016/12/22.
 */
@Service
@Transactional(readOnly = true)
public class ScanCodeRefundOrderService {

  @Inject
  private ScanCodeRefundOrderRepository repository;

  @Inject
  private ScanCodeRefundLogRepository logRepository;

  public ScanCodeRefundOrder findByScanCodeOrder(ScanCodeOrder scanCodeOrder) {
    List<ScanCodeRefundOrder> list = repository.findByScanCodeOrder(scanCodeOrder);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 获取某一天的已完成退款单列表  2016/12/29
   */
  public List<ScanCodeRefundOrder> findByCompleteDateBetween(Date beginDate,
                                                                     Date endDate) {
    return repository.findByStateAndCompleteDateBetween(1, beginDate, endDate);
  }


  /**
   * 保存退款单  2016/12/26
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveOrder(ScanCodeRefundOrder refundOrder) {
    repository.saveAndFlush(refundOrder);
  }

  /**
   * 保存退款日志  2016/12/26
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveOrderLog(Map<String, String> map) {
    String resultCode = map.get("result_code");
    ScanCodeRefundLog log = new ScanCodeRefundLog();
    log.setResultCode(resultCode);
    if ("000000".equals(resultCode)) {
      log.setResultMsg("SUCCESS");
    } else {
      log.setResultMsg(map.get("result_msg"));
    }
    log.setOrderType(map.get("order_type"));
    log.setOrderSid(map.get("mchnt_order_no"));
    log.setRefundOrderSid(map.get("refund_order_no"));
    log.setTransactionId(map.get("transaction_id"));
    log.setRefundId(map.get("refund_id"));
    log.setFySettleDt(map.get("reserved_ fy_settle_dt"));
    logRepository.save(log);
  }

  /**
   * 富友扫码退款单分页条件查询  16/12/20
   *
   * @param criteria 查询条件
   * @param limit    查询条数
   */
  public Page findOrderByPage(ScanCodeRefundOrderCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "completeDate");
    return repository
        .findAll(getWhereClause(criteria),
                 new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  private static Specification<ScanCodeRefundOrder> getWhereClause(
      ScanCodeRefundOrderCriteria orderCriteria) {
    return new Specification<ScanCodeRefundOrder>() {
      @Override
      public Predicate toPredicate(Root<ScanCodeRefundOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        predicate.getExpressions().add(cb.equal(r.get("state"), 1));

        if (orderCriteria.getRefundStartDate() != null && !""
            .equals(orderCriteria.getRefundStartDate())) {//退款完成时间
          predicate.getExpressions().add(
              cb.between(r.get("completeDate"), new Date(orderCriteria.getRefundStartDate()),
                         new Date(orderCriteria.getRefundEndDate())));
        }
        if (orderCriteria.getStartDate() != null && !""
            .equals(orderCriteria.getStartDate())) {//交易完成时间
          predicate.getExpressions().add(
              cb.between(r.<ScanCodeOrder>get("scanCodeOrder").get("completeDate"),
                         new Date(orderCriteria.getStartDate()),
                         new Date(orderCriteria.getEndDate())));
        }

        if (orderCriteria.getUserSid() != null && !"".equals(orderCriteria.getUserSid())) {//消费者SID
          predicate.getExpressions().add(
              cb.like(
                  r.<ScanCodeOrder>get("scanCodeOrder").<LeJiaUser>get("leJiaUser").get("userSid"),
                  "%" + orderCriteria.getUserSid() + "%"));
        }

        if (orderCriteria.getPhoneNumber() != null && !""
            .equals(orderCriteria.getPhoneNumber())) { //消费者手机号
          predicate.getExpressions().add(
              cb.like(r.<ScanCodeOrder>get("scanCodeOrder").<LeJiaUser>get("leJiaUser")
                          .get("phoneNumber"),
                      "%" + orderCriteria.getPhoneNumber() + "%"));
        }

        if (orderCriteria.getMerchantName() != null && !""
            .equals(orderCriteria.getMerchantName())) { //门店名称
          predicate.getExpressions().add(
              cb.like(r.<ScanCodeOrder>get("scanCodeOrder").<Merchant>get("merchant").get("name"),
                      "%" + orderCriteria.getMerchantName() + "%"));
        }

        if (orderCriteria.getMerchantUserId() != null) { //商户ID
          predicate.getExpressions().add(cb.equal(r.<ScanCodeOrder>get("scanCodeOrder").get(
                                                      "merchantUserId"),
                                                  orderCriteria.getMerchantUserId()));
        }

        if (orderCriteria.getOrderType() != null) { //订单类型
          predicate.getExpressions().add(
              cb.equal(r.<ScanCodeOrder>get("scanCodeOrder").<Category>get("orderType").get("id"),
                       orderCriteria.getOrderType()));
        }

        if (orderCriteria.getOrderSid() != null && !"".equals(orderCriteria.getOrderSid())) { //订单编号
          predicate.getExpressions().add(cb.equal(
              r.<ScanCodeOrder>get("scanCodeOrder").get("orderSid"), orderCriteria.getOrderSid()));
        }

        if (orderCriteria.getMerchantNum() != null && !""
            .equals(orderCriteria.getMerchantNum())) {//富友商户号
          predicate.getExpressions().add(
              cb.equal(r.get("merchantNum"), orderCriteria.getMerchantNum()));
        }
        return predicate;
      }
    };
  }


}
