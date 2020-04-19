package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "school", schema = "iknow_dev", catalog = "")
public class School {
    private int id;
    private String name;
    private Collection<College> collegesById;
    private Collection<Useridentity> useridentitiesById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School that = (School) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "schoolBySchoolId")
    public Collection<College> getCollegesById() {
        return collegesById;
    }

    public void setCollegesById(Collection<College> collegesById) {
        this.collegesById = collegesById;
    }

    @OneToMany(mappedBy = "schoolBySchoolId")
    public Collection<Useridentity> getUseridentitiesById() {
        return useridentitiesById;
    }

    public void setUseridentitiesById(Collection<Useridentity> useridentitiesById) {
        this.useridentitiesById = useridentitiesById;
    }
}
