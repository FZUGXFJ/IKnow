package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Answer {
    public static final byte ANSWER_UN_DELETED = 0;
    public static final byte ANSWER_DELETED = 1;
    public static final byte ANSWER_ANONYMOUS = 1;
    public static final byte ANSWER_UN_ANONYMOUS = 0;

    private Integer id;
    private String contentHtml;
    private String contentText;
    private Date date;
    private Byte isDelete;
    private User userByUserId;
    private Question questionByQuestionId;
    private Collection<Browsinghistory> browsinghistoriesById;
    private Collection<Approvalanswer> approvalanswersById;
    private Collection<Oppositionanswer> oppositionanswersById;
    private Collection<Comment> commentsById;
    private Byte isAnonymous;
    private Integer approvalCount;
    private Byte isRoof;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "contentHtml", nullable = false, length = -1)
    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Basic
    @Column(name = "contentText", nullable = false, length = -1)
    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "isDelete", nullable = false)
    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Answer answer = (Answer) o;

        if (id != null ? !id.equals(answer.id) : answer.id != null) {
            return false;
        }
        if (contentHtml != null ? !contentHtml.equals(answer.contentHtml) : answer.contentHtml != null) {
            return false;
        }
        if (date != null ? !date.equals(answer.date) : answer.date != null) {
            return false;
        }
        if (isDelete != null ? !isDelete.equals(answer.isDelete) : answer.isDelete != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contentHtml != null ? contentHtml.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
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
    public Collection<Browsinghistory> getBrowsinghistoriesById() {
        return browsinghistoriesById;
    }

    public void setBrowsinghistoriesById(Collection<Browsinghistory> browsinghistoriesById) {
        this.browsinghistoriesById = browsinghistoriesById;
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

    @OneToMany(mappedBy = "answerByAnswerId")
    public Collection<Comment> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<Comment> commentsById) {
        this.commentsById = commentsById;
    }

    @Basic
    @Column(name = "isAnonymous", nullable = false)
    public Byte getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Byte isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    @Basic
    @Column(name = "approvalCount", nullable = false)
    public Integer getApprovalCount() {
        return approvalCount;
    }

    public void setApprovalCount(Integer approvalCount) {
        this.approvalCount = approvalCount;
    }

    @Basic
    @Column(name = "isRoof", nullable = false)
    public Byte getIsRoof() {
        return isRoof;
    }

    public void setIsRoof(Byte isRoof) {
        this.isRoof = isRoof;
    }
}
