package com.jifenke.lepluslive.product.repository;

import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wcg on 16/3/28.
 */
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer> {

  List<ProductDetail> findAllByProduct(Product product);
}
