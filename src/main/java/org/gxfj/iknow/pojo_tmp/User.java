package org.gxfj.iknow.pojo_tmp;

import java.sql.Timestamp;

public class User {

  private Integer id;
  private String email;
  private String passwd;
  private String name;
  private String introduction;
  private String gender;
  private Integer isAttest;
  private Timestamp date;
  private Integer badgeNum;
  private Integer exp;
  private Integer stateId;
  private Integer identityId;


  public long getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public void setIsAttest(Integer isAttest) {
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

  public void setBadgeNum(Integer badgeNum) {
    this.badgeNum = badgeNum;
  }


  public long getExp() {
    return exp;
  }

  public void setExp(Integer exp) {
    this.exp = exp;
  }


  public long getStateId() {
    return stateId;
  }

  public void setStateId(Integer stateId) {
    this.stateId = stateId;
  }


  public long getIdentityId() {
    return identityId;
  }

  public void setIdentityId(Integer identityId) {
    this.identityId = identityId;
  }

}
