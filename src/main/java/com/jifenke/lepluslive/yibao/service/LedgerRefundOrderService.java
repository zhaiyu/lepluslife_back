package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.repository.LedgerRefundOrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerRefundOrderService {

  @Inject
  private LedgerRefundOrderRepository ledgerRefundOrderRepository;

}