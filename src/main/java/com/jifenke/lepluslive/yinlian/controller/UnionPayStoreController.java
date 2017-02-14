package com.jifenke.lepluslive.yinlian.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionPayStore;
import com.jifenke.lepluslive.yinlian.service.UnionPayStoreService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 银联商务门店 Created by zhangwen on 2017/1/18.
 */
@RestController
@RequestMapping("/manage/unionPayStore")
public class UnionPayStoreController {

  @Inject
  private UnionPayStoreService unionPayStoreService;

  /**
   * 新建或更新银商门店信息   2017/01/18
   *
   * @param unionPayStore 银商门店
   */
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public LejiaResult refundSubmit(@RequestBody UnionPayStore unionPayStore) {
    Map
        map =
        unionPayStoreService.shopQuery(unionPayStore.getShopName(), unionPayStore.getAddress());
    if (map != null) {
      String resultCode = String.valueOf(map.get("msg_rsp_code"));
      if ("0000".equals(resultCode)) {
        List<Map> shops = (List<Map>) map.get("shops");
        if (shops == null) {
          return LejiaResult.build(501, "未找到相关门店信息");
        } else if (shops.size() > 1) {
          return LejiaResult.build(502, "异常,找到" + shops.size() + "个相关门店信息");
        }
        Map shop = shops.get(0);
        try {
          unionPayStoreService.saveStore(shop, unionPayStore);
        } catch (Exception e) {
          e.printStackTrace();
          return LejiaResult.build(503, "数据存储异常");
        }
      } else {
        return LejiaResult
            .build(504, String.valueOf(map.get("msg_rsp_desc")) + "(" + resultCode + ")");
      }
    } else {
      return LejiaResult.build(505, "系统异常");
    }
    return LejiaResult.ok();
  }


}
