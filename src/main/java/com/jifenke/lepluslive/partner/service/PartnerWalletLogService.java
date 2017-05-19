package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.partner.domain.criteria.PartnerWalletLogCriteria;
import com.jifenke.lepluslive.partner.domain.criteria.PartnerWalletOnlineLogCriteria;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnlineLog;
import com.jifenke.lepluslive.partner.repository.PartnerWalletLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineLogRepository;
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
public class PartnerWalletLogService {

    @Inject
    private PartnerWalletLogRepository partnerWalletLogRepository;


    public Page findByPage(PartnerWalletLogCriteria partnerWalletLogCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return partnerWalletLogRepository
                .findAll(getWhereClause(partnerWalletLogCriteria),
                        new PageRequest(partnerWalletLogCriteria.getOffset() - 1, limit, sort));
    }



    public static Specification<PartnerWalletLog> getWhereClause(PartnerWalletLogCriteria partnerWalletLogCriteria) {
        return new Specification<PartnerWalletLog>() {
            @Override
            public Predicate toPredicate(Root<PartnerWalletLog> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (partnerWalletLogCriteria.getCreatedStartDate() != null && !"".equals(partnerWalletLogCriteria.getCreatedStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("createDate"), new Date(partnerWalletLogCriteria.getCreatedStartDate()),
                                    new Date(partnerWalletLogCriteria.getCreatedEndDate())));
                }
                if (partnerWalletLogCriteria.getType() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("type"),
                                    partnerWalletLogCriteria.getType() ));
                }
                if (partnerWalletLogCriteria.getPartnerId() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("partnerId"),
                                    partnerWalletLogCriteria.getPartnerId() ));
                }
                return predicate;
            }
        };
    }


}
