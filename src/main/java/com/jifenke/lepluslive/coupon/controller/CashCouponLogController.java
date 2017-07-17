package com.jifenke.lepluslive.coupon.controller;

import com.jifenke.lepluslive.coupon.domain.criteria.CashCouponLogCriteria;
import com.jifenke.lepluslive.coupon.domain.entities.CashCouponLog;
import com.jifenke.lepluslive.coupon.service.CashCouponLogService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * CashCouponLogController
 *  领券记录
 * @author XF
 * @date 2017/7/5
 */
@RestController
@RequestMapping("/manage")
public class CashCouponLogController {
    @Inject
    private CashCouponLogService cashCouponLogService;

    /**
     *  跳转到列表页面
     *  Created by xf on 2017-07-05
     */
    @RequestMapping("/cashCouponLog/list")
    public ModelAndView toListPage() {
        return MvUtil.go("/cashon/logList");
    }

    /**
     * 分页展示
     * Created by xf on  2017-07-05.
     */
    @RequestMapping("/cashCouponLog/findByCriteria")
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody CashCouponLogCriteria couponLogCriteria) {
        if(couponLogCriteria.getOffset()==null) {
            couponLogCriteria.setOffset(1);
        }
        Page<CashCouponLog> page = cashCouponLogService.findByCriteria(couponLogCriteria,10);
        return LejiaResult.ok(page);
    }

}
