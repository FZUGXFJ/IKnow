package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "college", schema = "iknow_dev", catalog = "")
public class CollegeEntity {
    private int id;
    private String name;
    private int schoolId;
    private SchoolEntity schoolBySchoolId;
    private Collection<MajorEntity> majorsById;
    private Collection<UseridentityEntity> useridentitiesById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "schoolID")
    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollegeEntity that = (CollegeEntity) o;

        if (id != that.id) return false;
        if (schoolId != that.schoolId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + schoolId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "schoolID", referencedColumnName = "id", nullable = false)
    public SchoolEntity getSchoolBySchoolId() {
        return schoolBySchoolId;
    }

    public void setSchoolBySchoolId(SchoolEntity schoolBySchoolId) {
        this.schoolBySchoolId = schoolBySchoolId;
    }

    @OneToMany(mappedBy = "collegeByCollegeId")
    public Collection<MajorEntity> getMajorsById() {
        return majorsById;
    }

    public void setMajorsById(Collection<MajorEntity> majorsById) {
        this.majorsById = majorsById;
    }

    @OneToMany(mappedBy = "collegeByCollegeId")
    public Collection<UseridentityEntity> getUseridentitiesById() {
        return useridentitiesById;
    }

    public void setUseridentitiesById(Collection<UseridentityEntity> useridentitiesById) {
        this.useridentitiesById = useridentitiesById;
    }
}
