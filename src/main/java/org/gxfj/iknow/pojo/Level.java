package org.gxfj.iknow.pojo;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "level")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer level;
    private Integer expTopLimit;
    private Integer expBotLimit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExpTopLimit() {
        return expTopLimit;
    }

    public void setExpTopLimit(int expTopLimit) {
        this.expTopLimit = expTopLimit;
    }

    public int getExpBotLimit() {
        return expBotLimit;
    }

    public void setExpBotLimit(int expBotLimit) {
        this.expBotLimit = expBotLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level that = (Level) o;

        if (id != that.id) return false;
        if (level != that.level) return false;
        if (expTopLimit != that.expTopLimit) return false;
        if (expBotLimit != that.expBotLimit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + level;
        result = 31 * result + expTopLimit;
        result = 31 * result + expBotLimit;
        return result;
    }
}
