package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "subjecttype", schema = "iknow_dev", catalog = "")
public class SubjecttypeEntity {
    private int id;
    private String name;
    private int categoryId;
    private Collection<MajortypeEntity> majortypesById;
    private Collection<QuestiontypeEntity> questiontypesById;
    private CategoriestypeEntity categoriestypeByCategoryId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "categoryID")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjecttypeEntity that = (SubjecttypeEntity) o;

        if (id != that.id) return false;
        if (categoryId != that.categoryId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + categoryId;
        return result;
    }

    @OneToMany(mappedBy = "subjecttypeBySubjectId")
    public Collection<MajortypeEntity> getMajortypesById() {
        return majortypesById;
    }

    public void setMajortypesById(Collection<MajortypeEntity> majortypesById) {
        this.majortypesById = majortypesById;
    }

    @OneToMany(mappedBy = "subjecttypeBySubjectId")
    public Collection<QuestiontypeEntity> getQuestiontypesById() {
        return questiontypesById;
    }

    public void setQuestiontypesById(Collection<QuestiontypeEntity> questiontypesById) {
        this.questiontypesById = questiontypesById;
    }

    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "id", nullable = false)
    public CategoriestypeEntity getCategoriestypeByCategoryId() {
        return categoriestypeByCategoryId;
    }

    public void setCategoriestypeByCategoryId(CategoriestypeEntity categoriestypeByCategoryId) {
        this.categoriestypeByCategoryId = categoriestypeByCategoryId;
    }
}
