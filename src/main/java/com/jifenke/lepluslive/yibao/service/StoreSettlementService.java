package com.jifenke.lepluslive.yibao.service;


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


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class StoreSettlementService {

    @Inject
    private StoreSettlementRepository storeSettlementRepository;


    /***
     *  根据条件查询门店结算单
     *  Created by xf on 2017-07-13.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<StoreSettlement> findByCriteria(StoreSettlementCriteria criteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return storeSettlementRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
    }

    public static Specification<StoreSettlement> getWhereClause(StoreSettlementCriteria criteria) {
        return new Specification<StoreSettlement>() {
            @Override
            public Predicate toPredicate(Root<StoreSettlement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 门店ID
                if (criteria.getMerchantId() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("merchant").get("id"), criteria.getMerchantId()));
                }
                // 门店名称
                if (criteria.getMerchantName() != null) {
                    predicate.getExpressions().add(
                            cb.like(root.get("merchant").get("name"), "%" + criteria.getMerchantName() + "%"));
                }
                // 易宝商户号
                if (criteria.getLedgerNo() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("ledgerNo"), criteria.getLedgerNo()));
                }
                //  通道结算单号
                if (criteria.getLedgerSid() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("ledgerSid"), criteria.getLedgerSid()));
                }
                //  清算日期
                if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
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
