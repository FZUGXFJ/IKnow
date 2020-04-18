package org.gxfj.iknow.pojo_tmp;


import java.sql.Timestamp;

public class AchievementRecord {

  private Integer id;
  private Timestamp date;
  private Integer userId;
  private Integer achievementId;


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


  public long getAchievementId() {
    return achievementId;
  }

  public void setAchievementId(Integer achievementId) {
    this.achievementId = achievementId;
  }

}
