package com.jifenke.lepluslive.weixin.controller;


import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.weixin.domain.entities.AutoReplyRule;
import com.jifenke.lepluslive.weixin.service.AutoReplyService;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangwen on 2016/5/25.
 */
@RestController
@RequestMapping("/manage/weixin")
public class AutoReplyController {


  @Inject
  private AutoReplyService autoReplyService;

  @RequestMapping(value = "/reply/list")
  public ModelAndView list(@RequestParam(value = "page", required = false) Integer offset,
                           Model model) {
    if (offset == null) {
      offset = 1;
    }
    Page page = autoReplyService.findAllReplyRule(offset);

    model.addAttribute("replyRuleList", page.getContent());

    model.addAttribute("pages", page.getTotalPages());
    model.addAttribute("currentPage", offset);

    return MvUtil.go("/weixin/replyList");
  }


  @RequestMapping(value = "/reply/textEdit/{id}")
  public ModelAndView textEdit(@PathVariable Long id, Model model) {
    if (id == 0) {
    } else {
      model.addAttribute("textReply", autoReplyService.findTextReplyById(id));
    }
    return MvUtil.go("/weixin/editTextReply");
  }

  @RequestMapping(value = "/reply/saveText")
  public LejiaResult saveText(@RequestBody AutoReplyRule autoReplyRule) {
    try {
      autoReplyService.editTextReply(autoReplyRule);
      return LejiaResult.build(200, "创建文字回复成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "创建文字回复失败");
    }
  }

  @RequestMapping(value = "/reply/imageEdit/{id}")
  public ModelAndView imageEdit(@PathVariable Long id, Model model) {
    if (id == 0) {
    } else {
      model.addAttribute("imageReply", autoReplyService.findImageReplyById(id));
    }
    return MvUtil.go("/weixin/editImageReply");
  }

  @RequestMapping(value = "/reply/saveImage")
  public LejiaResult saveImage(@RequestBody AutoReplyRule autoReplyRule) {
    try {
      autoReplyService.editImageReply(autoReplyRule);
      return LejiaResult.build(200, "创建图文回复成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "创建图文回复失败");
    }
  }

  @RequestMapping(value = "/reply/deleteAutoReply/{id}", method = RequestMethod.DELETE)
  public LejiaResult deleteAutoReply(@PathVariable Long id) {
    try {
      autoReplyService.deleteAutoReply(id);
      return LejiaResult.build(200, "删除成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "删除失败");
    }
  }


}
