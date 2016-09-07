package com.jifenke.lepluslive.withdrawBill.service;




import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.repository.MerchantWalletRepository;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.PayWay;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.withdrawBill.domain.criteria.WithdrawBillCriteria;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WithdrawBill;
import com.jifenke.lepluslive.withdrawBill.repository.WithdrawBillRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Created by Administrator on 2016/8/26.
 */
@Service
@Transactional(readOnly = true)
public class WithdrawBillService {

  @Inject
  private WithdrawBillRepository withdrawBillRepository;



  public Page findWithdrawBillByPage(WithdrawBillCriteria withdrawBillCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return withdrawBillRepository
        .findAll(getWhereClause(withdrawBillCriteria),
                 new PageRequest(withdrawBillCriteria.getOffset() - 1, limit, sort));
  }

  public static Specification<WithdrawBill> getWhereClause(WithdrawBillCriteria withdrawBillCriteria) {
    return new Specification<WithdrawBill>() {
      @Override
      public Predicate toPredicate(Root<WithdrawBill> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (withdrawBillCriteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       withdrawBillCriteria.getState()));
        }

        if (withdrawBillCriteria.getBillType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("billType"),
                       withdrawBillCriteria.getBillType()));
        }

        if (withdrawBillCriteria.getStartDate() != null && withdrawBillCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("createdDate"), new Date(withdrawBillCriteria.getStartDate()),
                         new Date(withdrawBillCriteria.getEndDate())));
        }

        if (withdrawBillCriteria.getMerchant() != null && withdrawBillCriteria.getMerchant() != "") {
          if (withdrawBillCriteria.getMerchant().matches("^\\d{1,7}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                        "%" + withdrawBillCriteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("name"),
                        "%" + withdrawBillCriteria.getMerchant() + "%"));
          }
        }

        if (withdrawBillCriteria.getPartner() != null && withdrawBillCriteria.getPartner() != "") {
          if (withdrawBillCriteria.getPartner().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Partner>get("partner").get("partnerSid"),
                        "%" + withdrawBillCriteria.getPartner() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Partner>get("partner").get("name"),
                        "%" + withdrawBillCriteria.getPartner() + "%"));
          }
        }



        return predicate;
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void reject(Long id,String rejectNote) {
    WithdrawBill withdrawBill =withdrawBillRepository.findOne(id);
    withdrawBill.setNote(rejectNote);
    withdrawBill.setState(2);
    withdrawBillRepository.saveAndFlush(withdrawBill);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public WithdrawBill findWithdrawBillById(Long id) {
    return withdrawBillRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveWithdrawBill(WithdrawBill withdrawBill) {
    withdrawBillRepository.saveAndFlush(withdrawBill);
  }
}
