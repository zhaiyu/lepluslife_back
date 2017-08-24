package com.jifenke.lepluslive.mq.messenger;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifenke.lepluslive.mq.dto.WxMessageRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author ZM.Wang
 */
public class WxMsgMessengerImpl implements WxMsgMessenger {

  private static final Logger logger = LoggerFactory.getLogger(WxMsgMessengerImpl.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final String topic;
  private final Producer producer;
  private final String messageSendTag;

  public WxMsgMessengerImpl(String topic, Producer producer, String messageSendTag) {
    this.topic = topic;
    this.producer = producer;
    this.messageSendTag = messageSendTag;
  }

  @Override
  public void publishWxMsg(WxMessageRequest request) {
    String tag = this.messageSendTag;
    String key = UUID.randomUUID().toString().replaceAll("-", "");
    byte[] body;
    try {
      body = MAPPER.writeValueAsBytes(request);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("发布发送微信通知消息对象转json异常");
    }
    Message message = new Message(topic, tag, key, body);
    message.setStartDeliverTime(System.currentTimeMillis());
    SendResult sendResult = producer.send(message);
    logger.info("发布发送微信通知消息[{}]至主题[{}]，标签[{}]，延迟[{}]", sendResult.getMessageId(), topic, tag, 0);
  }

}
