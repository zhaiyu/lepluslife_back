package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.criteria.GrouponProductCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.repository.GrouponProductRepository;
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
 * 团购产品 Service
 * Created by xf on 17-6-16.
 */
@Service
@Transactional(readOnly = true)
public class GrouponProductService {
    @Inject
    private GrouponProductRepository grouponProductRepository;

    /***
     *  根据条件查询团购产品
     *  Created by xf on 2017-06-16.
     */
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public Page<GrouponProduct> findByCriteria(GrouponProductCriteria criteria,Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        return grouponProductRepository.findAll(getWhereClause(criteria),new PageRequest(criteria.getOffset()-1,limit,sort));
    }

    public static Specification<GrouponProduct> getWhereClause(GrouponProductCriteria criteria) {
        return new Specification<GrouponProduct>() {
            @Override
            public Predicate toPredicate(Root<GrouponProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if(criteria.getMerchantUser()!=null) {
                    // 商户
                    if (criteria.getMerchantUser().matches("^\\d{1,6}$")) {
                        predicate.getExpressions().add(
                                cb.equal(root.<Merchant>get("merchantUser").get("id"),criteria.getMerchantUser()));
                    } else {
                        predicate.getExpressions().add(
                                cb.like(root.<Merchant>get("merchantUser").get("name"),
                                        "%" + criteria.getMerchantUser() + "%"));
                    }
                    // 团购SID
                    if(criteria.getSid()!=null) {
                        predicate.getExpressions().add(
                                cb.equal(root.get("sid"),criteria.getSid()));
                    }
                    // 团购名称
                    if(criteria.getName()!=null) {
                        predicate.getExpressions().add(
                                cb.like(root.get("name"),"%"+criteria.getName()+"%"));
                    }
                    // 团购全部状态
                    if(criteria.getState()!=null) {
                        predicate.getExpressions().add(
                                cb.equal(root.get("state"),criteria.getState()));
                    }
                }
                return predicate;
            }
        };
    }
}
