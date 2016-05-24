package com.jifenke.lepluslive.topic.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.topic.domain.entities.Topic;
import com.jifenke.lepluslive.topic.service.TopicService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/12.
 */
@RestController
@RequestMapping("/manage")
public class TopicController {

  @Inject
  private TopicService topicService;

  @RequestMapping(value = "/topic", method = RequestMethod.GET)
  public ModelAndView goTopicManagePage(Model model) {
    model.addAttribute("topics", topicService.findAllTopic());
    return MvUtil.go("/topic/topicList");
  }


  @RequestMapping(value = "/topic/edit/{id}", method = RequestMethod.GET)
  public ModelAndView goTopicEditPage(@PathVariable Long id, Model model) {
    model.addAttribute("topic", topicService.findTopicById(id));
    return MvUtil.go("/topic/topicCreate");
  }

  @RequestMapping(value = "/topic/create", method = RequestMethod.GET)
  public ModelAndView goTopicCreatePage() {
    return MvUtil.go("/topic/topicCreate");
  }


  @RequestMapping(value = "/topic", method = RequestMethod.POST)
  public LejiaResult saveTopic(@RequestBody Topic topic) {
    topicService.editTopic(topic);
    try {
      return LejiaResult.build(200, "保存成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "保存失败");
    }
  }

  @RequestMapping(value = "/topic", method = RequestMethod.PUT)
  public LejiaResult editTopic(@RequestBody Topic topic) {
    topicService.editTopic(topic);
    try {
      return LejiaResult.build(200, "修改成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "修改失败");
    }
  }

  @RequestMapping(value = "/topic/{id}", method = RequestMethod.DELETE)
  public LejiaResult deleteTopic(@PathVariable Long id) {
    try {
      topicService.deleteTopic(id);
      return LejiaResult.build(200, "删除成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "删除失败");
    }
  }

  @RequestMapping(value = "/test")
  public ModelAndView test() {
    return MvUtil.go("/test");
  }

}
