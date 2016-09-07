package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.MvUtil;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wcg on 16/9/5.
 */
@RestController
@RequestMapping("/manage")
public class PosController {

  @RequestMapping(value = "/pos", method = RequestMethod.GET)
  public ModelAndView posManage() {
    return MvUtil.go("/merchant/posManage");
  }

}
