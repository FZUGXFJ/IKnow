package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "iknow_dev", catalog = "")
public class UserEntity {
    private int id;
    private String email;
    private String passwd;
    private String name;
    private String introduction;
    private String gender;
    private byte isAttest;
    private Timestamp date;
    private int badgeNum;
    private Integer exp;
    private int stateId;
    private Integer identityId;
    private Collection<AchievementrecordEntity> achievementrecordsById;
    private Collection<AnswerEntity> answersById;
    private Collection<ApprovalanswerEntity> approvalanswersById;
    private Collection<ApprovalcommentEntity> approvalcommentsById;
    private Collection<ApprovalreplyEntity> approvalrepliesById;
    private Collection<BrowsinghistoryEntity> browsinghistoriesById;
    private Collection<CollectionproblemEntity> collectionproblemsById;
    private Collection<CommentEntity> commentsById;
    private Collection<CommentEntity> commentsById_0;
    private Collection<MessageEntity> messagesById;
    private Collection<OppositionanswerEntity> oppositionanswersById;
    private Collection<QuestionEntity> questionsById;
    private Collection<ReplyEntity> repliesById;
    private Collection<ReportEntity> reportsById;
    private Collection<SearchhistoryEntity> searchhistoriesById;
    private UserstateEntity userstateByStateId;
    private UseridentityEntity useridentityByIdentityId;
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
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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

        UserEntity that = (UserEntity) o;

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
    public Collection<AchievementrecordEntity> getAchievementrecordsById() {
        return achievementrecordsById;
    }

    public void setAchievementrecordsById(Collection<AchievementrecordEntity> achievementrecordsById) {
        this.achievementrecordsById = achievementrecordsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<AnswerEntity> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<AnswerEntity> answersById) {
        this.answersById = answersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<ApprovalanswerEntity> getApprovalanswersById() {
        return approvalanswersById;
    }

    public void setApprovalanswersById(Collection<ApprovalanswerEntity> approvalanswersById) {
        this.approvalanswersById = approvalanswersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<ApprovalcommentEntity> getApprovalcommentsById() {
        return approvalcommentsById;
    }

    public void setApprovalcommentsById(Collection<ApprovalcommentEntity> approvalcommentsById) {
        this.approvalcommentsById = approvalcommentsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<ApprovalreplyEntity> getApprovalrepliesById() {
        return approvalrepliesById;
    }

    public void setApprovalrepliesById(Collection<ApprovalreplyEntity> approvalrepliesById) {
        this.approvalrepliesById = approvalrepliesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<BrowsinghistoryEntity> getBrowsinghistoriesById() {
        return browsinghistoriesById;
    }

    public void setBrowsinghistoriesById(Collection<BrowsinghistoryEntity> browsinghistoriesById) {
        this.browsinghistoriesById = browsinghistoriesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<CollectionproblemEntity> getCollectionproblemsById() {
        return collectionproblemsById;
    }

    public void setCollectionproblemsById(Collection<CollectionproblemEntity> collectionproblemsById) {
        this.collectionproblemsById = collectionproblemsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    @OneToMany(mappedBy = "userByAnswerId")
    public Collection<CommentEntity> getCommentsById_0() {
        return commentsById_0;
    }

    public void setCommentsById_0(Collection<CommentEntity> commentsById_0) {
        this.commentsById_0 = commentsById_0;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<MessageEntity> getMessagesById() {
        return messagesById;
    }

    public void setMessagesById(Collection<MessageEntity> messagesById) {
        this.messagesById = messagesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<OppositionanswerEntity> getOppositionanswersById() {
        return oppositionanswersById;
    }

    public void setOppositionanswersById(Collection<OppositionanswerEntity> oppositionanswersById) {
        this.oppositionanswersById = oppositionanswersById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<QuestionEntity> getQuestionsById() {
        return questionsById;
    }

    public void setQuestionsById(Collection<QuestionEntity> questionsById) {
        this.questionsById = questionsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<ReplyEntity> getRepliesById() {
        return repliesById;
    }

    public void setRepliesById(Collection<ReplyEntity> repliesById) {
        this.repliesById = repliesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<ReportEntity> getReportsById() {
        return reportsById;
    }

    public void setReportsById(Collection<ReportEntity> reportsById) {
        this.reportsById = reportsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<SearchhistoryEntity> getSearchhistoriesById() {
        return searchhistoriesById;
    }

    public void setSearchhistoriesById(Collection<SearchhistoryEntity> searchhistoriesById) {
        this.searchhistoriesById = searchhistoriesById;
    }

    @ManyToOne
    @JoinColumn(name = "stateID", referencedColumnName = "id", nullable = false)
    public UserstateEntity getUserstateByStateId() {
        return userstateByStateId;
    }

    public void setUserstateByStateId(UserstateEntity userstateByStateId) {
        this.userstateByStateId = userstateByStateId;
    }

    @ManyToOne
    @JoinColumn(name = "identityID", referencedColumnName = "id")
    public UseridentityEntity getUseridentityByIdentityId() {
        return useridentityByIdentityId;
    }

    public void setUseridentityByIdentityId(UseridentityEntity useridentityByIdentityId) {
        this.useridentityByIdentityId = useridentityByIdentityId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UseridentityEntity> getUseridentitiesById() {
        return useridentitiesById;
    }

    public void setUseridentitiesById(Collection<UseridentityEntity> useridentitiesById) {
        this.useridentitiesById = useridentitiesById;
    }
}
