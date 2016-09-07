package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.service.ScrollPictureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/11.
 */
@RestController
@RequestMapping("/manage")
public class ScrollPictureController {

  private Logger LOG = LoggerFactory.getLogger(ScrollPictureController.class);
  @Inject
  private ScrollPictureService scrollPictureService;

  @RequestMapping("/scrollPicture/{id}")
  public @ResponseBody ScrollPicture findScrollPictureById(@PathVariable Integer id ){
    return scrollPictureService.findScrollPictureById(id);

  }

  @RequestMapping(value = "/scrollPicture",method = RequestMethod.PUT)
  public @ResponseBody LejiaResult editScrollPicture(@RequestBody ScrollPicture scrollPicture){
    scrollPictureService.editScrollPicture(scrollPicture);
    return LejiaResult.build(200,"修改成功");

  }

  @RequestMapping(value = "/scrollPicture",method = RequestMethod.POST)
  public @ResponseBody LejiaResult createScrollPicture(@RequestBody ScrollPicture scrollPicture){
    scrollPictureService.editScrollPicture(scrollPicture);
    return LejiaResult.build(200,"保存成功");
  }

  @RequestMapping(value = "/scrollPicture/{id}",method = RequestMethod.DELETE)
  public @ResponseBody LejiaResult deleteScrollPicture(@PathVariable Integer id){
    scrollPictureService.deleteScrollPicture(id);
    return LejiaResult.build(200,"删除成功");
  }

}
