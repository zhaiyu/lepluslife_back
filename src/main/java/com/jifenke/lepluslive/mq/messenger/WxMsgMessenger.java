package com.jifenke.lepluslive.mq.messenger;


import com.jifenke.lepluslive.mq.dto.WxMessageRequest;

/**
 * @author ZM.Wang
 */
public interface WxMsgMessenger {

  void publishWxMsg(WxMessageRequest request);

}
