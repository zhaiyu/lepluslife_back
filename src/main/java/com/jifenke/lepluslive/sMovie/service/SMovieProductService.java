package com.jifenke.lepluslive.sMovie.service;

import com.jifenke.lepluslive.sMovie.domain.criteria.SMovieProductCriteria;
import com.jifenke.lepluslive.sMovie.domain.entities.SMovieProduct;
import com.jifenke.lepluslive.sMovie.repository.SMovieProductRepository;
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

/**
 * Created by lss on 2017/5/4.
 */
@Service
@Transactional(readOnly = false)
public class SMovieProductService {
    @Inject
    private SMovieProductRepository sMovieProductRepository;

    public void saveOne(SMovieProduct sMovieProduct) {
        sMovieProductRepository.save(sMovieProduct);
    }

    public void editOne(SMovieProduct sMovieProduct) {
        SMovieProduct sMovieProduct1 = sMovieProductRepository.findOne(sMovieProduct.getId());
        sMovieProductRepository.delete(sMovieProduct1);
        sMovieProductRepository.save(sMovieProduct);
    }

    public void changeSMovieProductState(Long id) {
        SMovieProduct sMovieProduct = sMovieProductRepository.findOne(id);
        SMovieProduct sMovieProduct1 = new SMovieProduct();

        if (sMovieProduct.getState() == 1) {
            sMovieProduct1.setState(0);
        }
        if (sMovieProduct.getState() == 0) {
            sMovieProduct1.setState(1);
        }
        sMovieProduct1.setPicture(sMovieProduct.getPicture());
        sMovieProduct1.setCostPrice(sMovieProduct.getCostPrice());
        sMovieProduct1.setDateCreated(sMovieProduct.getDateCreated());
        sMovieProduct1.setIntroduce(sMovieProduct.getIntroduce());
        sMovieProduct1.setName(sMovieProduct.getName());
        sMovieProduct1.setPayBackA(sMovieProduct.getPayBackA());
        sMovieProduct1.setPrice(sMovieProduct.getPrice());
        sMovieProduct1.setSid(sMovieProduct.getSid());
        sMovieProduct1.setToMerchant(sMovieProduct.getToMerchant());
        sMovieProduct1.setToPartner(sMovieProduct.getToPartner());
        sMovieProduct1.setToPartnerManager(sMovieProduct.getToPartnerManager());
        sMovieProductRepository.delete(sMovieProduct);
        sMovieProductRepository.save(sMovieProduct1);
    }


    public Page findSMovieProductByPage(SMovieProductCriteria sMovieProductCriteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "sid");
        return sMovieProductRepository
                .findAll(getWhereClause(sMovieProductCriteria),
                        new PageRequest(sMovieProductCriteria.getOffset() - 1, limit, sort));
    }

    public static Specification<SMovieProduct> getWhereClause(SMovieProductCriteria sMovieProductCriteria) {
        return new Specification<SMovieProduct>() {
            @Override
            public Predicate toPredicate(Root<SMovieProduct> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (sMovieProductCriteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("state"), sMovieProductCriteria.getState())
                    );
                }
                return predicate;
            }
        };
    }


    public SMovieProduct findById(Long id) {
        return sMovieProductRepository.findOne(id);
    }
}
