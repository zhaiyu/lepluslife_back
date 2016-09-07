package com.jifenke.lepluslive.weixin.service;

import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.weixin.domain.entities.Dictionary;
import com.jifenke.lepluslive.weixin.repository.DictionaryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by zhangwen on 2016/5/25.
 */
@Service
@Transactional(readOnly = true)
public class DictionaryService {

  @Inject
  private DictionaryRepository dictionaryRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Dictionary findDictionaryById(Long id) {

    return dictionaryRepository.findOne(id);
  }

  /**
   * 修改
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void update(Long id, String value) throws Exception {
    try {
      Dictionary dictionary = dictionaryRepository.findOne(id);
      dictionary.setValue(value);
      dictionaryRepository.save(dictionary);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 判断发送状态
   *
   * @return true=可发送   false=不可发送
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public boolean checkSendMassStatus() {

    Dictionary dictionary1 = dictionaryRepository.findOne(16L);
    Dictionary dictionary2 = dictionaryRepository.findOne(17L);
    //如果今日已发送或累计群发4条则返回false
    Integer time = Integer.valueOf(dictionary2.getValue());
    if (time <= 0) {
      return false;
    }
    String lastSendDate = dictionary1.getValue();
    String today = DataUtils.dateTOString(new Date());

    return !today.equalsIgnoreCase(lastSendDate);
  }

  /**
   * 更新推荐商品时间戳
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void updateProductRecommend(Date date) {

    Dictionary dictionary = dictionaryRepository.findOne(5L);
    dictionary.setValue(String.valueOf(date.getTime()));
    dictionaryRepository.save(dictionary);
  }

  /**
   * 更新推荐商家时间戳
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void updateMerchantRecommend(Date date) {

    Dictionary dictionary = dictionaryRepository.findOne(6L);
    dictionary.setValue(String.valueOf(date.getTime()));
    dictionaryRepository.save(dictionary);
  }

  /**
   * 更新jsApiTicket
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void updateJsApiTicket(String jsApiTicket, Long id) {

    Dictionary dictionary = dictionaryRepository.findOne(id);
    dictionary.setValue(jsApiTicket);
    dictionaryRepository.save(dictionary);
  }

  /**
   * 更新access_token
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void updateAccessToken(String access_token, Long id) {

    Dictionary dictionary = dictionaryRepository.findOne(id);
    dictionary.setValue(access_token);
    dictionaryRepository.save(dictionary);
  }

  /**
   * 更新is_to_all=true群发时间和剩余次数
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void updateMassTime(Long dateId, Long timeId) {
    String date = DataUtils.dateTOString(new Date());
    Dictionary dictionary1 = dictionaryRepository.findOne(dateId);
    dictionary1.setValue(date);
    Dictionary dictionary2 = dictionaryRepository.findOne(timeId);
    Integer old = Integer.valueOf(dictionary2.getValue());
    if (old > 0) {
      Integer now = old - 1;
      dictionary2.setValue(String.valueOf(now));
      dictionaryRepository.save(dictionary2);
    }

    dictionaryRepository.save(dictionary1);
  }

}
