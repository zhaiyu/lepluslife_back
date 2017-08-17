package com.jifenke.lepluslive.order.service;


import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeRefundOrder;
import com.jifenke.lepluslive.fuyou.service.FuYouPayService;
import com.jifenke.lepluslive.fuyou.service.ScanCodeRefundOrderService;
import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWallet;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWalletLog;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantWalletLogService;
import com.jifenke.lepluslive.order.domain.criteria.ScanCodeOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrderExt;
import com.jifenke.lepluslive.order.repository.ScanCodeOrderRepository;
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
import com.jifenke.lepluslive.score.service.ScoreAService;
import com.jifenke.lepluslive.score.service.ScoreBService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.weixin.domain.entities.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 富友扫码订单 Created by zhangwen on 2016/12/19.
 */
@Service
public class ScanCodeOrderService {


  @Inject
  private ScanCodeOrderRepository repository;

  @Inject
  private ScoreAService scoreAService;

  @Inject
  private ScoreBService scoreBService;

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
   * 根据订单号获取订单信息  2016/12/27
   *
   * @param orderSid 订单号
   */
  public ScanCodeOrder findByOrderSid(String orderSid) {
    return repository.findByOrderSid(orderSid);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void saveOrder(ScanCodeOrder order) {
    repository.save(order);
  }

  /**
   * 点击退款，获取退款信息  2016/12/22
   *
   * @param orderSid 订单号
   */
  public Map<String, Object> getRefundInfo(String orderSid) {
    Map<String, Object> result = new HashMap<>();
    Date date = new Date();
    Date begin = DataUtils.getCurrDayBeginDate();
    ScanCodeOrder order = repository.findByOrderSid(orderSid);
    //订单信息
    result.put("order", order);
    //用户信息
    LeJiaUser leJiaUser = order.getLeJiaUser();
    ScoreA scoreA = scoreAService.findScoreAByWeiXinUser(leJiaUser);
    ScoreB scoreB = scoreBService.findScoreBByWeiXinUser(leJiaUser);
    result.put("user", leJiaUser);
    result.put("userScoreA", scoreA.getScore());
    result.put("userScoreB", scoreB.getScore());
    //商户信息
    Merchant merchant = order.getMerchant();
    result.put("merchant", merchant);
    //获取该商户使用的商户号今日可退款金额和红包
    Object[]
        o =
        repository
            .countByMerchantNumToday(order.getScanCodeOrderExt().getMerchantNum(), begin, date)
            .get(0);
    Map<String, Object> canRefund = new HashMap<>();
    canRefund.put("totalPrice", o[0] == null ? 0 : o[0]);
    canRefund.put("truePay", o[1] == null ? 0 : o[1]);
    canRefund.put("trueScore", o[2] == null ? 0 : o[2]);
    result.put("canRefund", canRefund);
    //分润信息
    OffLineOrderShare orderShare = shareService.findByScanCodeOrder(order.getId());
    if (orderShare != null) {
      Map<String, Object> share = new HashMap<>();
      //锁定商户分润信息
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
    ScanCodeOrder order = repository.findByOrderSid(orderSid);
    //用户信息
    LeJiaUser leJiaUser = order.getLeJiaUser();
    ScoreA scoreA = scoreAService.findScoreAByWeiXinUser(leJiaUser);
    ScoreB scoreB = scoreBService.findScoreBByWeiXinUser(leJiaUser);
    //获取该商户使用的商户号今日可退款金额和红包
    Object[]
        o =
        repository
            .countByMerchantNumToday(order.getScanCodeOrderExt().getMerchantNum(), begin, date)
            .get(0);
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
    Long b = scoreB.getScore() - order.getScoreC();
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
      repository.save(order);
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
        refundOrder.setRealScoreB(order.getScoreC());
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
      bDetail.setNumber(-order.getScoreC());
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

  /**
   * 通道订单分页条件查询  16/12/20
   *
   * @param criteria 查询条件
   * @param limit    查询条数
   */
  public Page findOrderByPage(ScanCodeOrderCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return repository
        .findAll(getWhereClause(criteria),
                 new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  private static Specification<ScanCodeOrder> getWhereClause(ScanCodeOrderCriteria orderCriteria) {
    return new Specification<ScanCodeOrder>() {
      @Override
      public Predicate toPredicate(Root<ScanCodeOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (orderCriteria.getState() != null) { //订单状态
          predicate.getExpressions().add(cb.equal(r.get("state"), orderCriteria.getState()));
        }

        if (orderCriteria.getStartDate() != null && !""
            .equals(orderCriteria.getStartDate())) {//交易完成时间
          predicate.getExpressions().add(
              cb.between(r.get("completeDate"), new Date(orderCriteria.getStartDate()),
                         new Date(orderCriteria.getEndDate())));
        }

        if (orderCriteria.getPayment() != null) {  //付款方式  0=纯现金|1=纯红包|2=混合
          predicate.getExpressions().add(
              cb.equal(r.<ScanCodeOrderExt>get("scanCodeOrderExt").get("payment"),
                       orderCriteria.getPayment()));
        }

        if (orderCriteria.getUserSid() != null && !"".equals(orderCriteria.getUserSid())) {//消费者SID
          predicate.getExpressions().add(
              cb.like(r.<LeJiaUser>get("leJiaUser").get("userSid"),
                      "%" + orderCriteria.getUserSid() + "%"));
        }

        if (orderCriteria.getPhoneNumber() != null && !""
            .equals(orderCriteria.getPhoneNumber())) { //消费者手机号
          predicate.getExpressions().add(
              cb.like(r.<LeJiaUser>get("leJiaUser").get("phoneNumber"),
                      "%" + orderCriteria.getPhoneNumber() + "%"));
        }

        if (orderCriteria.getMerchantName() != null && !""
            .equals(orderCriteria.getMerchantName())) { //门店名称
          predicate.getExpressions().add(
              cb.like(r.<Merchant>get("merchant").get("name"),
                      "%" + orderCriteria.getMerchantName() + "%"));
        }

        if (orderCriteria.getOrderType() != null) { //订单类型
          predicate.getExpressions().add(
              cb.equal(r.get("orderType"), orderCriteria.getOrderType()));
        }

        if (orderCriteria.getOrderSid() != null && !"".equals(orderCriteria.getOrderSid())) { //订单编号
          predicate.getExpressions().add(cb.equal(r.get("orderSid"), orderCriteria.getOrderSid()));
        }

        if (orderCriteria.getSource() != null) { //支付来源  0=WAP|1=APP
          predicate.getExpressions().add(
              cb.equal(r.<ScanCodeOrderExt>get("scanCodeOrderExt").get("source"),
                       orderCriteria.getSource()));
        }
        if (orderCriteria.getGatewayType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<ScanCodeOrderExt>get("scanCodeOrderExt").get("gatewayType"),
                       orderCriteria.getGatewayType()));
        }
        if (orderCriteria.getPayType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<ScanCodeOrderExt>get("scanCodeOrderExt").get("payType"),
                       orderCriteria.getPayType()));
        }

        if (orderCriteria.getMerchantNum() != null && !""
            .equals(orderCriteria.getMerchantNum())) {//通道商户号
          predicate.getExpressions().add(
              cb.equal(r.<ScanCodeOrderExt>get("scanCodeOrderExt").get("merchantNum"),
                       orderCriteria.getMerchantNum()));
        }
        return predicate;
      }
    };
  }

  /**
   * 每日富友已完成订单统计(group by merchantNum)  2016/12/29
   *
   * @param currDay 订单支付时间yyyyMMdd%
   */
  public List countTransferGroupByMerchantNum(String currDay) {
    String
        sql =
        "SELECT merchant_num,SUM(transfer_money),SUM(transfer_money_from_true_pay),SUM(transfer_money_from_score) FROM scan_code_order WHERE state = 1 AND settle_date LIKE '"
        + currDay + "' GROUP BY merchant_num";

    System.out.println(sql);
    return entityManager.createNativeQuery(sql).getResultList();
  }

  /**
   * 每日富友已完成订单统计(group by Merchant)  2016/12/29
   *
   * @param currDay 订单支付时间yyyyMMdd%
   */
  public List countTransferGroupByMerchant(String currDay) {
    String
        sql =
        "SELECT merchant_id,SUM(transfer_money),SUM(transfer_money_from_true_pay),SUM(transfer_money_from_score) FROM scan_code_order WHERE state = 1 AND settle_date LIKE '"
        + currDay + "' GROUP BY merchant_id";

    System.out.println(sql);
    return entityManager.createNativeQuery(sql).getResultList();
  }

  /**
   * 统计某个门店的累计流水和累计收取红包  2017/02/10
   *
   * @param merchantId 门店ID
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map<String, Long> countPriceByMerchant(Long merchantId) {
    List<Object[]> list = repository.countPriceByMerchant(merchantId);
    Map<String, Long> map = new HashMap<>();
    Long totalPrice = 0L;
    Long trueScore = 0L;

    if (list != null && list.size() > 0) {
      Object[] o = list.get(0);
      totalPrice = Long.valueOf(String.valueOf(o[0] != null ? o[0] : 0));
      trueScore = Long.valueOf(String.valueOf(o[1] != null ? o[1] : 0));
    }
    map.put("totalPrice_fy", totalPrice);
    map.put("trueScore_fy", trueScore);
    return map;
  }
}
