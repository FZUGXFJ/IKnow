package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.College;
import org.gxfj.iknow.pojo.Comment;

import java.util.List;

public interface CollegeDAO extends BaseDAO<College>{

    /**
     * 通过学院名与学校id获取学院
     * @param collegeName 学院名称
     * @param schoolId 学校id
     * @return  学院列表
     */
    public College getCollegeByName(String collegeName, Integer schoolId);

    /**
     * 通过学校id获取学院列表
     * @param schoolId 用户id
     * @return  学院列表
     */
    List<College> listBySchoolId(Integer schoolId);

    /**
     * 删除学院
     * @param collegeId 学院Id
     * @return 是否删除成功
     */
    boolean delete(Integer collegeId);

}
