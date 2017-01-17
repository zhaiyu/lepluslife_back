package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.fuyou.service.ScanCodeStatementService;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantSettlement;
import com.jifenke.lepluslive.merchant.repository.MerchantSettlementRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 富友结算规则、类型及结算账户 Created by zhangwen on 2016/12/29.
 */
@Service
@Transactional(readOnly = true)
public class MerchantSettlementService {

  @Inject
  private MerchantSettlementRepository repository;

  @Inject
  private MerchantSettlementStoreService storeService;

  @Inject
  private ScanCodeStatementService scanCodeStatementService;

  /**
   * 根据商户号查找对应结算信息  2016/12/29
   *
   * @param merchantNum 商户号
   */
  public MerchantSettlement findByMerchantNum(String merchantNum) {
    return repository.findByMerchantNum(merchantNum);
  }

  /**
   * 查询某一商户下富友商户号类型的数量  2017/01/04
   *
   * @param merchantUserId 商户ID
   */
  public List<Object[]> countByMerchantUser(Long merchantUserId) {
    return repository.countByMerchantUser(merchantUserId);
  }

  /**
   * 根据ID查找对应结算信息  2017/01/09
   *
   * @param id ID
   */
  public MerchantSettlement findById(Long id) {
    return repository.findOne(id);
  }

  /**
   * 根据商户ID获取商户号列表  2017/01/10
   *
   * @param merchantUserId 商户ID
   */
  public List<MerchantSettlement> findByMerchantUser(Long merchantUserId) {
    return repository.findByMerchantUserId(merchantUserId);
  }

  /**
   * 根据商户ID获取商户号列表(统计数据)  2017/01/09
   *
   * @param merchantUserId 商户ID
   */
  public List<Map<String, Object>> findByMerchantUserId(Long merchantUserId) {
    List<Map<String, Object>> result = new ArrayList<>();
    List<MerchantSettlement> list = repository.findByMerchantUserId(merchantUserId);
    if (list != null && list.size() > 0) {
      for (MerchantSettlement s : list) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", s.getId());
        map.put("merchantNum", s.getMerchantNum());
        map.put("type", s.getType());
        map.put("commission", s.getCommission());
        //应用门店数量
        Map<String, String> map2 = storeService.findMerchantIdAndNameBySettleId(s.getType(),
                                                                                s.getId());
        map.putAll(map2);
        map.put("payee", s.getPayee());
        map.put("card", s.getBankNumber());
        //累计流水，从商户号结算单统计
        Map<String, Object>
            map3 =
            scanCodeStatementService.countByMerchantNumAndState(s.getMerchantNum(), 1);
        map.putAll(map3);
        map.put("createDate", s.getCreateDate());
        result.add(map);
      }
    }
    return result;
  }

  /**
   * 新建或修改商户号  2017/01/10
   *
   * @param settlement 商户号信息
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public String save(MerchantSettlement settlement) throws Exception {
    try {
      if (settlement.getId() != null) {
        MerchantSettlement db_settlement = repository.findOne(settlement.getId());

        db_settlement.setPayee(settlement.getPayee());
        db_settlement.setType(settlement.getType());
        db_settlement.setAccountType(settlement.getAccountType());
        db_settlement.setBankName(settlement.getBankName());
        db_settlement.setBankNumber(settlement.getBankNumber());
        db_settlement.setBankUnion(settlement.getBankUnion());
        db_settlement.setCommission(settlement.getCommission());
        if (!db_settlement.getMerchantNum().equals(settlement.getMerchantNum())) {
          //如果修改了商户号，判断数据库是否已经存在该商户号
          MerchantSettlement s = repository.findByMerchantNum(settlement.getMerchantNum());
          if (s != null) {
            return "该商户号已存在（商户ID为" + s.getMerchantUserId() + "）";
          }
        }
        db_settlement.setMerchantNum(settlement.getMerchantNum());
        db_settlement.setMerchantUserId(settlement.getMerchantUserId());
        repository.save(db_settlement);
      } else {
        //判断数据库是否已经存在该商户号
        MerchantSettlement s = repository.findByMerchantNum(settlement.getMerchantNum());
        if (s != null) {
          return "该商户号已存在（商户ID为" + s.getMerchantUserId() + "）";
        }
        repository.save(settlement);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
    return "200";
  }

}
