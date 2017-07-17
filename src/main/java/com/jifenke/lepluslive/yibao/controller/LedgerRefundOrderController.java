package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.controller.view.LedgerRefundOrderExcel;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerRefundOrderCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerRefundOrder;
import com.jifenke.lepluslive.yibao.service.LedgerRefundOrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * 退款单记录
 * Created by xf on 17-7-14.
 */
@RestController
@RequestMapping("/manage/refund")
public class LedgerRefundOrderController {

    @Inject
    private LedgerRefundOrderService ledgerRefundOrderService;
    @Inject
    private LedgerRefundOrderExcel ledgerSettlementExcel;
    /**
     * 跳转到列表页面
     * Created by xf on 2017-07-13.
     */
    @RequestMapping(value = "/ledger/list", method = RequestMethod.GET)
    public ModelAndView toListPage() {
        return MvUtil.go("/yibao/ledger/refundList");
    }

    @RequestMapping(value = "/ledger/findByCriteria", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody LedgerRefundOrderCriteria refundCriteria) {
        if (refundCriteria.getOffset() == null) {
            refundCriteria.setOffset(1);
        }
        Page<LedgerRefundOrder> page = ledgerRefundOrderService.findByCriteria(refundCriteria, 10);
        return LejiaResult.ok(page);
    }

    /**
     * 导出 Excel
     * Created by xf on 2017-07-14.
     */
    @RequestMapping(value = "/ledger/export", method = RequestMethod.POST)
    public ModelAndView export(LedgerRefundOrderCriteria refundCriteria) {
        if (refundCriteria.getOffset() == null) {
            refundCriteria.setOffset(1);
        }
        Page<LedgerRefundOrder> page = ledgerRefundOrderService.findByCriteria(refundCriteria, 10000);
        Map map = new HashMap();
        map.put("ledgerRefundOrderList", page.getContent());
        return new ModelAndView(ledgerSettlementExcel, map);
    }
}
