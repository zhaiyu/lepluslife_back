package com.jifenke.lepluslive.mq.config;


import com.aliyun.openservices.ons.api.Producer;
import com.jifenke.lepluslive.mq.messenger.WxMsgMessenger;
import com.jifenke.lepluslive.mq.messenger.WxMsgMessengerImpl;
import com.jifenke.lepluslive.mq.utils.OnsUtils;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author ZM.Wang
 */
@Configuration
@EnableConfigurationProperties({
    AliyunConfig.Properties.class,
    AliyunConfig.OnsProperties.class,
    AliyunConfig.MsgProperties.class
})
public class AliyunConfig {

  @Autowired
  public Properties aliyun;
  @Autowired
  public OnsProperties ons;
  @Autowired
  public MsgProperties msg;

//
//  public final Properties aliyun;
//
//  public final OnsProperties ons;
//
//  public final MsgProperties msg;
//
//  public AliyunConfig(Properties aliyun, OnsProperties ons, MsgProperties msg) {
//    this.aliyun = aliyun;
//    this.ons = ons;
//    this.msg = msg;
//  }

  @Bean
  Producer msgProducer() {
    return OnsUtils.createAndStartProducer(
        aliyun.getAccessKeyId(),
        aliyun.getAccessKeySecret(),
        msg.getProducer(),
        ons.getAddress());
  }

  @Bean
  WxMsgMessenger wxMsgMessenger() {
    return new WxMsgMessengerImpl(
        msg.getTopic(),
        msgProducer(),
        msg.getMessageSendTag());
  }

  @Validated
  @ConfigurationProperties(prefix = "aliyun")
  public static class Properties {

    @NotBlank
    private String accessKeyId;
    @NotBlank
    private String accessKeySecret;

    public String getAccessKeyId() {
      return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
      this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
      return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
      this.accessKeySecret = accessKeySecret;
    }
  }


  @Validated
  @ConfigurationProperties(prefix = "aliyun.ons")
  public static class OnsProperties {

    @NotBlank
    private String address;

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }
  }

  @Validated
  @ConfigurationProperties(prefix = "wxMessenger")
  public static class MsgProperties {

    @NotBlank
    private String topic;

    @NotBlank
    private String producer;

    @NotBlank
    private String messageSendTag;

    public String getTopic() {
      return topic;
    }

    public void setTopic(String topic) {
      this.topic = topic;
    }

    public String getProducer() {
      return producer;
    }

    public void setProducer(String producer) {
      this.producer = producer;
    }

    public String getMessageSendTag() {
      return messageSendTag;
    }

    public void setMessageSendTag(String messageSendTag) {
      this.messageSendTag = messageSendTag;
    }
  }

}
