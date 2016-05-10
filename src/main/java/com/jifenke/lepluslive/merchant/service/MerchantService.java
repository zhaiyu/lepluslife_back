package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantType;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantTypeRepository;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.user.domain.entities.RegisterOrigin;
import com.jifenke.lepluslive.user.repository.RegisterOriginRepository;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/3/17.
 */
@Service
@Transactional(readOnly = true)
public class MerchantService {

  @Inject
  private MerchantRepository merchantRepository;

  @Inject
  private MerchantTypeRepository merchantTypeRepository;

  @Inject
  private RegisterOriginRepository registerOriginRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findMerchantsByPage(MerchantCriteria  merchantCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "sid");
    return merchantRepository
        .findAll(getWhereClause(merchantCriteria),
                 new PageRequest(merchantCriteria.getOffset() - 1, limit, sort));
  }

  public Merchant findMerchantById(Long id) {
    return merchantRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void createMerchant(Merchant merchant) {
    if (merchant.getId() != null) {
      throw new RuntimeException("新建商户ID不能存在");
    }
    merchant.setSid((int) merchantRepository.count());
    merchantRepository.save(merchant);
    RegisterOrigin registerOrigin = new RegisterOrigin();
    registerOrigin.setOriginType(3);
    registerOrigin.setMerchant(merchant);
    registerOriginRepository.save(registerOrigin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editMerchant(Merchant merchant) {
    Merchant origin = merchantRepository.findOne(merchant.getId());
    if (origin == null) {
      throw new RuntimeException("不存在的商户");
    }
    try {
      BeanUtils.copyProperties(origin, merchant);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    long l = merchant.getId();
    origin.setSid((int) l);
    merchantRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void disableMerchant(Long id) {
   Merchant merchant =  merchantRepository.findOne(id);
    if (merchant == null) {
      throw new RuntimeException("不存在的商户");
    }
    merchant.setState(0);
    merchantRepository.save(merchant);
  }

  public List<MerchantType> findAllMerchantTypes() {
    return merchantTypeRepository.findAll();
  }

  public static Specification<Merchant> getWhereClause(MerchantCriteria merchantCriteria) {
    return new Specification<Merchant>() {
      @Override
      public Predicate toPredicate(Root<Merchant> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (merchantCriteria.getPartnership() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("partnership"),
                       merchantCriteria.getPartnership()));
        }

        if (merchantCriteria.getMerchantType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("merchantType"),
                       new MerchantType(merchantCriteria.getMerchantType())));
        }

        if (merchantCriteria.getMerchant() != null && merchantCriteria.getMerchant() != "") {
          if (merchantCriteria.getMerchant().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                        "%" + merchantCriteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("name"),
                        "%" + merchantCriteria.getMerchant() + "%"));
          }
        }
        return predicate;
      }
    };
  }
}
