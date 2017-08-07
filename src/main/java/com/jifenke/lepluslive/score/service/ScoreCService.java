package com.jifenke.lepluslive.score.service;

import com.jifenke.lepluslive.score.domain.criteria.ScoreCriteria;
import com.jifenke.lepluslive.score.domain.criteria.ScoreDetailCriteria;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.score.domain.entities.ScoreADetail;
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
import java.util.Date;
import java.util.List;

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

    /**
     * 保存用户金币  2016/12/26
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveScore(ScoreC scoreC) {
        scoreCRepository.save(scoreC);
    }

    /**
     * 保存用户金币记录  2016/12/26
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDetail(ScoreCDetail detail) {
        scoreCDetailRepository.save(detail);
    }

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
                            cb.equal(r.<ScoreB>get("scoreC").get("id"), scoreCriteria.getId()));
                }
                return predicate;
            }
        };
    }

    public List<ScoreC> monitorScoreC() {
        return scoreCRepository.findByScoreGreaterThan(10000L).get();
    }


    public long scoreCSum() {
        return scoreCRepository.findScoreCSum();
    }

    public long useScoreCSum() {
        return scoreCRepository.findUseScoreCSum();
    }

    public long sendScoreCSum() {
        return scoreCRepository.findSendScoreCSum();
    }



    public List<Object[]> findScoreCStatistics(String startDate,String endDate) {
        return scoreCRepository.findScoreCStatistics(startDate,endDate);
    }


    public long findSendScoreCByDate(String startDate,String endDate) {
        return scoreCRepository.findSendScoreCByDate(startDate,endDate);
    }

    public long findUseScoreCByDate(String startDate,String endDate) {
        return scoreCRepository.findUseScoreCByDate(startDate,endDate);
    }


//lss 金币详情
    public Page findScoreCDetailAll(ScoreDetailCriteria scoreDetailCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
        return scoreCDetailRepository
                .findAll(getWhereClause1(scoreDetailCriteria),
                        new PageRequest(scoreDetailCriteria.getOffset() - 1, limit, sort));
    }

    public static Specification<ScoreCDetail> getWhereClause1(ScoreDetailCriteria scoreDetailCriteria) {
        return new Specification<ScoreCDetail>() {
            @Override
            public Predicate toPredicate(Root<ScoreCDetail> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (scoreDetailCriteria.getOrigin() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.<ScoreCDetail>get("origin"), scoreDetailCriteria.getOrigin()));
                }
                predicate.getExpressions().add(
                        cb.between(r.get("dateCreated"), new Date(scoreDetailCriteria.getDateCreated()+" 00:00:00"),
                                new Date(scoreDetailCriteria.getDateCreated()+" 23:59:59")));

                return predicate;
            }
        };
    }

}
