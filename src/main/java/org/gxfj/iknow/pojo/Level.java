package org.gxfj.iknow.pojo;

import javax.persistence.*;

@Entity
@Table(name = "level")
public class Level {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer level;
  private Integer expTopLimit;
  private Integer expBotLimit;


  public long getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public long getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }


  public long getExpTopLimit() {
    return expTopLimit;
  }

  public void setExpTopLimit(Integer expTopLimit) {
    this.expTopLimit = expTopLimit;
  }


  public long getExpBotLimit() {
    return expBotLimit;
  }

  public void setExpBotLimit(Integer expBotLimit) {
    this.expBotLimit = expBotLimit;
  }

}
