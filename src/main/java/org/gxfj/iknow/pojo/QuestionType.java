package org.gxfj.iknow.pojo;

public class QuestionType {

  private Integer id;
  private Integer categoryId;
  private Integer subjectId;
  private Integer majorId;


  public long getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }


  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Integer subjectId) {
    this.subjectId = subjectId;
  }


  public long getMajorId() {
    return majorId;
  }

  public void setMajorId(Integer majorId) {
    this.majorId = majorId;
  }

}
