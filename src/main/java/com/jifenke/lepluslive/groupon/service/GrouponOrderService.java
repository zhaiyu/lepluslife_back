package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.criteria.GrouponOrderCriteria;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponProductCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.repository.GrouponOrderRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
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
 * 团购订单  Service
 *
 * @author XF
 * @date 2017/6/19
 */
@Service
@Transactional(readOnly = true)
public class GrouponOrderService {

    @Inject
    private GrouponOrderRepository grouponOrderRepository;

    /***
     *  根据条件查询团购订单
     *  Created by xf on 2017-06-19.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<GrouponOrder> findByCriteria(GrouponOrderCriteria criteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        return grouponOrderRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
    }

    public static Specification<GrouponOrder> getWhereClause(GrouponOrderCriteria criteria) {
        return new Specification<GrouponOrder>() {
            @Override
            public Predicate toPredicate(Root<GrouponOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 团购SID
                if (criteria.getProductSid() != null) {
                    predicate.getExpressions().add(
                             cb.like(root.get("grouponProduct").get("sid"), "%" + criteria.getProductSid() + "%"));
                }
                // 团购名称
                if (criteria.getName() != null) {
                    predicate.getExpressions().add(
                            cb.like(root.get("grouponProduct").get("name"), "%" + criteria.getName() + "%"));
                }
                // 订单状态  0 未付款 1 已完成
                if (criteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("state"), criteria.getState()));
                }
                //  订单编号
                if(criteria.getSid()!=null) {
                    predicate.getExpressions().add(
                            cb.like(root.get("sid"), "%" + criteria.getSid() + "%"));
                }
                //  订单类型  0 普通订单 1 乐加订单
                if(criteria.getType()!=null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("orderType"), criteria.getType()));
                }
                return predicate;
            }
        };
    }
}
