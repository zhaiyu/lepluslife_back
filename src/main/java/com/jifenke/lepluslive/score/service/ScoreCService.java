package com.jifenke.lepluslive.score.service;

import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.score.domain.entities.ScoreC;
import com.jifenke.lepluslive.score.repository.ScoreCRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by xf on 17-2-20.
 */
@Service
@Transactional(readOnly = true)
public class ScoreCService {
    @Inject
    private ScoreCRepository scoreCRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ScoreC findScoreBByWeiXinUser(LeJiaUser leJiaUser) {
        return scoreCRepository.findByLeJiaUser(leJiaUser);
    }
}
