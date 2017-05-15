package com.jifenke.lepluslive.withdrawBill.service;

import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.WeixinPayUtil;
import com.jifenke.lepluslive.job.WeiXinWithDrawBillJob;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.partner.domain.entities.*;
import com.jifenke.lepluslive.partner.repository.PartnerWalletLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineLogRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletOnlineRepository;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;
import com.jifenke.lepluslive.partner.service.PartnerWalletService;
import com.jifenke.lepluslive.withdrawBill.domain.criteria.WeiXinWithdrawBillCriteria;
import com.jifenke.lepluslive.withdrawBill.domain.criteria.WithdrawBillCriteria;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WeiXinWithdrawBill;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WithdrawBill;
import com.jifenke.lepluslive.withdrawBill.repository.WeixinWithdrawBillRepository;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.net.ssl.SSLContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 2017/5/11.
 */
@Service
@Transactional(readOnly = false)
public class WeiXinWithdrawBillService {

  private static String jobGroupName = "WITHDRAW";
  private static String triggerGroupName = "WITHDRAW_TRAGGER_NAME";

  @Inject
  private WeixinWithdrawBillRepository weixinWithdrawBillRepository;

  @Inject
  private PartnerWalletRepository partnerWalletRepository;

  @Inject
  private PartnerWalletOnlineRepository partnerWalletOnlineRepository;



  @Inject
  private PartnerWalletLogRepository partnerWalletLogRepository;

  @Inject
  private PartnerWalletOnlineLogRepository partnerWalletOnlineLogRepository;



  @Inject
  private SSLContext sslContext;

  @Value("${leplusshop.wxapiKey}")
  private String wxapiKey;

  @Value("${leplusshop.appId}")
  private String appId;

  @Value("${leplusshop.mchId}")
  private String mchId;

  @Inject
  private Scheduler scheduler;

  @Inject
  private PartnerWalletService partnerWalletService;

  /**
   * 发放红包接口,调用此接口会转账给用户
   *
   * @return 返回参数 result_code :success/fail  err_code_des: 错误描述 更多参数见 https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Map withdraw(Long id) {
    Map<String, String> map = null;
    WeiXinWithdrawBill weiXinWithdrawBill = weixinWithdrawBillRepository.findOne(id);
    if (weiXinWithdrawBill.getState() == 0) {

      SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
      parameters.put("wxappid", appId);
      parameters.put("mch_id", mchId);
      parameters.put("nonce_str", MvUtil.getRandomStr());
      parameters
          .put("mch_billno",
               weiXinWithdrawBill.getMchBillno());
//        parameters.put("mch_billno",
//                       "1358860502201705116581849585");
      parameters.put("send_name", "乐加生活");
      parameters.put("re_openid", weiXinWithdrawBill.getWeiXinOtherUser().getOpenId());
      parameters.put("total_amount", weiXinWithdrawBill.getTotalPrice() + "");
      parameters.put("total_num", "1");
      parameters.put("wishing", "提现到账");
      parameters.put("client_ip", "127.0.0.1");
      parameters.put("act_name", "提现");
      parameters.put("remark", "提现");
      parameters.put("sign", createSign("UTF-8", parameters, wxapiKey));
      map = WeixinPayUtil.initialRebateOrder(
          "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack",
          getRequestXml(parameters), sslContext);
      if ("SUCCESS".equals(map.get("result_code").toString()) && "SUCCESS".equals(map.get(
          "return_code").toString())) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
          Date time = sdf.parse(sdf.format(new Date().getTime() + Constants.WITHDRAW_CHECK));
          JobDetail weiXinWithDrawBillJob = JobBuilder.newJob(WeiXinWithDrawBillJob.class)
              .withIdentity("OrderJob" + weiXinWithdrawBill.getId(), jobGroupName)
              .usingJobData("mch_billno", map.get("mch_billno"))
              .build();
          Trigger autoCheckedWithdrawJobTrigger = TriggerBuilder.newTrigger()
              .withIdentity(
                  TriggerKey.triggerKey("autoCheckedWithdrawJobTrigger"
                                        + weiXinWithdrawBill.getId(), triggerGroupName))
              .startAt(time)
              .build();
          scheduler.scheduleJob(weiXinWithDrawBillJob, autoCheckedWithdrawJobTrigger);
          scheduler.start();
          weiXinWithdrawBill.setState(1);
          weiXinWithdrawBill.setNote(map.get("return_code"));
          weixinWithdrawBillRepository.save(weiXinWithdrawBill);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return map;
  }

  /**
   * 查看用户是否接收红包,未接受退款
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Map checkRedPackInfo(String mchBillno) {
    SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
    parameters.put("nonce_str", MvUtil.getRandomStr());
    parameters.put("appid", appId);
    parameters.put("mch_id", mchId);
    parameters.put("bill_type", "MCHT");
    parameters.put("mch_billno", mchBillno);
    parameters.put("sign", createSign("UTF-8", parameters, wxapiKey));
    Map<String, String> map = WeixinPayUtil.initialRebateOrder(
        "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo",
        getRequestXml(parameters), sslContext);
    WeiXinWithdrawBill weiXinWithdrawBill = weixinWithdrawBillRepository.findByMchBillno(mchBillno);
    weiXinWithdrawBill.setNote(map.get("status"));
    if (!"RECEIVED".equals(map.get("status")) && ("REFUND".equals(map.get("status")) || "RFUND_ING"
        .equals(map.get("status")))) {//如果退款或者退款中返回佣金
      if (weiXinWithdrawBill.getOfflineWallet() != 0) {
        partnerWalletService.changePartnerWallet(weiXinWithdrawBill.getOfflineWallet(),
                                                 weiXinWithdrawBill.getPartner(),
                                                 weiXinWithdrawBill.getMchBillno(), 15004L);
      }
      if (weiXinWithdrawBill.getOnlineWallet() != 0) {
        partnerWalletService.changeOnlinePartnerWallet(weiXinWithdrawBill.getOfflineWallet(),
                                                       weiXinWithdrawBill.getPartner(),
                                                       weiXinWithdrawBill.getMchBillno(), 16004L);
      }
      weiXinWithdrawBill.setState(3);
    } else if ("RECEIVED".equals(map.get("status"))) { //成功接收
      weiXinWithdrawBill.setState(4);
    } else {
      weiXinWithdrawBill.setState(5);
    }
    return map;

  }


  /**
   * 生成微信签名
   */
  public String createSign(String characterEncoding, SortedMap<Object, Object> parameters,
                           String mch_key) {
    StringBuffer sb = new StringBuffer();
    Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      Object v = entry.getValue();
      if (null != v && !"".equals(v)
          && !"sign".equals(k) && !"key".equals(k)) {
        sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key=" + mch_key);
    String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
    return sign;
  }


  /**
   * 获取请求的XML
   */
  public String getRequestXml(SortedMap<Object, Object> parameters) {
    StringBuffer sb = new StringBuffer();
    sb.append("<xml>");
    Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      String v = (String) entry.getValue();
      sb.append("<" + k + ">" + v + "</" + k + ">");
    }
    sb.append("</xml>");
    return sb.toString();
  }


