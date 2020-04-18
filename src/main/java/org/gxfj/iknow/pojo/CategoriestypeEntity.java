package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "categoriestype", schema = "iknow_dev", catalog = "")
public class CategoriestypeEntity {
    private int id;
    private String name;
    private Collection<QuestiontypeEntity> questiontypesById;
    private Collection<SubjecttypeEntity> subjecttypesById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriestypeEntity that = (CategoriestypeEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "categoriestypeByCategoryId")
    public Collection<QuestiontypeEntity> getQuestiontypesById() {
        return questiontypesById;
    }

    public void setQuestiontypesById(Collection<QuestiontypeEntity> questiontypesById) {
        this.questiontypesById = questiontypesById;
    }

    @OneToMany(mappedBy = "categoriestypeByCategoryId")
    public Collection<SubjecttypeEntity> getSubjecttypesById() {
        return subjecttypesById;
    }

    public void setSubjecttypesById(Collection<SubjecttypeEntity> subjecttypesById) {
        this.subjecttypesById = subjecttypesById;
    }
}
