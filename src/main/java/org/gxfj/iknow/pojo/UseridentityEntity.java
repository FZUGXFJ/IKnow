package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "useridentity", schema = "iknow_dev", catalog = "")
public class UseridentityEntity {
    private int id;
    private int userId;
    private int schoolId;
    private int collegeId;
    private Integer majorId;
    private Integer studentNum;
    private Integer jobNum;
    private String name;
    private String type;
    private Collection<UserEntity> usersById;
    private UserEntity userByUserId;
    private SchoolEntity schoolBySchoolId;
    private CollegeEntity collegeByCollegeId;
    private MajorEntity majorByMajorId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "schoolID")
    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "collegeID")
    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    @Basic
    @Column(name = "majorID")
    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    @Basic
    @Column(name = "studentNum")
    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    @Basic
    @Column(name = "jobNum")
    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
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

        UseridentityEntity that = (UseridentityEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (schoolId != that.schoolId) return false;
        if (collegeId != that.collegeId) return false;
        if (majorId != null ? !majorId.equals(that.majorId) : that.majorId != null) return false;
        if (studentNum != null ? !studentNum.equals(that.studentNum) : that.studentNum != null) return false;
        if (jobNum != null ? !jobNum.equals(that.jobNum) : that.jobNum != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + schoolId;
        result = 31 * result + collegeId;
        result = 31 * result + (majorId != null ? majorId.hashCode() : 0);
        result = 31 * result + (studentNum != null ? studentNum.hashCode() : 0);
        result = 31 * result + (jobNum != null ? jobNum.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "useridentityByIdentityId")
    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "schoolID", referencedColumnName = "id", nullable = false)
    public SchoolEntity getSchoolBySchoolId() {
        return schoolBySchoolId;
    }

    public void setSchoolBySchoolId(SchoolEntity schoolBySchoolId) {
        this.schoolBySchoolId = schoolBySchoolId;
    }

    @ManyToOne
    @JoinColumn(name = "collegeID", referencedColumnName = "id", nullable = false)
    public CollegeEntity getCollegeByCollegeId() {
        return collegeByCollegeId;
    }

    public void setCollegeByCollegeId(CollegeEntity collegeByCollegeId) {
        this.collegeByCollegeId = collegeByCollegeId;
    }

    @ManyToOne
    @JoinColumn(name = "majorID", referencedColumnName = "id")
    public MajorEntity getMajorByMajorId() {
        return majorByMajorId;
    }

    public void setMajorByMajorId(MajorEntity majorByMajorId) {
        this.majorByMajorId = majorByMajorId;
    }
}
