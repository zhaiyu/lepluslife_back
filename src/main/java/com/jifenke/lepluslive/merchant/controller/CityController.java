package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.PaginationUtil;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.service.CityService;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/17.
 */
@RestController
@RequestMapping("/manage")
public class CityController {

    @Inject
    private CityService cityService;

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    public ModelAndView goShowCityPage(@RequestParam(value = "page", required = false) Integer offset,
                                       @RequestParam(value = "per_page", required = false) Integer limit,
                                       Model model) {
        model.addAttribute("cities", cityService
                .findCitiesByPage(PaginationUtil.generatePageRequest(offset, limit)));
        return MvUtil.go("/merchant/cityList");
    }

    //测试提交
    @RequestMapping("/city/ajax")
    public
    @ResponseBody
    List<City> findAllCity() {
        return cityService.findAllCity();
    }

    @RequestMapping(value = "/city/edit", method = RequestMethod.GET)
    public ModelAndView goEditCityPage(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("city", cityService.findCityById(id));
        }
        return MvUtil.go("/merchant/cityEdit");
    }

    @RequestMapping(value = "/city", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public LejiaResult createCity(@RequestBody City city) {
        cityService.createCity(city);
        return LejiaResult.ok("创建城市成功");
    }

    @RequestMapping(value = "/city", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public LejiaResult editCity(@RequestBody City city) {
        cityService.editCity(city);
        return LejiaResult.ok("修改城市成功");
    }

    @RequestMapping(value = "/city/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LejiaResult deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return LejiaResult.ok("删除城市成功");
    }

}
