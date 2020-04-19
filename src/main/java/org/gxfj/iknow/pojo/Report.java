package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Report {
    private Integer id;
    private Integer type;
    private String description;
    private Timestamp date;
    private User userByUserId;
    private Reporttype reporttypeByTypeId;
    private Reportreason reportreasonByReasonId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (id != null ? !id.equals(report.id) : report.id != null) return false;
        if (type != null ? !type.equals(report.type) : report.type != null) return false;
        if (description != null ? !description.equals(report.description) : report.description != null) return false;
        if (date != null ? !date.equals(report.date) : report.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
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
