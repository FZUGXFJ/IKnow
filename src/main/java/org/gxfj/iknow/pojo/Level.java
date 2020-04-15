package org.gxfj.iknow.pojo;

public class Level {

  private long id;
  private long level;
  private long expTopLimit;
  private long expBotLimit;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getLevel() {
    return level;
  }

  public void setLevel(long level) {
    this.level = level;
  }


  public long getExpTopLimit() {
    return expTopLimit;
  }

  public void setExpTopLimit(long expTopLimit) {
    this.expTopLimit = expTopLimit;
  }


  public long getExpBotLimit() {
    return expBotLimit;
  }

  public void setExpBotLimit(long expBotLimit) {
    this.expBotLimit = expBotLimit;
  }

}
