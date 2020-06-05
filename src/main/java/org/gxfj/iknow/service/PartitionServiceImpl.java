package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("partitionService")
public class PartitionServiceImpl implements PartitionService{
    @Autowired
    CategoriesTypeDAO categoriesTypeDAO;
    @Autowired
    SubjectTypeDAO subjectTypeDAO;
    @Autowired
    MajorTypeDAO majorTypeDAO;
    @Autowired
    QuestionTypeDAO questionTypeDAO;
    @Autowired
    QuestionDAO questionDAO;

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
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
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
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
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
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getQuestion(Integer categoryId, Integer subjectId, Integer majorId, Integer start,
                                           Integer count) {
        if (categoryId == null || subjectId == null || majorId == null || start == null || count == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<>(2);
        List<Map<String, Object>> questionsMapList = new ArrayList<>();
        Map<String, Object> questionMap;
        Questiontype questiontype = questionTypeDAO.get(categoryId, subjectId, majorId);

        List<Question> questionList = questionDAO.listByQuestionType(questiontype.getId(), start, count);

        for (Question question : questionList) {
            questionMap = new HashMap<>(2);

            questionMap.put("id", question.getId());
            questionMap.put("title", question.getTitle());
            questionMap.put("isSolved", question.getAnswerByAdoptId() == null ? 1 : 0);
            questionMap.put("browsingNum", question.getBrowsinghistoriesById().size());
            questionMap.put("answerNum", question.getAnswersById().size());
            questionMap.put("collectionNum", question.getCollectionproblemsById().size());
            questionsMapList.add(questionMap);
        }
        result.put("question", questionsMapList);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }


}
