package org.gxfj.iknow.pojo;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collection;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class College {
    private Integer id;
    private String name;
    private School schoolBySchoolId;
    private Collection<Major> majorsById;
    private Collection<Useridentity> useridentitiesById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        College college = (College) o;

        if (id != null ? !id.equals(college.id) : college.id != null) {
            return false;
        }
        if (name != null ? !name.equals(college.name) : college.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne()
    @JoinColumn(name = "schoolID", referencedColumnName = "id", nullable = false)
    public School getSchoolBySchoolId() {
        return schoolBySchoolId;
    }

    public void setSchoolBySchoolId(School schoolBySchoolId) {
        this.schoolBySchoolId = schoolBySchoolId;
    }

    @OneToMany(mappedBy = "collegeByCollegeId",orphanRemoval=true)
    public Collection<Major> getMajorsById() {
        return majorsById;
    }

    public void setMajorsById(Collection<Major> majorsById) {
        this.majorsById = majorsById;
    }

    @OneToMany(mappedBy = "collegeByCollegeId",orphanRemoval=true)
    public Collection<Useridentity> getUseridentitiesById() {
        return useridentitiesById;
    }

    public void setUseridentitiesById(Collection<Useridentity> useridentitiesById) {
        this.useridentitiesById = useridentitiesById;
    }
}
