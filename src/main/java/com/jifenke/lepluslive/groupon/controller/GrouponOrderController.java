package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponOrderCriteria;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponProductCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.service.GrouponOrderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * 团购订单 Controller
 * @author XF
 * @date 2017/6/19
 */
@Controller
@RequestMapping("/manage")
public class GrouponOrderController {
    @Inject
    private GrouponOrderService grouponOrderService;
    /**
     * 分页展示
     * Created by xf on 2017-06-19.
     */
    @RequestMapping("/grouponOrder/findByCriteria")
    public LejiaResult findByCriteria(GrouponOrderCriteria orderCriteria) {
        if(orderCriteria.getOffset()==null) {
            orderCriteria.setOffset(1);
        }
        Page<GrouponOrder> page = grouponOrderService.findByCriteria(orderCriteria,10);
        return LejiaResult.ok(page);
    }
}
