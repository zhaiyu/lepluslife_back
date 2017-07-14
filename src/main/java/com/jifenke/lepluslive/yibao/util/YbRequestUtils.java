package com.jifenke.lepluslive.yibao.util;

import com.jifenke.lepluslive.global.config.YBConstants;
import com.jifenke.lepluslive.global.util.HttpClientUtil;
import com.jifenke.lepluslive.global.util.JsonUtils;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;

import java.util.HashMap;
import java.util.Map;

/**
 * 易宝请求工具类（参数生成&发送请求）
 * Created by zhangwen on 2017/7/14.
 */
public class YbRequestUtils {

  /**
   * 子账户注册  2017/7/14
   *
   * @param ledger 　注册信息
   * @return map　注册结果
   */
  public static Map<String, Object> register(MerchantUserLedger ledger) {

    //返回结果

    //请求加密参数
    Map<String, String> dataMap = getCommonDataMap();
    //Hmac签名参数
    StringBuffer array = new StringBuffer(YBConstants.CUSTOMER_NUMBER + ",");

    String requestId = MvUtil.getOrderNumber(8);
    dataMap.put("requestid", requestId);
    array.append(requestId).append(",");

    dataMap.put("bindmobile", ledger.getBindMobile());
    array.append(ledger.getBindMobile()).append(",");

    //注册类型  1=PERSON(个人)|| 2=ENTERPRISE(企业)
    String customerType = ledger.getCustomerType() == 1 ? "PERSON" : "ENTERPRISE";
    dataMap.put("customertype", customerType);
    array.append(customerType).append(",");

    dataMap.put("signedname", ledger.getSignedName());
    array.append(ledger.getSignedName()).append(",");

    dataMap.put("linkman", ledger.getLinkman());
    array.append(ledger.getLinkman()).append(",");

    dataMap.put("idcard", ledger.getIdCard());
    array.append(ledger.getIdCard()).append(",");

    if (ledger.getCustomerType() == 2) {
      dataMap.put("businesslicence", ledger.getBusinessLicence());
      array.append(ledger.getBusinessLicence()).append(",");
      dataMap.put("legalperson", ledger.getLegalPerson());
      array.append(ledger.getLegalPerson()).append(",");
    } else {
      array.append(" ,");
      array.append(" ,");
    }

    dataMap.put("minsettleamount", "" + ledger.getMinSettleAmount() / 100);
    array.append(ledger.getMinSettleAmount() / 100).append(",");

    dataMap.put("riskreserveday", "" + ledger.getRiskReserveDay());
    array.append(ledger.getRiskReserveDay()).append(",");

    dataMap.put("bankaccountnumber", ledger.getBankAccountNumber());
    array.append(ledger.getBankAccountNumber()).append(",");

    dataMap.put("bankname", ledger.getBankName());
    array.append(ledger.getBankName()).append(",");

    dataMap.put("accountname", ledger.getAccountName());
    array.append(ledger.getAccountName()).append(",");

    //结算账户类型 1=PrivateCash(对私)||2=PublicCash(对公)
    String bankAccountYype = ledger.getBankAccountType() == 1 ? "PrivateCash" : "PublicCash";
    dataMap.put("bankaccounttype", bankAccountYype);
    array.append(bankAccountYype).append(",");

    dataMap.put("bankprovince", ledger.getBankProvince());
    array.append(ledger.getBankProvince()).append(",");

    dataMap.put("bankcity", ledger.getBankCity());
    array.append(ledger.getBankCity());

    String[] stringArray = getCommonDataMap(array);

    System.out.println("参与Hmac签名数组===" + String.join(",", stringArray));

    String data = DataUtil.getData(dataMap, stringArray);

    dataMap.clear();
    dataMap.put("data", data);
    dataMap.put("customernumber", YBConstants.CUSTOMER_NUMBER);

    String resultString = HttpClientUtil.post(YBConstants.REGISTER_URL, dataMap, "utf-8");

    System.out.println("请求返回字符串===" + resultString);

    Map<String, Object> map = JsonUtils.jsonToPojo(resultString, Map.class);

    if (map.containsKey("code")) {
      return map;
    }

    String decrypt = AESUtil.decrypt(map.get("data").toString(), YBConstants.SECRET_16);

    Map<String, Object> resultMap = JsonUtils.jsonToPojo(decrypt, Map.class);

    System.out.println("请求返回解密后Map===" + resultMap.toString());

    return map;
  }

  /**
   * 获取公共dataMAP  2017/7/14
   */
  private static Map<String, String> getCommonDataMap() {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("customernumber", YBConstants.CUSTOMER_NUMBER);
    return dataMap;
  }

  /**
   * 获取加密stringArray  2017/7/14
   */
  private static String[] getCommonDataMap(StringBuffer s) {
    return s.toString().split(",");
  }

}
