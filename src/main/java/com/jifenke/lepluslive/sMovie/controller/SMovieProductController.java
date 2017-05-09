package com.jifenke.lepluslive.sMovie.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.sMovie.domain.criteria.SMovieProductCriteria;
import com.jifenke.lepluslive.sMovie.domain.entities.SMovieProduct;
import com.jifenke.lepluslive.sMovie.service.SMovieProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by lss on 2017/5/4.
 */
@RestController
@RequestMapping("/manage/sMovieProduct")
public class SMovieProductController {
    @Inject
    private SMovieProductService sMovieProductService;

    @RequestMapping(value = "/addSMovieProduct", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult addSMovieProduct(@RequestBody SMovieProduct sMovieProduct) {
        try {
            sMovieProductService.saveOne(sMovieProduct);
            return LejiaResult.ok();
        } catch (Exception e) {
            return LejiaResult.build(501, "保存失败");
        }
    }

    @RequestMapping(value = "/editSMovieProduct", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult editSMovieProduct(@RequestBody SMovieProduct sMovieProduct) {
        try {
            sMovieProductService.editOne(sMovieProduct);
            return LejiaResult.ok();
        } catch (Exception e) {
            return LejiaResult.build(501, "保存失败");
        }
    }


    @RequestMapping(value = "/getSMovieProductByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getSMovieProductByAjax(@RequestBody SMovieProductCriteria sMovieProductCriteria) {
        if (sMovieProductCriteria.getOffset() == null) {
            sMovieProductCriteria.setOffset(1);
        }
        Page page = sMovieProductService.findSMovieProductByPage(sMovieProductCriteria, 10);
        return LejiaResult.ok(page);
    }


    @RequestMapping(value = "/editSMovieProduct/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult editSMovieProduct(@PathVariable Long id) {
        SMovieProduct sMovieProduct = sMovieProductService.findById(id);
        return LejiaResult.ok(sMovieProduct);
    }

    @RequestMapping(value = "/changeSMovieProductState/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult changeSMovieProductState(@PathVariable Long id) {
        sMovieProductService.changeSMovieProductState(id);
        return LejiaResult.ok();
    }


}
