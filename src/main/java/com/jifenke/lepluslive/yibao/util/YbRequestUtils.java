package com.jifenke.lepluslive.yibao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jifenke.lepluslive.global.config.YBConstants;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 易宝请求工具类（参数生成&发送请求）
 * Created by zhangwen on 2017/7/14.
 */
public class YbRequestUtils {

  /**
   * 资质图片上传类型顺序
   * ID_CARD_FRONT 身份证正面、0
   * ID_CARD_BACK 身份证背面 、1
   * BANK_CARD_FRONT 银行卡正面、2
   * BANK_CARD_BACK 银行卡背面、3
   * PERSON_PHOTO 手持身份证照片、4
   * BUSSINESS_LICENSE 营业执照、5
   * BUSSINESS_CERTIFICATES 工商证、6
   * ORGANIZATION_CODE 组织机构代码证、7
   * TAX_REGISTRATION 税务登记证、8
   * BANK_ACCOUNT_LICENCE 银行开户许可证、9
   */
  private static final String[]
      UPLOAD_TYPE =
      {"ID_CARD_FRONT", "ID_CARD_BACK", "BANK_CARD_FRONT", "BANK_CARD_BACK", "PERSON_PHOTO",
       "BUSSINESS_LICENSE", "BUSSINESS_CERTIFICATES", "ORGANIZATION_CODE", "TAX_REGISTRATION",
       "BANK_ACCOUNT_LICENCE"};

