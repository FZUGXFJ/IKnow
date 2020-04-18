package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "reportreason", schema = "iknow_dev", catalog = "")
public class ReportreasonEntity {
    private int id;
    private String content;
    private Collection<ReportEntity> reportsById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportreasonEntity that = (ReportreasonEntity) o;

        if (id != that.id) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reportreasonByReasonId")
    public Collection<ReportEntity> getReportsById() {
        return reportsById;
    }

    public void setReportsById(Collection<ReportEntity> reportsById) {
        this.reportsById = reportsById;
    }
}
