package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "answer", schema = "iknow_dev", catalog = "")
public class AnswerEntity {
    private int id;
    private int userId;
    private String content;
    private int questionId;
    private Timestamp date;
    private byte isDelete;
    private UserEntity userByUserId;
    private QuestionEntity questionByQuestionId;
    private Collection<ApprovalanswerEntity> approvalanswersById;
    private Collection<OppositionanswerEntity> oppositionanswersById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "questionID")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "isDelete")
    public byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerEntity that = (AnswerEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (questionId != that.questionId) return false;
        if (isDelete != that.isDelete) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + questionId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) isDelete;
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
    @JoinColumn(name = "questionID", referencedColumnName = "id", nullable = false)
    public QuestionEntity getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(QuestionEntity questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }

    @OneToMany(mappedBy = "answerByAnswerId")
    public Collection<ApprovalanswerEntity> getApprovalanswersById() {
        return approvalanswersById;
    }

    public void setApprovalanswersById(Collection<ApprovalanswerEntity> approvalanswersById) {
        this.approvalanswersById = approvalanswersById;
    }

    @OneToMany(mappedBy = "answerByAnswerId")
    public Collection<OppositionanswerEntity> getOppositionanswersById() {
        return oppositionanswersById;
    }

    public void setOppositionanswersById(Collection<OppositionanswerEntity> oppositionanswersById) {
        this.oppositionanswersById = oppositionanswersById;
    }
}
