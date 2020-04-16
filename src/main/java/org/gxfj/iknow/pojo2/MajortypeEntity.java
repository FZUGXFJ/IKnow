package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "majortype", schema = "iknow_dev", catalog = "")
public class MajortypeEntity {
    private int id;
    private String name;
    private int subjectId;
    private SubjecttypeEntity subjecttypeBySubjectId;
    private Collection<QuestiontypeEntity> questiontypesById;

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

        MajortypeEntity that = (MajortypeEntity) o;

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
    public SubjecttypeEntity getSubjecttypeBySubjectId() {
        return subjecttypeBySubjectId;
    }

    public void setSubjecttypeBySubjectId(SubjecttypeEntity subjecttypeBySubjectId) {
        this.subjecttypeBySubjectId = subjecttypeBySubjectId;
    }

    @OneToMany(mappedBy = "majortypeByMajorId")
    public Collection<QuestiontypeEntity> getQuestiontypesById() {
        return questiontypesById;
    }

    public void setQuestiontypesById(Collection<QuestiontypeEntity> questiontypesById) {
        this.questiontypesById = questiontypesById;
    }
}
