package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Reporttype {
    private Integer id;
    private String type;
    private Collection<Report> reportsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reporttype that = (Reporttype) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reporttypeByTypeId")
    public Collection<Report> getReportsById() {
        return reportsById;
    }

    public void setReportsById(Collection<Report> reportsById) {
        this.reportsById = reportsById;
    }
}
