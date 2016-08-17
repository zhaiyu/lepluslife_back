package com.jifenke.lepluslive.sales.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;
import com.jifenke.lepluslive.sales.repository.SalesStaffRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.List;

/**
 * Created by lss on 2016/8/10.
 */
@Service
@Transactional(readOnly = true)
public class SalesService {

  @Inject
  private SalesStaffRepository salesStaffRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveStaff(SalesStaff salesStaff) {

    salesStaffRepository.save(salesStaff);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<SalesStaff> findAllSaleStaff() {
    return salesStaffRepository.findAll();
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public SalesStaff findSaleStaffById(Long id) {
    return salesStaffRepository.findOne(id);
  }


}
