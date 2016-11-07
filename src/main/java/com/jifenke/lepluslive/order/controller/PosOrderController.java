package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.domain.criteria.PosOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.domain.entities.PosErrorLog;
import com.jifenke.lepluslive.order.service.PosDailyBillService;
import com.jifenke.lepluslive.order.service.PosOrderService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wcg on 16/8/30.
 */
@RestController
@RequestMapping("/manage")
public class PosOrderController {

    @Inject
    private PosOrderService posOrderService;
    @Inject
    private FileImageService fileImageService;
    @Inject
    private PosDailyBillService posDailyBillService;

    @RequestMapping(value = "/pos_order", method = RequestMethod.GET)
    public ModelAndView goPosOrderListPage(HttpServletRequest request) {
        return MvUtil.go("/order/posOrderList");
    }

    @RequestMapping(value = "/pos_order", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getOffLineOrder(@RequestBody PosOrderCriteria posOrderCriteria) {
        if (posOrderCriteria.getOffset() == null) {
            posOrderCriteria.setOffset(1);
        }
        Page page = posOrderService.findOrderByPage(posOrderCriteria, 10);
        return LejiaResult.ok(page);
    }

    @RequestMapping(value = "/pos_daily_bill/content/{offset}", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult findPosDaliyBillByPage(@PathVariable Integer offset) {
        List list = posDailyBillService.findPosDailyBillByPage(offset, 10);
        return new LejiaResult(list);
    }

    @RequestMapping(value = "/pos_daily_bill/count", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult findPosDailyBillCount() {
        Long billNum = posDailyBillService.countDailyBillNum();
        return new LejiaResult(billNum);
    }

    @RequestMapping(value = "/pos_daily_bill/pages", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult findPosDailyBillPages() {
        Integer pages = posDailyBillService.countDailyBillPages(10);
        return new LejiaResult(pages);
    }


    /**
     * 下载账单Excel
     *
     * @param id
     * @param response
     */
    @RequestMapping(value = "/pos_daily_bill/download/{id}", method = RequestMethod.GET)
    public void downloadBillExcel(@PathVariable Long id, HttpServletResponse response) {
        PosDailyBill posDailyBill = posDailyBillService.findById(id);
        String filename = posDailyBill.getFilename();
        if (filename == null) {
            filename = new SimpleDateFormat("yyyy-MM-dd").format(posDailyBill.getCreateDate());
        }
        String url = posDailyBill.getUrl();
        fileImageService.downloadExcel(filename, url, response);
    }


    @RequestMapping(value = "/pos_errorlog/pagecount", method = RequestMethod.GET)
    public LejiaResult getPageCount() {
        Map page = new HashMap();
        Long totalCount = posOrderService.countErroPosDailyBill();
        Integer totalPage = posDailyBillService.countDailyBillPages(5);
        page.put("totalCount", totalCount);
        page.put("totalPage", totalPage);
        return new LejiaResult(page);
    }

    @RequestMapping(value = "/pos_errorlog/{offset}", method = RequestMethod.GET)
    public LejiaResult getErrlogByPage(@PathVariable Integer offset) {
        Map content = new HashMap();
        if (offset == null) {
            offset = 1;
        }
        List<PosDailyBill> errorBills = posOrderService.findErrorPosDailyBill(offset, 5);        // 查询错误账单
        List<List<PosErrorLog>> errorLogs = posOrderService.findPosErrorLogByBill(errorBills);  // 对应的错误记录
        content.put("errorBills", errorBills);
        content.put("errorLogs", errorLogs);
        return new LejiaResult(content);
    }
}
