package com.jifenke.lepluslive.withdrawBill.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.withdrawBill.controller.view.WeiXinWithdrawBillExcel;
import com.jifenke.lepluslive.withdrawBill.domain.criteria.WeiXinWithdrawBillCriteria;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WeiXinWithdrawBill;
import com.jifenke.lepluslive.withdrawBill.service.WeiXinWithdrawBillService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lss on 2017/5/15.
 */
@RestController
@RequestMapping("/manage/weiXinWithdrawBill")
public class WeiXinWithdrawBillController {
    @Inject
    private WeiXinWithdrawBillService weiXinWithdrawBillService;

    @Inject
    private WeiXinWithdrawBillExcel weiXinWithdrawBillExcel;


    @RequestMapping("/weiXinWithdrawBillList")
    public ModelAndView weiXinWithdrawBillList() {
        return MvUtil.go("/withdraw/weiXinWithdrawBillList");
    }


    @RequestMapping(value = "/getWeiXinWithdrawBillByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getWeiXinWithdrawBillByAjax(@RequestBody WeiXinWithdrawBillCriteria weiXinWithdrawBillCriteria) {
        Page page = weiXinWithdrawBillService.findWeiXinWithdrawBillByPage(weiXinWithdrawBillCriteria, 10);
        if (weiXinWithdrawBillCriteria.getOffset() == null) {
            weiXinWithdrawBillCriteria.setOffset(1);
        }
        return LejiaResult.ok(page);
    }


    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public ModelAndView exportExcel(WeiXinWithdrawBillCriteria weiXinWithdrawBillCriteria) {
        if (weiXinWithdrawBillCriteria.getOffset() == null) {
            weiXinWithdrawBillCriteria.setOffset(1);
        }
        Page page = weiXinWithdrawBillService.findWeiXinWithdrawBillByPage(weiXinWithdrawBillCriteria, 10000);
        Map map = new HashMap();
        map.put("weiXinWithdrawBillList", page.getContent());
        return new ModelAndView(weiXinWithdrawBillExcel, map);
    }


    @RequestMapping(value = "/getOneWeiXinWithdrawBill/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult reject(@PathVariable Long id) {

        WeiXinWithdrawBill weiXinWithdrawBill = weiXinWithdrawBillService.findById(id);
        return LejiaResult.ok(weiXinWithdrawBill);
    }


    @RequestMapping(value = "/rejectConfirm/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult rejectConfirm(@PathVariable Long id) {
        try {
            weiXinWithdrawBillService.rejectConfirm(id);
        } catch (Exception e) {
            return LejiaResult.build(500, "error");
        }
        return LejiaResult.build(500, "ok");
    }


}
