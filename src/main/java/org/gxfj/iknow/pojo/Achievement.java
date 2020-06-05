package org.gxfj.iknow.pojo;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collection;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Achievement {
    private Integer id;
    private String name;
    private String introduct;
    private Collection<Achievementrecord> achievementrecordsById;

    /**
     * 答题专家
     */
    public static final Integer ACHIEVEMENT_ANSWER_EXPERT = 1;
    /**
     * 我有问题
     */
    public static final Integer ACHIEVEMENT_I_HAVE_PROBLEM = 2;
    /**
     * 我爱吃瓜
     */
    public static final Integer ACHIEVEMENT_I_AM_ONLOOKER = 3;
    /**
     * 我知道
     */
    public static final Integer ACHIEVEMENT_I_KNOW = 4;
    /**
     * 就没什么我不懂的
     */
    public static final Integer ACHIEVEMENT_NOTHING_I_DONT_UNDERSTAND = 5;
    /**
     * 十万个为什么
     */
    public static final Integer ACHIEVEMENT_ONE_HUNDRED_THOUSAND_WHY = 6;
    /**
     * 我是专业的
     */
    public static final Integer ACHIEVEMENT_I_AM_PROFESSIONAL = 7;


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

    @Basic
    @Column(name = "introduct", nullable = false, length = 20)
    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Achievement that = (Achievement) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
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

    @OneToMany(mappedBy = "achievementByAchievementId")
    public Collection<Achievementrecord> getAchievementrecordsById() {
        return achievementrecordsById;
    }

    public void setAchievementrecordsById(Collection<Achievementrecord> achievementrecordsById) {
        this.achievementrecordsById = achievementrecordsById;
    }
}
