package org.gxfj.iknow.pojo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Messagetype {
    private Integer id;
    private String name;
    private Collection<Message> messagesById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
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

        Messagetype that = (Messagetype) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "messagetypeByTypeId")
    public Collection<Message> getQuestiontypesById() {
        return messagesById;
    }

    public void setQuestiontypesById(Collection<Message> messagesById) {
        this.messagesById = messagesById;
    }
}
