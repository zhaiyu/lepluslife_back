package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.controller.view.GrouponRefundExcel;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponRefundCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponRefund;
import com.jifenke.lepluslive.groupon.service.GrouponRefundService;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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
    private GrouponRefundService grouponRefundService;
    @Inject
    private GrouponRefundExcel grouponRefundExcel;

    /**
     *  跳转到列表页面
     *  Created by xf on 2017-06-21.
     */
    @RequestMapping("/refundOrder/list")
    public ModelAndView toListPage() {
        return MvUtil.go("/groupon/refundList");
    }


    @RequestMapping("/refundOrder/findByCriteria")
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody GrouponRefundCriteria refundCriteria) {
        if(refundCriteria.getOffset()==null) {
            refundCriteria.setOffset(1);
        }
        Page<GrouponRefund> page = grouponRefundService.findByCriteria(refundCriteria,10);
        return LejiaResult.ok(page);
    }

    /**
     *  导出 Excel
     *  Created by xf on 2017-06-19.
     */
    @RequestMapping(value = "/refundOrder/export",method = RequestMethod.POST)
    public ModelAndView export(GrouponRefundCriteria refundCriteria) {
        if(refundCriteria.getOffset()==null) {
            refundCriteria.setOffset(1);
        }
        Page<GrouponRefund> page = grouponRefundService.findByCriteria(refundCriteria,10000);
        Map map = new HashMap();
        map.put("refundList", page.getContent());
        return new ModelAndView(grouponRefundExcel, map);
    }
}
