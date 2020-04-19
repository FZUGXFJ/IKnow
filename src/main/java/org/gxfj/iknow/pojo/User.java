package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "user", schema = "iknow_dev", catalog = "")
public class User {
    private int id;
    private String email;
    private String passwd;
    private String name;
    private String introduction;
    private String gender;
    private byte isAttest;
    private Date date;
    private int badgeNum;
    private Integer exp;
    private int stateId;
    private Integer identityId;
    private Collection<Achievementrecord> achievementrecordsById;
    private Collection<Answer> answersById;
    private Collection<Approvalanswer> approvalanswersById;
    private Collection<Approvalcomment> approvalcommentsById;
    private Collection<Approvalreply> approvalrepliesById;
    private Collection<Browsinghistory> browsinghistoriesById;
    private Collection<Collectionproblem> collectionproblemsById;
    private Collection<Comment> commentsById;
    private Collection<Comment> commentsById_0;
    private Collection<Message> messagesById;
    private Collection<Oppositionanswer> oppositionanswersById;
    private Collection<Question> questionsById;
    private Collection<Reply> repliesById;
    private Collection<Report> reportsById;
    private Collection<Searchhistory> searchhistoriesById;
    private Userstate userstateByStateId;
    private Useridentity useridentityByIdentityId;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "passwd")
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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
    @Column(name = "introduction")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "isAttest")
    public byte getIsAttest() {
        return isAttest;
    }

    public void setIsAttest(byte isAttest) {
        this.isAttest = isAttest;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "badgeNum")
    public int getBadgeNum() {
        return badgeNum;
    }

    public void setBadgeNum(int badgeNum) {
        this.badgeNum = badgeNum;
    }

    @Basic
    @Column(name = "exp")
    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    @Basic
    @Column(name = "stateID")
    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "identityID")
    public Integer getIdentityId() {
        return identityId;
    }

    public void setIdentityId(Integer identityId) {
        this.identityId = identityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (id != that.id) return false;
        if (isAttest != that.isAttest) return false;
        if (badgeNum != that.badgeNum) return false;
        if (stateId != that.stateId) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (passwd != null ? !passwd.equals(that.passwd) : that.passwd != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (introduction != null ? !introduction.equals(that.introduction) : that.introduction != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (exp != null ? !exp.equals(that.exp) : that.exp != null) return false;
        if (identityId != null ? !identityId.equals(that.identityId) : that.identityId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (int) isAttest;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + badgeNum;
        result = 31 * result + (exp != null ? exp.hashCode() : 0);
        result = 31 * result + stateId;
        result = 31 * result + (identityId != null ? identityId.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Achievementrecord> getAchievementrecordsById() {
        return achievementrecordsById;
    }

    public void setAchievementrecordsById(Collection<Achievementrecord> achievementrecordsById) {
        this.achievementrecordsById = achievementrecordsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Answer> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<Answer> answersById) {
        this.answersById = answersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Approvalanswer> getApprovalanswersById() {
        return approvalanswersById;
    }

    public void setApprovalanswersById(Collection<Approvalanswer> approvalanswersById) {
        this.approvalanswersById = approvalanswersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Approvalcomment> getApprovalcommentsById() {
        return approvalcommentsById;
    }

    public void setApprovalcommentsById(Collection<Approvalcomment> approvalcommentsById) {
        this.approvalcommentsById = approvalcommentsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Approvalreply> getApprovalrepliesById() {
        return approvalrepliesById;
    }

    public void setApprovalrepliesById(Collection<Approvalreply> approvalrepliesById) {
        this.approvalrepliesById = approvalrepliesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Browsinghistory> getBrowsinghistoriesById() {
        return browsinghistoriesById;
    }

    public void setBrowsinghistoriesById(Collection<Browsinghistory> browsinghistoriesById) {
        this.browsinghistoriesById = browsinghistoriesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Collectionproblem> getCollectionproblemsById() {
        return collectionproblemsById;
    }

    public void setCollectionproblemsById(Collection<Collectionproblem> collectionproblemsById) {
        this.collectionproblemsById = collectionproblemsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Comment> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<Comment> commentsById) {
        this.commentsById = commentsById;
    }

    @OneToMany(mappedBy = "userByAnswerId")
    public Collection<Comment> getCommentsById_0() {
        return commentsById_0;
    }

    public void setCommentsById_0(Collection<Comment> commentsById_0) {
        this.commentsById_0 = commentsById_0;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Message> getMessagesById() {
        return messagesById;
    }

    public void setMessagesById(Collection<Message> messagesById) {
        this.messagesById = messagesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Oppositionanswer> getOppositionanswersById() {
        return oppositionanswersById;
    }

    public void setOppositionanswersById(Collection<Oppositionanswer> oppositionanswersById) {
        this.oppositionanswersById = oppositionanswersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Question> getQuestionsById() {
        return questionsById;
    }

    public void setQuestionsById(Collection<Question> questionsById) {
        this.questionsById = questionsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Reply> getRepliesById() {
        return repliesById;
    }

    public void setRepliesById(Collection<Reply> repliesById) {
        this.repliesById = repliesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Report> getReportsById() {
        return reportsById;
    }

    public void setReportsById(Collection<Report> reportsById) {
        this.reportsById = reportsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Searchhistory> getSearchhistoriesById() {
        return searchhistoriesById;
    }

    public void setSearchhistoriesById(Collection<Searchhistory> searchhistoriesById) {
        this.searchhistoriesById = searchhistoriesById;
    }

    @ManyToOne
    @JoinColumn(name = "stateID", referencedColumnName = "id", nullable = false)
    public Userstate getUserstateByStateId() {
        return userstateByStateId;
    }

    public void setUserstateByStateId(Userstate userstateByStateId) {
        this.userstateByStateId = userstateByStateId;
    }

    @ManyToOne
    @JoinColumn(name = "identityID", referencedColumnName = "id")
    public Useridentity getUseridentityByIdentityId() {
        return useridentityByIdentityId;
    }

    public void setUseridentityByIdentityId(Useridentity useridentityByIdentityId) {
        this.useridentityByIdentityId = useridentityByIdentityId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Useridentity> getUseridentitiesById() {
        return useridentitiesById;
    }

    public void setUseridentitiesById(Collection<Useridentity> useridentitiesById) {
        this.useridentitiesById = useridentitiesById;
    }
}
