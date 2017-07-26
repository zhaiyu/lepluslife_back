package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.yibao.domain.criteria.StoreSettlementCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.StoreSettlement;
import com.jifenke.lepluslive.yibao.repository.StoreSettlementRepository;

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
import java.util.Map;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class StoreSettlementService {

  @Inject
  private StoreSettlementRepository storeSettlementRepository;

  @Transactional(propagation = Propagation.REQUIRED)
  public void createStoreSettlement(Map<String, Object> orderMap, Map<String, Object> refundMap,
                                    String tradeDate) {

    String merchantId = "" + orderMap.get("merchantId");
    Long transferMoney = Long.valueOf("" + orderMap.get("transferMoney"));
    Long refundExpend = 0L;
    Integer refundNumber = 0;
    if (refundMap.get(merchantId) != null) {
      String[] refunds = ("" + refundMap.get(merchantId)).split("_");
      if (refunds.length == 2) {
        refundExpend = Long.valueOf(refunds[0]);
        transferMoney -= refundExpend;
        refundNumber = Integer.valueOf(refunds[1]);
      }
    }
    StoreSettlement settlement = new StoreSettlement();
    Merchant merchant = new Merchant();
    merchant.setId(Long.valueOf(merchantId));
    settlement.setMerchant(merchant);
    settlement.setTradeDate(tradeDate);
    settlement.setLedgerNo(orderMap.get("ledgerNo") == null ? "" : "" + orderMap.get("ledgerNo"));
    settlement.setRefundNumber(refundNumber);
    settlement.setRefundExpend(refundExpend);
    settlement.setWxTruePayTransfer(Long.valueOf("" + orderMap.get("transferMoneyByWx")));
    settlement.setAliTruePayTransfer(Long.valueOf("" + orderMap.get("transferMoneyByZfb")));
    settlement.setScoreTransfer(Long.valueOf("" + orderMap.get("transferMoneyByScore")));
    settlement.setActualTransfer(transferMoney);
    storeSettlementRepository.save(settlement);
  }


  /***
   *  根据条件查询门店结算单
   *  Created by xf on 2017-07-13.
   */
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Page<StoreSettlement> findByCriteria(StoreSettlementCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return storeSettlementRepository
        .findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  public static Specification<StoreSettlement> getWhereClause(StoreSettlementCriteria criteria) {
    return new Specification<StoreSettlement>() {
      @Override
      public Predicate toPredicate(Root<StoreSettlement> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        // 门店ID
        if (criteria.getMerchantId() != null && !"".equals(criteria.getMerchantId())) {
          predicate.getExpressions().add(
              cb.equal(root.get("merchant").get("id"), criteria.getMerchantId()));
        }
        // 门店名称
        if (criteria.getMerchantName() != null && !"".equals(criteria.getMerchantName())) {
          predicate.getExpressions().add(
              cb.like(root.get("merchant").get("name"), "%" + criteria.getMerchantName() + "%"));
        }
        // 易宝商户号
        if (criteria.getLedgerNo() != null && !"".equals(criteria.getLedgerNo())) {
          predicate.getExpressions().add(
              cb.equal(root.get("ledgerNo"), criteria.getLedgerNo()));
        }
        //  通道结算单号
        if (criteria.getLedgerSid() != null && !"".equals(criteria.getLedgerSid())) {
          predicate.getExpressions().add(
              cb.equal(root.get("ledgerSid"), criteria.getLedgerSid()));
        }
        //  清算日期
        if (criteria.getStartDate() != null && criteria.getEndDate() != null && !""
            .equals(criteria.getStartDate())) {
          Date start = new Date(criteria.getStartDate());
          Date end = new Date(criteria.getEndDate());
          predicate.getExpressions().add(
              cb.between(root.get("tradeDate"), start, end));
        }
        return predicate;
      }
    };
  }
}
