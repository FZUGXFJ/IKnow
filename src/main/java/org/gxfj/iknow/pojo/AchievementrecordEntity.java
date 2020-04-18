package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "achievementrecord", schema = "iknow_dev", catalog = "")
public class AchievementrecordEntity {
    private int id;
    private Date date;
    private int userId;
    private int achievementId;
    private UserEntity userByUserId;
    private AchievementEntity achievementByAchievementId;

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
    @Column(name = "achievementID")
    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AchievementrecordEntity that = (AchievementrecordEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (achievementId != that.achievementId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + achievementId;
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
    @JoinColumn(name = "achievementID", referencedColumnName = "id", nullable = false)
    public AchievementEntity getAchievementByAchievementId() {
        return achievementByAchievementId;
    }

    public void setAchievementByAchievementId(AchievementEntity achievementByAchievementId) {
        this.achievementByAchievementId = achievementByAchievementId;
    }
}
