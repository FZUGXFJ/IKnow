package org.gxfj.iknow.pojo;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collection;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Categoriestype {
    private Integer id;
    private String name;
    private Collection<Questiontype> questiontypesById;
    private Collection<Subjecttype> subjecttypesById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Categoriestype that = (Categoriestype) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "categoriestypeByCategoryId")
    public Collection<Questiontype> getQuestiontypesById() {
        return questiontypesById;
    }

    public void setQuestiontypesById(Collection<Questiontype> questiontypesById) {
        this.questiontypesById = questiontypesById;
    }

    @OneToMany(mappedBy = "categoriestypeByCategoryId")
    public Collection<Subjecttype> getSubjecttypesById() {
        return subjecttypesById;
    }

    public void setSubjecttypesById(Collection<Subjecttype> subjecttypesById) {
        this.subjecttypesById = subjecttypesById;
    }
}
