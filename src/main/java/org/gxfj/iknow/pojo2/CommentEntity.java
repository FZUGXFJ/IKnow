package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "comment", schema = "iknow_dev", catalog = "")
public class CommentEntity {
    private int id;
    private int userId;
    private String content;
    private int answerId;
    private Date date;
    private byte isDelete;
    private Collection<ApprovalcommentEntity> approvalcommentsById;
    private Collection<ApprovalreplyEntity> approvalrepliesById;
    private UserEntity userByUserId;
    private UserEntity userByAnswerId;
    private Collection<ReplyEntity> repliesById;

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
    @Column(name = "answerID")
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

        CommentEntity that = (CommentEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (answerId != that.answerId) return false;
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
        result = 31 * result + answerId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) isDelete;
        return result;
    }

    @OneToMany(mappedBy = "commentByCommentId")
    public Collection<ApprovalcommentEntity> getApprovalcommentsById() {
        return approvalcommentsById;
    }

    public void setApprovalcommentsById(Collection<ApprovalcommentEntity> approvalcommentsById) {
        this.approvalcommentsById = approvalcommentsById;
    }

    @OneToMany(mappedBy = "commentByCommentId")
    public Collection<ApprovalreplyEntity> getApprovalrepliesById() {
        return approvalrepliesById;
    }

    public void setApprovalrepliesById(Collection<ApprovalreplyEntity> approvalrepliesById) {
        this.approvalrepliesById = approvalrepliesById;
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
    public UserEntity getUserByAnswerId() {
        return userByAnswerId;
    }

    public void setUserByAnswerId(UserEntity userByAnswerId) {
        this.userByAnswerId = userByAnswerId;
    }

    @OneToMany(mappedBy = "commentByCommentId")
    public Collection<ReplyEntity> getRepliesById() {
        return repliesById;
    }

    public void setRepliesById(Collection<ReplyEntity> repliesById) {
        this.repliesById = repliesById;
    }
}
