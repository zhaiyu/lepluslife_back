package com.jifenke.lepluslive.datacenter.service;

import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zxf on 2016/9/7.
 */
@Service
@Transactional(readOnly = true)
public class MemberDataService {

  @Inject
  private OffLineOrderRepository offLineOrderRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Integer> doBarChart() {
    List<Object[]> list = offLineOrderRepository.countUserByOffLineOrder();
    List data = new ArrayList();
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      for (int i = 0; i < obj.length; i++) {
        data.add(obj[i]);
      }
    }
    return data;
  }



  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public  List<Object[]> findOfflineWxTruePayAndDate() {
    return  offLineOrderRepository.findOfflineWxTruePayAndDate();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public  List<Object[]> findOnlineWxTruePayAndDate() {
    return  offLineOrderRepository.findOnlineWxTruePayAndDate();
  }



  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public  List<Object[]> findOfflineWxCommissionAndDate() {
    return  offLineOrderRepository.findOfflineWxCommissionAndDate();
  }




  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public  List<Object[]> findOnlineWxCommissionAndDate() {
    return  offLineOrderRepository.findOnlineWxCommissionAndDate();
  }

}
