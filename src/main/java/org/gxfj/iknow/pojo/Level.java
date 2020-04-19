package org.gxfj.iknow.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Level {
    private Integer id;
    private Integer level;
    private Integer expTopLimit;
    private Integer expBotLimit;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "level", nullable = false)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "expTopLimit", nullable = false)
    public Integer getExpTopLimit() {
        return expTopLimit;
    }

    public void setExpTopLimit(Integer expTopLimit) {
        this.expTopLimit = expTopLimit;
    }

    @Basic
    @Column(name = "expBotLimit", nullable = false)
    public Integer getExpBotLimit() {
        return expBotLimit;
    }

    public void setExpBotLimit(Integer expBotLimit) {
        this.expBotLimit = expBotLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level1 = (Level) o;

        if (id != null ? !id.equals(level1.id) : level1.id != null) return false;
        if (level != null ? !level.equals(level1.level) : level1.level != null) return false;
        if (expTopLimit != null ? !expTopLimit.equals(level1.expTopLimit) : level1.expTopLimit != null) return false;
        if (expBotLimit != null ? !expBotLimit.equals(level1.expBotLimit) : level1.expBotLimit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (expTopLimit != null ? expTopLimit.hashCode() : 0);
        result = 31 * result + (expBotLimit != null ? expBotLimit.hashCode() : 0);
        return result;
    }
}