  /**
   * 子账户注册  2017/7/14
   *
   * @param ledger 　注册信息
   * @return map　注册结果
   */
  public static Map<String, String> register(MerchantUserLedger ledger) {

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();

    String requestId = MvUtil.getOrderNumber(8);
    dataMap.put("requestid", requestId);
    dataMap.put("bindmobile", ledger.getBindMobile());
    //注册类型  1=PERSON(个人)|| 2=ENTERPRISE(企业)
    String customerType = ledger.getCustomerType() == 1 ? "PERSON" : "ENTERPRISE";
    dataMap.put("customertype", customerType);
    dataMap.put("signedname", ledger.getSignedName());
    dataMap.put("linkman", ledger.getLinkman());
    dataMap.put("idcard", ledger.getIdCard());
    if (ledger.getCustomerType() == 2) {
      dataMap.put("businesslicence", ledger.getBusinessLicence());
      dataMap.put("legalperson", ledger.getLegalPerson());
    }
    dataMap.put("minsettleamount", "" + ledger.getMinSettleAmount() / 100.0);
    dataMap.put("riskreserveday", "" + ledger.getRiskReserveDay());
    dataMap.put("bankaccountnumber", ledger.getBankAccountNumber());
    dataMap.put("bankname", ledger.getBankName());
    dataMap.put("accountname", ledger.getAccountName());
    //结算账户类型 1=PrivateCash(对私)||2=PublicCash(对公)
    String bankAccountYype = ledger.getBankAccountType() == 1 ? "PrivateCash" : "PublicCash";
    dataMap.put("bankaccounttype", bankAccountYype);
    dataMap.put("bankprovince", ledger.getBankProvince());
    dataMap.put("bankcity", ledger.getBankCity());

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.REGISTERAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.REGISTER_URL, data);
    return callBack(map);
  }

  /**
   * 子账户信息修改  2017/7/16
   *
   * @param dbLedger 数据库商户信息
   * @param ledger   修改信息
   * @return map　修改通讯结果（不代表修改成功）
   */
  public static Map<String, String> modify(MerchantUserLedger dbLedger, MerchantUserLedger ledger) {

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();

    String requestId = MvUtil.getOrderNumber(8);
    dataMap.put("requestid", requestId);
    dataMap.put("ledgerno", dbLedger.getLedgerNo());
    dataMap.put("bankaccountnumber", ledger.getBankAccountNumber());
    dataMap.put("bankname", ledger.getBankName());
    dataMap.put("accountname", dbLedger.getAccountName());
    //结算账户类型 1=PrivateCash(对私)||2=PublicCash(对公)
    String bankAccountYype = dbLedger.getBankAccountType() == 1 ? "PrivateCash" : "PublicCash";
    dataMap.put("bankaccounttype", bankAccountYype);
    dataMap.put("bankprovince", ledger.getBankProvince());
    dataMap.put("bankcity", ledger.getBankCity());
    dataMap.put("minsettleamount", "" + ledger.getMinSettleAmount() / 100.0);
    dataMap.put("riskreserveday", "" + dbLedger.getRiskReserveDay());
    //是否自助结算  todo: 需确认
    dataMap.put("manualsettle", "N");
    //后台接受修改成功回调通知地址
    dataMap.put("callbackurl", YBConstants.MODIFY_CALLBACK_URL);
    dataMap.put("bindmobile", ledger.getBindMobile());

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.MODIFYREQUESTAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.MODIFY_REQUEST_URL, data);
    return callBack(map);
  }

  /**
   * 子账户信息修改查询  2017/7/16
   *
   * @param requestId 修改请求号
   * @return map　修改结果
   */
  public static Map<String, String> modifyQuery(String requestId) {

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("requestid", requestId);

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.QUERYMODIFYREQUESTAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.QUERY_MODIFY_REQUEST_URL, data);
    return callBack(map);
  }

  /**
   * 易宝子商户余额查询  2017/7/17
   *
   * @param ledgerNos 易宝的子商户号(可多个，多个已‘,’分隔)
   */
  public static Map<String, String> queryBalance(String ledgerNos) {
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("ledgerno", ledgerNos);

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.QUERYBALANCEAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.QUERY_BALANCE_URL, data);
    return callBack(map);
  }

  /**
   * 分账方审核结果查询  2017/7/17
   *
   * @param ledgerNo 易宝的子商户号
   */
  public static Map<String, String> queryCheckRecord(String ledgerNo) {
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("ledgerno", ledgerNo);

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.QUERYRECORDAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.QUERY_CHECK_RECORD_URL, data);
    return callBack(map);
  }

  /**
   * 转账  2017/7/19
   *
   * @param ledgerNo 易宝的子商户号
   * @param amount   转账金额（注意：此时单位为分，调用接口时需/100转换为元）
   * @param orderSid 转账单号(请求号)
   * @return map　转账结果
   */
  public static Map<String, String> transfer(String ledgerNo, Long amount,
                                             String orderSid) {

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("requestid", orderSid);
    dataMap.put("ledgerno", ledgerNo);
    dataMap.put("amount", "" + amount / 100.0);

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.TRANSFERAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.TRANSFER_URL, data);
    return callBack(map);
  }

  /**
   * 转账查询  2017/7/19
   *
   * @param requestId 转账请求号
   * @return map　转账结果
   */
  public static Map<String, String> queryTransfer(String requestId) {

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("requestid", requestId);

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.TRANSFERQUERYAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.TRANSFER_QUERY_URL, data);
    return callBack(map);
  }

  /**
   * 退款  2017/8/6
   *
   * @param orderSid 要退款的订单号
   * @param amount   退款金额（注意：需小于等于该订单金额，此时单位为分，调用接口时需/100转换为元）
   * @return map　退款结果
   */
  public static Map<String, String> refund(String orderSid, Long amount) {

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("requestid", MvUtil.getOrderNumber());
    dataMap.put("orderrequestid", orderSid);
    dataMap.put("amount", "" + amount / 100.0);
    dataMap.put("confirm", "1");

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.REFUNDAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.REFUND_URL, data);
    return callBack(map);
  }

  /**
   * 退款结果查询  2017/8/6
   *
   * @param orderSid 要退款的订单号
   * @return map　退款结果
   */
  public static Map<String, String> queryRefund(String orderSid) {

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("orderrequestid", orderSid);

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.QUERYREFUNDAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.QUERY_REFUND_URL, data);
    return callBack(map);
  }

  /**
   * 结算结果查询  2017/7/25
   *
   * @param ledgerNos 易宝的子商户号(可多个，多个已‘|’分隔)
   * @param date      结算日期(yyyy-MM-dd. 若为空，则查询近 7 天内的结算结果) null时查询最近七天
   */
  public static Map<String, String> querySettlement(String ledgerNos, String date) {
    Map<String, String> dataMap = getCommonDataMap();
    dataMap.put("ledgerno", ledgerNos);
    if (date != null) {
      dataMap.put("date", date);
    }
    //默认查询详情本版关闭,当查询详情时“Y”，ledgerno为空或只能为一个商编，且日期必填
    //dataMap.put("isdetail", "N");

    String data = ZGTUtils.buildData(dataMap, ZGTUtils.QUERYSETTLEMENTAPI_REQUEST_HMAC_ORDER);
    Map<String, String> map = ZGTUtils.httpPost(YBConstants.QUERY_SETTLEMENT_URL, data);
    return callBack(map);
  }

  /**
   * 分账方资质上传 2017/7/18
   *
   * @param ledgerNo 易宝子商户号
   * @param picPath  图片保存完整路径
   * @param type     图片所属类型在易宝上传接口fileType数组属性(从0开始)
   */
  public static Map<String, String> uploadQualification(String ledgerNo, String picPath,
                                                        Integer type) {
    picPath = ZGTUtils.getBasePath() + '/' + picPath;
    String fileType = UPLOAD_TYPE[type];
    StringBuffer signature = new StringBuffer();
    signature.append(ZGTUtils.getCustomernumber()).append(ledgerNo).append(fileType);
    String hmac = Digest.hmacSign(signature.toString(), ZGTUtils.getKeyForHmac());
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("customernumber", ZGTUtils.getCustomernumber());
    dataMap.put("ledgerno", ledgerNo);
    dataMap.put("filetype", fileType);
    dataMap.put("hmac", hmac); // hmac 按照 properties 中声明的顺序进行签名
    String dataJsonString = JSON.toJSONString(dataMap); //map 中数据转为 json 格式
    String content = AESUtil.encrypt(dataJsonString, ZGTUtils.getKeyForAes());
    Map<String, Object> params = new HashMap<>();
    params.put("customernumber", ZGTUtils.getCustomernumber());
    params.put("data", content);// 加密 json 格式数据作为 value 赋值给 data 参数
    File file = new File(picPath);
    params.put("file", file);
    System.out.println(params);
    try {
      String
          data =
          ZGTUtils.uploadFile(params, YBConstants.UPLOAD_LEDGER_QUALIFICATIONS_URL);
      TreeMap<String, String>
          treeMap =
          JSON.parseObject(data, new TypeReference<TreeMap<String, String>>() {
          });
      return callBack(treeMap);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 请求响应中解析返回结果  2017/7/16
   *
   * @param stringMap 响应Map
   * @return Map
   */
  private static Map<String, String> callBack(Map<String, String> stringMap) {
    System.out.println("易宝的同步响应：" + stringMap);

    if (stringMap.containsKey("code")) {
      return stringMap;
    }
    Map<String, String> responseDataMap = ZGTUtils.decryptData(stringMap.get("data"));
    System.out.println("data解密后明文：" + responseDataMap);
    return responseDataMap;
  }

  /**
   * 获取公共dataMAP  2017/7/14
   */
  private static Map<String, String> getCommonDataMap() {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("customernumber", ZGTUtils.getCustomernumber());
    return dataMap;
  }

}
