package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantType;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantTypeRepository;
import com.jifenke.lepluslive.user.domain.entities.RegisterOrigin;
import com.jifenke.lepluslive.user.repository.RegisterOriginRepository;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;

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
  public Page findMerchantsByPage(Pageable pageable) {
    return merchantRepository.findAll(pageable);
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
  public void deleteMerchant(Long id) {
    if (merchantRepository.findOne(id) == null) {
      throw new RuntimeException("不存在的商户");
    }
    merchantRepository.delete(id);
  }

  public List<MerchantType> findAllMerchantTypes() {
    return merchantTypeRepository.findAll();
  }
}
