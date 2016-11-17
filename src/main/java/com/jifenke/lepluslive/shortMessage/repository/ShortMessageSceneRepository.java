package com.jifenke.lepluslive.shortMessage.repository;

import com.jifenke.lepluslive.shortMessage.domain.entities.ShortMessageScene;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by jfk on 2016/10/18.
 */
public interface ShortMessageSceneRepository extends JpaRepository<ShortMessageScene, Long> {


  @Query(value = "SELECT * from short_message_scene  where send_reason=?1", nativeQuery = true)
  ShortMessageScene findSceneBySendReason(Integer sendReason);


  //查询场景的字符串
  @Query(value = "select distinct(category)  from  short_message_scene ", nativeQuery = true)
  List<Object[]> findshortmessageSence();


  @Query(value = "SELECT * from short_message_scene where scene_sid=?1", nativeQuery = true)
  ShortMessageScene findBySid(String sceneSid);


  @Query(value = "SELECT * from short_message_scene where name=?1", nativeQuery = true)
  ShortMessageScene findSceneByName(String name);

}
