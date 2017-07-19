package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.domain.criteria.LedgerModifyCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerModify;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.repository.LedgerModifyRepository;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.Predicate;


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
   * 分页条件查询 2017/7/17
   *
   * @param criteria 查询条件
   */
  public Page findAllByCriteria(LedgerModifyCriteria criteria) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return repository
        .findAll(getWhereClause(criteria),
                 new PageRequest(criteria.getCurrPage() - 1, criteria.getPageSize(), sort));
  }

  private Specification<LedgerModify> getWhereClause(LedgerModifyCriteria criteria) {
    return (r, q, cb) -> {
      Predicate p = cb.conjunction();

      if (criteria.getState() != null) {
        p.getExpressions().add(cb.equal(r.get("state"), criteria.getState()));
      }
      if (criteria.getLedgerNo() != null && (!"".equals(criteria.getLedgerNo()))) {
        p.getExpressions().add(cb.like(r.get("merchantUserLedger").get("ledgerNo"),
                                       "%" + criteria.getLedgerNo() + "%"));
      }
      if (criteria.getMerchantUserId() != null) {
        p.getExpressions()
            .add(cb.equal(r.get("merchantUserLedger").get("merchantUser").get("id"),
                          criteria.getMerchantUserId()));
      }
      if (criteria.getMerchantName() != null && (!"".equals(criteria.getMerchantName()))) {
        p.getExpressions()
            .add(cb.like(r.get("merchantUserLedger").get("merchantUser").get("merchantName"),
                         "%" + criteria.getMerchantName() + "%"));

      }
      return p;
    };
  }

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

  /**
   * 查询修改商户记录结果  2017/7/19
   *
   * @param requestId 查询唯一请求号
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Map<String, String> queryModify(String requestId) throws Exception {
    LedgerModify modify = findByRequestId(requestId);
    MerchantUserLedger ledger = modify.getMerchantUserLedger();
    Map<String, String> resultMap = YbRequestUtils.modifyQuery(requestId);
    if (!"1".equals(resultMap.get("code"))) {
      return resultMap;
    }
    String status = resultMap.get("status");
    modify.setDateCompleted(new Date());
    resultMap.put("diff", "NO");
    try {
      if ("SUCCESS".equals(status)) {
        if (modify.getState() != 1) {
          resultMap.put("diff", "YES");
        }
        modify.setState(1);
        ledger.setCheckState(1);
      } else if ("INIT".equals(status)) {
        if (modify.getState() != 0) {
          resultMap.put("diff", "YES");
        }
        modify.setState(0);
        ledger.setCheckState(0);
      } else {
        if (modify.getState() != 2) {
          resultMap.put("diff", "YES");
        }
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
      return resultMap;
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("save error");
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
