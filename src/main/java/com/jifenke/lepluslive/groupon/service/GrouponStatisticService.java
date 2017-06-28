package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.entities.GrouponStatistic;
import com.jifenke.lepluslive.groupon.repository.GrouponStatisticRepository;
import com.jifenke.lepluslive.merchant.domain.entities.BankName;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;

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

  @Inject
  private MerchantService merchantService;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void statistic(Date start, Date end) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String starts = sdf.format(start);
    String ends = sdf.format(end);
//    String starts = "2016-10-18 00:32:16";
//    String ends = "2016-11-18 23:32:16";
    StringBuffer sql = new StringBuffer();
    sql.append(
        "select sum(trasnfer_money),merchant_id,count(*) from groupon_code where state = 1 and check_date between '");
    sql.append(starts);
    sql.append("' and '");
    sql.append(ends);
    sql.append("' group by merchant_id");
    List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
    for (Object[] obj : resultList) {
      Merchant merchant = merchantService.findMerchantById(Long.parseLong(obj[1].toString()));
      GrouponStatistic grouponStatistic = new GrouponStatistic();
      grouponStatistic.setCheck(Long.parseLong(obj[2].toString()));
      grouponStatistic.setTransferMoney(Long.parseLong(obj[0].toString()));
      grouponStatistic.setBalanceDate(end);
      grouponStatisticRepository.save(grouponStatistic);
    }

  }


}
