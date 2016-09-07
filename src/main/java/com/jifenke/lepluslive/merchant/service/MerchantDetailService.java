package com.jifenke.lepluslive.merchant.service;


import com.jifenke.lepluslive.merchant.domain.entities.MerchantDetail;
import com.jifenke.lepluslive.merchant.repository.MerchantDetailRepository;

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
 * 商家详情图 Created by zhangwen on 16/9/3.
 */
@Service
@Transactional(readOnly = true)
public class MerchantDetailService {

  @Inject
  private MerchantDetailRepository detailRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findDetailPicByPage(Integer offset, Long merchantId) {
    Sort sort = new Sort(Sort.Direction.ASC, "sid");
    return detailRepository
        .findAll(getWhereClause(merchantId), new PageRequest(offset - 1, 10, sort));
  }

  private static Specification<MerchantDetail> getWhereClause(Long merchantId) {
    return new Specification<MerchantDetail>() {
      @Override
      public Predicate toPredicate(Root<MerchantDetail> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        predicate.getExpressions().add(
            cb.equal(r.get("merchant").get("id"),
                     merchantId));
        return predicate;
      }
    };
  }


  public MerchantDetail findDetailPictureById(Long id) {
    return detailRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editDetailPicture(MerchantDetail merchantDetail) {
    MerchantDetail origin = null;
    if (merchantDetail.getId() != null) {
      origin = detailRepository.findOne(merchantDetail.getId());
    } else {
      origin = new MerchantDetail();
    }
    origin.setPicture(merchantDetail.getPicture());
    origin.setSid(merchantDetail.getSid());
    origin.setMerchant(merchantDetail.getMerchant());
    detailRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteDetailPicture(Long id) {
    detailRepository.delete(id);
  }

}
