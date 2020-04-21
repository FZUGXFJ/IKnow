package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Subjecttype;

public interface SubjectTypeDAO {
    public void add(Subjecttype bean);

    public Subjecttype get(Integer id);

    public void update(Subjecttype bean);
}
