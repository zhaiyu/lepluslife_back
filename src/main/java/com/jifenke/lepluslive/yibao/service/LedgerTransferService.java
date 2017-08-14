package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerTransferCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransferLog;
import com.jifenke.lepluslive.yibao.repository.LedgerTransferRepository;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerTransferService {

  @Inject
  private LedgerTransferRepository ledgerTransferRepository;

  @Inject
  private LedgerTransferLogService ledgerTransferLogService;

  @Transactional(propagation = Propagation.REQUIRED)
  public LedgerTransfer findById(Long id) {
    return ledgerTransferRepository.findOne(id);
  }

  /**
   * 转账  2017/7/19
   *
   * @param ledgerNo  易宝的子商户号
   * @param amount    转账金额（注意：此时单位为分，调用接口时需/100转换为元）
   * @param tradeDate 清算日期
   * @param type      转账类型 1=交易实时转账，2=定时合并转账
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void transfer(String ledgerNo, Long amount, String tradeDate, Integer type) {
    LedgerTransfer transfer = new LedgerTransfer();
    String orderSid = MvUtil.getOrderNumber(10);
    transfer.setActualTransfer(amount);
    transfer.setType(type);
    transfer.setLedgerNo(ledgerNo);
    transfer.setOrderSid(orderSid);
    transfer.setTradeDate(tradeDate);
    LedgerTransferLog log = new LedgerTransferLog();
    log.setAmount(amount);
    log.setLedgerNo(ledgerNo);
    log.setOrderSid(orderSid);
    log.setRequestId(orderSid);
    log.setType(type);
    int state = 0;
    Map<String, String>
        resultMap =
        YbRequestUtils.transfer(ledgerNo, amount, transfer.getOrderSid());
    //注意：错误码为 162005、988888、999999：需要通过补偿查询（5.6 转账查询接口）确认转账状
    //态。状态为成功时：转账已成功；若状态为非终止状态（终止转态：COMPLETE、FAIL）需等状态
    // 为终止状态时，再进行下一步操作
    String code = resultMap.get("code");
    if (!"1".equals(code)) {
      state = Integer.valueOf(code);
      log.setMsg(resultMap.get("msg"));
      //第一次转账异常，给对应人员发送短信或消息 todo: 待完成
      System.out.println("==============第一次转账异常===============");
      if ("162005".equals(code) || "988888".equals(code) || "999999".equals(code)) {
        //补偿查询
        Map<String, String> map = YbRequestUtils.queryTransfer(transfer.getOrderSid());
        if ("1".equals(map.get("code"))) {
          String status = map.get("status");
          if ("COMPLETE".equals(status)) {
            state = 1;
          } else if ("FAIL".equals(status)) {
            state = 2;
          } else { //非终态，全部转账完成后对该状态统一查询一次
            state = 3;
          }
        }
      } else {
        state = 2;
      }
    } else {
      state = 1;
    }
    transfer.setDateCompleted(new Date());
    transfer.setState(state);
    ledgerTransferRepository.save(transfer);
    log.setDateCompleted(new Date());
    log.setState(Integer.valueOf(code));
    ledgerTransferLogService.saveLog(log);
  }

  /**
   * 转账(手动补单)  2017/7/27
   *
   * @param transferId 转账单ID
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Map<String, String> transferRetry(Long transferId) throws Exception {
    LedgerTransfer transfer = ledgerTransferRepository.findOne(transferId);
    if (transfer.getState() == 2) {
      transfer.setRepair(1);
      String requestId = MvUtil.getOrderNumber(10);//转载请求号
      LedgerTransferLog log = new LedgerTransferLog();
      log.setAmount(transfer.getActualTransfer());
      log.setLedgerNo(transfer.getLedgerNo());
      log.setOrderSid(transfer.getOrderSid());
      log.setRequestId(requestId);
      log.setType(3);
      int state = 0;
      Map<String, String>
          resultMap =
          YbRequestUtils
              .transfer(transfer.getLedgerNo(), transfer.getActualTransfer(), requestId);
      //注意：错误码为 162005、988888、999999：需要通过补偿查询（5.6 转账查询接口）确认转账状
      //态。状态为成功时：转账已成功；若状态为非终止状态（终止转态：COMPLETE、FAIL）需等状态
      // 为终止状态时，再进行下一步操作
      try {
        String code = resultMap.get("code");
        if (!"1".equals(code)) {
          state = Integer.valueOf(code);
          log.setMsg(resultMap.get("msg"));
          if ("162005".equals(code) || "988888".equals(code) || "999999".equals(code)) {
            //补偿查询
            Map<String, String> map = YbRequestUtils.queryTransfer(transfer.getOrderSid());
            if ("1".equals(map.get("code"))) {
              String status = map.get("status");
              if ("COMPLETE".equals(status)) {
                state = 1;
              } else if ("FAIL".equals(status)) {
                state = 2;
              } else { //非终态，全部转账完成后对该状态统一查询一次
                state = 3;
              }
            }
          } else {
            state = 2;
          }
        } else {
          state = 1;
        }
        transfer.setDateCompleted(new Date());
        transfer.setState(state);
        ledgerTransferRepository.save(transfer);
        log.setDateCompleted(new Date());
        log.setState(Integer.valueOf(code));
        ledgerTransferLogService.saveLog(log);
      } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("请求异常");
      }
      return resultMap;
    }
    return null;
  }

  /***
   *  根据条件查询转账记录
   *  Created by xf on 2017-07-14.
   */
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Page<LedgerTransfer> findByCriteria(LedgerTransferCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return ledgerTransferRepository
        .findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  public static Specification<LedgerTransfer> getWhereClause(LedgerTransferCriteria criteria) {
    return new Specification<LedgerTransfer>() {
      @Override
      public Predicate toPredicate(Root<LedgerTransfer> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        // 转账状态 0=待转账，1=转账成功，2=转账失败，3=转账中（查询非终态）
        if (criteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(root.get("state"), criteria.getState()));
        }
        // 易宝商户号
        if (criteria.getLedgerNo() != null && !"".equals(criteria.getLedgerNo())) {
          predicate.getExpressions().add(
              cb.equal(root.get("ledgerNo"), criteria.getLedgerNo()));
        }
        //转账单号(非定时转账为关联的订单号，定时转账自己生成)
        if (criteria.getOrderSid() != null && !"".equals(criteria.getOrderSid())) {
          predicate.getExpressions().add(
              cb.equal(root.get("orderSid"), criteria.getOrderSid()));
        }
        return predicate;
      }
    };
  }
}
