package org.gxfj.iknow.pojo_tmp;

import java.sql.Timestamp;

public class SearchHistory {

  private Integer id;
  private Timestamp date;
  private Integer userId;
  private String content;


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


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
