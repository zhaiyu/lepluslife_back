package com.jifenke.lepluslive.shortMessage.controller;


import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.shortMessage.domain.criteria.ShortMessageCriteria;
import com.jifenke.lepluslive.shortMessage.domain.entities.LeJiaUser_ShortMessage;
import com.jifenke.lepluslive.shortMessage.domain.entities.ReplyShortMessage;
import com.jifenke.lepluslive.shortMessage.domain.entities.ShortMessageScene;
import com.jifenke.lepluslive.shortMessage.service.LeJiaUser_ShortMessageService;
import com.jifenke.lepluslive.shortMessage.service.ReplyShortMessageService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageSceneService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by lss on 16/5/9.
 */
@RestController
@RequestMapping("/manage")
public class ShortMessageController {

  @Inject
  private UserService userService;

  @Inject
  private ShortMessageService shortMessageService;

  @Inject
  private ShortMessageSceneService shortMessageSceneService;

  @Inject
  private LeJiaUser_ShortMessageService leJiaUser_ShortMessageService;

  @Inject
  private ReplyShortMessageService replyShortMessageService;


  @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult sendMessage(@RequestBody String json) {

    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map = new HashMap<>();
    // 将json字符串转换成jsonObject
    JSONObject jsonObject = JSONObject.fromObject(json);

    String content = jsonObject.get("messageContent").toString();
    String phoneNumber = jsonObject.get("phoneNumber").toString();
    LeJiaUser leJiaUser1 = userService.findUserByPhoneNumber(phoneNumber);
    ShortMessageScene scene = shortMessageSceneService.findBySid("16110315524199857");
    Map<String, String> returnMap = new HashMap<>();
      boolean b=unsubscribe(phoneNumber,scene);
    if (b) {
      boolean bl = shortMessageService.sendOneMessage(leJiaUser1, scene, content);

      if (bl) {
        returnMap.put("state", "成功");
      } else {
        returnMap.put("state", "失败");
      }
    } else {

      returnMap.put("state", "退订");
    }
    return LejiaResult.ok(returnMap);
  }

  //查看记录跳页
  @RequestMapping(value = "/shortMessagesListPage", method = RequestMethod.GET)
  public ModelAndView serchSendRecordPage(Model model) {
    List<ShortMessageScene> shortMessageSceneList = shortMessageSceneService.findAll();
    Set<String> set = new HashSet<String>();
    for (ShortMessageScene shortMessageScene : shortMessageSceneList) {
      set.add(shortMessageScene.getCategory());
    }
    List<String> categoryList = new ArrayList<String>();
    categoryList.addAll(set);
    model.addAttribute("categoryList", categoryList);
    return MvUtil.go("/shortMessage/shortMessagesList");
  }


