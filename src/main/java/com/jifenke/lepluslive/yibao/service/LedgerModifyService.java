package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.domain.entities.LedgerModify;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.repository.LedgerModifyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerModifyService {

  @Inject
  private LedgerModifyRepository repository;

  @Inject
  private MerchantUserLedgerService merchantUserLedgerService;

  /**
   * 保存修改记录  2017/7/16
   *
   * @param dbLedger  修改前信息
   * @param ledger    修改后信息
   * @param resultMap 修改请求接口返回
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void saveModify(MerchantUserLedger dbLedger, MerchantUserLedger ledger,
                         Map<String, String> resultMap) {
    LedgerModify modify = new LedgerModify();
    modify.setMerchantUserLedger(dbLedger);
    try {
      modify.setRequestId(resultMap.get("requestid"));
      //dataAfter&dataBefore字段顺序：绑定手机号,银行卡号,开户行,开户省,开户市,起结金额
      StringBuffer data = new StringBuffer();
      //before
      data.append(dbLedger.getBindMobile()).append(",")
          .append(dbLedger.getBankAccountNumber()).append(",")
          .append(dbLedger.getBankName()).append(",")
          .append(dbLedger.getBankProvince()).append(",")
          .append(dbLedger.getBankCity()).append(",")
          .append(dbLedger.getMinSettleAmount());
      modify.setDataBefore(data.toString());
      //after
      data.setLength(0);
      data.append(ledger.getBindMobile()).append(",")
          .append(ledger.getBankAccountNumber()).append(",")
          .append(ledger.getBankName()).append(",")
          .append(ledger.getBankProvince()).append(",")
          .append(ledger.getBankCity()).append(",")
          .append(ledger.getMinSettleAmount());
      modify.setDataAfter(data.toString());

      repository.save(modify);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 修改商户信息异步通知  2017/7/16
   *
   * @param map 异步通知
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void modifyCallBack(Map<String, String> map) {
    LedgerModify modify = findByRequestId(map.get("requestid"));
    if (modify != null) {
      modify.setDateCompleted(new Date());
      MerchantUserLedger ledger = modify.getMerchantUserLedger();
      if ("SUCCESS".equals(map.get("status"))) {
        modify.setState(1);
        ledger.setCheckState(1);
      } else {
        modify.setState(2);
        ledger.setCheckState(2);
        //失败需要回滚修改前数据 绑定手机号,银行卡号,开户行,开户省,开户市,起结金额
        String[] datas = modify.getDataBefore().split(",");
        ledger.setBindMobile(datas[0]);
        ledger.setBankAccountNumber(datas[1]);
        ledger.setBankName(datas[2]);
        ledger.setBankProvince(datas[3]);
        ledger.setBankCity(datas[4]);
        ledger.setMinSettleAmount(Integer.valueOf(datas[5]));
        ledger.setDateUpdated(new Date());
      }
      repository.save(modify);
      merchantUserLedgerService.saveLedger(ledger);
    }
  }

  public LedgerModify findByRequestId(String requestId) {
    return repository.findByRequestId(requestId);
  }

  /**
   * 查询某子商户某个状态的修改记录列表 2017/7/16
   *
   * @param merchantUserLedger 子商户
   * @param state              状态
   */
  public List<LedgerModify> findAllByMerchantUserLedgerAndState(
      MerchantUserLedger merchantUserLedger,
      Integer state) {
    return repository.findAllByMerchantUserLedgerAndState(merchantUserLedger, state);
  }

}
