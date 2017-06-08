package com.jifenke.lepluslive.scoreAAccount.repository;

import com.jifenke.lepluslive.scoreAAccount.domain.entities.ScoreAAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/12.
 */
public interface ScoreAAccountRepository extends JpaRepository<ScoreAAccount,Long> {

  Page findAll(Specification<ScoreAAccount> whereClause, Pageable pageRequest);

  @Query(value = "SELECT  DATE_FORMAT(date_created,'%Y%m%d') days FROM scorea_detail GROUP BY days", nativeQuery = true)
  List<String> findAllFromDetail();

  @Query(value = "SELECT * FROM(SELECT  DATE_FORMAT(date_created,'%Y%m%d') days FROM scorea_detail GROUP BY days)aa WHERE days=?1", nativeQuery = true)
  List<String> findOneDayFromDetail(String day);





  @Query(value = "SELECT SUM(true_score-lj_commission+true_pay_commission) FROM off_line_order where  DATE_FORMAT(complete_date,'%Y%m%d')=?1 and state=1", nativeQuery = true)
  Long findSettlementAmount(String o);





  @Query(value = "SELECT num FROM (SELECT  DATE_FORMAT(date_created,'%Y%m%d') days,SUM(number)num FROM scorea_detail WHERE number>=0 GROUP BY days)aa WHERE days=?1", nativeQuery = true)
  Long findIssueScoreaByday(String day);

  @Query(value = "SELECT sum(true_score) from off_line_order where state=1 AND DATE_FORMAT(complete_date,'%Y%m%d')=?1", nativeQuery = true)
  Long findUseScoreaByday(String day);

  @Query(value = "SELECT num FROM(SELECT  DATE_FORMAT(complete_date,'%Y%m%d') days,SUM(lj_commission)num FROM off_line_order GROUP BY days)aa WHERE days=?1", nativeQuery = true)
  Long findfindLjCommissionByday(String day);

  @Query(value = "SELECT num FROM(SELECT  DATE_FORMAT(create_date,'%Y%m%d') days,SUM(share_money)num FROM off_line_order_share GROUP BY days)aa WHERE days=?1", nativeQuery = true)
  Long findShareMoneyByday(String day);


  @Query(value = "SELECT SUM(score) FROM scorea", nativeQuery = true)
  Long findPresentHoldScorea();
  @Query(value = "SELECT SUM(score) FROM scorea ", nativeQuery = true)
  Long findPresentHoldScoreaByDate(Date start, Date end);

  @Query(value = "SELECT SUM(total_score) FROM scorea ", nativeQuery = true)
  Long findIssueScorea();

  @Query(value = "SELECT SUM(number) FROM scorea_detail WHERE number<0", nativeQuery = true)
  Long findUseScorea();

  @Query(value = "SELECT  SUM(lj_commission) FROM off_line_order WHERE  rebate_way=1", nativeQuery = true)
  Long findLjCommission();

  @Query(value = "SELECT SUM(share_money) FROM off_line_order_share", nativeQuery = true)
  Long findShareMoney();


  /**
   * 用户持有金币数
   */
  @Query(value = "SELECT SUM(total_score) FROM scorec ", nativeQuery = true)
  Long findPresentHoldScorec();

  @Query(value = "SELECT origin,SUM(number) from scorea_detail  WHERE number>0 AND date_created between ?1 and ?2 GROUP BY origin", nativeQuery = true)
  List<Object[]> findScoreaDistribution(String startDate,String endDate);

}
