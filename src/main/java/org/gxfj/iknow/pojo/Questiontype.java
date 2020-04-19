package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "questiontype", schema = "iknow_dev", catalog = "")
public class Questiontype {
    private int id;
    private int categoryId;
    private int subjectId;
    private int majorId;
    private Collection<Question> questionsById;
    private Categoriestype categoriestypeByCategoryId;
    private Subjecttype subjecttypeBySubjectId;
    private Majortype majortypeByMajorId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "categoryID")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "subjectID")
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Basic
    @Column(name = "majorID")
    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Questiontype that = (Questiontype) o;

        if (id != that.id) return false;
        if (categoryId != that.categoryId) return false;
        if (subjectId != that.subjectId) return false;
        if (majorId != that.majorId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + categoryId;
        result = 31 * result + subjectId;
        result = 31 * result + majorId;
        return result;
    }

    @OneToMany(mappedBy = "questiontypeByTypeId")
    public Collection<Question> getQuestionsById() {
        return questionsById;
    }

    public void setQuestionsById(Collection<Question> questionsById) {
        this.questionsById = questionsById;
    }

    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "id", nullable = false)
    public Categoriestype getCategoriestypeByCategoryId() {
        return categoriestypeByCategoryId;
    }

    public void setCategoriestypeByCategoryId(Categoriestype categoriestypeByCategoryId) {
        this.categoriestypeByCategoryId = categoriestypeByCategoryId;
    }

    @ManyToOne
    @JoinColumn(name = "subjectID", referencedColumnName = "id", nullable = false)
    public Subjecttype getSubjecttypeBySubjectId() {
        return subjecttypeBySubjectId;
    }

    public void setSubjecttypeBySubjectId(Subjecttype subjecttypeBySubjectId) {
        this.subjecttypeBySubjectId = subjecttypeBySubjectId;
    }

    @ManyToOne
    @JoinColumn(name = "majorID", referencedColumnName = "id", nullable = false)
    public Majortype getMajortypeByMajorId() {
        return majortypeByMajorId;
    }

    public void setMajortypeByMajorId(Majortype majortypeByMajorId) {
        this.majortypeByMajorId = majortypeByMajorId;
    }
}
