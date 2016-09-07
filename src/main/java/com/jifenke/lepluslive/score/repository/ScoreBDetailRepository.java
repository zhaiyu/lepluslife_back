package com.jifenke.lepluslive.score.repository;

import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.score.domain.entities.ScoreBDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wcg on 16/3/18.
 */
public interface ScoreBDetailRepository extends JpaRepository<ScoreBDetail, Long> {

  List<ScoreBDetail> findAllByScoreB(ScoreB scoreB);

  Page findAll(Specification<ScoreBDetail> whereClause, Pageable pageRequest);
}
