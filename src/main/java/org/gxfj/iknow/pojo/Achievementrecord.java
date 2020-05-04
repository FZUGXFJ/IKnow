package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Achievementrecord {
    private Integer id;
    private Date date;
    private User userByUserId;
    private Achievement achievementByAchievementId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Achievementrecord that = (Achievementrecord) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
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
    @JoinColumn(name = "achievementID", referencedColumnName = "id", nullable = false)
    public Achievement getAchievementByAchievementId() {
        return achievementByAchievementId;
    }

    public void setAchievementByAchievementId(Achievement achievementByAchievementId) {
        this.achievementByAchievementId = achievementByAchievementId;
    }
}
