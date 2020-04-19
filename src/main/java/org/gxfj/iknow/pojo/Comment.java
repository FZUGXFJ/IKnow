package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "comment", schema = "iknow_dev", catalog = "")
public class Comment {
    private int id;
    private int userId;
    private String content;
    private int answerId;
    private Date date;
    private byte isDelete;
    private Collection<Approvalcomment> approvalcommentsById;
    private Collection<Approvalreply> approvalrepliesById;
    private User userByUserId;
    private User userByAnswerId;
    private Collection<Reply> repliesById;

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

        Comment that = (Comment) o;

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
    public Collection<Approvalcomment> getApprovalcommentsById() {
        return approvalcommentsById;
    }

    public void setApprovalcommentsById(Collection<Approvalcomment> approvalcommentsById) {
        this.approvalcommentsById = approvalcommentsById;
    }

    @OneToMany(mappedBy = "commentByCommentId")
    public Collection<Approvalreply> getApprovalrepliesById() {
        return approvalrepliesById;
    }

    public void setApprovalrepliesById(Collection<Approvalreply> approvalrepliesById) {
        this.approvalrepliesById = approvalrepliesById;
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
    @JoinColumn(name = "answerID", referencedColumnName = "id", nullable = false)
    public User getUserByAnswerId() {
        return userByAnswerId;
    }

    public void setUserByAnswerId(User userByAnswerId) {
        this.userByAnswerId = userByAnswerId;
    }

    @OneToMany(mappedBy = "commentByCommentId")
    public Collection<Reply> getRepliesById() {
        return repliesById;
    }

    public void setRepliesById(Collection<Reply> repliesById) {
        this.repliesById = repliesById;
    }
}
