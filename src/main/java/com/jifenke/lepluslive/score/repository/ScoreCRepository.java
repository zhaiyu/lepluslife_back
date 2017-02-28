package com.jifenke.lepluslive.score.repository;

import com.jifenke.lepluslive.score.domain.entities.ScoreC;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xf on 17-2-20.
 */
public interface ScoreCRepository extends JpaRepository<ScoreC, Long> {
    ScoreC findByLeJiaUser(LeJiaUser leJiaUser);
}
