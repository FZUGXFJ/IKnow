package org.gxfj.iknow.pojo2;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "question", schema = "iknow_dev", catalog = "")
public class QuestionEntity {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int typeId;
    private int stateId;
    private int scenarioId;
    private Date date;
    private byte isDelete;
    private Collection<AnswerEntity> answersById;
    private Collection<BrowsinghistoryEntity> browsinghistoriesById;
    private Collection<CollectionproblemEntity> collectionproblemsById;
    private UserEntity userByUserId;
    private QuestiontypeEntity questiontypeByTypeId;
    private QuestionstateEntity questionstateByStateId;
    private QuestionscenarioEntity questionscenarioByScenarioId;

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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "typeID")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
    @Column(name = "scenarioID")
    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
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
    @Column(name = "isDelete")
    public byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionEntity that = (QuestionEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (typeId != that.typeId) return false;
        if (stateId != that.stateId) return false;
        if (scenarioId != that.scenarioId) return false;
        if (isDelete != that.isDelete) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + stateId;
        result = 31 * result + scenarioId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) isDelete;
        return result;
    }

    @OneToMany(mappedBy = "questionByQuestionId")
    public Collection<AnswerEntity> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<AnswerEntity> answersById) {
        this.answersById = answersById;
    }

    @OneToMany(mappedBy = "questionByQuestionId")
    public Collection<BrowsinghistoryEntity> getBrowsinghistoriesById() {
        return browsinghistoriesById;
    }

    public void setBrowsinghistoriesById(Collection<BrowsinghistoryEntity> browsinghistoriesById) {
        this.browsinghistoriesById = browsinghistoriesById;
    }

    @OneToMany(mappedBy = "questionByQuestionId")
    public Collection<CollectionproblemEntity> getCollectionproblemsById() {
        return collectionproblemsById;
    }

    public void setCollectionproblemsById(Collection<CollectionproblemEntity> collectionproblemsById) {
        this.collectionproblemsById = collectionproblemsById;
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
    @JoinColumn(name = "typeID", referencedColumnName = "id", nullable = false)
    public QuestiontypeEntity getQuestiontypeByTypeId() {
        return questiontypeByTypeId;
    }

    public void setQuestiontypeByTypeId(QuestiontypeEntity questiontypeByTypeId) {
        this.questiontypeByTypeId = questiontypeByTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "stateID", referencedColumnName = "id", nullable = false)
    public QuestionstateEntity getQuestionstateByStateId() {
        return questionstateByStateId;
    }

    public void setQuestionstateByStateId(QuestionstateEntity questionstateByStateId) {
        this.questionstateByStateId = questionstateByStateId;
    }

    @ManyToOne
    @JoinColumn(name = "scenarioID", referencedColumnName = "id", nullable = false)
    public QuestionscenarioEntity getQuestionscenarioByScenarioId() {
        return questionscenarioByScenarioId;
    }

    public void setQuestionscenarioByScenarioId(QuestionscenarioEntity questionscenarioByScenarioId) {
        this.questionscenarioByScenarioId = questionscenarioByScenarioId;
    }
}
