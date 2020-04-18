package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "reporttype", schema = "iknow_dev", catalog = "")
public class ReporttypeEntity {
    private int id;
    private String type;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReporttypeEntity that = (ReporttypeEntity) o;

        if (id != that.id) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reporttypeByTypeId")
    public Collection<ReportEntity> getReportsById() {
        return reportsById;
    }

    public void setReportsById(Collection<ReportEntity> reportsById) {
        this.reportsById = reportsById;
    }
}
