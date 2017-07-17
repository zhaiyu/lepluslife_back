package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.domain.criteria.LedgerTransferCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
import com.jifenke.lepluslive.yibao.repository.LedgerTransferRepository;
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


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerTransferService {

  @Inject
  private LedgerTransferRepository ledgerTransferRepository;

  /***
   *  根据条件查询转账记录
   *  Created by xf on 2017-07-14.
   */
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Page<LedgerTransfer> findByCriteria(LedgerTransferCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return ledgerTransferRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  public static Specification<LedgerTransfer> getWhereClause(LedgerTransferCriteria criteria) {
    return new Specification<LedgerTransfer>() {
      @Override
      public Predicate toPredicate(Root<LedgerTransfer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        // 通道结算单号
        if (criteria.getLedgerSid() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("ledgerSid"), criteria.getLedgerSid()));
        }
        // 转账状态 0=待转账，1=转账成功，其他为易宝错误码
        if (criteria.getState() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("state"), criteria.getState()));
        }
        // 易宝商户号
        if (criteria.getLedgerNo() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("ledgerNo"),  criteria.getLedgerNo()));
        }
        // 转账请求号（转账单号）
        if (criteria.getOrderSid() != null) {
          predicate.getExpressions().add(
                  cb.equal(root.get("orderSid"), criteria.getOrderSid()));
        }
        return predicate;
      }
    };
  }
}
