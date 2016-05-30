package com.jifenke.lepluslive.weixin.service;

import com.jifenke.lepluslive.weixin.domain.entities.AutoReplyImageText;
import com.jifenke.lepluslive.weixin.domain.entities.AutoReplyRule;
import com.jifenke.lepluslive.weixin.repository.AutoReplyImageTextRepository;
import com.jifenke.lepluslive.weixin.repository.AutoReplyRuleRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangwen on 2016/5/25.
 */
@Service
@Transactional(readOnly = true)
public class AutoReplyService {

  @Inject
  private AutoReplyRuleRepository autoReplyRuleRepository;

  @Inject
  private AutoReplyImageTextRepository autoReplyImageTextRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findAllReplyRule(Integer offset) {
    Page<AutoReplyRule>
        page =
        autoReplyRuleRepository.findAll(new PageRequest(offset - 1, 10));
    return page;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public AutoReplyRule findTextReplyById(Long id) {
    return autoReplyRuleRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public AutoReplyRule findImageReplyById(Long id) {
    return autoReplyRuleRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editTextReply(AutoReplyRule autoReplyRule) throws Exception {
    AutoReplyRule origin = null;
    try {
      if (autoReplyRule.getId() == null) {
        origin = new AutoReplyRule();
      } else {
        origin = autoReplyRuleRepository.findOne(autoReplyRule.getId());
      }
      origin.setKeyword(autoReplyRule.getKeyword());
      origin.setReplyText(autoReplyRule.getReplyText());
      origin.setReplyType(autoReplyRule.getReplyType());
      autoReplyRuleRepository.save(origin);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editImageReply(AutoReplyRule autoReplyRule) throws Exception {
    AutoReplyRule origin = null;
    AutoReplyImageText imageText = null;
    try {
      if (autoReplyRule.getId() == null) {
        origin = new AutoReplyRule();
        imageText = new AutoReplyImageText();
      } else {
        origin = autoReplyRuleRepository.findOne(autoReplyRule.getId());
        imageText =
            autoReplyImageTextRepository.findOne(autoReplyRule.getReplyImageText0().getId());
      }
      if (origin == null || imageText == null) {
        throw new Exception();
      }

      imageText.setImageUrl(autoReplyRule.getReplyImageText0().getImageUrl());
      imageText.setTextLink(autoReplyRule.getReplyImageText0().getTextLink());
      imageText.setTextNote(autoReplyRule.getReplyImageText0().getTextNote());
      imageText.setTextTitle(autoReplyRule.getReplyImageText0().getTextTitle());
      autoReplyImageTextRepository.save(imageText);

      origin.setKeyword(autoReplyRule.getKeyword());
      origin.setReplyType(autoReplyRule.getReplyType());
      origin.setReplyImageText0(imageText);
      autoReplyRuleRepository.save(origin);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  /**
   * 删除
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteAutoReply(Long id) throws Exception {
    try {
      AutoReplyRule autoReplyRule = autoReplyRuleRepository.findOne(id);
      autoReplyRuleRepository.delete(autoReplyRule);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
