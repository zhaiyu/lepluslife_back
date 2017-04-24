package com.jifenke.lepluslive.yinlian.service;

import com.jifenke.lepluslive.yinlian.domain.criteria.UnionBankCardCriteria;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionBankCard;
import com.jifenke.lepluslive.yinlian.repository.UnionBankCardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by lss on 17-4-17.
 */
@Service
@Transactional(readOnly = true)
public class UnionBankCardService {
    @Inject
    private UnionBankCardRepository unionBankCardRepository;

    public static Specification<UnionBankCard> getWhereClause(UnionBankCardCriteria unionBankCardCriteria) {
        return new Specification<UnionBankCard>() {
            @Override
            public Predicate toPredicate(Root<UnionBankCard> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (unionBankCardCriteria.getStartDate() != null && !"".equals(unionBankCardCriteria.getStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("createDate"), new Date(unionBankCardCriteria.getStartDate()),
                                    new Date(unionBankCardCriteria.getEndDate())));
                }

                if (unionBankCardCriteria.getNumber() != null && !"".equals(unionBankCardCriteria.getNumber())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("number"),
                                    unionBankCardCriteria.getNumber()));
                }

                if (unionBankCardCriteria.getUserSid() != null && !"".equals(unionBankCardCriteria.getUserSid())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("userSid"),
                                    unionBankCardCriteria.getUserSid()));
                }

                if (unionBankCardCriteria.getRegisterWay() != null && !"".equals(unionBankCardCriteria.getRegisterWay())) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("registerWay"),
                                    unionBankCardCriteria.getRegisterWay()));
                }


                return predicate;
            }
        };
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOne(UnionBankCard unionBankCard) {
        unionBankCardRepository.save(unionBankCard);
    }

    public Page findUnionBankCardByPage(UnionBankCardCriteria unionBankCardCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return unionBankCardRepository
                .findAll(getWhereClause(unionBankCardCriteria),
                        new PageRequest(unionBankCardCriteria.getOffset() - 1, limit, sort));
    }



    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UnionBankCard findUnionBankCardByNumber(String number) {
      return   unionBankCardRepository.findUnionBankCardByNumber(number);
    }


}
