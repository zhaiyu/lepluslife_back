package com.jifenke.lepluslive.score.repository;

import com.jifenke.lepluslive.score.domain.entities.ScoreDailyTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * ScoreDailyTotalRepository
 *
 * @author XF
 * @date 2017/6/8
 */
public interface ScoreDailyTotalRepository extends JpaRepository<ScoreDailyTotal,Long> {
    @Query(value="select * from score_daily_total where dead_line between ?1 and ?2 ",nativeQuery =true)
    ScoreDailyTotal findByDeadLine(Date start,Date end);
}
