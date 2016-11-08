package com.jifenke.lepluslive.activity.repository;

import com.jifenke.lepluslive.activity.domain.entities.ActivityPhoneRule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhangwen on 16/10/26.
 */
public interface ActivityPhoneRuleRepository extends JpaRepository<ActivityPhoneRule, Long> {

  List<ActivityPhoneRule> findByStateOrderByLastUpdateDesc(Integer state);
}
