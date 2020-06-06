package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Question {
    public static final byte QUESTION_UN_DELETED = 0;
    public static final byte QUESTION_DELETED = 1;
    public static final byte QUESTION_ANONYMOUS = 1;
    public static final byte QUESTION_NO_ANONYMOUS = 1;

    private Integer id;
    private String title;
    private String contentHtml;
    private String contentText;
    private Date date;
    private Byte isDelete;
    private Collection<Answer> answersById;
    private Collection<Browsinghistory> browsinghistoriesById;
    private Collection<Collectionproblem> collectionproblemsById;
    private User userByUserId;
    private Questiontype questiontypeByTypeId;
    private Questionstate questionstateByStateId;
    private Questionscenario questionscenarioByScenarioId;
    private Byte isAnonymous;
    private Answer answerByAdoptId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 30)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "contentHtml", nullable = true, length = -1)
    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Basic
    @Column(name = "contentText", nullable = true, length = -1)
    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "isDelete", nullable = false)
    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null) {
            return false;
        }
        if (title != null ? !title.equals(question.title) : question.title != null) {
            return false;
        }
        if (contentHtml != null ? !contentHtml.equals(question.contentHtml) : question.contentHtml != null) {
            return false;
        }
        if (date != null ? !date.equals(question.date) : question.date != null) {
            return false;
        }
        if (isDelete != null ? !isDelete.equals(question.isDelete) : question.isDelete != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (contentHtml != null ? contentHtml.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "questionByQuestionId")
    public Collection<Answer> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<Answer> answersById) {
        this.answersById = answersById;
    }

    @OneToMany(mappedBy = "questionByQuestionId")
    public Collection<Browsinghistory> getBrowsinghistoriesById() {
        return browsinghistoriesById;
    }

    public void setBrowsinghistoriesById(Collection<Browsinghistory> browsinghistoriesById) {
        this.browsinghistoriesById = browsinghistoriesById;
    }

    @OneToMany(mappedBy = "questionByQuestionId")
    public Collection<Collectionproblem> getCollectionproblemsById() {
        return collectionproblemsById;
    }

    public void setCollectionproblemsById(Collection<Collectionproblem> collectionproblemsById) {
        this.collectionproblemsById = collectionproblemsById;
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
    @JoinColumn(name = "typeID", referencedColumnName = "id", nullable = false)
    public Questiontype getQuestiontypeByTypeId() {
        return questiontypeByTypeId;
    }

    public void setQuestiontypeByTypeId(Questiontype questiontypeByTypeId) {
        this.questiontypeByTypeId = questiontypeByTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "stateID", referencedColumnName = "id", nullable = false)
    public Questionstate getQuestionstateByStateId() {
        return questionstateByStateId;
    }

    public void setQuestionstateByStateId(Questionstate questionstateByStateId) {
        this.questionstateByStateId = questionstateByStateId;
    }

    @ManyToOne
    @JoinColumn(name = "scenarioID", referencedColumnName = "id", nullable = false)
    public Questionscenario getQuestionscenarioByScenarioId() {
        return questionscenarioByScenarioId;
    }

    public void setQuestionscenarioByScenarioId(Questionscenario questionscenarioByScenarioId) {
        this.questionscenarioByScenarioId = questionscenarioByScenarioId;
    }

    @Basic
    @Column(name = "isAnonymous", nullable = false)
    public Byte getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Byte isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    @ManyToOne
    @JoinColumn(name = "adoptID", referencedColumnName = "id", nullable = true)
    public Answer getAnswerByAdoptId() {
        return answerByAdoptId;
    }

    public void setAnswerByAdoptId(Answer answerByAdoptId) {
        this.answerByAdoptId = answerByAdoptId;
    }
}
