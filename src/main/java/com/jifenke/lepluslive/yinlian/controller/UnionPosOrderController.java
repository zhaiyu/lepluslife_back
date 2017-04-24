package com.jifenke.lepluslive.yinlian.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.service.WeiXinUserService;
import com.jifenke.lepluslive.yinlian.controller.view.UnionPosOrderViewExcel;
import com.jifenke.lepluslive.yinlian.domain.criteria.UnionPosOrderCriteria;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionPosOrder;
import com.jifenke.lepluslive.yinlian.service.UnionPosOrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lss on 2017/3/30.
 */
@RestController
@RequestMapping("/manage/unionPosOrder")
public class UnionPosOrderController {

    @Inject
    private UnionPosOrderService unionPosOrderService;

    @Inject
    private WeiXinUserService weiXinUserService;

    @Inject
    private UnionPosOrderViewExcel unionPosOrderViewExcel;

    @RequestMapping("/unionPosOrderPage")
    public ModelAndView unionPosOrderPage() {
        return MvUtil.go("/yinlian/unionPosOrderList");
    }

    @RequestMapping(value = "/getUnionPosOrderByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getUnionPosOrderByAjax(@RequestBody UnionPosOrderCriteria unionPosOrderCriteria) {
        if (unionPosOrderCriteria.getOffset() == null) {
            unionPosOrderCriteria.setOffset(1);
        }
        Page page = unionPosOrderService.findUnionPosOrderByPage(unionPosOrderCriteria, 10);
        List<UnionPosOrder> list = page.getContent();
        List<WeiXinUser> wxUsers = new ArrayList<>();
        Map map = new HashMap();
        map.put("page", page);

        for (UnionPosOrder unionPosOrder : list) {
            if (unionPosOrder.getLeJiaUser() != null) {
                Long leJiaUserId = unionPosOrder.getLeJiaUser().getId();
                WeiXinUser weiXinUsereiXinUser = weiXinUserService.findWeiXinUserByLeJiaUserId(leJiaUserId);
                wxUsers.add(weiXinUsereiXinUser);
            }
        }
        map.put("wxUsers", wxUsers);
        return LejiaResult.ok(map);
    }


    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public ModelAndView exporeExcel(UnionPosOrderCriteria unionPosOrderCriteria) {
        if (unionPosOrderCriteria.getOffset() == null) {
            unionPosOrderCriteria.setOffset(1);
        }
        Page page = unionPosOrderService.findUnionPosOrderByPage(unionPosOrderCriteria, 10000);
        Map map = new HashMap();
        map.put("orderList", page.getContent());
        return new ModelAndView(unionPosOrderViewExcel, map);
    }


}
