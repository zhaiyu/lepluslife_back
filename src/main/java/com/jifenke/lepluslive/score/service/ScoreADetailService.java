package com.jifenke.lepluslive.score.service;

import com.jifenke.lepluslive.score.domain.criteria.ScoreDetailCriteria;
import com.jifenke.lepluslive.score.domain.entities.ScoreADetail;
import com.jifenke.lepluslive.score.repository.ScoreADetailRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by lss on 2016/9/21.
 */
@Service
@Transactional(readOnly = true)
public class ScoreADetailService {

  @Inject
  private ScoreADetailRepository scoreADetailRepository;


  public Specification<ScoreADetail> getWhereClause(ScoreDetailCriteria scoreDetailCriteria) {
    return new Specification<ScoreADetail>() {
      @Override
      public Predicate toPredicate(Root<ScoreADetail> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (scoreDetailCriteria.getDateCreated() != null && (!"".equals(
            scoreDetailCriteria.getDateCreated()))) {
          predicate.getExpressions().add(
              cb.between(r.get("dateCreated"),
                         new Date(scoreDetailCriteria.getDateCreated() + " " + "00:00:00"),
                         new Date(scoreDetailCriteria.getDateCreated() + " " + "23:59:59")));
        }
        if (scoreDetailCriteria.getOrigin() != null && (!""
            .equals(scoreDetailCriteria.getOrigin()))) {
          predicate.getExpressions().add(
              cb.equal(r.get("origin"),
                       scoreDetailCriteria.getOrigin()));
        }
        return predicate;
      }
    };
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Integer findscoreADetailcount(String dc, Integer ogItg) {
    return scoreADetailRepository.findscoreADetailcount(dc, ogItg);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Integer findscoreADetailcount(String dc) {
    return scoreADetailRepository.findscoreADetailcount(dc);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findScoreADetailByPage(ScoreDetailCriteria scoreDetailCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
    return scoreADetailRepository.findAll(getWhereClause(scoreDetailCriteria),
                                          new PageRequest(scoreDetailCriteria.getOffset() - 1,
                                                          limit, sort));
  }

}
