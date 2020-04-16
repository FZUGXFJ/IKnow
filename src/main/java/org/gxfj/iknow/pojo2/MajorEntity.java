package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "major", schema = "iknow_dev", catalog = "")
public class MajorEntity {
    private int id;
    private String name;
    private int collegeId;
    private CollegeEntity collegeByCollegeId;
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
    @Column(name = "collegeID")
    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MajorEntity that = (MajorEntity) o;

        if (id != that.id) return false;
        if (collegeId != that.collegeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + collegeId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "collegeID", referencedColumnName = "id", nullable = false)
    public CollegeEntity getCollegeByCollegeId() {
        return collegeByCollegeId;
    }

    public void setCollegeByCollegeId(CollegeEntity collegeByCollegeId) {
        this.collegeByCollegeId = collegeByCollegeId;
    }

    @OneToMany(mappedBy = "majorByMajorId")
    public Collection<UseridentityEntity> getUseridentitiesById() {
        return useridentitiesById;
    }

    public void setUseridentitiesById(Collection<UseridentityEntity> useridentitiesById) {
        this.useridentitiesById = useridentitiesById;
    }
}
