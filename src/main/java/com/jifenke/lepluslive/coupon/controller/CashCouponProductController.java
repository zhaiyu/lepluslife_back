package com.jifenke.lepluslive.coupon.controller;

import com.jifenke.lepluslive.coupon.domain.criteria.CashCouponProductCriteria;
import com.jifenke.lepluslive.coupon.domain.entities.CashCouponProduct;
import com.jifenke.lepluslive.coupon.service.CashCouponProductService;
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
 * CashCouponProductController
 *  满减券管理
 * @author XF
 * @date 2017/7/6
 */
@RestController
@RequestMapping("/manage")
public class CashCouponProductController {
    @Inject
    private CashCouponProductService cashCouponProductService;

    /**
     *  跳转到列表页面
     *  Created by xf on 2017-07-06
     */
    @RequestMapping("/cashCouponProduct/list")
    public ModelAndView toListPage() {
        return MvUtil.go("/cashon/logList");
    }

    /**
     * 分页展示
     * Created by xf on  2017-07-06.
     */
    @RequestMapping("/cashCouponProduct/findByCriteria")
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody CashCouponProductCriteria couponProductCriteria) {
        if(couponProductCriteria.getOffset()==null) {
            couponProductCriteria.setOffset(1);
        }
        Page<CashCouponProduct> page = cashCouponProductService.findByCriteria(couponProductCriteria,10);
        return LejiaResult.ok(page);
    }
}
