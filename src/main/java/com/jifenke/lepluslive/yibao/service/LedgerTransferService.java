package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.domain.criteria.LedgerTransferCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
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

  /**
   * 转账(根据通道结算单转账并生成转账记录)  2017/7/19
   *
   * @param ledgerNo    易宝的子商户号
   * @param amount      转账金额（注意：此时单位为分，调用接口时需/100转换为元）
   * @param ledgerSid   通道结算单号
   * @param tradeDate   清算日期
   * @param retryNumber 重试次数，目前固定为一次，传入1,重试一次，传入0,不会重试转账
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void transfer(String ledgerNo, Long amount, String ledgerSid,
                       String tradeDate, int retryNumber) {
    LedgerTransfer transfer = new LedgerTransfer();
    transfer.setActualTransfer(amount);
    transfer.setLedgerNo(ledgerNo);
    transfer.setLedgerSid(ledgerSid);
    transfer.setTradeDate(tradeDate);
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
      //第一次转账异常，给对应人员发送短信或消息 todo: 待完成
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
      } else if (retryNumber != 0) {//转账失败，进行一次重新转账
        transfer(ledgerNo, amount, ledgerSid, tradeDate, 0);
      } else {
        //第二次转账失败，发送短信或消息警告 todo:待完成
      }
    } else {
      state = 1;
    }
    transfer.setDateCompleted(new Date());
    transfer.setState(state);
    ledgerTransferRepository.save(transfer);
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
        // 通道结算单号
        if (criteria.getLedgerSid() != null && !"".equals(criteria.getLedgerSid())) {
          predicate.getExpressions().add(
              cb.equal(root.get("ledgerSid"), criteria.getLedgerSid()));
        }
        // 转账状态 0=待转账，1=转账成功，其他为易宝错误码
        if (criteria.getState() != null && !"".equals(criteria.getState())) {
          predicate.getExpressions().add(
              cb.equal(root.get("state"), criteria.getState()));
        }
        // 易宝商户号
        if (criteria.getLedgerNo() != null && !"".equals(criteria.getLedgerNo())) {
          predicate.getExpressions().add(
              cb.equal(root.get("ledgerNo"), criteria.getLedgerNo()));
        }
        // 转账请求号（转账单号）
        if (criteria.getOrderSid() != null && !"".equals(criteria.getOrderSid())) {
          predicate.getExpressions().add(
              cb.equal(root.get("orderSid"), criteria.getOrderSid()));
        }
        return predicate;
      }
    };
  }
}
