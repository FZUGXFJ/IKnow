package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Questiontype {
    private Integer id;
    private Collection<Question> questionsById;
    private Categoriestype categoriestypeByCategoryId;
    private Subjecttype subjecttypeBySubjectId;
    private Majortype majortypeByMajorId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Questiontype that = (Questiontype) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
