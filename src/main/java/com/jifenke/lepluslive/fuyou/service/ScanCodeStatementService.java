package com.jifenke.lepluslive.fuyou.service;

import com.jifenke.lepluslive.fuyou.domain.criteria.ScanCodeSettleOrderCriteria;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeMerchantStatement;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeRefundOrder;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeSettleOrder;
import com.jifenke.lepluslive.fuyou.repository.ScanCodeMerchantStatementRepository;
import com.jifenke.lepluslive.fuyou.repository.ScanCodeSettleOrderRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlement;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlementStore;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantSettlementService;
import com.jifenke.lepluslive.merchant.service.MerchantSettlementStoreService;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.merchant.service.MerchantWalletService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 富友扫码分润单 Created by zhangwen on 2016/12/19.
 */
@Service
public class ScanCodeStatementService {


  @Inject
  private ScanCodeSettleOrderRepository settleOrderRepository;

  @Inject
  private ScanCodeMerchantStatementRepository statementRepository;

  @Inject
  private MerchantService merchantService;

  @Inject
  private MerchantSettlementService merchantSettlementService;

  @Inject
  private MerchantSettlementStoreService settlementStoreService;

  @Inject
  private MerchantUserService merchantUserService;

  @Inject
  private ScanCodeRefundOrderService refundOrderService;

  @Inject
  private MerchantWalletService merchantWalletService;

  /**
   * 生成以商户号为单位的结算单  2016/12/29
   *
   * @param object    结算信息
   * @param tradeDate 交易日期
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void createScanCodeSettleOrder(Object[] object, String tradeDate) {
    String merchantNum = String.valueOf(object[0]);
    MerchantSettlement settlement = merchantSettlementService.findByMerchantNum(merchantNum);
    if (settlement != null) {
      ScanCodeSettleOrder settleOrder = new ScanCodeSettleOrder();
      Date date = new Date();
      settleOrder.setCreatedDate(date);
      settleOrder.setTradeDate(tradeDate);
      settleOrder.setTransferMoney(object[1] == null ? 0 : ((BigDecimal) object[1]).longValue());
      settleOrder.setTransferMoneyFromTruePay(
          object[2] == null ? 0 : ((BigDecimal) object[2]).longValue());
      settleOrder
          .setTransferMoneyFromScore(object[3] == null ? 0 : ((BigDecimal) object[3]).longValue());
      settleOrder.setMerchantNum(merchantNum);
      settleOrder
          .setCommission(settlement.getCommission());
      settleOrder.setType(settlement.getType());
      Map<String, String>
          map =
          settlementStoreService
              .findMerchantIdAndNameBySettleId(settlement.getType(), settlement.getId());
      settleOrder.setShopIds(map.get("ids") == null ? "0" : map.get("ids"));
      settleOrder.setShopNames(map.get("names") == null ? "未知" : map.get("names"));
      settleOrder.setMerchantUserId(settlement.getMerchantUserId());
      settleOrder.setMerchantName(
          merchantUserService.findMerchantNameById(settlement.getMerchantUserId()));
      settleOrder.setAccountType(settlement.getAccountType());
      settleOrder.setBankNumber(settlement.getBankNumber());
      settleOrder.setBankName(settlement.getBankName());
      settleOrder.setPayee(settlement.getPayee());

      settleOrderRepository.saveAndFlush(settleOrder);
    }
  }

  /**
   * 生成以门店ID为单位的结算单  2016/12/29
   *
   * @param object    结算信息
   * @param tradeDate 交易日期
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void createScanCodeMerchantStatement(Object[] object, String tradeDate) {
    Long merchantId = Long.parseLong(object[0].toString());
    Merchant merchant = merchantService.findMerchantById(merchantId);
    MerchantSettlementStore settlementStore = settlementStoreService.findByMerchantId(merchantId);
    ScanCodeMerchantStatement settleOrder = new ScanCodeMerchantStatement();
    Date date = new Date();
    settleOrder.setCreatedDate(date);
    settleOrder.setTradeDate(tradeDate);
    settleOrder.setMerchant(merchant);
    if (settlementStore != null) {
      settleOrder.setCommonSettlementId(settlementStore.getCommonSettlementId());
      settleOrder.setAllianceSettlementId(settlementStore.getAllianceSettlementId());
    }
    settleOrder.setTransferMoney(object[1] == null ? 0 : ((BigDecimal) object[1]).longValue());
    settleOrder.setTransferMoneyFromTruePay(
        object[2] == null ? 0 : ((BigDecimal) object[2]).longValue());
    settleOrder
        .setTransferMoneyFromScore(object[3] == null ? 0 : ((BigDecimal) object[3]).longValue());
    settleOrder.setMerchantUserId(merchant.getMerchantUser().getId());

    statementRepository.saveAndFlush(settleOrder);

  }

  /**
   * 根据昨天的已完成退款单冲正两种结算  2016/12/30
   *
   * @param tradeDate 交易日期
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void reverseStatement(Date start, Date end, String tradeDate) throws ParseException {
    //获取某一天的已完成退款单列表
    List<ScanCodeRefundOrder> list = refundOrderService.findByCompleteDateBetween(start, end);
    if (list != null && list.size() > 0) {
      for (ScanCodeRefundOrder refundOrder : list) {
        ScanCodeOrder order = refundOrder.getScanCodeOrder();
        //如果是交易当天的结算单，则不计算在内
        if (order != null && order.getSettleDate() != null && (!order.getSettleDate()
            .startsWith(tradeDate))) {
          //根据商户号和交易日期获取商户号结算单并修正
          ScanCodeSettleOrder
              settleOrder =
              settleOrderRepository
                  .findByMerchantNumAndTradeDate(order.getScanCodeOrderExt().getMerchantNum(), tradeDate);
          if (settleOrder != null) {
            settleOrder.setRefundMoney(settleOrder.getRefundMoney() + order.getTransferMoney());
            settleOrder.setTransferMoney(settleOrder.getTransferMoney() - order.getTransferMoney());
            settleOrder.setTransferMoneyFromTruePay(
                settleOrder.getTransferMoneyFromTruePay() - order.getTransferMoneyFromTruePay());
            settleOrder.setTransferMoneyFromScore(
                settleOrder.getTransferMoneyFromScore() - order.getTransferMoneyFromScore());
            settleOrderRepository.save(settleOrder);
          }
          //根据门店和交易日期获取门店结算单并修正
          ScanCodeMerchantStatement
              statement =
              statementRepository.findByMerchantAndTradeDate(order.getMerchant(), tradeDate);
          if (statement != null) {
            statement.setRefundMoney(statement.getRefundMoney() + order.getTransferMoney());
            statement.setTransferMoney(statement.getTransferMoney() - order.getTransferMoney());
            statement.setTransferMoneyFromTruePay(
                statement.getTransferMoneyFromTruePay() - order.getTransferMoneyFromTruePay());
            statement.setTransferMoneyFromScore(
                statement.getTransferMoneyFromScore() - order.getTransferMoneyFromScore());
            statementRepository.save(statement);
          }
        }
      }
    }
  }

  /**
   * 富友扫码结算单分页条件查询  17/01/03
   *
   * @param criteria 查询条件
   * @param limit    查询条数
   */
  public Page findOrderByPage(ScanCodeSettleOrderCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return settleOrderRepository
        .findAll(getWhereClause(criteria),
                 new PageRequest(criteria.getOffset() - 1, limit, sort));
  }

