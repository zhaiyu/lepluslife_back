package com.jifenke.lepluslive.datacenter.controller;

import com.jifenke.lepluslive.datacenter.service.MemberDataService;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.service.OffLineOrderService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zxf on 2016/9/7.
 */
@RestController
@RequestMapping("/manage")
public class MemberDataController {

  @Inject
  private MemberDataService memberDataService;


  /*
   *  跳转到展示页面
   */
  @RequestMapping("/member_data")
  public ModelAndView userData() {
    return MvUtil.go("/datacenter/dataCenter");
  }

  /*
   * 统计消费会员数据
   */
  @RequiresPermissions("financial:transfer")
  @RequestMapping(value = "/member_data/count", method = RequestMethod.GET)
  @ResponseBody
  public List doBarChart() {
    return  memberDataService.doBarChart();
  }

}
