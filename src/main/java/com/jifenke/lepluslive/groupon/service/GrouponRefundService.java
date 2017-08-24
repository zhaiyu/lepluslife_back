package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.criteria.GrouponRefundCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponRefund;
import com.jifenke.lepluslive.groupon.repository.GrouponRefundRepository;

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
 * 退款管理 Service
 *
 * @author XF
 * @date 2017/6/20
 */
@Service
@Transactional(readOnly = true)
public class GrouponRefundService {
    @Inject
    private GrouponRefundRepository grouponRefundRepository;

    /***
     *  根据条件查询退款单
     *  Created by xf on 2017-06-22.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<GrouponRefund> findByCriteria(GrouponRefundCriteria criteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return grouponRefundRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
    }

    public static Specification<GrouponRefund> getWhereClause(GrouponRefundCriteria criteria) {
        return new Specification<GrouponRefund>() {
            @Override
            public Predicate toPredicate(Root<GrouponRefund> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                //  订单编号
                if(criteria.getOrderSid()!=null&&!"".equals(criteria.getOrderSid())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("grouponOrder").get("orderSid"), "%" + criteria.getOrderSid() + "%"));
                }
                // 团购SID
                if (criteria.getProductSid() != null&&!"".equals(criteria.getProductSid())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("grouponOrder").get("grouponProduct").get("sid"), "%" + criteria.getProductSid() + "%"));
                }
                // 团购名称
                if (criteria.getProductName() != null&&!"".equals(criteria.getProductSid())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("grouponOrder").get("grouponProduct").get("name"), "%" + criteria.getProductName() + "%"));
                }
                //  订单类型  0 普通订单 1 乐加订单
                if(criteria.getOrderType()!=null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("grouponOrder").get("orderType"), criteria.getOrderType()));
                }
                // 订单状态  0 退款中 1 退款完成 2 退款驳回
                if (criteria.getState()!= null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("state"), criteria.getState()));
                }
                return predicate;
            }
        };
    }
}
