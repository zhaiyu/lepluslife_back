package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.entities.GrouponStatistic;
import com.jifenke.lepluslive.groupon.repository.GrouponStatisticRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by wcg on 2017/6/27.
 */
@Service
@Transactional(readOnly = true)
public class GrouponStatisticService {

  @Inject
  private EntityManager entityManager;

  @Inject
  private GrouponStatisticRepository grouponStatisticRepository;

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
    grouponStatistic.setCheck(Long.parseLong(obj[2].toString()));
    grouponStatistic.setTransferMoney(Long.parseLong(obj[0].toString()));
    grouponStatistic.setBalanceDate(end);
    grouponStatistic.setCommission(Long.parseLong(obj[4].toString()));
    grouponStatistic.setTotalMoney(Long.parseLong(obj[3].toString()));
    grouponStatisticRepository.save(grouponStatistic);
  }


}
