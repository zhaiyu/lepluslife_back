package com.jifenke.lepluslive.yibao.service;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.yibao.domain.criteria.MerchantUserLedgerCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerModify;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerQualification;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.repository.MerchantUserLedgerRepository;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.Predicate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class MerchantUserLedgerService {

  @Inject
  private MerchantUserLedgerRepository repository;

  @Inject
  private LedgerModifyService ledgerModifyService;

  @Inject
  private LedgerQualificationService ledgerQualificationService;

  public MerchantUserLedger findById(Long id) {
    return repository.findOne(id);
  }

  public List<MerchantUserLedger> findAllByMerchantUser(MerchantUser merchantUser) {
    return repository.findAllByMerchantUser(merchantUser);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void saveLedger(MerchantUserLedger ledger) {
    repository.save(ledger);
  }

  /**
   * 分页条件查询 2017/7/17
   *
   * @param criteria 查询条件
   */
  public Page findAllByCriteria(MerchantUserLedgerCriteria criteria) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return repository
        .findAll(getWhereClause(criteria),
                 new PageRequest(criteria.getCurrPage() - 1, criteria.getPageSize(), sort));
  }

  private Specification<MerchantUserLedger> getWhereClause(MerchantUserLedgerCriteria criteria) {
    return (r, q, cb) -> {
      Predicate p = cb.conjunction();

      if (criteria.getState() != null) {
        p.getExpressions().add(cb.equal(r.get("state"), criteria.getState()));
      }
      if (criteria.getCostSide() != null) {
        p.getExpressions().add(cb.equal(r.get("costSide"), criteria.getCostSide()));
      }
      if (criteria.getLedgerNo() != null && (!"".equals(criteria.getLedgerNo()))) {
        p.getExpressions().add(cb.like(r.get("ledgerNo"), "%" + criteria.getLedgerNo() + "%"));
      }
      if (criteria.getMerchantUserId() != null) {
        p.getExpressions()
            .add(cb.equal(r.get("merchantUser").get("id"), criteria.getMerchantUserId()));
      }
      if (criteria.getMerchantName() != null && (!"".equals(criteria.getMerchantName()))) {
        p.getExpressions()
            .add(cb.like(r.get("merchantUser").get("merchantName"),
                         "%" + criteria.getMerchantName() + "%"));

      }

      return p;
    };
  }

  /**
   * 易宝子商户修改结算费用承担方  2017/7/17
   *
   * @param ledgerId 易宝子商户ID
   * @param costSide 结算费用承担方  0=积分客（主商户）|1=子商户
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void editCostSide(Long ledgerId, Integer costSide) {
    MerchantUserLedger ledger = repository.findOne(ledgerId);
    if (costSide == 1) {
      ledger.setCostSide(1);
    } else {
      ledger.setCostSide(0);
    }
    repository.save(ledger);
  }

  /**
   * 分账方审核结果主动查询  2017/7/17
   *
   * @param ledgerId 易宝子商户ID
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Map<String, String> queryCheckRecord(Long ledgerId) {
    MerchantUserLedger ledger = repository.findOne(ledgerId);
    Map<String, String> resultMap = YbRequestUtils.queryCheckRecord(ledger.getLedgerNo());
    if (!"1".equals(resultMap.get("code"))) {
      return resultMap;
    }
    if ("SUCCESS".equals(resultMap.get("status"))) {
      ledger.setState(1);
    } else {
      ledger.setState(2);
    }
    repository.save(ledger);
    return resultMap;
  }

  /**
   * 易宝子商户资质审核异步通知地址  2017/7/16
   *
   * @param map 异步通知
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void checkCallBack(Map<String, String> map) {
    MerchantUserLedger ledger = repository.findByLedgerNo(map.get("ledgerno"));
    Map<String, String> resultMap = YbRequestUtils.queryCheckRecord(ledger.getLedgerNo());
    if ("SUCCESS".equals(resultMap.get("status"))) {
      ledger.setState(1);
    } else {
      ledger.setState(2);
    }
    repository.save(ledger);
  }

  /**
   * 新建或编辑易宝子商户  2017/7/14
   *
   * @param ledger 子商户
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Map<String, String> edit(MerchantUserLedger ledger) {
    Map<String, String> resultMap = null;
    try {
      if (ledger.getId() == 0) {
        //新建，调用易宝子账户注册接口
        resultMap = YbRequestUtils.register(ledger);
        if (!"1".equals(resultMap.get("code"))) {
          return resultMap;
        }
        //注册成功
        ledger.setId(null);
        ledger.setLedgerNo(resultMap.get("ledgerno"));
        MerchantUserLedger saveLedger = repository.save(ledger);
        //新建资质记录
        LedgerQualification qualification = new LedgerQualification();
        qualification.setMerchantUserLedger(saveLedger);
        ledgerQualificationService.saveQualification(qualification);
      } else {
        MerchantUserLedger dbLedger = repository.findOne(ledger.getId());
        //查询是否有该子商户审核中的修改记录
        List<LedgerModify>
            modifies =
            ledgerModifyService.findAllByMerchantUserLedgerAndState(dbLedger, 0);
        if (modifies != null && modifies.size() > 0) {
          resultMap = new HashMap<>();
          resultMap.put("code", "303");
          resultMap.put("msg", "该子商户有审核中的修改记录，无法再次提交");
          return resultMap;
        }
        //判断是否有易宝数据修改
        if (isModify(dbLedger, ledger)) {
          //编辑，调用易宝子账户编辑接口
          resultMap = YbRequestUtils.modify(dbLedger, ledger);
          if (!"1".equals(resultMap.get("code"))) {
            return resultMap;
          }
          //修改通讯成功，插入修改记录，修改商户信息
          ledgerModifyService.saveModify(dbLedger, ledger, resultMap);
          dbLedger.setCheckState(0);
          dbLedger.setBindMobile(ledger.getBindMobile());
          dbLedger.setBankAccountNumber(ledger.getBankAccountNumber());
          dbLedger.setBankName(ledger.getBankName());
          dbLedger.setBankProvince(ledger.getBankProvince());
          dbLedger.setBankCity(ledger.getBankCity());
          dbLedger.setMinSettleAmount(ledger.getMinSettleAmount());
          repository.save(dbLedger);
        } else {
          resultMap = new HashMap<>();
          resultMap.put("code", "302");
          resultMap.put("msg", "商户信息无修改");
          return resultMap;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return resultMap;
  }

  /**
   * 判断是否有数据修改  true=是  2017/7/16
   *
   * @param dbLedger 数据库信息
   * @param ledger   页面提交信息
   */
  private boolean isModify(MerchantUserLedger dbLedger, MerchantUserLedger ledger) {
    //如果有任意数据不相等，则为true,表示可提交修改
    return (!dbLedger.getBindMobile().equals(ledger.getBindMobile())) ||
           (!dbLedger.getBankAccountNumber().equals(ledger.getBankAccountNumber())) ||
           (!dbLedger.getBankName().equals(ledger.getBankName())) ||
           (!dbLedger.getBankProvince().equals(ledger.getBankProvince())) ||
           (!dbLedger.getBankCity().equals(ledger.getBankCity())) ||
           (dbLedger.getMinSettleAmount().intValue() != ledger.getMinSettleAmount().intValue());
  }

}
