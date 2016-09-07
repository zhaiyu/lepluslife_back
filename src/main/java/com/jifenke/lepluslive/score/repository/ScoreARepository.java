package com.jifenke.lepluslive.score.repository;

import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/3/18.
 */
public interface ScoreARepository extends JpaRepository<ScoreA,Long>{

  ScoreA findByLeJiaUser(LeJiaUser leJiaUser);



}
