package com.jifenke.lepluslive.scoreAAccount.service;

import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.order.repository.OffLineOrderShareRepository;
import com.jifenke.lepluslive.score.repository.ScoreADetailRepository;
import com.jifenke.lepluslive.scoreAAccount.domain.criteria.ScoreAAccountCriteria;
import com.jifenke.lepluslive.scoreAAccount.domain.entities.ScoreAAccount;
import com.jifenke.lepluslive.scoreAAccount.repository.ScoreAAccountRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Administrator on 2016/9/12.
 */
@Service
@Transactional(readOnly = true)
public class ScoreAAccountService {

  @Inject
  private ScoreAAccountRepository scoreAAccountRepository;

  @Inject
  private ScoreADetailRepository scoreADetailRepository;

  @Inject
  private OffLineOrderShareRepository offLineOrderShareRepository;


  @Inject
  private OffLineOrderRepository offLineOrderRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)

  public static Specification<ScoreAAccount> getWhereClause(ScoreAAccountCriteria scoreAAccountCriteria) {
    return new Specification<ScoreAAccount>() {
      @Override
      public Predicate toPredicate(Root<ScoreAAccount> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (scoreAAccountCriteria.getStartDate() != null && scoreAAccountCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("changeDate"), new Date(scoreAAccountCriteria.getStartDate()),
                         new Date(scoreAAccountCriteria.getEndDate())));
        }
        return predicate;
      }
    };
  }



  public Page findScoreAAccountByPage(ScoreAAccountCriteria scoreAAccountCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "changeDate");
    return scoreAAccountRepository
        .findAll(getWhereClause(scoreAAccountCriteria),
                 new PageRequest(scoreAAccountCriteria.getOffset() - 1, limit, sort));
  }


  //添加所有的红包账户

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void addAllScoreAAccount() {
    List<String> list = scoreAAccountRepository.findAllFromDetail();
    addScoreAAccountData(list);
  }
//添加一天的红包账户

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void addOneDayScoreAAccountData() {
    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
    Date beginDate = new Date();
    Calendar date = Calendar.getInstance();
    date.setTime(beginDate);
    date.set(Calendar.DATE, date.get(Calendar.DATE) - 2);

    try {
      Date endDate = dft.parse(dft.format(date.getTime()));
      String endDateString=dft.format(endDate);
      String[] stringArray = endDateString.split("-");
      String dateStr=stringArray[0]+stringArray[1]+stringArray[2];
      List<String> list = scoreAAccountRepository.findOneDayFromDetail(dateStr);
      addScoreAAccountData(list);
    } catch (ParseException e) {
      e.printStackTrace();
    }



  }
public void addScoreAAccountData(List<String> list) {
  for (String dataStrFromSql : list) {
    ScoreAAccount scoreAAccount = new ScoreAAccount();
    //日期
    String s1 = dataStrFromSql.substring(0, 4);
    String s2 = dataStrFromSql.substring(4, 6);
    String s3 = dataStrFromSql.substring(6);
    String dateStr1 = s1 + "-" + s2 + "-" + s3;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date date = sdf.parse(dateStr1);
      scoreAAccount.setChangeDate(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String dateStr2 = dataStrFromSql;
    //发红包
    Long issueScorea = 0L;
    if (scoreAAccountRepository.findIssueScoreaByday(dateStr2) == null) {
      scoreAAccount.setIssuedScoreA(issueScorea);
    } else {
      scoreAAccount.setIssuedScoreA(scoreAAccountRepository.findIssueScoreaByday(dateStr2));
    }
    //用红包
    Long useScoreA = 0L;
    if (scoreAAccountRepository.findUseScoreaByday(dateStr2) == null) {
      scoreAAccount.setUseScoreA(useScoreA);
    } else {
      useScoreA = -scoreAAccountRepository.findUseScoreaByday(dateStr2);
      scoreAAccount.setUseScoreA(useScoreA);
    }
    //佣金
    Long ljCommission = 0L;
    if (scoreAAccountRepository.findfindLjCommissionByday(dateStr2) == null) {
      scoreAAccount.setCommissionIncome(ljCommission);
    } else {
      scoreAAccount
          .setCommissionIncome(scoreAAccountRepository.findfindLjCommissionByday(dateStr2));
    }
    //分润
    Long jfkShare = 0L;
    if (scoreAAccountRepository.findShareMoneyByday(dateStr2) == null) {
      scoreAAccount.setJfkShare(jfkShare);
    } else {
      scoreAAccount.setJfkShare(scoreAAccountRepository.findShareMoneyByday(dateStr2));
    }
    //应结算金额
    Long settlementAmount = 0L;
    List<Object[]> objects = scoreAAccountRepository.findSettlementAmount(dateStr2);
    if (objects.size() == 1) {
      Long transferPrice = 0L;
      Long transferFromTruePay = 0L;
      if (objects.get(0)[1] != null) {
        transferPrice = Long.parseLong(objects.get(0)[1].toString());
      }
      if (objects.get(0)[2] != null) {
        transferFromTruePay = Long.parseLong(objects.get(0)[2].toString());
      }
      settlementAmount = transferPrice - transferFromTruePay;
      scoreAAccount.setSettlementAmount(settlementAmount);
    } else {
      scoreAAccount.setSettlementAmount(settlementAmount);
    }
    scoreAAccountRepository.save(scoreAAccount);
  }
}

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long findPresentHoldScorea() {
    return  scoreAAccountRepository.findPresentHoldScorea();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long findIssueScorea() {
    return  scoreAAccountRepository.findIssueScorea();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long findUseScorea() {
    return  scoreAAccountRepository.findUseScorea();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long findLjCommission() {
    return  scoreAAccountRepository.findLjCommission();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long findShareMoney() {
    return  scoreAAccountRepository.findShareMoney();
  }


}
