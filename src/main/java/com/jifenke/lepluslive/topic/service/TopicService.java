package com.jifenke.lepluslive.topic.service;

import com.jifenke.lepluslive.topic.domain.entities.Content;
import com.jifenke.lepluslive.topic.domain.entities.Topic;
import com.jifenke.lepluslive.topic.repository.ContentRespository;
import com.jifenke.lepluslive.topic.repository.TopicRespository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/12.
 */
@Service
@Transactional(readOnly = true)
public class TopicService {

  @Inject
  private TopicRespository topicRespository;

  @Inject
  private ContentRespository contentRespository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editTopic(Topic topic) {
    if (topic.getId() == null) {
      topic.getContent().setTopic(topic);
    } else {
      Content content = contentRespository.findByTopic(topic);
      content.setContent(topic.getContent().getContent());
      topic.setContent(content);
    }
    topicRespository.save(topic);


  }

  public List<Topic> findAllTopic() {
    return topicRespository.findAll();
  }

  public Topic findTopicById(Long id) {

    Topic topic = topicRespository.findOne(id);
    topic.setContent(contentRespository.findByTopic(topic));
    return topic;
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteTopic(Long id) {
    topicRespository.delete(id);
  }
}
