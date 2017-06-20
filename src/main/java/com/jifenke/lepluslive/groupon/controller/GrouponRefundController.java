package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponOrderCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import com.jifenke.lepluslive.groupon.service.GrouponOrderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
