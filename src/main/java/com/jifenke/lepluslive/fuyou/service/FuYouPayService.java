package com.jifenke.lepluslive.fuyou.service;



import com.fuiou.mpay.encrypt.RSAUtils;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.HttpClientUtil;
import com.jifenke.lepluslive.global.util.MapUtil;
import com.jifenke.lepluslive.global.util.MvUtil;

import org.springframework.stereotype.Service;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 富友支付 Created by zhangwen on 2016/12/6.
 */
@Service
public class FuYouPayService {

  /**
   * 富友退款接口  16/12/26
   */
  public Map<String, String> orderRefundHttp(ScanCodeOrder order, String refundSid)
      throws Exception {

    SortedMap<String, Object> params = new TreeMap<>();
    params.put("version", "1.0");
    params.put("ins_cd", Constants.FUYOU_INS_CD);//机构号
    params.put("mchnt_cd", order.getScanCodeOrderExt().getMerchantNum());//商户号
    params.put("term_id", MvUtil.getRandomNumber(8));//终端号, 随机八位
    params.put("mchnt_order_no", order.getOrderSid());
    params.put("random_str", MvUtil.getRandomStr(32));//随机字符串
    if (order.getScanCodeOrderExt().getPayType() == 0) {//订单类型:1=ALIPAY|0=WECHAT
      params.put("order_type", "WECHAT");
    } else {
      params.put("order_type", "ALIPAY");
    }
    params.put("refund_order_no", refundSid); //退款单号
    params.put("total_amt", order.getTruePay()); //总金额
    params.put("refund_amt", order.getTruePay()); //总金额
    params.put("operator_id", "caoguangyan"); //操作员
    String srcSign = MapUtil.mapBlankJoin(params, false, false);

    String sign = RSAUtils.sign(srcSign.getBytes("GBK"), Constants.FUYOU_PRI_KEY);
    System.out.println(sign);
    params.put("sign", sign);
    StringBuffer paramBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?><xml>");
    MapUtil.mapToXML(params, paramBuffer);
    paramBuffer.append("</xml>");
    System.out.println(paramBuffer.toString());
    try {
      Map<String, String> content = new HashMap<>();
      content.put("req", URLEncoder.encode(paramBuffer.toString(), "GBK"));
      String result =
          HttpClientUtil.post(Constants.FUYOU_REFUND_URL, content, "GBK");
      result = new URLDecoder().decode(result, "GBK");
      System.out.println(result);
      return MapUtil.xmlStringToMap(result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }


  /**
   * @return ip地址
   */
  private String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip != null && ip.length() > 15) {
      ip = ip.split(",")[0];
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
  }

}
