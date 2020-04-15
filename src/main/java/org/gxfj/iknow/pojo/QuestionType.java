package org.gxfj.iknow.pojo;

public class QuestionType {

  private long id;
  private long categoryId;
  private long subjectId;
  private long majorId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(long categoryId) {
    this.categoryId = categoryId;
  }


  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(long subjectId) {
    this.subjectId = subjectId;
  }


  public long getMajorId() {
    return majorId;
  }

  public void setMajorId(long majorId) {
    this.majorId = majorId;
  }

}
