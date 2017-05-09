package com.jifenke.lepluslive.sMovie.service;

import com.jifenke.lepluslive.sMovie.domain.criteria.SMovieOrderCriteria;
import com.jifenke.lepluslive.sMovie.domain.entities.SMovieOrder;
import com.jifenke.lepluslive.sMovie.repository.SMovieOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by lss on 17-5-2.
 */
@Service
@Transactional(readOnly = true)
public class SMovieOrderService {

    @Inject
    private SMovieOrderRepository sMovieOrderRepository;

    public Page findOrderByPage(SMovieOrderCriteria sMovieOrderCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "dateCreated");
        return sMovieOrderRepository
                .findAll(getWhereClause(sMovieOrderCriteria),
                        new PageRequest(sMovieOrderCriteria.getOffset() - 1, limit, sort));
    }

    public static Specification<SMovieOrder> getWhereClause(SMovieOrderCriteria sMovieOrderCriteria) {
        return new Specification<SMovieOrder>() {
            @Override
            public Predicate toPredicate(Root<SMovieOrder> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (sMovieOrderCriteria.getCreatedStartDate() != null && !"".equals(sMovieOrderCriteria.getCreatedStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("dateCreated"), new Date(sMovieOrderCriteria.getCreatedStartDate()),
                                    new Date(sMovieOrderCriteria.getCreatedEndDate())));
                }

                if (sMovieOrderCriteria.getCompletedStartDate() != null && !"".equals(sMovieOrderCriteria.getCompletedStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("dateCompleted"), new Date(sMovieOrderCriteria.getCompletedStartDate()),
                                    new Date(sMovieOrderCriteria.getCompletedEndDate())));
                }

                if (sMovieOrderCriteria.getDateUsedStartDate() != null && !"".equals(sMovieOrderCriteria.getDateUsedStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("dateUsed"), new Date(sMovieOrderCriteria.getDateUsedStartDate()),
                                    new Date(sMovieOrderCriteria.getDateUsedEndDate())));
                }

                if (sMovieOrderCriteria.getOrderSid() != null && !"".equals(sMovieOrderCriteria.getOrderSid())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("orderSid"), sMovieOrderCriteria.getOrderSid())
                    );
                }
                if (sMovieOrderCriteria.getPhoneNumber() != null && !"".equals(sMovieOrderCriteria.getPhoneNumber())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("leJiaUser").get("phoneNumber"), sMovieOrderCriteria.getPhoneNumber())
                    );
                }
                if (sMovieOrderCriteria.getUserSid() != null && !"".equals(sMovieOrderCriteria.getUserSid())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("leJiaUser").get("userSid"), sMovieOrderCriteria.getUserSid())
                    );
                }
                if (sMovieOrderCriteria.getState() != null && !"".equals(sMovieOrderCriteria.getState())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("state"), sMovieOrderCriteria.getState())
                    );
                }
                if (sMovieOrderCriteria.getsMovieTerminalId() != null && !"".equals(sMovieOrderCriteria.getsMovieTerminalId()) && !"nullValue".equals(sMovieOrderCriteria.getsMovieTerminalId())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("sMovieTerminal").get("id"), Long.valueOf(sMovieOrderCriteria.getsMovieTerminalId()))
                    );
                }
                return predicate;
            }
        };
    }

}
