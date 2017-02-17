package com.jifenke.lepluslive.activity.service;

import com.jifenke.lepluslive.activity.domain.criteria.RechargeCardCriteria;
import com.jifenke.lepluslive.activity.domain.entities.RechargeCard;
import com.jifenke.lepluslive.activity.repository.RechargeCardRepository;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantBank;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.product.domain.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by xf on 2017-1-4.
 */
@Service
@Transactional(readOnly = true)
public class RechargeCardService {

    @Inject
    private RechargeCardRepository rechargeCardRepository;

    /**
     * 条件查询 充值卡记录
     * @param rechargeCardCriteria
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page findByCriteria(RechargeCardCriteria rechargeCardCriteria) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(rechargeCardCriteria.getOffset() - 1, rechargeCardCriteria.getPageSize(), sort);
        return rechargeCardRepository.findAll(getWhereClause(rechargeCardCriteria), pageRequest);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public static Specification<RechargeCard> getWhereClause(RechargeCardCriteria rechargeCardCriteria) {
        return new Specification<RechargeCard>() {
            @Override
            public Predicate toPredicate(Root<RechargeCard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (rechargeCardCriteria.getStartDate() != null && rechargeCardCriteria.getEndDate() != null) {//兑换时间
                  predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime"), new Date(rechargeCardCriteria.getStartDate())));
                  predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime"), new Date(rechargeCardCriteria.getEndDate())));
                }
                if (rechargeCardCriteria.getExchangeCode() != null && !rechargeCardCriteria.getExchangeCode().equals("")) {//兑换码
                    predicate.getExpressions().add(cb.equal(root.get("exchangeCode"), rechargeCardCriteria.getExchangeCode()));
                }
                if (rechargeCardCriteria.getLjUserSid() != null && !rechargeCardCriteria.getLjUserSid().equals("")) {//会员sid
                  predicate.getExpressions().add(cb.equal(root.get("weiXinUser").get("leJiaUser").get(
                      "userSid"), rechargeCardCriteria.getLjUserSid()));
                }
                if (rechargeCardCriteria.getUserPhone() != null && !rechargeCardCriteria.getUserPhone().equals("")) {//会员手机号
                  predicate.getExpressions().add(cb.equal(root.get("weiXinUser").get("leJiaUser").get(
                      "phoneNumber"), rechargeCardCriteria.getUserPhone()));
                }
                if (rechargeCardCriteria.getRechargeStatus() != null && !rechargeCardCriteria.getRechargeStatus().equals("")) {//充值状态
                  predicate.getExpressions().add(cb.equal(root.get("rechargeStatus"), rechargeCardCriteria.getRechargeStatus()));
                }
                return predicate;
            }
        };
    }

    /**
     * 修改充值状态
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public LejiaResult editStatus(Long id, Integer rechargeStatus) {
      RechargeCard rechargeCard = rechargeCardRepository.findOne(id);
      if (rechargeCard == null) {
        return LejiaResult.build(500, "充值记录不存在!");
      }
      rechargeCard.setRechargeStatus(rechargeStatus);
      rechargeCard.setCompleteTime(new Date());
      rechargeCardRepository.save(rechargeCard);
      return LejiaResult.build(200, "操作成功!");
    }

}
