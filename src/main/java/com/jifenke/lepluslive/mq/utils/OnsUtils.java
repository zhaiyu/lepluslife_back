package com.jifenke.lepluslive.mq.utils;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * @author ZM.Wang
 */
public class OnsUtils {

  private static final int SEND_TIMEOUT = 3000; //发送超时，单位毫秒

  public static Producer createAndStartProducer(String accessKey, String secretKey,
                                                String producerId, String address) {
    Properties properties = new Properties();
    properties.put(PropertyKeyConst.ProducerId, producerId);
    properties.put(PropertyKeyConst.AccessKey, accessKey);
    properties.put(PropertyKeyConst.SecretKey, secretKey);
    properties.put(PropertyKeyConst.ONSAddr, address);
    properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "" + SEND_TIMEOUT);
    Producer producer = ONSFactory.createProducer(properties);
    producer.start();
    return producer;
  }

}