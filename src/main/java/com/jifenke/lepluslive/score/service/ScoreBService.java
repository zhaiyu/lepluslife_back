package com.jifenke.lepluslive.score.service;

import com.jifenke.lepluslive.score.domain.criteria.ScoreCriteria;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.score.domain.entities.ScoreADetail;
import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.score.domain.entities.ScoreBDetail;
import com.jifenke.lepluslive.score.repository.ScoreBDetailRepository;
import com.jifenke.lepluslive.score.repository.ScoreBRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class ScoreBService {

  @Inject
  private ScoreBRepository scoreBRepository;

  @Inject
  private ScoreBDetailRepository scoreBDetailRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ScoreB findScoreBByWeiXinUser(LeJiaUser leJiaUser) {
    return scoreBRepository.findByLeJiaUser(leJiaUser);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ScoreBDetail> findAllScoreBDetail(WeiXinUser weiXinUser) {
    return scoreBDetailRepository
        .findAllByScoreB(findScoreBByWeiXinUser(weiXinUser.getLeJiaUser()));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void paySuccess(LeJiaUser leJiaUser, Long totalScore) {
    ScoreB scoreB = findScoreBByWeiXinUser(leJiaUser);
    if (scoreB.getScore() - totalScore > 0) {
      scoreB.setScore(scoreB.getScore() - totalScore);
      ScoreBDetail scoreBDetail = new ScoreBDetail();
      scoreBDetail.setOperate("乐+商城消费");
      scoreBDetail.setScoreB(scoreB);
      scoreBDetail.setNumber(-totalScore);
      scoreBDetailRepository.save(scoreBDetail);
      scoreBRepository.save(scoreB);
    } else {
      throw new RuntimeException("积分不足");
    }
  }

  public Page findScoreBDetailByPage(ScoreCriteria scoreCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return scoreBDetailRepository
        .findAll(getWhereClause(scoreCriteria),
                 new PageRequest(scoreCriteria.getOffset() - 1, limit, sort));
  }

  public static Specification<ScoreBDetail> getWhereClause(ScoreCriteria scoreCriteria) {
    return new Specification<ScoreBDetail>() {
      @Override
      public Predicate toPredicate(Root<ScoreBDetail> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (scoreCriteria.getId() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<ScoreB>get("scoreB").get("id"), scoreCriteria.getId()));
        }
        return predicate;
      }
    };
  }
}
