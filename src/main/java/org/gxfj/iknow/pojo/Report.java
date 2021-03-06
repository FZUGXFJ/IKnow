package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Report {
    private Integer id;
    private Integer targetId;
    private String description;
    private Date date;
    private User userByUserId;
    private Reporttype reporttypeByTypeId;
    private Reportreason reportreasonByReasonId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "targetID", nullable = false)
    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer type) {
        this.targetId = type;
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

        Report report = (Report) o;

        if (id != null ? !id.equals(report.id) : report.id != null) {
            return false;
        }
        if (targetId != null ? !targetId.equals(report.targetId) : report.targetId != null) {
            return false;
        }
        if (description != null ? !description.equals(report.description) : report.description != null) {
            return false;
        }
        if (date != null ? !date.equals(report.date) : report.date != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
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
