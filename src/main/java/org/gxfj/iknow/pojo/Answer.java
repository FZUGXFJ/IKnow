package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "answer", schema = "iknow_dev", catalog = "")
public class Answer {
    private int id;
    private int userId;
    private String content;
    private int questionId;
    private Date date;
    private byte isDelete;
    private User userByUserId;
    private Question questionByQuestionId;
    private Collection<Approvalanswer> approvalanswersById;
    private Collection<Oppositionanswer> oppositionanswersById;

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
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

        Answer that = (Answer) o;

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
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "questionID", referencedColumnName = "id", nullable = false)
    public Question getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(Question questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }

    @OneToMany(mappedBy = "answerByAnswerId")
    public Collection<Approvalanswer> getApprovalanswersById() {
        return approvalanswersById;
    }

    public void setApprovalanswersById(Collection<Approvalanswer> approvalanswersById) {
        this.approvalanswersById = approvalanswersById;
    }

    @OneToMany(mappedBy = "answerByAnswerId")
    public Collection<Oppositionanswer> getOppositionanswersById() {
        return oppositionanswersById;
    }

    public void setOppositionanswersById(Collection<Oppositionanswer> oppositionanswersById) {
        this.oppositionanswersById = oppositionanswersById;
    }
}
