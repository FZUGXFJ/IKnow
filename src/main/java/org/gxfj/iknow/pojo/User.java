package org.gxfj.iknow.pojo;

import java.sql.Timestamp;

public class User {

  private long id;
  private String email;
  private String passwd;
  private String name;
  private String introduction;
  private String gender;
  private long isAttest;
  private Timestamp date;
  private long badgeNum;
  private long exp;
  private long stateId;
  private long identityId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }


  public long getIsAttest() {
    return isAttest;
  }

  public void setIsAttest(long isAttest) {
    this.isAttest = isAttest;
  }


  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }


  public long getBadgeNum() {
    return badgeNum;
  }

  public void setBadgeNum(long badgeNum) {
    this.badgeNum = badgeNum;
  }


  public long getExp() {
    return exp;
  }

  public void setExp(long exp) {
    this.exp = exp;
  }


  public long getStateId() {
    return stateId;
  }

  public void setStateId(long stateId) {
    this.stateId = stateId;
  }


  public long getIdentityId() {
    return identityId;
  }

  public void setIdentityId(long identityId) {
    this.identityId = identityId;
  }

}
