package com.jifenke.lepluslive.product.repository;

import com.jifenke.lepluslive.product.domain.entities.ProductShare;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lss on 2017/5/16.
 */
public interface ProductShareRepository extends JpaRepository<ProductShare, Long> {

    ProductShare findByProductId(Long id);

}
