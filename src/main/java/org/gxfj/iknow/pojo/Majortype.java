package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "majortype", schema = "iknow_dev", catalog = "")
public class Majortype {
    private int id;
    private String name;
    private int subjectId;
    private Subjecttype subjecttypeBySubjectId;
    private Collection<Questiontype> questiontypesById;

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
    @Column(name = "subjectID")
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Majortype that = (Majortype) o;

        if (id != that.id) return false;
        if (subjectId != that.subjectId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + subjectId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "subjectID", referencedColumnName = "id", nullable = false)
    public Subjecttype getSubjecttypeBySubjectId() {
        return subjecttypeBySubjectId;
    }

    public void setSubjecttypeBySubjectId(Subjecttype subjecttypeBySubjectId) {
        this.subjecttypeBySubjectId = subjecttypeBySubjectId;
    }

    @OneToMany(mappedBy = "majortypeByMajorId")
    public Collection<Questiontype> getQuestiontypesById() {
        return questiontypesById;
    }

    public void setQuestiontypesById(Collection<Questiontype> questiontypesById) {
        this.questiontypesById = questiontypesById;
    }
}
