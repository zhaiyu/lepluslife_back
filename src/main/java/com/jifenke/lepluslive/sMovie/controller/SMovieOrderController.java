package com.jifenke.lepluslive.sMovie.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.sMovie.controller.view.SMovieOrderViewExcel;
import com.jifenke.lepluslive.sMovie.domain.criteria.SMovieOrderCriteria;
import com.jifenke.lepluslive.sMovie.domain.entities.SMovieTerminal;
import com.jifenke.lepluslive.sMovie.service.SMovieOrderService;
import com.jifenke.lepluslive.sMovie.service.SMovieTerminalService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lss on 17/5/9.
 */
@RestController
@RequestMapping("/manage/sMovieOrder")
public class SMovieOrderController {
    @Inject
    private SMovieOrderService sMovieOrderService;

    @Inject
    private SMovieTerminalService sMovieTerminalService;

    @Inject
    private SMovieOrderViewExcel sMovieOrderViewExcel;

    @RequestMapping("/list")
    public ModelAndView sMovieOrderList(Model model) {
        List<SMovieTerminal> sMovieTerminals = sMovieTerminalService.findAll();
        model.addAttribute("sMovieTerminals", sMovieTerminals);
        return MvUtil.go("/sMovie/sMovie");
    }


    @RequestMapping(value = "/getSMovieOrderByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getSMovieOrderByAjax(@RequestBody SMovieOrderCriteria sMovieOrderCriteria) {
        Page page = sMovieOrderService.findOrderByPage(sMovieOrderCriteria, 10);
        if (sMovieOrderCriteria.getOffset() == null) {
            sMovieOrderCriteria.setOffset(1);
        }
        return LejiaResult.ok(page);
    }


    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public ModelAndView exportExcel(SMovieOrderCriteria sMovieOrderCriteria) {
        if (sMovieOrderCriteria.getOffset() == null) {
            sMovieOrderCriteria.setOffset(1);
        }
        Page page = sMovieOrderService.findOrderByPage(sMovieOrderCriteria, 10);
        Map map = new HashMap();
        map.put("orderList", page.getContent());
        return new ModelAndView(sMovieOrderViewExcel, map);
    }


}
