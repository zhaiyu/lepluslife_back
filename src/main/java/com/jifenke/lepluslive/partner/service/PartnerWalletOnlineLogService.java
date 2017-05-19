package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.partner.domain.criteria.PartnerWalletOnlineLogCriteria;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnlineLog;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineLogRepository;
import com.jifenke.lepluslive.sMovie.domain.criteria.SMovieOrderCriteria;
import com.jifenke.lepluslive.sMovie.domain.entities.SMovieOrder;
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
 * Created by lss on 2017/5/17.
 */
@Service
@Transactional(readOnly = true)
public class PartnerWalletOnlineLogService {



    @Inject
    private PartnerWalletOnlineLogRepository partnerWalletOnlineLogRepository;


    public Page findByPage(PartnerWalletOnlineLogCriteria partnerWalletOnlineLogCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return partnerWalletOnlineLogRepository
                .findAll(getWhereClause(partnerWalletOnlineLogCriteria),
                        new PageRequest(partnerWalletOnlineLogCriteria.getOffset() - 1, limit, sort));
    }


    public static Specification<PartnerWalletOnlineLog> getWhereClause(PartnerWalletOnlineLogCriteria partnerWalletOnlineLogCriteria) {
        return new Specification<PartnerWalletOnlineLog>() {
            @Override
            public Predicate toPredicate(Root<PartnerWalletOnlineLog> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (partnerWalletOnlineLogCriteria.getCreatedStartDate() != null && !"".equals(partnerWalletOnlineLogCriteria.getCreatedStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("createDate"), new Date(partnerWalletOnlineLogCriteria.getCreatedStartDate()),
                                    new Date(partnerWalletOnlineLogCriteria.getCreatedEndDate())));
                }



                if (partnerWalletOnlineLogCriteria.getType() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("type"),
                                    partnerWalletOnlineLogCriteria.getType() ));
                }

                if (partnerWalletOnlineLogCriteria.getPartnerId() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("partnerId"),
                                    partnerWalletOnlineLogCriteria.getPartnerId() ));
                }

                return predicate;
            }
        };
    }



}
