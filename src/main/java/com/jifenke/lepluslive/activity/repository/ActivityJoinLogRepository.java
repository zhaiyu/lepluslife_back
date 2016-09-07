package com.jifenke.lepluslive.activity.repository;

import com.jifenke.lepluslive.activity.domain.entities.ActivityJoinLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhangwen on 16/8/4.
 */
public interface ActivityJoinLogRepository extends JpaRepository<ActivityJoinLog, String> {

  Page findAll(Specification<ActivityJoinLog> whereClause, Pageable pageRequest);
}