  @RequestMapping(value = "/getShortMessageByAjax", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getShortMessageByAjax(@RequestBody ShortMessageCriteria shortMessageCriteria) {
    Page page = shortMessageService.findShortMessageByPage(shortMessageCriteria, 10);
    return LejiaResult.ok(page);
  }

  @RequestMapping(value = "/shortMessage/serchDetails", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getShortMessageDetailsByAjax(@RequestBody String jsonStr) {
    JSONObject json = JSONObject.fromObject(jsonStr);
    String reqId = json.get("reqId").toString();
    String offsetStr = json.get("offset").toString();
    int offset = Integer.parseInt(offsetStr);
    Long shortMessageId = shortMessageService.findIdByReqId(reqId);
    List<Object[]> objectList = shortMessageService.findShortMessageDetailsById(shortMessageId);
    List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
    Map<String, Object> dataMap = new HashMap<String, Object>();
    if (offset <= 0) {
      offset = 1;
    }
    int present = offset * 10;
    int q = present - 10;
    int h;

    if (offset * 10 > objectList.size()) {
      h = objectList.size();
    } else {
      h = present;
    }
    int successAcount = 0;
    for (int i = 0; i < objectList.size(); i++) {
      String sendState = "";
      if (objectList.get(0)[5] != null) {
        sendState = objectList.get(0)[5].toString();
      }
      if ("1".equals(sendState)) {
        successAcount = successAcount + 1;
      }
    }
    int defeatedAcount = objectList.size() - successAcount;
    for (int i = q; i < h; i++) {
      Map<String, String> map = new HashMap<String, String>();
      String userSid = objectList.get(i)[0].toString();
      map.put("userSid", userSid);
      String head_image_url = objectList.get(i)[1].toString();
      map.put("head_image_url", head_image_url);
      String nickname = objectList.get(i)[2].toString();
      map.put("nickname", nickname);
      if (objectList.get(i)[3] == null) {
        map.put("phone_number", null);
      } else {
        String phone_number = objectList.get(i)[3].toString();
        map.put("phone_number", phone_number);
      }
      dataList.add(map);
    }
    String sendDate = objectList.get(0)[4].toString();
    String sendState = "";
    if (objectList.get(0)[5] != null) {
      sendState = objectList.get(0)[5].toString();
    }
    String shortMessageContent = objectList.get(0)[6].toString();
    String sendAcount = objectList.get(0)[7].toString();
    dataMap.put("content", dataList);
    Integer totalElements = objectList.size();
    double db = totalElements.intValue();
    Double dbPages = Math.ceil(db / 10);
    Integer totalPages = dbPages.intValue();
    dataMap.put("totalElements", totalElements);
    dataMap.put("totalPages", totalPages);
    dataMap.put("sendDate", sendDate);
    dataMap.put("sendState", sendState);
    dataMap.put("shortMessageContent", shortMessageContent);
    dataMap.put("sendAcount", sendAcount);
    dataMap.put("successAcount", successAcount);
    dataMap.put("defeatedAcount", defeatedAcount);
    return LejiaResult.ok(dataMap);
  }


  //状态报告和上行消息
  @RequestMapping(value = "/shortMessage/statusReport", method = RequestMethod.POST)
  public String statusReport(int msgtype, String phone, String subid, String content,
                             String receivetime, String reqid, String extend, String sendtime,
                             String state) {
    if (msgtype == 0) {
      ReplyShortMessage replyShortMessage = new ReplyShortMessage();
      replyShortMessage.setContent(content);
      replyShortMessage.setPhone(phone);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

      try {
        Date d = sdf.parse(receivetime);
        replyShortMessage.setReceivetime(d);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      replyShortMessage.setSubid(subid);
      shortMessageService.saveReplyShortMessage(replyShortMessage);


    }
    if (msgtype == 2) {

      Long shortMessageId = shortMessageService.findIdByReqId(reqid);
      LeJiaUser user = userService.findUserByPhoneNumber(phone);
      Long userId = user.getId();
      LeJiaUser_ShortMessage
          leJiaUser_ShortMessage =
          leJiaUser_ShortMessageService.findOneByUserIdAndShortMEssageId(shortMessageId, userId);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      try {
        Date d = sdf.parse(receivetime);
        leJiaUser_ShortMessage.setReceivetime(d);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      if (state.equals("0")) {
        leJiaUser_ShortMessage.setSendState(1);
      } else {
        leJiaUser_ShortMessage.setSendState(0);
      }
      leJiaUser_ShortMessage.setSendStatusCode(state);
      leJiaUser_ShortMessageService.saveOne(leJiaUser_ShortMessage);
    }
    return "ok";
  }

  //自动发送设置
  @RequestMapping(value = "/shortMessage/setAutomaticSendShortMesssage", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult setAutomaticSendShortMesssage(@RequestBody String sceneStr) {
    JSONObject jsonObject = JSONObject.fromObject(sceneStr);
    if (jsonObject.get("offLineAddedcondition1") != null) {
      String offLineAddedcondition1 = jsonObject.get("offLineAddedcondition1").toString();
      String offLineAddedcondition2 = jsonObject.get("offLineAddedcondition2").toString();
      double addedcondition1 = Double.valueOf(offLineAddedcondition1) * 100;
      double addedcondition2 = Double.valueOf(offLineAddedcondition2) * 100;
      Long at1 = Long.valueOf((long) addedcondition1);
      Long at2 = Long.valueOf((long) addedcondition2);
      ShortMessageScene shortMessageScene = shortMessageSceneService.findSceneByName("线下订单后发送");
      shortMessageScene.setAddedcondition1(at1);
      shortMessageScene.setAddedcondition2(at2);
      shortMessageSceneService.saveShortMessageScene(shortMessageScene);
    }
    if (jsonObject.get("sceneSid") != null) {
      String sceneSidStr = jsonObject.get("sceneSid").toString();
      String recloserStr = jsonObject.get("recloser").toString();
      ShortMessageScene shortMessageScene = shortMessageSceneService.findBySid(sceneSidStr);
      if (recloserStr.equals("1")) {
        shortMessageScene.setRecloser(true);
      }
      if (recloserStr.equals("0")) {
        shortMessageScene.setRecloser(false);
      }
      shortMessageSceneService.saveShortMessageScene(shortMessageScene);
    }
    return LejiaResult.ok();
  }

//自动发短信

  @RequestMapping(value = "/automaticSendShortMessage", method = RequestMethod.POST)
  public String automaticSendShortMessage(String userSid, String offLineOrderSid,
                                          String onLineOrderSid, String sceneSid, String sign) {
    try {
      boolean b2 = true;
      String appkey = "!w3-+]";
      String secret = "OZI-#<>(_{*S3X$@}%DU)9[28MJ.;N1W57&:'\\T,/G=CQ4YH|`~!A0PL\"RB]EKVF+?^6";
// 创建参数表
      Map<String, String> paramMap = new HashMap<String, String>();
      paramMap.put("userSid", userSid);
      paramMap.put("offLineOrderSid", offLineOrderSid);
      paramMap.put("onLineOrderSid", onLineOrderSid);
      paramMap.put("sceneSid", sceneSid);
// 对参数名进行字典排序
      String[] keyArray = paramMap.keySet().toArray(new String[0]);
      Arrays.sort(keyArray);
// 拼接有序的参数名-值串
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(appkey);
      for (String key : keyArray) {
        stringBuilder.append(key).append(paramMap.get(key));
      }
      stringBuilder.append(secret);
      String codes = stringBuilder.toString();
      String sign1 = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
      if (sign.equals(sign1)) {
        ShortMessageScene shortMessageScene = shortMessageSceneService.findBySid(sceneSid);
        LeJiaUser user = null;
        if (userSid != "") {
          user = userService.findUserBySid(userSid);
        }
        if (onLineOrderSid != "") {
          OnLineOrder
              onLineOrder = shortMessageService.findOnLineOrderBySid(onLineOrderSid);
          user = onLineOrder.getLeJiaUser();
          if(onLineOrder.getPayBackA()==0L){
            shortMessageScene=shortMessageSceneService.findBySid("16110315511460726");
          }
        }
        if (offLineOrderSid != "") {
          OffLineOrder
              offLineOrder = shortMessageService.findOffLineOrderBySid(offLineOrderSid);
          user = offLineOrder.getLeJiaUser();
          //验证线下订单的额外条件

          Long totalPrice = offLineOrder.getTotalPrice();
          Long rebate = offLineOrder.getRebate();
          Long ad1 = shortMessageScene.getAddedcondition1();
          Long ad2 = shortMessageScene.getAddedcondition2();
          int i1 = totalPrice.compareTo(ad1);
          int i2 = rebate.compareTo(ad2);
          if (i1 <= 0 || i2 <= 0) {
            b2 = false;
          }

        }
        //公共的变量
        String memberName = "";
        String scoreATotal = "0";
        String scoreANow = "0";
        String scoreBNow = "0";
        String scoreBTotal = "0";
        String memberPhone = "";
        //注册用的变量
        String scoreARegister = "0";
        String scoreBRegister = "0";
        //邀请好友的变量
        String scoreAShare = "0";
        String scoreBShare = "0";
        //线下订单有关的变量
        String offlineOrderMerchantName = "";
        String offlineOrderTotalPrice = "";
        String offlineOrderTime = "";
        String offlineOrderNumber = "";
        String offlineOrderUseScorea = "0";
        String offlineOrderSendScorea = "0";
        String offlineOrderSendScoreb = "0";
        //商城订单有关的变量
        String onlineOrderTime = "";
        String onlineOrderNumber = "";
        String onlineTotalPrice = "0";
        String onlineUseRMB = "0";
        String onlineUseScoreb = "0";
        String onlineSendScorea = "0";
//替换公共变量
        List<Object[]> objects = shortMessageService.findPublicVariable(user.getUserSid());
        if (objects.size() != 0) {
          if (objects.get(0)[0] != null) {
            memberName = objects.get(0)[0].toString();
          }
          if (objects.get(0)[1] != null) {
            scoreATotal = objects.get(0)[1].toString();
            double a = new Double(scoreATotal);
            a = a / 100.0;
            scoreATotal = String.valueOf(a);
          }
          if (objects.get(0)[2] != null) {
            scoreANow = objects.get(0)[2].toString();
            double a = new Double(scoreANow);
            a = a / 100.0;
            scoreANow = String.valueOf(a);
          }
          if (objects.get(0)[3] != null) {
            scoreBNow = objects.get(0)[3].toString();
          }
          if (objects.get(0)[4] != null) {
            scoreBTotal = objects.get(0)[4].toString();
          }
          if (objects.get(0)[5] != null) {
            memberPhone = objects.get(0)[5].toString();
          }
        }
        String content = shortMessageScene.getContent();
        String contentStrArray[] = content.split("@");
        for (int i = 0; i < contentStrArray.length; i++) {
          if ("memberName".equals(contentStrArray[i])) {
            contentStrArray[i] = memberName;
          }
          if ("scoreATotal".equals(contentStrArray[i])) {
            contentStrArray[i] = scoreATotal;
          }
          if ("scoreANow".equals(contentStrArray[i])) {
            contentStrArray[i] = scoreANow;
          }
          if ("scoreBNow".equals(contentStrArray[i])) {
            contentStrArray[i] = scoreBNow;
          }
          if ("scoreBTotal".equals(contentStrArray[i])) {
            contentStrArray[i] = scoreBTotal;
          }
          if ("memberPhone".equals(contentStrArray[i])) {
            contentStrArray[i] = memberPhone;
          }
        }
//传的参数是会员sid的
        if (shortMessageScene.getParameterKind() == 1) {
          List<Object> objectList1 = shortMessageService.findRegistryScoreA(userSid);
          if (objectList1.size() != 0) {
            if (objectList1.get(0) != null) {
              scoreARegister = objectList1.get(0).toString();
              double a = new Double(scoreARegister);
              a = a / 100.0;
              scoreARegister = String.valueOf(a);

            }
          }
          List<Object> objectList2 = shortMessageService.findRegistryScoreB(userSid);
          if (objectList2.size() != 0) {
            if (objectList2.get(0) != null) {
              scoreBRegister = objectList2.get(0).toString();
            }
          }
          List<Object> objectList3 = shortMessageService.findShareScoreA(userSid);
          if (objectList3.size() != 0) {
            if (objectList3.get(0) != null) {
              scoreAShare = objectList3.get(0).toString();
              double a = new Double(scoreAShare);
              a = a / 100.0;
              scoreAShare = String.valueOf(a);
            }
          }
          List<Object> objectList4 = shortMessageService.findShareScoreB(userSid);
          if (objectList4.size() != 0) {
            if (objectList4.get(0) != null) {
              scoreBShare = objectList4.get(0).toString();
            }
          }
          for (int i = 0; i < contentStrArray.length; i++) {
            if ("scoreARegister".equals(contentStrArray[i])) {
              contentStrArray[i] = scoreARegister;
            }
            if ("scoreBRegister".equals(contentStrArray[i])) {
              contentStrArray[i] = scoreBRegister;
            }
            if ("scoreAShare".equals(contentStrArray[i])) {
              contentStrArray[i] = scoreAShare;
            }
            if ("scoreBShare".equals(contentStrArray[i])) {
              contentStrArray[i] = scoreBShare;
            }
          }
        }
//传的参数是线上订单Sid时
        if (shortMessageScene.getParameterKind() == 2) {
          List<Object[]> objectList5 = shortMessageService.findOnLineOrderVariable(onLineOrderSid);
          if (objectList5.size() != 0) {
            if (objectList5.get(0) != null) {
              if (objectList5.get(0)[0] != null) {
                onlineOrderTime = objectList5.get(0)[0].toString();
              }
              if (objectList5.get(0)[1] != null) {
                onlineOrderNumber = objectList5.get(0)[1].toString();
              }
              if (objectList5.get(0)[2] != null) {
                onlineTotalPrice = objectList5.get(0)[2].toString();
                double a = new Double(onlineTotalPrice);
                a = a / 100.0;
                onlineTotalPrice = String.valueOf(a);
              }
              if (objectList5.get(0)[3] != null) {
                onlineUseRMB = objectList5.get(0)[3].toString();
                double a = new Double(onlineUseRMB);
                a = a / 100.0;
                onlineUseRMB = String.valueOf(a);
              }
              if (objectList5.get(0)[4] != null) {
                onlineUseScoreb = objectList5.get(0)[4].toString();
              }
              if (objectList5.get(0)[5] != null) {
                onlineSendScorea = objectList5.get(0)[5].toString();
                double a = new Double(onlineSendScorea);
                a = a / 100.0;
                onlineSendScorea = String.valueOf(a);
              }
            }
          }
          for (int i = 0; i < contentStrArray.length; i++) {
            if ("onlineOrderTime".equals(contentStrArray[i])) {
              contentStrArray[i] = onlineOrderTime;
            }
            if ("onlineOrderNumber".equals(contentStrArray[i])) {
              contentStrArray[i] = onlineOrderNumber;
            }
            if ("onlineTotalPrice".equals(contentStrArray[i])) {
              contentStrArray[i] = onlineTotalPrice;
            }
            if ("onlineUseRMB".equals(contentStrArray[i])) {
              contentStrArray[i] = onlineUseRMB;
            }
            if ("onlineUseScoreb".equals(contentStrArray[i])) {
              contentStrArray[i] = onlineUseScoreb;
            }
            if ("onlineSendScorea".equals(contentStrArray[i])) {
              contentStrArray[i] = onlineSendScorea;
            }
          }
        }
//  传的参数是线下订单Sid时
        if (shortMessageScene.getParameterKind() == 3) {
          List<Object[]>
              objectList6 =
              shortMessageService.findOffLineOrderVariable(offLineOrderSid);
          if (objectList6.size() != 0) {
            if (objectList6.get(0) != null) {
              if (objectList6.get(0)[0] != null) {
                offlineOrderTotalPrice = objectList6.get(0)[0].toString();
                double a = new Double(offlineOrderTotalPrice);
                a = a / 100.0;
                offlineOrderTotalPrice = String.valueOf(a);
              }
              if (objectList6.get(0)[1] != null) {
                offlineOrderTime = objectList6.get(0)[1].toString();
              }
              if (objectList6.get(0)[2] != null) {
                offlineOrderNumber = objectList6.get(0)[2].toString();
              }
              if (objectList6.get(0)[3] != null) {
                offlineOrderUseScorea = objectList6.get(0)[3].toString();
                double a = new Double(offlineOrderUseScorea);
                a = a / 100.0;
                offlineOrderUseScorea = String.valueOf(a);
              }
              if (objectList6.get(0)[4] != null) {
                offlineOrderSendScorea = objectList6.get(0)[4].toString();
                double a = new Double(offlineOrderSendScorea);
                a = a / 100.0;
                offlineOrderSendScorea = String.valueOf(a);
              }
              if (objectList6.get(0)[5] != null) {
                offlineOrderSendScoreb = objectList6.get(0)[5].toString();
                double a = new Double(offlineOrderSendScoreb);
                a = a / 100.0;
                offlineOrderSendScoreb = String.valueOf(a);
              }
              if (objectList6.get(0)[6] != null) {
                offlineOrderMerchantName = objectList6.get(0)[6].toString();
              }
            }
          }
          for (int i = 0; i < contentStrArray.length; i++) {
            if ("offlineOrderTotalPrice".equals(contentStrArray[i])) {
              contentStrArray[i] = offlineOrderTotalPrice;
            }
            if ("offlineOrderTime".equals(contentStrArray[i])) {
              contentStrArray[i] = offlineOrderTime;
            }
            if ("offlineOrderNumber".equals(contentStrArray[i])) {
              contentStrArray[i] = offlineOrderNumber;
            }
            if ("offlineOrderUseScorea".equals(contentStrArray[i])) {
              contentStrArray[i] = offlineOrderUseScorea;
            }
            if ("offlineOrderSendScorea".equals(contentStrArray[i])) {
              contentStrArray[i] = offlineOrderSendScorea;
            }
            if ("offlineOrderSendScoreb".equals(contentStrArray[i])) {
              contentStrArray[i] = offlineOrderSendScoreb;
            }
            if ("offlineOrderMerchantName".equals(contentStrArray[i])) {
              contentStrArray[i] = offlineOrderMerchantName;
            }
          }
        }
        String contentStr = "";
        for (int i = 0; i < contentStrArray.length; i++) {
          contentStr = contentStr + contentStrArray[i];
        }
        boolean b=unsubscribe(user.getPhoneNumber(),shortMessageScene);
        if (b && b2 && shortMessageScene.isRecloser()) {
          shortMessageService.sendOneMessage(user, shortMessageScene, contentStr);
          return "ok";
        } else {
          return "Switch off";
        }

      } else {
        return "defeat";
      }
    } catch (Exception e) {
      return "defeat";
    }
  }
//判断退没退订 true没退订 false退订了
  public boolean unsubscribe(String phoneNumber,ShortMessageScene shortMessageScene) {
      List<ReplyShortMessage>
              replyShortList =
              replyShortMessageService.findByPhoneNumber(phoneNumber);
      boolean b = true;
      for (int i = 0; i < replyShortList.size(); i++) {
          String c = replyShortList.get(i).getContent();
          ReplyShortMessage message = replyShortList.get(i);
          String cstr = c;
          char[] bm;
          bm = cstr.toCharArray();
          c = String.valueOf(bm[0]);
          if (c.equals("t") || c.equals("T")) {
              b = false;
          }
      }
      if(b==false){
         if(shortMessageScene.isUnsubscribeRecloser()==true) {
             b=true;
         }
          if(shortMessageScene.isUnsubscribeRecloser()==false) {
              b=false;
          }
      }
      return b;
  }

}
