package com.jifenke.lepluslive.score.service;

import com.jifenke.lepluslive.score.domain.criteria.ScoreCriteria;
import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.score.domain.entities.ScoreC;
import com.jifenke.lepluslive.score.domain.entities.ScoreCDetail;
import com.jifenke.lepluslive.score.repository.ScoreCDetailRepository;
import com.jifenke.lepluslive.score.repository.ScoreCRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by xf on 17-2-20.
 */
@Service
@Transactional(readOnly = true)
public class ScoreCService {
    @Inject
    private ScoreCRepository scoreCRepository;

    @Inject
    private ScoreCDetailRepository scoreCDetailRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ScoreC findScoreCByWeiXinUser(LeJiaUser leJiaUser) {
        return scoreCRepository.findByLeJiaUser(leJiaUser);
    }

    public Page findScoreCDetailByPage(ScoreCriteria scoreCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
        return scoreCDetailRepository
                .findAll(getWhereClause(scoreCriteria),
                        new PageRequest(scoreCriteria.getOffset() - 1, limit, sort));
    }

    public static Specification<ScoreCDetail> getWhereClause(ScoreCriteria scoreCriteria) {
        return new Specification<ScoreCDetail>() {
            @Override
            public Predicate toPredicate(Root<ScoreCDetail> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (scoreCriteria.getId() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.<ScoreB>get("ScoreC").get("id"), scoreCriteria.getId()));
                }
                return predicate;
            }
        };
    }
}