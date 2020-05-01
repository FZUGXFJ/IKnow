package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Comment {
    private Integer id;
    private String content;
    private Date date;
    private Byte isDelete;
    private Collection<Approvalcomment> approvalcommentsById;
    private Collection<Approvalreply> approvalrepliesById;
    private User userByUserId;
    private Answer answerByAnswerId;
    private Collection<Reply> repliesById;
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "answerID", referencedColumnName = "id", nullable = false)
    public Answer getAnswerByAnswerId() {
        return answerByAnswerId;
    }

    public void setAnswerByAnswerId(Answer answerByAnswerId) {
        this.answerByAnswerId = answerByAnswerId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content", nullable = false, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (date != null ? !date.equals(comment.date) : comment.date != null) return false;
        if (isDelete != null ? !isDelete.equals(comment.isDelete) : comment.isDelete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "commentByCommentId")
    public Collection<Approvalcomment> getApprovalcommentsById() {
        return approvalcommentsById;
    }

    public void setApprovalcommentsById(Collection<Approvalcomment> approvalcommentsById) {
        this.approvalcommentsById = approvalcommentsById;
    }

    @OneToMany(mappedBy = "replyByReplytId")
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


    @OneToMany(mappedBy = "commentByCommentId")
    public Collection<Reply> getRepliesById() {
        return repliesById;
    }

    public void setRepliesById(Collection<Reply> repliesById) {
        this.repliesById = repliesById;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
