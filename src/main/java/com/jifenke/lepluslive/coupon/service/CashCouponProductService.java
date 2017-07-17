package com.jifenke.lepluslive.coupon.service;

import com.jifenke.lepluslive.coupon.domain.criteria.CashCouponProductCriteria;
import com.jifenke.lepluslive.coupon.domain.entities.CashCouponProduct;
import com.jifenke.lepluslive.coupon.repository.CashCouponProductRepository;
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
 * CashCouponProductService
 *  满减券管理
 * @author XF
 * @date 2017/7/6
 */
@Service
@Transactional(readOnly = true)
public class CashCouponProductService {
    @Inject
    private CashCouponProductRepository cashCouponProductRepository;

    /***
     *  根据条件查询满减券
     *  Created by xf on 2017-07-06.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<CashCouponProduct> findByCriteria(CashCouponProductCriteria criteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return cashCouponProductRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
    }

    public static Specification<CashCouponProduct> getWhereClause(CashCouponProductCriteria criteria) {
        return new Specification<CashCouponProduct>() {
            @Override
            public Predicate toPredicate(Root<CashCouponProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 所属商户
                if (criteria.getMerchantName() != null&&!"".equals(criteria.getMerchantName())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("merchantUser").get("name"), "%" + criteria.getMerchantName() + "%"));
                }
                // 面额
                if (criteria.getPrice() != null&&!"".equals(criteria.getPrice())) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("price"),criteria.getPrice()));
                }
                // 券码状态 0下架 1 上架
                if (criteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("state"), criteria.getState()));
                }
                //  券码编号
                if(criteria.getProductSid()!=null&&!"".equals(criteria.getProductSid())) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("sid"), "%" + criteria.getProductSid() + "%"));
                }
                return predicate;
            }
        };
    }
}
