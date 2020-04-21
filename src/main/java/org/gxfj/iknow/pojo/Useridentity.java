package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Useridentity {
    private Integer id;
    private Integer studentNum;
    private Integer jobNum;
    private String name;
    private String type;
    private Collection<User> usersById;
    private User userByUserId;
    private School schoolBySchoolId;
    private College collegeByCollegeId;
    private Major majorByMajorId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "studentNum", nullable = true)
    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    @Basic
    @Column(name = "jobNum", nullable = true)
    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Useridentity that = (Useridentity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (studentNum != null ? !studentNum.equals(that.studentNum) : that.studentNum != null) return false;
        if (jobNum != null ? !jobNum.equals(that.jobNum) : that.jobNum != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (studentNum != null ? studentNum.hashCode() : 0);
        result = 31 * result + (jobNum != null ? jobNum.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "useridentityByIdentityId")
    public Collection<User> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<User> usersById) {
        this.usersById = usersById;
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
    @JoinColumn(name = "schoolID", referencedColumnName = "id", nullable = false)
    public School getSchoolBySchoolId() {
        return schoolBySchoolId;
    }

    public void setSchoolBySchoolId(School schoolBySchoolId) {
        this.schoolBySchoolId = schoolBySchoolId;
    }

    @ManyToOne
    @JoinColumn(name = "collegeID", referencedColumnName = "id", nullable = false)
    public College getCollegeByCollegeId() {
        return collegeByCollegeId;
    }

    public void setCollegeByCollegeId(College collegeByCollegeId) {
        this.collegeByCollegeId = collegeByCollegeId;
    }

    @ManyToOne
    @JoinColumn(name = "majorID", referencedColumnName = "id")
    public Major getMajorByMajorId() {
        return majorByMajorId;
    }

    public void setMajorByMajorId(Major majorByMajorId) {
        this.majorByMajorId = majorByMajorId;
    }
}
