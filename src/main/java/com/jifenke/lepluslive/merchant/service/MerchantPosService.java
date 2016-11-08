package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.controller.dto.MerchantPosDto;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantPosCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPos;
import com.jifenke.lepluslive.merchant.repository.MerchantPosRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/9/6.
 */
@Service
@Transactional(readOnly = true)
public class MerchantPosService {

  @Inject
  private MerchantPosRepository merchantPosRepository;

  public Page findMerchantPosByCriteria(MerchantPosCriteria merchantPosCriteria, int limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return merchantPosRepository
        .findAll(getWhereClause(merchantPosCriteria),
                 new PageRequest(merchantPosCriteria.getOffset() - 1, limit, sort));
  }

  public static Specification<MerchantPos> getWhereClause(MerchantPosCriteria merchantPosCriteria) {
    return new Specification<MerchantPos>() {
      @Override
      public Predicate toPredicate(Root<MerchantPos> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (merchantPosCriteria.getStartDate() != null
            && merchantPosCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("createdDate"), new Date(merchantPosCriteria.getStartDate()),
                         new Date(merchantPosCriteria.getEndDate())));
        }

        if (merchantPosCriteria.getMerchantLocation() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("merchant").<City>get("city"),
                       new City(merchantPosCriteria.getMerchantLocation())));
        }
        if (merchantPosCriteria.getMerchant() != null && merchantPosCriteria.getMerchant() != "") {
          if (merchantPosCriteria.getMerchant().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                        "%" + merchantPosCriteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("name"),
                        "%" + merchantPosCriteria.getMerchant() + "%"));
          }
        }
        if (merchantPosCriteria.getState() != null) {
          if (merchantPosCriteria.getState() == 1) {
            predicate.getExpressions().add(
                cb.isNotNull(r.get("ljCommission")));
          } else {
            predicate.getExpressions().add(
                cb.isNull(r.get("ljCommission")));
          }
        }
        return predicate;
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public MerchantPosDto countPosOrderFlow(MerchantPosDto merchantPosDto) {
    List<Object[]> results = merchantPosRepository.countPosOrderFlow(merchantPosDto.getId());
    merchantPosDto.setNormalOrderFlow(((BigDecimal) results.get(0)[0]).longValue());
    merchantPosDto.setImportOrderFlow(((BigDecimal) results.get(0)[1]).longValue());
    return merchantPosDto;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changePosCommission(String id, BigDecimal commission) {
    MerchantPos merchantPos = merchantPosRepository.findByPosId(id);
    merchantPos.setLjCommission(commission);
    merchantPosRepository.save(merchantPos);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void savePosMachine(MerchantPos merchantPos) {
    if(merchantPos.getId()==null) {
      merchantPos.setCreatedDate(new Date());
      merchantPosRepository.save(merchantPos);
    }else {
      MerchantPos existMerchantPos = merchantPosRepository.findById(merchantPos.getId());
      existMerchantPos.setPosId(merchantPos.getPosId());
      existMerchantPos.setCeil(merchantPos.getCeil());
      existMerchantPos.setAliCommission(merchantPos.getAliCommission());
      existMerchantPos.setAliUserCommission(merchantPos.getAliUserCommission());
      existMerchantPos.setWxCommission(merchantPos.getWxCommission());
      existMerchantPos.setWxUserCommission(merchantPos.getWxUserCommission());
      existMerchantPos.setCreditCardCommission(merchantPos.getCreditCardCommission());
      existMerchantPos.setDebitCardCommission(merchantPos.getDebitCardCommission());
     /* existMerchantPos.setScoreARebate(merchantPos.getScoreARebate());
      existMerchantPos.setScoreBRebate(merchantPos.getScoreBRebate());*/
      existMerchantPos.setLjCommission(merchantPos.getLjCommission());
     /*existMerchantPos.setUserScoreARebate(merchantPos.getUserScoreARebate());
      existMerchantPos.setUserScoreBRebate(merchantPos.getUserScoreBRebate());*/
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveLjCommision(MerchantPos merchantPos) {
    MerchantPos existMerchantPos = merchantPosRepository.findById(merchantPos.getId());
    existMerchantPos.setLjCommission(merchantPos.getLjCommission());
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public MerchantPos findPosById(Long id) {
    return merchantPosRepository.findById(id);
  }

  public MerchantPos findPosByPosId(String posId) {
    return merchantPosRepository.findByPosId(posId);
  }


  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public List<Long> countPosByMerchants(List<Merchant> merchants) {
    if(merchants!=null && merchants.size()>0) {
      List<Long> posCounts = new ArrayList<>();
      merchants.forEach(merchant -> posCounts.add(merchantPosRepository.countByMerchant(merchant.getId())));
      return posCounts;
    }
    return null;
  }
}
