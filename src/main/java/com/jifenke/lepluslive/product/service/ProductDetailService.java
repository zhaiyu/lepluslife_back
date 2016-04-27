package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.repository.ProductDetailRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.inject.Inject;

/**
 * Created by wcg on 16/4/6.
 */
@Service
@Transactional(readOnly = true)
public class ProductDetailService {

  @Inject
  private ProductDetailRepository productDetailRepository;


  public ProductDetail findProductDetailById(Integer id) {

    return productDetailRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editProductDetail(ProductDetail productDetail) {

    ProductDetail origin = null;
    if (productDetail.getId() != null) {
      origin = productDetailRepository.findOne(productDetail.getId());
    } else {
      origin = new ProductDetail();
      try {
        URL url = new URL(productDetail.getPicture());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedImage buff = ImageIO.read( conn.getInputStream());
        origin.setWidth(buff.getWidth());
        origin.setHeight(buff.getHeight());
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    origin.setDescription(productDetail.getDescription());
    origin.setPicture(productDetail.getPicture());
    origin.setSid(productDetail.getSid());
    origin.setProduct(productDetail.getProduct());
    productDetailRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteProductDetail(Integer id) {
    productDetailRepository.delete(id);
  }
}
