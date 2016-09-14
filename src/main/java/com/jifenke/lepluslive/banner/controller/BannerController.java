package com.jifenke.lepluslive.banner.controller;

import com.jifenke.lepluslive.banner.domain.criteria.BannerCriteria;
import com.jifenke.lepluslive.banner.domain.entities.Banner;
import com.jifenke.lepluslive.banner.service.BannerService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.product.domain.entities.ProductType;
import com.jifenke.lepluslive.product.service.ProductTypeService;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

import org.springframework.data.domain.Page;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 各种轮播图 Created by zhangwen on 16/8/26.
 */
@RestController
@RequestMapping("/manage")
public class BannerController {

  @Inject
  private BannerService bannerService;

  @Inject
  private DictionaryService dictionaryService;

  @Inject
  private CityService cityService;

  @Inject
  private ProductTypeService productTypeService;

  @RequestMapping(value = "/banner", method = RequestMethod.GET)
  public ModelAndView goBannerPage(@RequestParam Integer type, Model model) {
    if (type == null) {
      type = 1;
    }
    model.addAttribute("cities", cityService.findAllCity());
    model.addAttribute("type", type);
    return MvUtil.go("/banner/list");
  }

  @RequestMapping(value = "/banner/ajaxList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult ajaxList(@RequestBody BannerCriteria bannerCriteria) {

    if (bannerCriteria != null) {
      if (bannerCriteria.getOffset() == null) {
        bannerCriteria.setOffset(1);
      }
    }
    Page page = bannerService.findBannersByPage(bannerCriteria, 10);

    return LejiaResult.ok(page);
  }

  //热门关键词
  @RequestMapping(value = "/banner/hotWord", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult hotWord() {
    ArrayList<Map> list = new ArrayList<>();
    List<City> cityList = cityService.findAllCity();
    for (City city : cityList) {
      HashMap<String, Object> map = new HashMap<>();
      map.put("cityId", city.getId());
      map.put("cityName", city.getName());
      List<Banner> bannerList = bannerService.findByCityOrderByCreateDateDesc(city);
      ArrayList<String> hotList = new ArrayList<>();
      for (Banner banner : bannerList) {
        hotList.add(banner.getTitle());
      }
      map.put("hotList", hotList);
      list.add(map);
    }
    return LejiaResult.build(200, "" + cityList.size(), list);
  }

  //某个城市的关键词
  @RequestMapping(value = "/banner/findHot/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult findHot(@PathVariable Long id) {
    City city = cityService.findCityById(id);
    HashMap<String, Object> map = new HashMap<>();
    map.put("cityId", city.getId());
    map.put("cityName", city.getName());
    List<Banner> bannerList = bannerService.findByCityOrderByCreateDateDesc(city);
    ArrayList<Map> hotList = new ArrayList<>();
    for (Banner banner : bannerList) {
      HashMap<String, String> map1 = new HashMap<>();
      map1.put("bannerId", "" + banner.getId());
      map1.put("hotName", banner.getTitle());
      hotList.add(map1);
    }
    map.put("hotList", hotList);
    return LejiaResult.ok(map);
  }

  //修改某个城市的关键词
  @RequestMapping(value = "/banner/saveHotWord", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult saveHotWord(@RequestBody List<Banner> bannerList) {
    try {
      bannerService.editHotWord(bannerList);
    } catch (Exception e) {
      return LejiaResult.build(201, "save failed");
    }
    return LejiaResult.ok();
  }

  //获取公告
  @RequestMapping(value = "/banner/notice", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult notice() {
    return LejiaResult.ok(dictionaryService.findDictionaryById(21L).getValue());
  }

  //修改公告
  @RequestMapping(value = "/banner/saveNotice", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult saveNotice(@RequestBody String content) {
    try {
      dictionaryService.update(21L, content);
    } catch (Exception e) {
      return LejiaResult.build(201, "save failed");
    }
    return LejiaResult.ok();
  }

  //获取商品类别列表
  @RequestMapping(value = "/banner/productType", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult productTypeList() {
    List<ProductType> list = productTypeService.findAllType();
    return LejiaResult.ok(list);
  }

  //新建或修改商品类别
  @RequestMapping(value = "/banner/saveProductType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult saveProductType(@RequestBody ProductType productType) {
    try {
      int status = productTypeService.saveProductType(productType);
      return LejiaResult.build(status, "ok");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "服务器异常");
    }
  }

  //获取某个商品类别
  @RequestMapping(value = "/banner/findProductType/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult findProductType(@PathVariable Integer id) {
    return LejiaResult.ok(productTypeService.findProductTypeById(id));
  }

  @RequestMapping(value = "/banner/find/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult find(@PathVariable Long id) {
    Banner banner = bannerService.findById(id);
    return LejiaResult.ok(banner);
  }

  //新建或修改
  @RequestMapping(value = "/banner/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult save(@RequestBody Banner banner) {
    int status = bannerService.editBanner(banner);
    return LejiaResult.build(status, "ok");
  }

  //上架或下架
  @RequestMapping(value = "/banner/status/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult changeStatus(@PathVariable Long id) {
    bannerService.changeStatus(id);
    return LejiaResult.ok("ok");
  }

  //当期或往期
  @RequestMapping(value = "/banner/alive/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult changeAlive(@PathVariable Long id) {
    bannerService.changeAlive(id);
    return LejiaResult.ok("ok");
  }

}
