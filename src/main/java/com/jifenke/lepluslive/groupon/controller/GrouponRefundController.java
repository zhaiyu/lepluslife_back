package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponOrderCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import com.jifenke.lepluslive.groupon.service.GrouponOrderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * GrouponRefundController
 * 退款管理 Controller
 * @author XF
 * @date 2017/6/20
 */
@Controller
@RequestMapping("/manage")
public class GrouponRefundController {

    @Inject
    private GrouponOrderService grouponOrderService;

    /**
     *  跳转到列表页面
     *  Created by xf on 2017-06-21.
     */
    @RequestMapping("/grouponOrder/refundList")
    public ModelAndView toListPage() {
        return MvUtil.go("/groupon/refundList");
    }


    @RequestMapping("/grouponOrder/refundOrder")
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody GrouponOrderCriteria orderCriteria) {
        if(orderCriteria.getOffset()==null) {
            orderCriteria.setOffset(1);
        }
        Page<GrouponOrder> page = grouponOrderService.findByCriteria(orderCriteria,10);
        return LejiaResult.ok(page);
    }
}
