package com.jifenke.lepluslive.product.repository;

import com.jifenke.lepluslive.product.domain.entities.ProductSecKillTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 秒杀时段
 * Created by tqy on 2017/1/11.
 */
public interface ProductSecKillTimeRepository extends JpaRepository<ProductSecKillTime, Integer> {

  Page findAll(Specification<ProductSecKillTime> specification, Pageable pageRequest);

  List<ProductSecKillTime> findProductSecKillTimeBySecKillDateAndStartTimeAndEndTime(String secKillDate, String startTime, String endTime);

}
