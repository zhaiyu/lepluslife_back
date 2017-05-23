package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.product.domain.entities.ProductShare;
import com.jifenke.lepluslive.product.repository.ProductShareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lss on 2017/5/16.
 */
@Service
@Transactional(readOnly = false)
public class ProductShareService {
    @Inject
    private ProductShareRepository productShareRepository;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ProductShare findById(Long id) {
        return productShareRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ProductShare findByProductId(Long id) {
        return productShareRepository.findByProductId(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOne(ProductShare productShare) {
        productShareRepository.save(productShare);
    }


}
