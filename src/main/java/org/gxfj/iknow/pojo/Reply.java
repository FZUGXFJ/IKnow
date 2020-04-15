package org.gxfj.iknow.pojo;

import java.sql.Timestamp;

public class Reply {

  private Integer id;
  private Integer userId;
  private String content;
  private Integer commentId;
  private Timestamp date;
  private Integer count;
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


  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(Integer commentId) {
    this.commentId = commentId;
  }


  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }


  public long getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }


  public long getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }

}
