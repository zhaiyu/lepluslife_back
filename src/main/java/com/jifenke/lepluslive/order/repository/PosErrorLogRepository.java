package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.domain.entities.PosErrorLog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wcg on 2016/10/31.
 */
public interface PosErrorLogRepository extends JpaRepository<PosErrorLog,Long> {
    List<PosErrorLog> findByPosDailyBill(PosDailyBill posDailyBill);
}
