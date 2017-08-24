package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.criteria.GrouponStatisticCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponStatistic;
import com.jifenke.lepluslive.groupon.repository.GrouponStatisticRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWeiXinUser;
import com.jifenke.lepluslive.merchant.domain.entities.TemporaryMerchantUserShop;
import com.jifenke.lepluslive.merchant.service.MerchantWeiXinUserService;
import com.jifenke.lepluslive.merchant.service.TemporaryMerchantUserShopService;
import com.jifenke.lepluslive.mq.dto.WxMessageRequest;
import com.jifenke.lepluslive.mq.dto.WxMsgType;
import com.jifenke.lepluslive.mq.messenger.WxMsgMessenger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 2017/6/27.
 */
@Service
@Transactional(readOnly = true)
public class GrouponStatisticService {

  @Inject
  private EntityManager entityManager;

  @Inject
  private WxMsgMessenger wxMsgMessenger;

  @Inject
  private GrouponStatisticRepository repository;

  @Inject
  private TemporaryMerchantUserShopService temporaryMerchantUserShopService;

  @Inject
  private MerchantWeiXinUserService merchantWeiXinUserService;

  public List<Object[]> statistic(Date start, Date end) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String starts = sdf.format(start);
    String ends = sdf.format(end);
    StringBuffer sql = new StringBuffer();
    sql.append(
        "select sum(trasnfer_money),merchant_id,count(*),sum(total_price),sum(commission) from groupon_code where state = 1 and check_date between '");
    sql.append(starts);
    sql.append("' and '");
    sql.append(ends);
    sql.append("' group by merchant_id");
    List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
    return resultList;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void createStatistic(Object[] obj, Date end) {
    Merchant merchant = new Merchant();
    merchant.setId(Long.parseLong(obj[1].toString()));
    GrouponStatistic grouponStatistic = new GrouponStatistic();
    grouponStatistic.setMerchant(merchant);
    grouponStatistic.setCheckNum(Long.parseLong(obj[2].toString()));
    grouponStatistic.setTransferMoney(Long.parseLong(obj[0].toString()));
    grouponStatistic.setBalanceDate(end);
    grouponStatistic.setCommission(Long.parseLong(obj[4].toString()));
    grouponStatistic.setTotalMoney(Long.parseLong(obj[3].toString()));
    repository.save(grouponStatistic);
  }

  public Page findByCirteria(GrouponStatisticCriteria criteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "balanceDate");
    return repository
        .findAll(getFinancialClause(criteria),
                 new PageRequest(criteria.getOffset() - 1, limit, sort));
  }


  private static Specification<GrouponStatistic> getFinancialClause(
      GrouponStatisticCriteria criteria) {
    return new Specification<GrouponStatistic>() {
      @Override
      public Predicate toPredicate(Root<GrouponStatistic> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       criteria.getState()));
        }

        if (criteria.getStartDate() != null && criteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("balanceDate"), new Date(criteria.getStartDate()),
                         new Date(criteria.getEndDate())));
        }

        if (criteria.getTransferEndDate() != null
            && criteria.getTransferStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("transferDate"), new Date(criteria.getTransferStartDate()),
                         new Date(criteria.getTransferEndDate())));
        }

        if (criteria.getMerchant() != null && !"".equals(criteria.getMerchant())) {
          predicate.getExpressions().add(
              cb.like(r.<Merchant>get("merchant").get("name"),
                      "%" + criteria.getMerchant() + "%"));
        }
        return predicate;
      }
    };
  }

  public GrouponStatistic findOne(Long id) {
    return repository.findOne(id);
  }

  /**
   * 单笔结算单确认转账并发送模板消息 2017/8/23
   *
   * @param statistic 结算单ID
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public GrouponStatistic transfer(GrouponStatistic statistic) {
    statistic.setState(1);
    statistic.setCompleteDate(new Date());
    repository.save(statistic);
    return statistic;
  }

  /**
   * 获取所有的待结算单 2017/8/23
   */
  public List<GrouponStatistic> findAllNonTransferStatistic() {
    return repository.findAllByState(0);
  }

  /**
   * 挂账 2017/8/23
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void hover(Long id) {
    GrouponStatistic statistic = repository.findOne(id);
    statistic.setState(2);
    repository.save(statistic);
  }

  /**
   * 发送团购转账成功消息（单转账单）  2017/8/23
   */
  public void sendWxMsg(GrouponStatistic statistic) {

    String s = statistic.getMerchant().getMerchantBank().getBankNumber();
    StringBuffer sb = new StringBuffer(s.substring(s.length() - 4, s.length()));
    for (int i = 0; i < s.length() - 4; i++) {
      sb.insert(0, "*");
    }
    List<String> openIds = new ArrayList<>();
    List<TemporaryMerchantUserShop>
        merchantUserShopList =
        temporaryMerchantUserShopService.findAllByMerchant(statistic.getMerchant());
    for (TemporaryMerchantUserShop t : merchantUserShopList) {
      List<MerchantWeiXinUser>
          merchantWeiXinUsers =
          merchantWeiXinUserService.findMerchantWeiXinUserByMerchantUser(t.getMerchantUser());
      for (MerchantWeiXinUser merchantWeiXinUser : merchantWeiXinUsers) {
        openIds.add(merchantWeiXinUser.getOpenId());
      }
    }

    WxMessageRequest request = new WxMessageRequest();
    request.setFirst("团购结算到帐通知");
    request.setKeyWord1(sb.toString());
    request.setKeyWord2(statistic.getTransferMoney() / 100 + "元");
    request.setKeyWord3(DateFormat.getDateTimeInstance().format(new Date()));
    request.setWxMsgType(WxMsgType.PAY_TRANSFER_SUCCESSED);
    request.setOpenIds(openIds);

    wxMsgMessenger.publishWxMsg(request);

  }

}
