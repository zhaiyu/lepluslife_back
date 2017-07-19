package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.controller.view.StoreSettlementExcel;
import com.jifenke.lepluslive.yibao.domain.criteria.StoreSettlementCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.StoreSettlement;
import com.jifenke.lepluslive.yibao.service.StoreSettlementService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * 易宝门店结算单
 * Created by xf on 17-7-13.
 */
@RestController
@RequestMapping("/manage/settlement")
public class StoreSettlementController {
    @Inject
    private StoreSettlementService storeSettlementService;
    @Inject
    private StoreSettlementExcel storeSettlementExcel;
    /**
     *  跳转到列表页面
     *  Created by xf on 2017-07-13.
     */
    @RequestMapping(value = "/store/list",method = RequestMethod.GET)
    public ModelAndView toListPage() {
        return MvUtil.go("/");
    }

    @RequestMapping(value = "/store/findByCriteria",method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody StoreSettlementCriteria settlementCriteria) {
        if(settlementCriteria.getOffset()==null) {
            settlementCriteria.setOffset(1);
        }
        Page<StoreSettlement> page = storeSettlementService.findByCriteria(settlementCriteria,10);
        return LejiaResult.ok(page);
    }


    /**
     *  导出 Excel
     *  Created by xf on 2017-07-14.
     */
    @RequestMapping(value = "/store/export",method = RequestMethod.POST)
    public ModelAndView export(StoreSettlementCriteria settlementCriteria) {
        if(settlementCriteria.getOffset()==null) {
            settlementCriteria.setOffset(1);
        }
        Page<StoreSettlement> page = storeSettlementService.findByCriteria(settlementCriteria,1000);
        Map map = new HashMap();
        map.put("storeSettlementList", page.getContent());
        return new ModelAndView(storeSettlementExcel, map);
    }
}
