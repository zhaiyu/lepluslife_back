package com.jifenke.lepluslive.product.controller.dto;

import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;

import java.util.List;

/**
 * Created by wcg on 16/4/5.
 */
public class LimitProductDto {

  private Product product;
  private List<ScrollPicture> scrollPictureList;
  private List<ProductDetail> productDetailList;
  private List<ProductSpec> productSpecList;
  private List<ScrollPicture> delScrollList;
  private List<ProductDetail> delDetailList;
  private List<ProductSpec> delSpecList;


  public LimitProductDto() {
  }

  public LimitProductDto(Product product,
                         List<ScrollPicture> scrollPictureList,
                         List<ProductDetail> productDetailList,
                         List<ProductSpec> productSpecList,
                         List<ScrollPicture> delScrollList,
                         List<ProductDetail> delDetailList,
                         List<ProductSpec> delSpecList) {
    this.product = product;
    this.scrollPictureList = scrollPictureList;
    this.productDetailList = productDetailList;
    this.productSpecList = productSpecList;
    this.delScrollList = delScrollList;
    this.delDetailList = delDetailList;
    this.delSpecList = delSpecList;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public List<ScrollPicture> getScrollPictureList() {
    return scrollPictureList;
  }

  public void setScrollPictureList(List<ScrollPicture> scrollPictureList) {
    this.scrollPictureList = scrollPictureList;
  }

  public List<ProductDetail> getProductDetailList() {
    return productDetailList;
  }

  public void setProductDetailList(List<ProductDetail> productDetailList) {
    this.productDetailList = productDetailList;
  }

  public List<ProductSpec> getProductSpecList() {
    return productSpecList;
  }

  public List<ScrollPicture> getDelScrollList() {
    return delScrollList;
  }

  public void setDelScrollList(List<ScrollPicture> delScrollList) {
    this.delScrollList = delScrollList;
  }

  public List<ProductDetail> getDelDetailList() {
    return delDetailList;
  }

  public void setDelDetailList(List<ProductDetail> delDetailList) {
    this.delDetailList = delDetailList;
  }

  public List<ProductSpec> getDelSpecList() {
    return delSpecList;
  }

  public void setDelSpecList(List<ProductSpec> delSpecList) {
    this.delSpecList = delSpecList;
  }

  public void setProductSpecList(List<ProductSpec> productSpecList) {
    this.productSpecList = productSpecList;
  }
}
