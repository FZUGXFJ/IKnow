package org.gxfj.iknow.pojo_tmp;

import java.sql.Timestamp;

public class Question {

  private Integer id;
  private Integer userId;
  private String title;
  private String content;
  private Integer typeId;
  private Integer stateId;
  private Integer scenarioId;
  private Timestamp date;
  private Integer isDelete;


  public long getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }


  public long getStateId() {
    return stateId;
  }

  public void setStateId(Integer stateId) {
    this.stateId = stateId;
  }


  public long getScenarioId() {
    return scenarioId;
  }

  public void setScenarioId(Integer scenarioId) {
    this.scenarioId = scenarioId;
  }


  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }


  public long getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }

}
