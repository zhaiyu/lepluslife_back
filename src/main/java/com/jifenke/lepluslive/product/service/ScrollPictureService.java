package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.repository.ScrollPictureRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/11.
 */
@Service
@Transactional(readOnly = true)
public class ScrollPictureService {

  private Logger LOG = LoggerFactory.getLogger(ScrollPictureService.class);

  @Inject
  private ScrollPictureRepository scrollPictureRepository;

//  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//  public List<ScrollPicture> findAllScrollPcitures() {
//    return scrollPictureRepository.findAll();
//  }
//
//  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//  public void editScrollPciture(ScrollPicture scrollPicture) {
//    ScrollPicture scrollPictureOri = scrollPictureRepository.findOne(scrollPicture.getId());
//    if (scrollPictureOri == null) {
//      LOG.debug("不存在此轮播图");
//      throw new RuntimeException("轮播图不存在");
//    }
//    List<ScrollPicture> scrollPictures = null;
//
//    int sid = scrollPicture.getSid();
//    if (scrollPictureOri.getSid() > scrollPicture.getSid()) {
//      scrollPictures = scrollPictureRepository.findAllBySidIsGreaterThanEqualOrderBySid(
//          scrollPicture.getSid());
//      for (ScrollPicture scroP : scrollPictures) {
//        if (scroP.getId() != scrollPicture.getId()) {
//          sid++;
//          scroP.setSid(sid);
//          scrollPictureRepository.save(scroP);
//        }
//      }
//    }
//    if (scrollPictureOri.getSid() < scrollPicture.getSid()) {
//      scrollPictures = scrollPictureRepository.findAllBySidIsGreaterThanEqualOrderBySid(
//          scrollPictureOri.getSid());
//      for (ScrollPicture scroP : scrollPictures) {
//        if (scroP.getId() <=sid & scroP.getId() != scrollPicture.getId()) {
//          scroP.setSid(scroP.getSid() - 1);
//          scrollPictureRepository.save(scroP);
//        }
//      }
//    }
//    scrollPictureOri.setPicture(scrollPicture.getPicture());
//    scrollPictureOri.setDescription(scrollPicture.getDescription());
//    scrollPictureOri.setSid(scrollPicture.getSid());
//    scrollPictureRepository.save(scrollPictureOri);
//
//  }
//
//  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//  public void createScrollPciture(ScrollPicture scrollPicture) {
//   List<ScrollPicture> scrollPictures = scrollPictureRepository.findAllBySidIsGreaterThanEqualOrderBySid(scrollPicture.getSid());
//    scrollPictures.stream()
//        .map(scroP -> {
//          scroP.setSid(scroP.getSid() + 1);
//          scrollPictureRepository.save(scroP);
//          return scroP;
//        }).collect(Collectors.toList());
//    scrollPictureRepository.save(scrollPicture);
//  }
//
//
//  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//  public void deleteScrollPciture(Integer id) {
//    ScrollPicture origin = scrollPictureRepository.findOne(id);
//    if (origin == null) {
//      LOG.debug("不存在此轮播图");
//      throw new RuntimeException("不存在的轮播图");
//    }
//    scrollPictureRepository.delete(id);
//    List<ScrollPicture>
//        scrollPictures =
//        scrollPictureRepository.findAllBySidGreaterThanOrderBySid(origin.getSid());
//    for (ScrollPicture scrollPicture : scrollPictures) {
//      int sid = scrollPicture.getSid();
//      scrollPicture.setSid(sid - 1);
//      scrollPictureRepository.save(scrollPicture);
//    }
//  }
//
//  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//  public ScrollPicture findOneScrollPicture(Integer id) {
//    return scrollPictureRepository.findOne(id);
//  }

  public List<ScrollPicture> findAllScorllPicture(Product product) {
    return scrollPictureRepository.findAllByProduct(product);
  }


  public ScrollPicture findScrollPictureById(Integer id) {
    return scrollPictureRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editScrollPicture(ScrollPicture scrollPicture) {
    ScrollPicture origin = null;
    if (scrollPicture.getId() != null) {
      origin = scrollPictureRepository.findOne(scrollPicture.getId());
    } else {
      origin = new ScrollPicture();
    }
    origin.setDescription(scrollPicture.getDescription());
    origin.setPicture(scrollPicture.getPicture());
    origin.setSid(scrollPicture.getSid());
    origin.setProduct(scrollPicture.getProduct());
    scrollPictureRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteScrollPicture(Integer id) {
    scrollPictureRepository.delete(id);
  }
}
