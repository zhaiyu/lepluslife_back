package com.jifenke.lepluslive.coupon.service;

import com.jifenke.lepluslive.coupon.domain.criteria.CashCouponLogCriteria;
import com.jifenke.lepluslive.coupon.domain.entities.CashCouponLog;
import com.jifenke.lepluslive.coupon.repository.CashCouponLogRepository;
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
 * CashCouponLogService
 *   领券记录
 * @author XF
 * @date 2017/7/5
 */
@Service
@Transactional(readOnly = true)
public class CashCouponLogService {
    @Inject
    private CashCouponLogRepository cashCouponLogRepository;

    /***
     *  根据条件查询领券记录
     *  Created by xf on 2017-07-05.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<CashCouponLog> findByCriteria(CashCouponLogCriteria criteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return cashCouponLogRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
    }

    public static Specification<CashCouponLog> getWhereClause(CashCouponLogCriteria criteria) {
        return new Specification<CashCouponLog>() {
            @Override
            public Predicate toPredicate(Root<CashCouponLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 微信昵称
                if (criteria.getWxName() != null&&!"".equals(criteria.getWxName())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("leJiaUser").get("weiXinUser").get("nickname"), "%" + criteria.getWxName() + "%"));
                }
                // 消费者ID
                if (criteria.getUserId() != null&&!"".equals(criteria.getUserId())) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("leJiaUser").get("id"),criteria.getUserId()));
                }
                // 券码状态  0 未付款 1 已完成
                if (criteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("state"), criteria.getState()));
                }
                //  券码编号
                if(criteria.getCouponSid()!=null&&!"".equals(criteria.getCouponSid())) {
                    predicate.getExpressions().add(
                            cb.like(root.get("sid"), "%" + criteria.getCouponSid() + "%"));
                }
                return predicate;
            }
        };
    }
}
