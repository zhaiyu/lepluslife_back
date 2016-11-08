package com.jifenke.lepluslive.activity.service;

import com.jifenke.lepluslive.activity.domain.entities.ActivityPhoneRule;
import com.jifenke.lepluslive.activity.repository.ActivityPhoneRuleRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 手机话费规则相关 Created by zhangwen on 2016/10/26.
 */
@Service
public class ActivityPhoneRuleService {

  @Value("${telephone.appkey}")
  private String phoneKey;

  @Value("${telephone.secret}")
  private String phoneSecret;

  @Inject
  private ActivityPhoneRuleRepository repository;

  /**
   * 根据ID查询充值规则 16/10/27
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ActivityPhoneRule findById(Long id) {
    return repository.findOne(id);
  }

  /**
   * 新建或修改话费产品  16/10/27
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Map edit(ActivityPhoneRule phoneRule) throws Exception {
    ActivityPhoneRule dbRule = null;
    Map<Object, Object> result = new HashMap<>();

    Long id = phoneRule.getId();
    try {
      if (id != null) {
        dbRule = findById(id);
        if (dbRule == null) {
          result.put("status", 404);
          result.put("msg", "phoneRule not found");
          return result;
        }
      } else { //新建
        dbRule = new ActivityPhoneRule();
      }
      dbRule.setLastUpdate(new Date());
      dbRule.setWorth(phoneRule.getWorth());
      dbRule.setPayType(phoneRule.getPayType());
      dbRule.setPrice(phoneRule.getPrice());
      dbRule.setScore(phoneRule.getScore());
      dbRule.setCheap(phoneRule.getCheap());
      dbRule.setRepository(phoneRule.getRepository());
      dbRule.setTotalLimit(phoneRule.getTotalLimit());
      dbRule.setLimitType(phoneRule.getLimitType());
      dbRule.setBuyLimit(phoneRule.getBuyLimit());
      dbRule.setRebateType(phoneRule.getRebateType());
      dbRule.setRebate(phoneRule.getRebate());
      dbRule.setMinRebate(phoneRule.getMinRebate());
      repository.save(dbRule);

      result.put("status", 200);
      result.put("data", dbRule);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  /**
   * 上架或下架话费产品   16/10/27
   *
   * @param id 产品ID
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeState(Long id) throws Exception {
    ActivityPhoneRule phoneRule = repository.findOne(id);
    try {
      if (phoneRule.getState() == 1) {
        phoneRule.setState(0);
      } else {
        phoneRule.setState(1);
      }
      repository.save(phoneRule);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  /**
   * 按条件查询话费订单列表  16/10/27
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ActivityPhoneRule> findListByState(Integer state) {
    if (state == -1) {
      return repository.findAll();
    }
    return repository.findByStateOrderByLastUpdateDesc(state);
  }



}
