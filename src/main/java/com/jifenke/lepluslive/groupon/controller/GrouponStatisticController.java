package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.controller.view.GrouponStatisticExcel;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponStatisticCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponStatistic;
import com.jifenke.lepluslive.groupon.service.GrouponStatisticService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 团购结算单
 * Created by zhangwen on 2017/8/23.
 */
@RestController
@RequestMapping("/manage/groupon")
public class GrouponStatisticController {

  @Inject
  private GrouponStatisticService grouponStatisticService;

  @Inject
  private GrouponStatisticExcel grouponStatisticExcel;

  /**
   * 跳转到团购结算单页面   2017/8/23
   */
  @RequestMapping(value = "/statisticList", method = RequestMethod.GET)
  public ModelAndView financialList() {
    return MvUtil.go("/groupon/statisticList");
  }

  /**
   * 结算列表异步加载   2017/8/23
   */
  @RequestMapping(value = "/ajaxList", method = RequestMethod.POST)
  public LejiaResult searchFinancialBycriterial(@RequestBody GrouponStatisticCriteria criteria) {
    if (criteria.getOffset() == null) {
      criteria.setOffset(1);
    }
    Page page = grouponStatisticService.findByCirteria(criteria, 10);
    return LejiaResult.ok(page);
  }

  /**
   * 单笔结算单确认转账并发送模板消息 2017/8/23
   *
   * @param id 结算单ID
   */
  @RequestMapping(value = "/transfer/{id}", method = RequestMethod.GET)
  public LejiaResult transfer(@PathVariable Long id) {
    try {
      //改变结算单状态
      GrouponStatistic grouponStatistic = grouponStatisticService.findOne(id);
      grouponStatisticService.transfer(grouponStatistic);
      //发送模版消息
      grouponStatisticService.sendWxMsg(grouponStatistic);
      return LejiaResult.ok();
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "修改异常");
    }
  }

  /**
   * 批量确认转账并发送模板消息  2017/8/23
   */
  @RequestMapping(value = "/batchTransfer", method = RequestMethod.POST)
  public LejiaResult batchTransfer() {
    List<GrouponStatistic>
        statistics =
        grouponStatisticService.findAllNonTransferStatistic();
    for (GrouponStatistic statistic : statistics) {
      //改变结算单状态
      grouponStatisticService.transfer(statistic);
      //发送模版消息
      grouponStatisticService.sendWxMsg(statistic);
    }
    return LejiaResult.ok();
  }

  /**
   * 设为挂账   2017/8/23
   */
  @RequestMapping(value = "/hover/{id}", method = RequestMethod.GET)
  public LejiaResult changeFinancialStateToHover(@PathVariable Long id) {
    //改变统计单状态并发送模版消息
    grouponStatisticService.hover(id);
    return LejiaResult.ok();
  }


  /**
   * 结算单导出   2017/8/23
   */
  @RequestMapping(value = "/statistic/export", method = RequestMethod.POST)
  public ModelAndView exporeExcel(GrouponStatisticCriteria criteria) {
    if (criteria.getOffset() == null) {
      criteria.setOffset(1);
    }
    Page page = grouponStatisticService.findByCirteria(criteria, 10000);
    Map<String, Object> map = new HashMap<>();
    map.put("list", page.getContent());
    return new ModelAndView(grouponStatisticExcel, map);
  }
}
