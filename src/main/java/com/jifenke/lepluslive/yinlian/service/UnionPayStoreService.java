package com.jifenke.lepluslive.yinlian.service;

import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.global.util.HttpClientUtil;
import com.jifenke.lepluslive.global.util.JsonUtils;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.RSAUtil;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionPayStore;
import com.jifenke.lepluslive.yinlian.repository.UnionPayStoreRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.inject.Inject;

/**
 * 银联商务门店 Created by zhangwen on 2017/1/17.
 */
@Service
@Transactional(readOnly = true)
public class UnionPayStoreService {

  @Inject
  private UnionPayStoreRepository repository;

  /**
   * 根据银商门店名称和银商门店地址查询银联门店信息  2017/01/17
   *
   * @param shopName 银商门店名称
   * @param address  银商门店地址
   */
  public Map shopQuery(String shopName, String address) {
    SortedMap<String, String> params = commonParams("104001");
    //业务项
    params.put("shop_name", shopName);
    params.put("address", address);
    //params.put("term_no", "12340008");
    //签名
    params.put("sign", RSAUtil.sign(getOriginStr(params)));
    String result = HttpClientUtil.post(Constants.SHOP_QUERY_URL, params, "utf-8");

    return JsonUtils.jsonToPojo(result, Map.class);
  }

  /**
   * 会员注册  2017/01/19
   *
   * @param bankNumber 银行卡号码
   */
  public Map register(String bankNumber, String phone) {
    //先银行卡加密
    Map map = reSignBank(bankNumber);
    if (!"0000".equals(map.get("msg_rsp_code"))) {
      return map;
    }
    //注册
    SortedMap<String, String> params = commonParams("102103");
    //业务项
    params.put("sp_chnl_no", Constants.MSG_SENDER);
    params.put("enc_card_no", map.get("enc_card_no").toString());
    params.put("mobile_no", phone);  //公钥RSA加密后卡号
    //签名
    params.put("sign", RSAUtil.sign(getOriginStr(params)));
    String result = HttpClientUtil.post(Constants.BANK_REGISTER_URL, params, "utf-8");

    return JsonUtils.jsonToPojo(result, Map.class);
  }

  /**
   * 银行卡加密  2017/01/19
   *
   * @param bankNumber 银行卡号码
   */
  public Map reSignBank(String bankNumber) {
    SortedMap<String, String> params = commonParams("104003");
    //业务项
    params.put("sp_chnl_no", Constants.MSG_SENDER);//分配的渠道号
    params.put("pk_card_no", RSAUtil.encode(bankNumber));  //公钥RSA加密后卡号
    //params.put("mobile_no", "");  // o 手机号
    //签名
    params.put("sign", RSAUtil.sign(getOriginStr(params)));
    String result = HttpClientUtil.post(Constants.BANK_SIGN_URL, params, "utf-8");

    return JsonUtils.jsonToPojo(result, Map.class);
  }

  /**
   * 活动注册  2017/01/17
   */
  public Map process() {
    SortedMap<String, String> params = commonParams("101001");
    //获取所有的门店号
    List<Object> list = repository.findAllMerchantNum();
    StringBuilder builder = new StringBuilder();
    for (Object o : list) {
      builder.append(o).append(",");
    }
    //业务项
    params.put("sp_chnl_no", Constants.MSG_SENDER);//分配的渠道号
    params.put("event_no", Constants.EVENT_NO);//活动号
    params.put("event_title", "测试活动主题");
    params.put("event_desc", "测试活动介绍");  // o
    String now = DataUtils.formatYYYYMMDD(new Date());
    params.put("begin_date", now + "000000");
    params.put("end_date", now + "235959");
    params.put("event_link", "测试活动链接");  // o
    params.put("event_rule", "测试活动规则");
    params.put("rule_desc", "测试规则描述");
    params.put("spec_bank_flag", "0");  //  o  是否限制银行卡？
    params.put("event_status", "1");  //  表暂停=0|表有效=1
    //签名
    params.put("sign", RSAUtil.sign(getOriginStr(params)));
    //不对门店号签名
    params.put("shop_no", builder.substring(0, builder.length() - 1));//门店号
    String result = HttpClientUtil.post(Constants.EVENT_PROCESS_URL, params, "utf-8");

    return JsonUtils.jsonToPojo(result, Map.class);
  }

  /**
   * 保存或更新银商门店信息  2017/01/18
   *
   * @param shop  查询的银联门店信息
   * @param store 银联商务门店
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveStore(Map<String, String> shop, UnionPayStore store) throws Exception {
    try {
      UnionPayStore db_store = null;
      if (store.getId() != null) {
        db_store = repository.getOne(store.getId());
      } else {
        db_store = new UnionPayStore();
        db_store.setMerchant(store.getMerchant());
        db_store.setCreateDate(new Date());
      }
      //更新信息
      db_store.setShopNumber(shop.get("shop_no"));
      db_store.setShopName(shop.get("shop_name"));
      db_store.setAddress(shop.get("address"));
      db_store.setMerchantNum(shop.get("merchant_id"));
      db_store.setLatitude(shop.get("latitude"));
      db_store.setLongitude(shop.get("longitude"));
      db_store.setCityName(shop.get("city_name"));
      db_store.setCityCode(shop.get("city_code"));
      db_store.setProvinceName(shop.get("province_name"));
      db_store.setProvinceCode(shop.get("province_code"));
      db_store.setTermNos(shop.get("term_nos"));
      repository.save(db_store);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  private String getOriginStr(SortedMap<String, String> parameters) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : parameters.entrySet()) {
      String k = entry.getKey();
      String v = entry.getValue();
      if (null != v) {
        sb.append(k).append("=").append(v).append("&");
      }
    }
    if (sb.length() > 1) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  /**
   * 其他银商接口获取公共参数  2017/01/19
   *
   * @param code 交易代码
   */
  private SortedMap<String, String> commonParams(String code) {
    SortedMap<String, String> params = new TreeMap<>();
    params.put("msg_type", "10");
    params.put("msg_txn_code", code);
    params.put("msg_crrltn_id", UUID.randomUUID().toString().replace("-", ""));
    params.put("msg_flg", "0");
    params.put("msg_sender", Constants.MSG_SENDER);//分配的渠道号
    params.put("msg_time", DataUtils.formatDate(new Date()));
    params.put("msg_sys_sn", MvUtil.getOrderNumber());
    params.put("msg_ver", "0.1");
    return params;
  }

}
