package com.jifenke.lepluslive.merchant.controller.dto;

import com.jifenke.lepluslive.merchant.domain.entities.Area;

import java.util.List;

/**
 * Created by wcg on 16/4/6.
 */
public class CityDto {

  private Long id;

  private int sid;

  private String name;

  private List<Area> areas;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getSid() {
    return sid;
  }

  public void setSid(int sid) {
    this.sid = sid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Area> getAreas() {
    return areas;
  }

  public void setAreas(List<Area> areas) {
    this.areas = areas;
  }
}
