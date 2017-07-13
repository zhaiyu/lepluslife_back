package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.repository.LedgerQualificationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerQualificationService {

  @Inject
  private LedgerQualificationRepository ledgerQualificationRepository;

}
