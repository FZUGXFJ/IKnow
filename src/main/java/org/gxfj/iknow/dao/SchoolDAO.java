package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reply;
import org.gxfj.iknow.pojo.School;

import java.util.List;

public interface SchoolDAO extends BaseDAO<School>{
    /**
     * 获得包含关键字的学校列表
     * @param keyword 关键字
     * @return  学校列表
     */
    List<School> listKewordSchool(String keyword);

    /**
     * 获得指定学校名的实例
     * @param schoolName 关键字
     * @return  School对象
     */
    School getSchoolByName(String schoolName);

    /**
     * 获得所有学校
     * @return  学校列表
     */
    List<School> listAllSchool();

    /**
     * 删除学校表中对应id的记录
     * @param schoolId 学校Id
     * @return 删除成功
     */
    boolean delete(Integer schoolId);
}
