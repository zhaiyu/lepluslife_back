package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeRefundOrder;
import com.jifenke.lepluslive.fuyou.service.FuYouPayService;
import com.jifenke.lepluslive.fuyou.service.ScanCodeRefundOrderService;
import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWallet;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWalletLog;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantWalletLogService;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.order.service.ScanCodeOrderService;
import com.jifenke.lepluslive.order.service.ShareService;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManager;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManagerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManagerWalletLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletLog;
import com.jifenke.lepluslive.partner.service.PartnerService;
import com.jifenke.lepluslive.partner.service.PartnerWalletService;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.score.domain.entities.ScoreADetail;
import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.score.domain.entities.ScoreBDetail;
import com.jifenke.lepluslive.score.domain.entities.ScoreC;
import com.jifenke.lepluslive.score.service.ScoreAService;
import com.jifenke.lepluslive.score.service.ScoreBService;
import com.jifenke.lepluslive.score.service.ScoreCService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerRefundOrderCriteria;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerTransferCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerRefundOrder;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
import com.jifenke.lepluslive.yibao.repository.LedgerRefundOrderRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerRefundOrderService {

  @Inject
  private LedgerRefundOrderRepository ledgerRefundOrderRepository;

  @Inject
  private ScanCodeOrderService scanCodeOrderService;

  @Inject
  private ScoreAService scoreAService;

  @Inject
  private ScoreBService scoreBService;

  @Inject
  private ScoreCService scoreCService;

  @Inject
  private ShareService shareService;

  @Inject
  private PartnerWalletService partnerWalletService;

  @Inject
  private EntityManager entityManager;

  @Inject
  private ScanCodeRefundOrderService scanCodeRefundOrderService;

  @Inject
  private FuYouPayService fuYouPayService;

  @Inject
  private MerchantService merchantService;

  @Inject
  private MerchantWalletLogService merchantWalletLogService;

  @Inject
  private PartnerService partnerService;


  /**
   * 易宝 点击退款，获取退款信息  2017/8/3
   *
   * @param orderSid 订单号
   */
  public Map<String, Object> getRefundInfo(String orderSid) {
    Map<String, Object> result = new HashMap<>();
    ScanCodeOrder order = scanCodeOrderService.findByOrderSid(orderSid);
    if (order == null) {
      result.put("status", 1001);
      result.put("msg", "未找到该订单");
      return result;
    }
    if (order.getScanCodeOrderExt().getGatewayType() != 1) {
      result.put("status", 1002);
      result.put("msg", "该订单不是易宝通道订单");
      return result;
    }
    if (order.getState() != 1) {
      result.put("status", 1003);
      result.put("msg", "该订单未支付或已退款！state=" + order.getState());
      return result;
    }
    //订单信息
    result.put("order", order);
    //用户信息
    LeJiaUser leJiaUser = order.getLeJiaUser();
    ScoreA scoreA = scoreAService.findScoreAByWeiXinUser(leJiaUser);
    ScoreC scoreC = scoreCService.findScoreCByWeiXinUser(leJiaUser);
    result.put("user", leJiaUser);
    result.put("userScoreA", scoreA.getScore());
    result.put("userScoreC", scoreC.getScore());
    //商户信息
    Merchant merchant = order.getMerchant();
    result.put("merchant", merchant);
    //分润信息
    try {
      OffLineOrderShare orderShare = shareService.findByScanCodeOrder(order.getId());
      if (orderShare != null) {
        Map<String, Object> share = new HashMap<>();
        //锁定商户分润信息 todo:可能为空
        Merchant lockMerchant = orderShare.getLockMerchant();
        MerchantWallet merchantWallet = merchantService.findMerchantWalletByMerchant(lockMerchant);
        String
            toLockMerchant =
            orderShare.getToLockMerchant() + "_" + lockMerchant.getName() + "_" + merchantWallet
                .getAvailableBalance();
        share.put("toLockMerchant", toLockMerchant);
        //锁定商户的天使合伙人分润信息
        Partner lockPartner = orderShare.getLockPartner();
        PartnerWallet lockPartnerWallet = partnerWalletService.findByPartner(lockPartner);
        String
            toLockPartner =
            orderShare.getToLockPartner() + "_" + lockPartner.getName() + "_" + lockPartnerWallet
                .getAvailableBalance();
        share.put("toLockPartner", toLockPartner);
        //锁定商户的城市合伙人分润信息
        PartnerManager lockPartnerManager = orderShare.getLockPartnerManager();
        PartnerManagerWallet
            lockPartnerManagerWallet =
            partnerService.findPartnerManagerWalletByPartnerManager(lockPartnerManager);
        String
            toLockPartnerManager =
            orderShare.getToLockPartnerManager() + "_" + lockPartnerManager.getName() + "_"
            + lockPartnerManagerWallet
                .getAvailableBalance();
        share.put("toLockPartnerManager", toLockPartnerManager);
        //交易商户的天使合伙人分润信息
        Partner tradePartner = orderShare.getTradePartner();
        PartnerWallet tradePartnerWallet = partnerWalletService.findByPartner(tradePartner);
        String
            toTradePartner =
            orderShare.getToTradePartner() + "_" + tradePartner.getName() + "_" + tradePartnerWallet
                .getAvailableBalance();
        share.put("toTradePartner", toTradePartner);
        //交易商户的城市合伙人分润信息
        PartnerManager tradePartnerManager = orderShare.getTradePartnerManager();
        PartnerManagerWallet
            tradePartnerManagerWallet =
            partnerService.findPartnerManagerWalletByPartnerManager(tradePartnerManager);
        String
            toTradePartnerManager =
            orderShare.getToTradePartnerManager() + "_" + tradePartnerManager.getName() + "_"
            + tradePartnerManagerWallet
                .getAvailableBalance();
        share.put("toTradePartnerManager", toTradePartnerManager);

        result.put("share", share);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    result.put("status", 200);
    return result;
  }

  /**
   * 退款提交   2016/12/23
   *
   * @param orderSid 要退款的订单号
   * @param force    是否强制退款
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Map<String, Object> refundSubmit(String orderSid, Integer force) {
    Map<String, Object> result = new HashMap<>();
    Date date = new Date();
    Date begin = DataUtils.getCurrDayBeginDate();
    ScanCodeOrder order = scanCodeOrderService.findByOrderSid(orderSid);
    //用户信息
    LeJiaUser leJiaUser = order.getLeJiaUser();
    ScoreA scoreA = scoreAService.findScoreAByWeiXinUser(leJiaUser);
    ScoreB scoreB = scoreBService.findScoreBByWeiXinUser(leJiaUser);
    //获取该商户使用的商户号今日可退款金额和红包
    Object[] o = null;
    Long canRefundScore = o[2] == null ? 0L : Long.valueOf(o[2].toString());
    if (canRefundScore < order.getTrueScore()) {
      result.put("status", 1001);
      result.put("msg", "商户今日可退红包不足，无法退款");
      return result;
    }
    //分润信息
    OffLineOrderShare orderShare = shareService.findByScanCodeOrder(order.getId());
    Merchant lockMerchant = null;
    MerchantWallet merchantWallet = null;
    Partner lockPartner = null;
    PartnerWallet lockPartnerWallet = null;
    PartnerManager lockPartnerManager = null;
    PartnerManagerWallet lockPartnerManagerWallet = null;
    Partner tradePartner = null;
    PartnerWallet tradePartnerWallet = null;
    PartnerManager tradePartnerManager = null;
    PartnerManagerWallet tradePartnerManagerWallet = null;
    Long a = scoreA.getScore() + order.getTrueScore() - order.getRebate();
    Long b = scoreB.getScore() - order.getScoreB();
    if (force == 0) {//账户余额不足以抵扣，则无法退款
      if (a < 0) { //用户红包不足
        result.put("status", 1002);
        result.put("msg", "用户红包不足，无法退款");
        return result;
      }
      if (b < 0) { //用户积分余额不足
        result.put("status", 1003);
        result.put("msg", "用户积分不足，无法退款");
        return result;
      }
      if (orderShare != null) {
        //锁定商户分润信息
        lockMerchant = orderShare.getLockMerchant();
        merchantWallet = merchantService.findMerchantWalletByMerchant(lockMerchant);
        if (orderShare.getToLockMerchant() > merchantWallet.getAvailableBalance()) {
          result.put("status", 1004);
          result.put("msg", "会员锁定门店钱包余额不足，无法退款");
          return result;
        }
        //锁定商户的天使合伙人分润信息
        lockPartner = orderShare.getLockPartner();
        lockPartnerWallet = partnerWalletService.findByPartner(lockPartner);
        if (orderShare.getToLockPartner() > lockPartnerWallet.getAvailableBalance()) {
          result.put("status", 1005);
          result.put("msg", "会员锁定天使合伙人钱包余额不足，无法退款");
          return result;
        }
        //锁定商户的城市合伙人分润信息
        lockPartnerManager = orderShare.getLockPartnerManager();
        lockPartnerManagerWallet =
            partnerService.findPartnerManagerWalletByPartnerManager(lockPartnerManager);
        if (orderShare.getToLockPartnerManager() > lockPartnerManagerWallet.getAvailableBalance()) {
          result.put("status", 1006);
          result.put("msg", "会员锁定城市合伙人钱包余额不足，无法退款");
          return result;
        }
        //交易商户的天使合伙人分润信息
        tradePartner = orderShare.getTradePartner();
        tradePartnerWallet = partnerWalletService.findByPartner(tradePartner);
        if (orderShare.getToTradePartner() > tradePartnerWallet.getAvailableBalance()) {
          result.put("status", 1007);
          result.put("msg", "交易商户的天使合伙人钱包余额不足，无法退款");
          return result;
        }
        //交易商户的城市合伙人分润信息
        tradePartnerManager = orderShare.getTradePartnerManager();
        tradePartnerManagerWallet =
            partnerService.findPartnerManagerWalletByPartnerManager(tradePartnerManager);
        if (orderShare.getToTradePartnerManager() > tradePartnerManagerWallet
            .getAvailableBalance()) {
          result.put("status", 1008);
          result.put("msg", "交易商户的城市合伙人钱包余额不足，无法退款");
          return result;
        }
      }
    }
    //查询该订单有没有对应的退款单
    ScanCodeRefundOrder refundOrder = scanCodeRefundOrderService.findByScanCodeOrder(order);
    if (refundOrder == null) {
      refundOrder = new ScanCodeRefundOrder();
      refundOrder.setMerchantNum(order.getScanCodeOrderExt().getMerchantNum());
      refundOrder.setScanCodeOrder(order);
      if (orderShare != null) {
        refundOrder.setOrderShareId(orderShare.getId());
      }
    } else if (refundOrder.getState() == 1) {
      result.put("status", 1009);
      result.put("msg", "该订单已完成退款，无需再次退款");
      return result;
    }
    try {
      scanCodeRefundOrderService.saveOrder(refundOrder);
      Map<String, String> map = null;
      if (order.getTruePay() == 0) {
        map = new HashMap<>();
        map.put("result_code", "000000");
        map.put("transaction_id", "" + date.getTime());
        map.put("mchnt_order_no", orderSid);
        map.put("refund_order_no", refundOrder.getRefundOrderSid());
      } else {//如果不是全红包则调接口退款
        map = fuYouPayService.orderRefundHttp(order, refundOrder.getRefundOrderSid());
      }

      String resultCode = map.get("result_code");
      //添加退款日志
      scanCodeRefundOrderService.saveOrderLog(map);
      if (!"000000".equals(resultCode)) { //退款失败
        result.put("status", 1010);
        result.put("msg", resultCode + "==" + map.get("result_msg"));
        return result;
      }
      //退款成功，处理数据
      System.out.println("===============退款成功，处理数据===================");
      //将订单修改为已退款
      order.setState(2);
      order.setRefundDate(date);
      scanCodeOrderService.saveOrder(order);
      //修改退款单状态
      refundOrder.setState(1);
      refundOrder.setCompleteDate(date);
      refundOrder.setRefundSid(map.get("transaction_id"));
      if (a < 0) {
        a = 0L;
        refundOrder.setRealRebate(scoreA.getScore());
      } else {
        refundOrder.setRealRebate(order.getRebate());
      }
      if (b < 0) {
        b = 0L;
        refundOrder.setRealScoreB(scoreB.getScore());
      } else {
        refundOrder.setRealScoreB(order.getScoreB());
      }
      //消费者红包积分处理
      scoreA.setScore(a);
      scoreA.setLastUpdateDate(date);
      scoreAService.saveScore(scoreA);
      Long aD = order.getTrueScore() - order.getRebate();
      ScoreADetail aDetail = new ScoreADetail();
      aDetail.setOrigin(15002);
      aDetail.setScoreA(scoreA);
      aDetail.setOperate("线下支付退款");
      aDetail.setNumber(aD);
      aDetail.setOrderSid(orderSid);
      scoreAService.saveDetail(aDetail);
      scoreB.setScore(b);
      scoreB.setLastUpdateDate(date);
      scoreBService.saveScore(scoreB);
      ScoreBDetail bDetail = new ScoreBDetail();
      bDetail.setOrigin(15002);
      bDetail.setScoreB(scoreB);
      bDetail.setOperate("线下支付退款");
      bDetail.setNumber(-order.getScoreB());
      bDetail.setOrderSid(orderSid);
      scoreBService.saveDetail(bDetail);
      //如果有分润，追回
      if (orderShare != null) {
        if (orderShare.getToLockMerchant() > 0) {
          MerchantWalletLog log1 = new MerchantWalletLog();
          log1.setBeforeChangeMoney(merchantWallet.getAvailableBalance());
          Long m1 = merchantWallet.getAvailableBalance() - orderShare.getToLockMerchant();
          Long m2 = merchantWallet.getTotalMoney() - orderShare.getToLockMerchant();
          if (m1 < 0) {
            m1 = 0L;
            refundOrder.setToLockMerchant(merchantWallet.getAvailableBalance());
          } else {
            refundOrder.setToLockMerchant(orderShare.getToLockMerchant());
          }
          merchantWallet.setAvailableBalance(m1);
          merchantWallet.setTotalMoney(m2 < 0 ? 0 : m2);
          log1.setAfterChangeMoney(m1);
          log1.setMerchantId(lockMerchant.getId());
          log1.setOrderSid(orderSid);
          log1.setType(5L);
          merchantService.saveMerchantWallet(merchantWallet);
          merchantWalletLogService.saveMerchantWalletLog(log1);
        }
        if (orderShare.getToLockPartner() > 0) {
          PartnerWalletLog log2 = new PartnerWalletLog();
          log2.setBeforeChangeMoney(lockPartnerWallet.getAvailableBalance());
          Long m1 = lockPartnerWallet.getAvailableBalance() - orderShare.getToLockPartner();
          Long m2 = lockPartnerWallet.getTotalMoney() - orderShare.getToLockPartner();
          if (m1 < 0) {
            m1 = 0L;
            refundOrder.setToLockPartner(lockPartnerWallet.getAvailableBalance());
          } else {
            refundOrder.setToLockPartner(orderShare.getToLockPartner());
          }
          lockPartnerWallet.setAvailableBalance(m1);
          lockPartnerWallet.setTotalMoney(m2 < 0 ? 0 : m2);
          log2.setAfterChangeMoney(m1);
          log2.setPartnerId(lockPartner.getId());
          log2.setOrderSid(orderSid);
          log2.setType(4L);
          partnerService.savePartnerWallet(lockPartnerWallet);
          partnerService.partnerWalletLog(log2);
        }
        if (orderShare.getToTradePartner() > 0) {
          PartnerWalletLog log3 = new PartnerWalletLog();
          log3.setBeforeChangeMoney(tradePartnerWallet.getAvailableBalance());
          Long m1 = tradePartnerWallet.getAvailableBalance() - orderShare.getToTradePartner();
          Long m2 = tradePartnerWallet.getTotalMoney() - orderShare.getToTradePartner();
          if (m1 < 0) {
            m1 = 0L;
            refundOrder.setToTradePartner(tradePartnerWallet.getAvailableBalance());
          } else {
            refundOrder.setToTradePartner(orderShare.getToTradePartner());
          }
          tradePartnerWallet.setAvailableBalance(m1);
          tradePartnerWallet.setTotalMoney(m2 < 0 ? 0 : m2);
          log3.setAfterChangeMoney(m1);
          log3.setPartnerId(tradePartner.getId());
          log3.setOrderSid(orderSid);
          log3.setType(4L);
          partnerService.savePartnerWallet(tradePartnerWallet);
          partnerService.partnerWalletLog(log3);
        }
        if (orderShare.getToLockPartnerManager() > 0) {
          PartnerManagerWalletLog log4 = new PartnerManagerWalletLog();
          log4.setBeforeChangeMoney(lockPartnerManagerWallet.getAvailableBalance());
          Long
              m1 =
              lockPartnerManagerWallet.getAvailableBalance() - orderShare.getToLockPartnerManager();
          Long m2 = lockPartnerManagerWallet.getTotalMoney() - orderShare.getToLockPartnerManager();
          if (m1 < 0) {
            m1 = 0L;
            refundOrder.setToLockPartnerManager(lockPartnerManagerWallet.getAvailableBalance());
          } else {
            refundOrder.setToLockPartnerManager(orderShare.getToLockPartnerManager());
          }
          lockPartnerManagerWallet.setAvailableBalance(m1);
          lockPartnerManagerWallet.setTotalMoney(m2 < 0 ? 0 : m2);
          log4.setAfterChangeMoney(m1);
          log4.setPartnerManagerId(lockPartnerManager.getId());
          log4.setOrderSid(orderSid);
          log4.setType(4L);
          partnerService.savePartnerManagerWallet(lockPartnerManagerWallet);
          partnerService.savePartnerManagerWalletLog(log4);
        }
        if (orderShare.getToTradePartnerManager() > 0) {
          PartnerManagerWalletLog log5 = new PartnerManagerWalletLog();
          log5.setBeforeChangeMoney(tradePartnerManagerWallet.getAvailableBalance());
          Long
              m1 =
              tradePartnerManagerWallet.getAvailableBalance() - orderShare
                  .getToTradePartnerManager();
          Long
              m2 =
              tradePartnerManagerWallet.getTotalMoney() - orderShare.getToTradePartnerManager();
          if (m1 < 0) {
            m1 = 0L;
            refundOrder.setToTradePartnerManager(tradePartnerManagerWallet.getAvailableBalance());
          } else {
            refundOrder.setToTradePartnerManager(orderShare.getToTradePartnerManager());
          }
          tradePartnerManagerWallet.setAvailableBalance(m1);
          tradePartnerManagerWallet.setTotalMoney(m2 < 0 ? 0 : m2);
          log5.setAfterChangeMoney(m1);
          log5.setPartnerManagerId(tradePartnerManager.getId());
          log5.setOrderSid(orderSid);
          log5.setType(4L);
          partnerService.savePartnerManagerWallet(tradePartnerManagerWallet);
          partnerService.savePartnerManagerWalletLog(log5);
        }
      }
      scanCodeRefundOrderService.saveOrder(refundOrder);
      result.put("status", 200);
      result.put("msg", "SUCCESS");
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }


  /***
   *  根据条件查询退款单记录
   *  Created by xf on 2017-07-14.
   */
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Page<LedgerRefundOrder> findByCriteria(LedgerRefundOrderCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return ledgerRefundOrderRepository
        .findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  public static Specification<LedgerRefundOrder> getWhereClause(
      LedgerRefundOrderCriteria criteria) {
    return new Specification<LedgerRefundOrder>() {
      @Override
      public Predicate toPredicate(Root<LedgerRefundOrder> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        // 退款单编号
        if (criteria.getRefundOrderSid() != null && !"".equals(criteria.getMerchantId())) {
          predicate.getExpressions().add(
              cb.equal(root.get("refundOrderSid"), criteria.getRefundOrderSid()));
        }
        //退款状态 0=待退款，1=未开始退款，2=退款成功，3=退款失败，其他为通道返回码
        if (criteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(root.get("state"), criteria.getState()));
        }
        // 门店ID
        if (criteria.getMerchantId() != null && !"".equals(criteria.getMerchantId())) {
          predicate.getExpressions().add(
              cb.equal(root.get("merchantId"), criteria.getMerchantId()));
        }
        // 订单类型
        if (criteria.getOrderType() != null) {
          predicate.getExpressions().add(
              cb.equal(root.get("orderType"), criteria.getOrderType()));
        }
        // 退款完成时间
        if (criteria.getCompleteStart() != null && criteria.getCompleteEnd() != null) {
          Date start = new Date(criteria.getCompleteStart());
          Date end = new Date(criteria.getCompleteEnd());
          predicate.getExpressions().add(
              cb.between(root.get("dateCompleted"), start, end));
        }
        return predicate;
      }
    };
  }
}
