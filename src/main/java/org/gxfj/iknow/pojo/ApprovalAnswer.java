package org.gxfj.iknow.pojo;

import java.sql.Timestamp;

public class ApprovalAnswer {

  private long id;
  private Timestamp date;
  private long userId;
  private long answerId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getAnswerId() {
    return answerId;
  }

  public void setAnswerId(long answerId) {
    this.answerId = answerId;
  }

}
