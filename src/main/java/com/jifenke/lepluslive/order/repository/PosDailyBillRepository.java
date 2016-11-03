package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wcg on 2016/10/27.
 */
public interface PosDailyBillRepository extends JpaRepository<PosDailyBill, Long> {

    @Query(value = "select * from pos_daily_bill limit ?1,?2", nativeQuery = true)
    List<PosDailyBill> findByPage(Integer startIndex, Integer pageSize);

}
