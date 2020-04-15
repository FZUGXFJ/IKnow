package org.gxfj.iknow.pojo;

import java.sql.Timestamp;

public class ApprovalComment {

  private long id;
  private Timestamp date;
  private long userId;
  private long commentId;


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


  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(long commentId) {
    this.commentId = commentId;
  }

}
