package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.controller.view.OffLineOrderShareExcel;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.criteria.ShareCriteria;
import com.jifenke.lepluslive.order.service.ShareService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wcg on 16/7/19.
 */
@RestController
@RequestMapping("/manage")
public class ShareController {

  @Inject
  private ShareService shareService;

  @Inject
  private OffLineOrderShareExcel offLineOrderShareExcel;

  @RequestMapping("/offLineOrder/share")
  public ModelAndView goSharePage() {
    return MvUtil.go("/share/share");
  }

  @RequestMapping(value = "/offLineOrder/share", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getOffLineOrder(@RequestBody ShareCriteria shareCriteria) {
    if (shareCriteria.getOffset() == null) {
      shareCriteria.setOffset(1);
    }
    Page page = shareService.findShareByPage(shareCriteria, 10);
    return LejiaResult.ok(page);
  }

  @RequestMapping(value = "/offLineOrder/share/exportExcel", method = RequestMethod.POST)
  public ModelAndView exportExcel(ShareCriteria shareCriteria) {
    if (shareCriteria.getOffset() == null) {
      shareCriteria.setOffset(1);
    }
    Page page = shareService.findShareByPage(shareCriteria, 10000);
    Map map = new HashMap();
    map.put("offLineOrderShareList", page.getContent());
    return new ModelAndView(offLineOrderShareExcel, map);
  }



}
