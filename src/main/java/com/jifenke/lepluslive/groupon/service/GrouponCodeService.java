package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.criteria.GrouponCodeCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponCode;
import com.jifenke.lepluslive.groupon.repository.GrouponCodeRepository;

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
 * 券码管理 Service
 * @author XF
 * @date 2017/6/19
 */
@Service
@Transactional(readOnly = true)
public class GrouponCodeService {
    @Inject
    private GrouponCodeRepository grouponCodeRepository;

    /***
     *  根据条件查询团购券码
     *  Created by xf on 2017-06-19.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<GrouponCode> findByCriteria(GrouponCodeCriteria criteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return grouponCodeRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
    }

    public static Specification<GrouponCode> getWhereClause(GrouponCodeCriteria criteria) {
        return new Specification<GrouponCode>() {
            @Override
            public Predicate toPredicate(Root<GrouponCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 团购SID
                if (criteria.getProductSid() != null&&!"".equals(criteria.getProductSid())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("grouponProduct").get("sid"), "%" + criteria.getProductSid() + "%"));
                }
                // 团购名称
                if (criteria.getProductName() != null&&!"".equals(criteria.getProductName())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("grouponProduct").get("name"), "%" + criteria.getProductName() + "%"));
                }
                // 券码状态  0 未付款 1 已完成
                if (criteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("state"), criteria.getState()));
                }
                //  订单编号
                if(criteria.getOrderSid()!=null&&!"".equals(criteria.getOrderSid())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("grouponOrder").get("sid"), "%" + criteria.getOrderSid() + "%"));
                }
                //  订单类型  0 普通订单 1 乐加订单
                if(criteria.getOrderType()!=null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("grouponOrder").get("orderType"), criteria.getOrderType()));
                }
                return predicate;
            }
        };
    }
}
