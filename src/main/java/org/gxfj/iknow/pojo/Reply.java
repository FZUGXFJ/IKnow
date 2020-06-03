package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Reply {
    public static final byte REPLY_UN_DELETE = 0;
    public static final byte REPLY_DELETE = 1;

    private Integer id;
    private String content;
    private Date date;
    private Integer count;
    private Byte isDelete;
    private User userByUserId;
    private Comment commentByCommentId;
    private User userByTargetUserId;

    @Id
    @Column(name = "id", nullable = false)
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
    @Column(name = "count", nullable = false)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

        Reply reply = (Reply) o;

        if (id != null ? !id.equals(reply.id) : reply.id != null) {
            return false;
        }
        if (content != null ? !content.equals(reply.content) : reply.content != null) {
            return false;
        }
        if (date != null ? !date.equals(reply.date) : reply.date != null) {
            return false;
        }
        if (count != null ? !count.equals(reply.count) : reply.count != null) {
            return false;
        }
        if (isDelete != null ? !isDelete.equals(reply.isDelete) : reply.isDelete != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
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
    @JoinColumn(name = "commentID", referencedColumnName = "id", nullable = false)
    public Comment getCommentByCommentId() {
        return commentByCommentId;
    }

    public void setCommentByCommentId(Comment commentByCommentId) {
        this.commentByCommentId = commentByCommentId;
    }

    @ManyToOne
    @JoinColumn(name = "targetUserID", referencedColumnName = "id", nullable = false)
    public User getUserByTargetUserId() {
        return userByTargetUserId;
    }

    public void setUserByTargetUserId(User userByTargetUserId) {
        this.userByTargetUserId = userByTargetUserId;
    }
}
