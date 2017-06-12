package com.jifenke.lepluslive.score.domain.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * ScoreDailyCount
 * 记录每日钱包总额 ：
 * 1.截止至当日 用户持有鼓励金
 * 2.截止至当日 累计发放鼓励金
 * 3.截止至当日 消费已使用鼓励金
 * 4.截止至当日 累计佣金收入
 * 5.截止至当日 用户持有金币
 *
 * @author XF
 * @date 2017/6/8
 */
@Entity
@Table(name = "SCORE_DAILY_TOTAL")
public class ScoreDailyTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long totalScoreA;           //  用户当日 持有鼓励金总计

    private Long totalScoreC;           //  用户当日 持有金币总计

    private Long provideScoreA;         //  截止至当日 累计发放鼓励金

    private Long usedScoreA;            //  截止至当日 消费者已使用的鼓励金总计

    private Long commissionIncome;      //  截止至当日 累计累计 佣金收入

    private Date deadLine;               //  当天统计日期


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalScoreA() {
        return totalScoreA;
    }

    public void setTotalScoreA(Long totalScoreA) {
        this.totalScoreA = totalScoreA;
    }

    public Long getTotalScoreC() {
        return totalScoreC;
    }

    public void setTotalScoreC(Long totalScoreC) {
        this.totalScoreC = totalScoreC;
    }

    public Long getProvideScoreA() {
        return provideScoreA;
    }

    public void setProvideScoreA(Long provideScoreA) {
        this.provideScoreA = provideScoreA;
    }

    public Long getUsedScoreA() {
        return usedScoreA;
    }

    public void setUsedScoreA(Long usedScoreA) {
        this.usedScoreA = usedScoreA;
    }

    public Long getCommissionIncome() {
        return commissionIncome;
    }

    public void setCommissionIncome(Long commissionIncome) {
        this.commissionIncome = commissionIncome;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
}
