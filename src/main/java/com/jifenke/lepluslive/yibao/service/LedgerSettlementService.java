package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.global.config.YBConstants;
import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWeiXinUser;
import com.jifenke.lepluslive.merchant.domain.entities.TemporaryMerchantUserShop;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantWeiXinUserService;
import com.jifenke.lepluslive.merchant.service.TemporaryMerchantUserShopService;
import com.jifenke.lepluslive.weixin.service.WxTemMsgService;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerSettlementCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerSettlement;
import com.jifenke.lepluslive.yibao.repository.LedgerSettlementRepository;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
public class LedgerSettlementService {

  @Inject
  private LedgerSettlementRepository ledgerSettlementRepository;

  @Inject
  private YBOrderService ybOrderService;

  @Inject
  private MerchantService merchantService;

  @Inject
  private WxTemMsgService wxTemMsgService;

  @Inject
  private MerchantWeiXinUserService merchantWeiXinUserService;

  @Inject
  private TemporaryMerchantUserShopService temporaryMerchantUserShopService;

  /**
   * 插入通道结算单&发送通知 2017-07-26
   *
   * @param queryMap  查询结果
   * @param ledgerMap 商户信息
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void createLedgerSettlement(Map<String, String> queryMap, Map<String, Object> ledgerMap) {
    if ("1".equals(queryMap.get("code"))) {
      //插入一条通道结算单
      LedgerSettlement settlement = new LedgerSettlement();
      String[] info = queryMap.get("info").split(",");
      String startDate = info[5];
      String tradeDate = info[1];
      String bankNo = "" + ledgerMap.get("bankNo");
      Long merchantUserId = Long.valueOf("" + ledgerMap.get("merchantUserId"));
      int state = parseState(info[3]);
      settlement.setState(state);
      settlement.setTradeDate(tradeDate);
      settlement.setStartEndDate(startDate + "," + info[6]);
      settlement.setLedgerNo(info[0]);
      settlement.setMerchantUserId(merchantUserId);
      settlement.setSettlementTrueAmount(
          new BigDecimal(info[2]).multiply(YBConstants.BIG_DECIMAL_100).longValue());
      settlement.setBatchNo(info[4]);
      settlement.setAccountName("" + ledgerMap.get("accountName"));
      settlement.setBankAccountNumber(bankNo);
      int diff = DataUtils.dayDiff(startDate, info[6], null);
      if (diff <= 0) {
        settlement.setActualTransfer(0L);
        settlement.setTotalTransfer(0L);
      } else {
        Long actualTransfer = ybOrderService.findActualTransfer(diff, startDate, info[0]);
        settlement.setActualTransfer(actualTransfer);
        Long totalTransfer = ybOrderService.findTotalTransfer(diff, startDate, info[0]);
        settlement.setTotalTransfer(totalTransfer);
      }
      LedgerSettlement save = ledgerSettlementRepository.save(settlement);
      //更新对应门店结算单状态 多出无结算记录
      ybOrderService.resetStoreSettlementState(diff, startDate, info[0], state);
      //结算成功时发送微信模板消息
      if (state == 1) {
        sendWxMsg(info[2], tradeDate, bankNo, String.valueOf(save.getId()), merchantUserId);
      }
    }
  }

  /**
   * 查询近期非终态目前状态，若SUCCESS修改通道结算单状态&发送通知 2017-08-01
   *
   * @param queryMap     查询结果
   * @param settlementId 通道结算单ID
   * @param tradeDate    转入时间
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateLedgerSettlement(Map<String, String> queryMap, Long settlementId,
                                     String tradeDate) {
    if ("1".equals(queryMap.get("code"))) {
      //插入一条通道结算单
      String[] info = queryMap.get("info").split(",");
      String startDate = info[5];
      int state = parseState(info[3]);
      if (state == 1) {
        LedgerSettlement settlement = ledgerSettlementRepository.findOne(settlementId);
        settlement.setState(state);
        settlement.setDateUpdated(new Date());
        ledgerSettlementRepository.save(settlement);
        int diff = DataUtils.dayDiff(startDate, info[6], null);
        //更新对应门店结算单状态 多出无结算记录
        if (diff > 0) {
          ybOrderService.resetStoreSettlementState(diff, startDate, info[0], state);
        }
        //结算成功时发送微信模板消息
        sendWxMsg(info[2], tradeDate, settlement.getBankAccountNumber(),
                  String.valueOf(settlement.getId()),
                  settlement.getMerchantUserId());
      }
    }
  }

  /**
   * 结算结果查询 2017/8/3
   *
   * @param settlementId 结算单ID
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Map querySettlement(Long settlementId) {

    LedgerSettlement settlement = ledgerSettlementRepository.findOne(settlementId);
    if (settlement != null) {
      Map<String, String>
          queryMap =
          YbRequestUtils.querySettlement(settlement.getLedgerNo(), settlement.getTradeDate());
      if ("1".equals(queryMap.get("code"))) {
        String[] info = queryMap.get("info").split(",");
        int state = parseState(info[3]);
        if (state == 1 && settlement.getState() != 1) {
          updateLedgerSettlement(queryMap, settlementId, settlement.getTradeDate());
        }
        queryMap.put("status", "" + state);
      }
      return queryMap;
    }
    return null;
  }

  /**
   * 发送转账模板消息  2017/7/28
   *
   * @param transferMoney  转入金额
   * @param tradeDate      转入时间
   * @param bankNo         银行卡号
   * @param orderId        结算单ID
   * @param merchantUserId 商户ID
   */
  private void sendWxMsg(String transferMoney, String tradeDate, String bankNo, String orderId,
                         Long merchantUserId) {
    String[] keys = new String[3];
    keys[0] = "***************" + bankNo.substring(bankNo.length() - 4, bankNo.length());
    keys[1] = transferMoney;
    keys[2] = tradeDate;

    List<Object[]> list = merchantService.countByMerchantUser(merchantUserId);
    Merchant merchant = new Merchant();
    if (list != null && list.size() > 0) {
      for (Object[] o : list) {
        merchant.setId(Long.valueOf("" + o[0]));
        List<TemporaryMerchantUserShop>
            merchantUserShopList =
            temporaryMerchantUserShopService.findAllByMerchant(merchant);
        for (TemporaryMerchantUserShop s : merchantUserShopList) {
          List<MerchantWeiXinUser>
              merchantWeiXinUsers =
              merchantWeiXinUserService.findMerchantWeiXinUserByMerchantUser(s.getMerchantUser());
          for (MerchantWeiXinUser merchantWeiXinUser : merchantWeiXinUsers) {
            wxTemMsgService.sendTemMessage(merchantWeiXinUser.getOpenId(), 12L, 9L, keys, orderId);
          }
        }
      }
    }
  }

