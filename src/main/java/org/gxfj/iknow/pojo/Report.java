package org.gxfj.iknow.pojo;

import java.sql.Timestamp;

public class Report {

  private Integer id;
  private Integer userId;
  private Integer type;
  private String description;
  private Integer typeId;
  private Timestamp date;
  private Integer reasonId;


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


  public long getType() {
    return type;
  }

  public void setType(Integer type) {
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

  public void setTypeId(Integer typeId) {
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

  public void setReasonId(Integer reasonId) {
    this.reasonId = reasonId;
  }

}
