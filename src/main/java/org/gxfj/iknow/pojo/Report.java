package org.gxfj.iknow.pojo;

import java.sql.Timestamp;

public class Report {

  private long id;
  private long userId;
  private long type;
  private String description;
  private long typeId;
  private Timestamp date;
  private long reasonId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }


  public String getInt() {
    return description;
  }

  public void setInt(String description) {
    this.description = description;
  }


  public long getTypeId() {
    return typeId;
  }

  public void setTypeId(long typeId) {
    this.typeId = typeId;
  }


  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }


  public long getReasonId() {
    return reasonId;
  }

  public void setReasonId(long reasonId) {
    this.reasonId = reasonId;
  }

}