  /**
   * 易宝结算状态码转换 2017/7/27
   */
  private int parseState(String state) {
    if (state.contains("SUCCESS")) {
      return 1;
    }
    if (state.contains("INIT")) {
      return 3;
    }
    if (state.contains("PROCESSING")) {
      return 4;
    }
    if (state.contains("FAILED")) {
      return -1;
    }
    if (state.contains("BANKFAILED")) {
      return -2;
    }
    if (state.contains("REFUNDED")) {
      return 2;
    }
    return 0;
//    switch (state) {
//      case "SUCCESS":
//        return 1;
//      case "INIT":
//        return 3;
//      case "PROCESSING":
//        return 4;
//      case "FAILED":
//        return -1;
//      case "BANKFAILED":
//        return -2;
//      case "REFUNDED":
//        return 2;
//      default:
//        return 0;
//    }
  }

  /***
   *  根据条件查询通道结算单
   *  Created by xf on 2017-07-13.
   */
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Page<LedgerSettlement> findByCriteria(LedgerSettlementCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return ledgerSettlementRepository
        .findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  public static Specification<LedgerSettlement> getWhereClause(LedgerSettlementCriteria criteria) {
    return new Specification<LedgerSettlement>() {
      @Override
      public Predicate toPredicate(Root<LedgerSettlement> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        // 结算状态
        if (criteria.getState() != null && !"".equals(criteria.getState())) {
          predicate.getExpressions().add(
              cb.equal(root.get("state"), criteria.getState()));
        }
        // 乐加商户编号
        if (criteria.getMerchantUserId() != null && !"".equals(criteria.getMerchantUserId())) {
          predicate.getExpressions().add(
              cb.equal(root.get("merchantUserId"), criteria.getMerchantUserId()));
        }
        // 易宝商户号
        if (criteria.getLedgerNo() != null && !"".equals(criteria.getLedgerNo())) {
          predicate.getExpressions().add(
              cb.equal(root.get("ledgerNo"), criteria.getLedgerNo()));
        }
        //  通道结算单号
        if (criteria.getOrderSid() != null && !"".equals(criteria.getOrderSid())) {
          predicate.getExpressions().add(
              cb.equal(root.get("orderSid"), criteria.getOrderSid()));
        }
        //  清算日期
        if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
          Date start = new Date(criteria.getStartDate());
          Date end = new Date(criteria.getEndDate());
          predicate.getExpressions().add(
              cb.between(root.get("tradeDate"), start, end));
        }
        return predicate;
      }
    };
  }
}
