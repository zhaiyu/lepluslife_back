package com.jifenke.lepluslive.score.service;

import com.jifenke.lepluslive.score.domain.criteria.ScoreCriteria;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.score.domain.entities.ScoreADetail;
import com.jifenke.lepluslive.score.repository.ScoreADetailRepository;
import com.jifenke.lepluslive.score.repository.ScoreARepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.weixin.domain.criteria.MessageCriteria;
import com.jifenke.lepluslive.weixin.domain.entities.WeixinMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/3/18.
 */
@Service
@Transactional(readOnly = true)
public class ScoreAService {

  @Inject
  private ScoreARepository scoreARepository;

  @Inject
  private ScoreADetailRepository scoreADetailRepository;


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ScoreA findScoreAByWeiXinUser(LeJiaUser leJiaUser) {
    return scoreARepository.findByLeJiaUser(leJiaUser);
  }

  public List<ScoreADetail> findAllScoreADetail(WeiXinUser weiXinUser) {
    return scoreADetailRepository
        .findAllByScoreA(findScoreAByWeiXinUser(weiXinUser.getLeJiaUser()));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void paySuccess(LeJiaUser leJiaUser, Long totalPrice) {
    ScoreA scoreA = findScoreAByWeiXinUser(leJiaUser);
    Long payBackScore = (long) Math.ceil((double) (totalPrice * 12) / 100);
    scoreA.setScore(scoreA.getScore() + payBackScore);
    scoreA.setTotalScore(scoreA.getTotalScore() + payBackScore);
    ScoreADetail scoreADetail = new ScoreADetail();
    scoreADetail.setOperate("乐+商城返红包");
    scoreADetail.setScoreA(scoreA);
    scoreADetail.setNumber(payBackScore);
    scoreADetailRepository.save(scoreADetail);
    scoreARepository.save(scoreA);
  }

  public Page findScoreADetailByPage(ScoreCriteria scoreCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return scoreADetailRepository
        .findAll(getWhereClause(scoreCriteria),
                 new PageRequest(scoreCriteria.getOffset() - 1, limit, sort));
  }

  public static Specification<ScoreADetail> getWhereClause(ScoreCriteria scoreCriteria) {
    return new Specification<ScoreADetail>() {
      @Override
      public Predicate toPredicate(Root<ScoreADetail> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (scoreCriteria.getId() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<ScoreA>get("scoreA").get("id"), scoreCriteria.getId()));
        }
        return predicate;
      }
    };
  }
}
