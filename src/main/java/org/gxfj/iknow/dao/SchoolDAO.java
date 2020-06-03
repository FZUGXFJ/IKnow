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
}