  private static Specification<ScanCodeSettleOrder> getWhereClause(
      ScanCodeSettleOrderCriteria orderCriteria) {
    return new Specification<ScanCodeSettleOrder>() {
      @Override
      public Predicate toPredicate(Root<ScanCodeSettleOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (orderCriteria.getState() != null) {//账单状态
          predicate.getExpressions().add(
              cb.equal(r.get("state"), orderCriteria.getState()));
        }

        if (orderCriteria.getStartDate() != null && !""
            .equals(orderCriteria.getStartDate())) {//账单时间
          predicate.getExpressions().add(
              cb.between(r.get("tradeDate"), orderCriteria.getStartDate(),
                         orderCriteria.getEndDate()));
        }

        if (orderCriteria.getMerchantName() != null && !""
            .equals(orderCriteria.getMerchantName())) {//门店名称
          predicate.getExpressions().add(
              cb.like(r.get("shopNames"),
                      "%" + orderCriteria.getMerchantName() + "%"));
        }

        if (orderCriteria.getMerchantUserName() != null && !""
            .equals(orderCriteria.getMerchantUserName())) {//商户名称
          predicate.getExpressions().add(
              cb.like(r.get("merchantName"),
                      "%" + orderCriteria.getMerchantUserName() + "%"));
        }

        return predicate;
      }
    };
  }

  /**
   * 根据ID查找商户号结算单  2017/01/03
   *
   * @param id 结算单ID
   */
  public ScanCodeSettleOrder findById(Long id) {
    return settleOrderRepository.findOne(id);
  }

  /**
   * 批量转账  2017/01/04
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void batchTransfer() throws Exception {
    List<ScanCodeSettleOrder> list = settleOrderRepository.findByState(0);
    if (list != null && list.size() > 0) {
      for (ScanCodeSettleOrder order : list) {
        setTrade(order);
      }
    }
  }

  /**
   * 设为已转账  2017/01/03
   *
   * @param settleOrder 商户号结算单
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void setTrade(ScanCodeSettleOrder settleOrder) throws Exception {
    if (settleOrder != null && settleOrder.getState() != 1) {
      settleOrder.setState(1);
      settleOrder.setTransferDate(new Date());
      settleOrderRepository.save(settleOrder);
      String[] ids = settleOrder.getShopIds().split("_");
      if (ids.length == 1) {
        merchantWalletService.changeMerchantWalletTotalTransferMoney(Long.valueOf(ids[0]),
                                                                     settleOrder
                                                                         .getTransferMoney());
      } else {//如果结算单对应多个门店，则需要查找每个门店对应的结算单，加总转账金额
        for (String merchantId : ids) {
          ScanCodeMerchantStatement
              statement =
              statementRepository.findByMerchantIdAndTradeDate(Long.valueOf(merchantId),
                                                               settleOrder.getTradeDate());
          if (statement != null) {
            merchantWalletService.changeMerchantWalletTotalTransferMoney(
                statement.getMerchant().getId(),
                statement.getTransferMoney());
          }
        }
      }
    }
  }

  /**
   * 统计某个商户号的累计流水信息  2017/01/09
   *
   * @param merchantNum 商户号
   * @param state       结算单状态
   */
  public Map<String, Object> countByMerchantNumAndState(String merchantNum, int state) {
    Map<String, Object> result = new HashMap<>();
    List<Object[]> list = settleOrderRepository.countByMerchantNumAndState(merchantNum, state);
    if (list != null && list.size() > 0) {
      result.put("total", list.get(0)[0] == null ? 0 : list.get(0)[0]);
      result.put("totalByTruePay", list.get(0)[1] == null ? 0 : list.get(0)[1]);
      result.put("totalByScore", list.get(0)[2] == null ? 0 : list.get(0)[2]);
    } else {
      result.put("total", 0);
      result.put("totalByTruePay", 0);
      result.put("totalByScore", 0);
    }
    return result;
  }

}
