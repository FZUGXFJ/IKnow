package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.CategoriesTypeDAO;
import org.gxfj.iknow.dao.MajorTypeDAO;
import org.gxfj.iknow.dao.SubjectTypeDAO;
import org.gxfj.iknow.pojo.Categoriestype;
import org.gxfj.iknow.pojo.Major;
import org.gxfj.iknow.pojo.Majortype;
import org.gxfj.iknow.pojo.Subjecttype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("partitionService")
public class PartitionServiceImpl implements PartitionService{
    @Autowired
    CategoriesTypeDAO categoriesTypeDAO;
    @Autowired
    SubjectTypeDAO subjectTypeDAO;
    @Autowired
    MajorTypeDAO majorTypeDAO;
    @Override
    public Map<String, Object> getCategories() {
        Map<String,Object> category;
        Map<String,Object> result=new HashMap<>(2);
        List<Map<String,Object>> categories=new ArrayList<>();

        List<Categoriestype> categoriestypes= categoriesTypeDAO.list();
        for(Categoriestype categoriestype:categoriestypes){
            category=new HashMap<>(2);
            category.put("id",categoriestype.getId());
            category.put("name",categoriestype.getName());

            categories.add(category);
        }
        result.put("categories",categories);
        return result;
    }

    @Override
    public Map<String, Object> getSubjects(Integer cid) {
        Map<String,Object> subject;
        Map<String,Object> result=new HashMap<>(2);
        List<Map<String,Object>> subjects=new ArrayList<>();

        List<Subjecttype> subjecttypes=subjectTypeDAO.list(cid);
        for (Subjecttype subjecttype:subjecttypes){
            subject=new HashMap<>(2);
            subject.put("id",subjecttype.getId());
            subject.put("name",subjecttype.getName());

            subjects.add(subject);
        }
        result.put("subjects",subjects);
        return result;
    }

    @Override
    public Map<String, Object> getMajors(Integer sid) {
        Map<String,Object> major;
        Map<String,Object> result=new HashMap<>(2);
        List<Map<String,Object>> majors=new ArrayList<>();

        List<Majortype> majortypes=majorTypeDAO.list(sid);
        for (Majortype majortype:majortypes){
            major=new HashMap<>(2);
            major.put("id",majortype.getId());
            major.put("name",majortype.getName());

            majors.add(major);
        }

        result.put("majors",majors);
        return result;
    }
}
