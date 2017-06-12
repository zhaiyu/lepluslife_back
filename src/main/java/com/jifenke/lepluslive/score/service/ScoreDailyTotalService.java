package com.jifenke.lepluslive.score.service;

import com.jifenke.lepluslive.score.domain.entities.ScoreDailyTotal;
import com.jifenke.lepluslive.score.repository.ScoreDailyTotalRepository;
import com.jifenke.lepluslive.scoreAAccount.repository.ScoreAAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

/**
 * ScoreDailyTotalService
 *
 * @author XF
 * @date 2017/6/8
 */
@Service
@Transactional(readOnly = true)
public class ScoreDailyTotalService {

    @Inject
    private ScoreAAccountRepository scoreAAccountRepository;
    @Inject
    private ScoreDailyTotalRepository dailyTotalRepository;

    /**
     *  每日凌晨保存当日的统计数据
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void saveScoreDailyTotal() {
        Long totalScorea = scoreAAccountRepository.findPresentHoldScorea();
        Long totalScorec = scoreAAccountRepository.findPresentHoldScorec();
        Long usedScorea = scoreAAccountRepository.findUseScorea();
        Long commissionIncome = scoreAAccountRepository.findLjCommission();
        Long provideScorea = scoreAAccountRepository.findIssueScorea();
        ScoreDailyTotal dailyTotal = new ScoreDailyTotal();
        dailyTotal.setTotalScoreA(totalScorea);
        dailyTotal.setTotalScoreC(totalScorec);
        dailyTotal.setUsedScoreA(usedScorea);
        dailyTotal.setCommissionIncome(commissionIncome);
        dailyTotal.setProvideScoreA(provideScorea);
        dailyTotal.setDeadLine(new Date());
        dailyTotalRepository.save(dailyTotal);
    }

    /**
     *  根据截止日期查询当日统计数据
     */
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public ScoreDailyTotal findByDeadLine(Date date) {
       if(date!=null) {
           Date start,end = null;
           Calendar cla = Calendar.getInstance();
           cla.setTime(date);
           cla.add(Calendar.MINUTE,-3);
           start = cla.getTime();
           cla  =  Calendar.getInstance();
           cla.setTime(date);
           cla.add(Calendar.MINUTE,+3);
           end = cla.getTime();
           return dailyTotalRepository.findByDeadLine(start,end);
       }else {
           return  null;
       }
    }
}
