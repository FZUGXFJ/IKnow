package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "oppositionanswer", schema = "iknow_dev", catalog = "")
public class OppositionanswerEntity {
    private int id;
    private Date date;
    private int userId;
    private int answerId;
    private UserEntity userByUserId;
    private AnswerEntity answerByAnswerId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "userID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "answerID")
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OppositionanswerEntity that = (OppositionanswerEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (answerId != that.answerId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + answerId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "answerID", referencedColumnName = "id", nullable = false)
    public AnswerEntity getAnswerByAnswerId() {
        return answerByAnswerId;
    }

    public void setAnswerByAnswerId(AnswerEntity answerByAnswerId) {
        this.answerByAnswerId = answerByAnswerId;
    }
}
