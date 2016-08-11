package com.jifenke.lepluslive.activity.service;

import com.jifenke.lepluslive.activity.domain.entities.ActivityJoinLog;
import com.jifenke.lepluslive.activity.repository.ActivityJoinLogRepository;
import com.jifenke.lepluslive.user.domain.criteria.LeJiaUserCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 参加活动记录 Created by zhangwen 16/8/8.
 */
@Service
@Transactional(readOnly = true)
public class ActivityJoinLogService {

  @Inject
  private ActivityJoinLogRepository activityJoinLogRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findByPage(Long id, LeJiaUserCriteria userCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return activityJoinLogRepository.findAll(getWhereClause(id, userCriteria),
                                             new PageRequest(userCriteria.getOffset() - 1, limit,
                                                             sort));
  }

  public Specification<ActivityJoinLog> getWhereClause(Long id, LeJiaUserCriteria userCriteria) {

    return new Specification<ActivityJoinLog>() {
      @Override
      public Predicate toPredicate(Root<ActivityJoinLog> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (id != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("activityId"), id));
        }

        if (userCriteria.getStartDate() != null && (!""
            .equals(userCriteria.getStartDate()))) { //创建时间
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(userCriteria.getStartDate()),
                         new Date(userCriteria.getEndDate())));
        }

        return predicate;
      }
    };
  }

}
