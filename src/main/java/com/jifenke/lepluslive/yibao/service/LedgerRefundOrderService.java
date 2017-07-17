package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.domain.criteria.LedgerRefundOrderCriteria;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerTransferCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerRefundOrder;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
import com.jifenke.lepluslive.yibao.repository.LedgerRefundOrderRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerRefundOrderService {

  @Inject
  private LedgerRefundOrderRepository ledgerRefundOrderRepository;


  /***
   *  根据条件查询退款单记录
   *  Created by xf on 2017-07-14.
   */
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Page<LedgerRefundOrder> findByCriteria(LedgerRefundOrderCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return ledgerRefundOrderRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  public static Specification<LedgerRefundOrder> getWhereClause(LedgerRefundOrderCriteria criteria) {
    return new Specification<LedgerRefundOrder>() {
      @Override
      public Predicate toPredicate(Root<LedgerRefundOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        // 退款单编号
        if (criteria.getRefundOrderSid() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("refundOrderSid"), criteria.getRefundOrderSid()));
        }
        //退款状态 0=待退款，1=未开始退款，2=退款成功，3=退款失败，其他为通道返回码
        if (criteria.getState() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("state"), criteria.getState()));
        }
        // 门店ID
        if (criteria.getMerchantId() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("merchantId"),  criteria.getMerchantId()));
        }
        // 订单类型
        if (criteria.getOrderType() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("orderType"), criteria.getOrderType()));
        }
        // 退款完成时间
        if(criteria.getCompleteStart()!=null&&criteria.getCompleteEnd()!=null) {
          Date start = new Date(criteria.getCompleteStart());
          Date end = new Date(criteria.getCompleteEnd());
          predicate.getExpressions().add(
                  cb.between(root.get("dateCompleted"), start, end));
        }
        return predicate;
      }
    };
  }
}
