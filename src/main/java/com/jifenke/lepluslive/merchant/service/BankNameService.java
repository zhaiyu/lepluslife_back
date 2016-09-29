package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.BankName;
import com.jifenke.lepluslive.merchant.repository.BankNameRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by xf on 2016/9/28.
 */
@Service
@Transactional
public class BankNameService {

  @Inject
  private BankNameRepository bankNameRepository;

  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public BankName findOne(Long id) {
    return bankNameRepository.findOne(id);
  }
}
