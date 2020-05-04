package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Subjecttype {
    private Integer id;
    private String name;
    private Collection<Majortype> majortypesById;
    private Collection<Questiontype> questiontypesById;
    private Categoriestype categoriestypeByCategoryId;

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

        Subjecttype that = (Subjecttype) o;

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

    @OneToMany(mappedBy = "subjecttypeBySubjectId")
    public Collection<Majortype> getMajortypesById() {
        return majortypesById;
    }

    public void setMajortypesById(Collection<Majortype> majortypesById) {
        this.majortypesById = majortypesById;
    }

    @OneToMany(mappedBy = "subjecttypeBySubjectId")
    public Collection<Questiontype> getQuestiontypesById() {
        return questiontypesById;
    }

    public void setQuestiontypesById(Collection<Questiontype> questiontypesById) {
        this.questiontypesById = questiontypesById;
    }

    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "id", nullable = false)
    public Categoriestype getCategoriestypeByCategoryId() {
        return categoriestypeByCategoryId;
    }

    public void setCategoriestypeByCategoryId(Categoriestype categoriestypeByCategoryId) {
        this.categoriestypeByCategoryId = categoriestypeByCategoryId;
    }
}
