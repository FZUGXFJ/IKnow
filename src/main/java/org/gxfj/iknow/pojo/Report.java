package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "report", schema = "iknow_dev", catalog = "")
public class Report {
    private int id;
    private int userId;
    private int type;
    private String description;
    private int typeId;
    private Date date;
    private int reasonId;
    private User userByUserId;
    private Reporttype reporttypeByTypeId;
    private Reportreason reportreasonByReasonId;

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
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "typeID")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
    @Column(name = "reasonID")
    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report that = (Report) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (type != that.type) return false;
        if (typeId != that.typeId) return false;
        if (reasonId != that.reasonId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + type;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + reasonId;
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
    @JoinColumn(name = "typeID", referencedColumnName = "id", nullable = false)
    public Reporttype getReporttypeByTypeId() {
        return reporttypeByTypeId;
    }

    public void setReporttypeByTypeId(Reporttype reporttypeByTypeId) {
        this.reporttypeByTypeId = reporttypeByTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "reasonID", referencedColumnName = "id", nullable = false)
    public Reportreason getReportreasonByReasonId() {
        return reportreasonByReasonId;
    }

    public void setReportreasonByReasonId(Reportreason reportreasonByReasonId) {
        this.reportreasonByReasonId = reportreasonByReasonId;
    }
}
