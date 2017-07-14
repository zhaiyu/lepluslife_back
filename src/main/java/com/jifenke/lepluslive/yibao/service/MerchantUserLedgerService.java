package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.repository.MerchantUserLedgerRepository;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import javax.inject.Inject;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class MerchantUserLedgerService {

  @Inject
  private MerchantUserLedgerRepository repository;

  public MerchantUserLedger findById(Long id) {
    return repository.findOne(id);
  }

  /**
   * 新建或编辑易宝子商户  2017/7/14
   *
   * @param ledger 子商户
   */
  public Map<String, Object> edit(MerchantUserLedger ledger) {
    Map<String, Object> resultMap = null;

    if (ledger.getId() == 0) {
      //新建，调用易宝子账户注册接口
      resultMap = YbRequestUtils.register(ledger);
      if (!"1".equals(resultMap.get("code"))) {
        return resultMap;
      }
      //注册成功
      ledger.setId(null);
      ledger.setLedgerNo("" + resultMap.get("ledgerno"));
      repository.save(ledger);
    } else {
      //编辑，调用易宝子账户编辑接口

      MerchantUserLedger dbLedger = repository.findOne(ledger.getId());


    }

    return null;
  }

}
