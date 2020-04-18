package org.gxfj.iknow.pojo_tmp;

import java.sql.Timestamp;

public class ApprovalAnswer {

  private Integer id;
  private Timestamp date;
  private Integer userId;
  private Integer answerId;


  public long getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public long getAnswerId() {
    return answerId;
  }

  public void setAnswerId(Integer answerId) {
    this.answerId = answerId;
  }

}
