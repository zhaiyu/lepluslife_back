package com.jifenke.lepluslive.shortMessage.service;

import com.jifenke.lepluslive.shortMessage.domain.entities.ShortMessageScene;
import com.jifenke.lepluslive.shortMessage.repository.ShortMessageSceneRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jfk on 2016/10/18.
 */
@Service
@Transactional(readOnly = false)
public class ShortMessageSceneService {

  @Inject
  private ShortMessageSceneRepository shortMessageSceneRepository;


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ShortMessageScene findSceneById(Long id) {

    return shortMessageSceneRepository.findOne(id);

  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ShortMessageScene> findAll() {

    return shortMessageSceneRepository.findAll();

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ShortMessageScene findBySid(String sceneSid) {

    return shortMessageSceneRepository.findBySid(sceneSid);

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveShortMessageScene(ShortMessageScene scene) {
    shortMessageSceneRepository.save(scene);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ShortMessageScene findSceneByName(String name) {

    return shortMessageSceneRepository.findSceneByName(name);

  }
}
