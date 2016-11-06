package com.jifenke.lepluslive.weixin.controller;

import com.jifenke.lepluslive.global.util.JsonUtils;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.weixin.domain.entities.Menu;
import com.jifenke.lepluslive.weixin.service.MenuService;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zhangwen on 2016/5/25.
 */
@RestController
@RequestMapping("/manage/weixin")
public class MenuController {

  @Inject
  private MenuService menuService;

  @RequestMapping(value = "/index")
  public ModelAndView index() {
    return MvUtil.go("/weixin/index");
  }

  @RequestMapping(value = "/menu/list")
  public ModelAndView list(Model model) {

    List<Menu> menuList = menuService.findAllMenu();
    List<Menu> parentMenuList = menuService.findAllParentMenu();
    model.addAttribute("menuList", menuList);
    model.addAttribute("parentMenuList", parentMenuList);
    model.addAttribute("menuJson", JsonUtils.objectToJson(menuList));
//    model.addAttribute("parentMenuJson", JsonUtils.objectToJson(parentMenuList));

    return MvUtil.go("/weixin/menuList");
  }

  @RequestMapping(value = "/menu/save")
  public LejiaResult saveTopic(@RequestBody Menu menu) {
    try {
      menuService.editMenu(menu);
      return LejiaResult.build(200, "保存成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "保存失败");
    }
  }

  @RequestMapping(value = "/menu/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult deleteProduct(@PathVariable Long id) {
    try {
      menuService.deleteMenu(id);
      return LejiaResult.build(200, "删除成功");
    } catch (Exception e) {
      return new LejiaResult(500, "删除商品失败", null);
    }
  }

  @RequestMapping(value = "/menu/createWeixinMenu")
  public LejiaResult createWeixinMenu(@RequestBody String buttonString) {
    try {
      Map map = menuService.createWeixinMenu(buttonString);
      if (map != null && (Integer) map.get("errcode") == 0) {
        return LejiaResult.build(200, "创建成功");
      } else if (map != null && (Integer) map.get("errcode") != 0) {
        return LejiaResult.build(500, "创建失败，错误码为：" + map.get("errcode"));
      } else {
        return LejiaResult.build(500, "创建失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "创建失败");
    }
  }

  @RequestMapping(value = "/menu/deleteWeixinMenu")
  public LejiaResult deleteWeixinMenu() {
    try {
      Map map = menuService.deleteWeixinMenu();
      if (map != null && (Integer) map.get("errcode") == 0) {
        return LejiaResult.build(200, "删除成功");
      } else if (map != null && (Integer) map.get("errcode") != 0) {
        return LejiaResult.build(500, "删除失败，错误码为：" + map.get("errcode"));
      } else {
        return LejiaResult.build(500, "删除失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "删除失败");
    }
  }

//  @RequestMapping(value = "/menu/menuList", method = RequestMethod.GET)
//  public
//  @ResponseBody
//  Map menuList(@RequestParam String draw) {
//
//    List<Menu> menuList = menuService.findAllMenu();
//
//    Map<String, Object> map = new HashMap<>();
//    map.put("draw", draw);
//    map.put("recordsTotal", draw);
//    map.put("recordsFiltered", draw);
//    map.put("data", menuList);
//    return map;
//  }

}
