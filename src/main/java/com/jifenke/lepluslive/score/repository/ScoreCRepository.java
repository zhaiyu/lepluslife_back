package com.jifenke.lepluslive.score.repository;

import com.jifenke.lepluslive.score.domain.entities.ScoreC;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

/**
 * Created by xf on 17-2-20.
 */
public interface ScoreCRepository extends JpaRepository<ScoreC, Long> {
    ScoreC findByLeJiaUser(LeJiaUser leJiaUser);

    Optional<List<ScoreC>> findByScoreGreaterThan(Long i);
}
