package com.jifenke.lepluslive.score.repository;

import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/3/18.
 */
public interface ScoreBRepository  extends JpaRepository<ScoreB,Long>{

  ScoreB findByLeJiaUser(LeJiaUser leJiaUser);
}
