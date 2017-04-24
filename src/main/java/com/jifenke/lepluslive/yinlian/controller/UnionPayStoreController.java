package com.jifenke.lepluslive.yinlian.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.yinlian.domain.criteria.UnionPayStoreCriteria;
import com.jifenke.lepluslive.yinlian.domain.entities.MerchantUnionPos;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionPayStore;
import com.jifenke.lepluslive.yinlian.service.MerchantUnionPosService;
import com.jifenke.lepluslive.yinlian.service.UnionPayStoreService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银联商务门店 Created by zhangwen on 2017/1/18.
 */
@RestController
@RequestMapping("/manage/unionPayStore")
public class UnionPayStoreController {

  @Inject
  private UnionPayStoreService unionPayStoreService;

  @Inject
  private MerchantUnionPosService merchantUnionPosService;

  @Inject
  private MerchantService merchantService;

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
        if (shops == null||shops.size()==0) {
          return LejiaResult.build(501, "未找到相关门店信息");
        }else {
          for(Map shop :shops){
            String shopNumber=shop.get("shop_no").toString();
            UnionPayStore unionPayStore2=unionPayStoreService.findUnionPayStoreByShopNumber(shopNumber);
            if(unionPayStore2==null){
              try {
                Map map2=unionPayStoreService.saveStore(shop, unionPayStore);
                System.out.println(map2.toString());
              } catch (Exception e) {
                e.printStackTrace();
                return LejiaResult.build(503, "数据存储异常");
              }
            }
          }
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


  @RequestMapping("/unionPayStorePage")
  public ModelAndView unionPosOrderPage(Model model) {
    return MvUtil.go("/yinlian/unionPayStoreList");
  }




  @RequestMapping(value = "/getUnionPayStoreByAjax", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getUnionPayStoreByAjax(@RequestBody UnionPayStoreCriteria unionPayStoreCriteria) {
    Page page = unionPayStoreService.findUnionPayStoreByPage(unionPayStoreCriteria,10);
    return LejiaResult.ok(page);
  }




  @RequestMapping(value = "/getMerchantBySid/{merchantSid}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult getMerchantBySid(@PathVariable String merchantSid) {
    Merchant merchant=merchantService.findMerchantByMerchantSid(merchantSid);
    Map<String,Object> map =new HashMap<String,Object>();
    if(merchant==null){
      return LejiaResult.build(501, "未找到相关门店信息");
    }else {
      map.put("merchant",merchant);
      return LejiaResult.ok(map);
    }
  }


  @RequestMapping(value = "/editPage", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult editPage(Long id) {
    UnionPayStore unionPayStore =unionPayStoreService.findUnionPayStoreById(id);
    Merchant merchant=unionPayStore.getMerchant();
    MerchantUnionPos merchantUnionPos  =merchantUnionPosService.findByMerchantId(merchant.getId());
    Map<String,Object> map =new HashMap<String,Object>();
    map.put("unionPayStore",unionPayStore);
    map.put("merchantUnionPos",merchantUnionPos);
    return LejiaResult.ok(map);
  }



}
