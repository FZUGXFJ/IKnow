package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "questionstate", schema = "iknow_dev", catalog = "")
public class QuestionstateEntity {
    private int id;
    private String state;
    private Collection<QuestionEntity> questionsById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionstateEntity that = (QuestionstateEntity) o;

        if (id != that.id) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "questionstateByStateId")
    public Collection<QuestionEntity> getQuestionsById() {
        return questionsById;
    }

    public void setQuestionsById(Collection<QuestionEntity> questionsById) {
        this.questionsById = questionsById;
    }
}
