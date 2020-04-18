package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "approvalcomment", schema = "iknow_dev", catalog = "")
public class ApprovalcommentEntity {
    private int id;
    private Date date;
    private int userId;
    private int commentId;
    private UserEntity userByUserId;
    private CommentEntity commentByCommentId;

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
    @Column(name = "commentID")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApprovalcommentEntity that = (ApprovalcommentEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (commentId != that.commentId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + commentId;
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
    @JoinColumn(name = "commentID", referencedColumnName = "id", nullable = false)
    public CommentEntity getCommentByCommentId() {
        return commentByCommentId;
    }

    public void setCommentByCommentId(CommentEntity commentByCommentId) {
        this.commentByCommentId = commentByCommentId;
    }
}