  public Page findWeiXinWithdrawBillByPage(WeiXinWithdrawBillCriteria weiXinWithdrawBillCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
    return weixinWithdrawBillRepository
            .findAll(getWhereClause(weiXinWithdrawBillCriteria),
                    new PageRequest(weiXinWithdrawBillCriteria.getOffset() - 1, limit, sort));
  }

  public static Specification<WeiXinWithdrawBill> getWhereClause(WeiXinWithdrawBillCriteria weiXinWithdrawBillCriteria) {
    return new Specification<WeiXinWithdrawBill>() {
      @Override
      public Predicate toPredicate(Root<WeiXinWithdrawBill> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (weiXinWithdrawBillCriteria.getState() != null) {
          predicate.getExpressions().add(
                  cb.equal(r.get("state"),
                          weiXinWithdrawBillCriteria.getState()));
        }

        if (weiXinWithdrawBillCriteria.getStartDate() != null && weiXinWithdrawBillCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
                  cb.between(r.get("createdDate"), new Date(weiXinWithdrawBillCriteria.getStartDate()),
                          new Date(weiXinWithdrawBillCriteria.getEndDate())));
        }

        if (weiXinWithdrawBillCriteria.getPartnerName() != null&&!"".equals(weiXinWithdrawBillCriteria.getPartnerName())) {
          predicate.getExpressions().add(
                  cb.equal(r.get("partner").get("partnerName"),
                          weiXinWithdrawBillCriteria.getPartnerName()));
        }

        if (weiXinWithdrawBillCriteria.getPartnerSid() != null&&!"".equals(weiXinWithdrawBillCriteria.getPartnerSid())) {
          predicate.getExpressions().add(
                  cb.equal(r.get("partner").get("partnerSid"),
                          weiXinWithdrawBillCriteria.getPartnerSid()));
        }
        return predicate;
      }
    };
  }
public WeiXinWithdrawBill findById(Long id){
    return weixinWithdrawBillRepository.findOne(id);
}


public void rejectConfirm(Long id){
  WeiXinWithdrawBill weiXinWithdrawBill=weixinWithdrawBillRepository.findOne(id);

  PartnerWallet partnerWallet=partnerWalletRepository.findByPartner(weiXinWithdrawBill.getPartner());
  PartnerWalletOnline partnerWalletOnline=partnerWalletOnlineRepository.findByPartner(weiXinWithdrawBill.getPartner());
  weiXinWithdrawBill.setState(2);
  weixinWithdrawBillRepository.save(weiXinWithdrawBill);
  if (weiXinWithdrawBill.getOfflineWallet() != 0) {
    PartnerWalletLog partnerWalletLog=new PartnerWalletLog();
    partnerWalletLog.setOrderSid(weiXinWithdrawBill.getWithdrawBillSid());
    partnerWalletLog.setAfterChangeMoney(partnerWallet.getAvailableBalance());
    partnerWalletLog.setBeforeChangeMoney(partnerWallet.getAvailableBalance());
    partnerWalletLog.setCreateDate(new Date());
    partnerWalletLog.setType(15005l);
    partnerWalletLog.setPartnerId(weiXinWithdrawBill.getPartner().getId());
    partnerWalletLogRepository.save(partnerWalletLog);
  }
  if (weiXinWithdrawBill.getOnlineWallet() != 0) {
    PartnerWalletOnlineLog partnerWalletOnlineLog=new PartnerWalletOnlineLog();
    partnerWalletOnlineLog.setPartnerId(weiXinWithdrawBill.getPartner().getId());
    partnerWalletOnlineLog.setType(16005l);
    partnerWalletOnlineLog.setBeforeChangeMoney(partnerWalletOnline.getAvailableBalance());
    partnerWalletOnlineLog.setAfterChangeMoney(partnerWalletOnline.getAvailableBalance());
    partnerWalletOnlineLog.setOrderSid(weiXinWithdrawBill.getWithdrawBillSid());
    partnerWalletOnlineLog.setChangeMoney(0l);
    partnerWalletOnlineLog.setCreateDate(new Date());
    partnerWalletOnlineLogRepository.save(partnerWalletOnlineLog);
  }
}

}
