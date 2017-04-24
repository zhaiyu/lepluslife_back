package com.jifenke.lepluslive.yinlian.service;

import com.jifenke.lepluslive.yinlian.domain.criteria.UnionPosOrderCriteria;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionPosOrder;
import com.jifenke.lepluslive.yinlian.repository.UnionPosOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by lss on 2017/3/30.
 */
@Service
@Transactional(readOnly = true)
public class UnionPosOrderService {

    @Inject
    private UnionPosOrderRepository unionPosOrderRepository;

    public static Specification<UnionPosOrder> getWhereClause(UnionPosOrderCriteria unionPosOrderCriteria) {
        return new Specification<UnionPosOrder>() {
            @Override
            public Predicate toPredicate(Root<UnionPosOrder> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (unionPosOrderCriteria.getStartDate() != null && !"".equals(unionPosOrderCriteria.getStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("createdDate"), new Date(unionPosOrderCriteria.getStartDate()),
                                    new Date(unionPosOrderCriteria.getEndDate())));
                }

                if (unionPosOrderCriteria.getOrderState() != null && !"".equals(unionPosOrderCriteria.getOrderState())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("orderState"),
                                    unionPosOrderCriteria.getOrderState()));
                }
                if (unionPosOrderCriteria.getMerchant() != null && !"".equals(unionPosOrderCriteria.getMerchant())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("merchant").get("name"),
                                    unionPosOrderCriteria.getMerchant()));
                }
                if (unionPosOrderCriteria.getUserSid() != null && !"".equals(unionPosOrderCriteria.getUserSid())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("leJiaUser").get("userSid"),
                                    unionPosOrderCriteria.getUserSid()));
                }

                return predicate;
            }
        };
    }

    public Page findUnionPosOrderByPage(UnionPosOrderCriteria unionPosOrderCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        return unionPosOrderRepository
                .findAll(getWhereClause(unionPosOrderCriteria),
                        new PageRequest(unionPosOrderCriteria.getOffset() - 1, limit, sort));
    }

}
