package org.gxfj.iknow.pojo_tmp;


import java.sql.Timestamp;

public class Answer {

  private Integer id;
  private Integer userId;
  private String content;
  private Integer questionId;
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


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
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
