package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Major {
    private Integer id;
    private String name;
    private College collegeByCollegeId;
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

        Major major = (Major) o;

        if (id != null ? !id.equals(major.id) : major.id != null) {
            return false;
        }
        if (name != null ? !name.equals(major.name) : major.name != null) {
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

    @ManyToOne
    @JoinColumn(name = "collegeID", referencedColumnName = "id", nullable = false)
    public College getCollegeByCollegeId() {
        return collegeByCollegeId;
    }

    public void setCollegeByCollegeId(College collegeByCollegeId) {
        this.collegeByCollegeId = collegeByCollegeId;
    }

    @OneToMany(mappedBy = "majorByMajorId",orphanRemoval=true)
    public Collection<Useridentity> getUseridentitiesById() {
        return useridentitiesById;
    }

    public void setUseridentitiesById(Collection<Useridentity> useridentitiesById) {
        this.useridentitiesById = useridentitiesById;
    }
}
