package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.*;


/**
 * @author qmbx
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    QuestionTypeDAO questionTypeDAO;
    @Autowired
    CategoriesTypeDAO categoriesTypeDAO;
    @Autowired
    SubjectTypeDAO subjectTypeDAO;
    @Autowired
    MajorTypeDAO majorTypeDAO;

    @Override
    public void postQuestion(User user, String title, String context, Integer categoryType, Integer subjectType
            , Integer majorType, Byte isAnonymous) {
        Question question = new Question();
        Questiontype questiontype = questionTypeDAO.get(categoryType,subjectType,majorType);
        Questionstate questionstate = new Questionstate();
        Questionscenario questionscenario = new Questionscenario();
        Answer answer = new Answer();

        //TODO 修正魔法值的存在
        questionstate.setId(1);
        questionscenario.setId(1);
        answer.setId(0);

        question.setUserByUserId(user);
        question.setTitle(title);
        question.setContent(context);
        question.setQuestiontypeByTypeId(questiontype);
        question.setQuestionstateByStateId(questionstate);
        question.setQuestionscenarioByScenarioId(questionscenario);
        question.setDate(new Date());
        question.setIsDelete((byte)0);
        question.setIsAnonymous(isAnonymous);


        questionDAO.add(question);

    }

    final static private int MAP_NUM = 20;

    @Override
    public Map<String, Object> getQuestionType() {
        Map<String, Object> questionType= new HashMap<>(MAP_NUM);

        //查询到的所有门类
        List<Categoriestype> categoriestypeList = categoriesTypeDAO.list();
        //JSON中数组
        List<Map<String, Object>> categoriesTypeList = new ArrayList<>();
        List<Map<String, Object>> subjectTypeList;
        List<Map<String, Object>> majorTypeList;
        //JSON中数组的成员
        Map<String, Object> categoriesTypeMap;
        Map<String, Object> subjectTypeMap;
        Map<String, Object> majorTypeMap;

        for (Categoriestype categoriestype: categoriestypeList) {
            categoriesTypeMap = new HashMap<>(MAP_NUM);
            categoriesTypeMap.put("id", categoriestype.getId());
            categoriesTypeMap.put("name", categoriestype.getName());

            subjectTypeList = new ArrayList<>();
            for (Subjecttype subjecttype : subjectTypeDAO.list(categoriestype.getId())) {
                subjectTypeMap = new HashMap<>(MAP_NUM);
                subjectTypeMap.put("id", subjecttype.getId());
                subjectTypeMap.put("name", subjecttype.getName());

                majorTypeList = new ArrayList<>();
                for (Majortype majortype : majorTypeDAO.list(subjecttype.getId())) {
                    majorTypeMap = new HashMap<>(MAP_NUM);
                    majorTypeMap.put("id", majortype.getId());
                    majorTypeMap.put("name", majortype.getName());

                    majorTypeList.add(majorTypeMap);
                }

                subjectTypeMap.put("majorTypes", majorTypeList);
                subjectTypeList.add(subjectTypeMap);
            }
            categoriesTypeMap.put("subjectTypes", subjectTypeList);
            categoriesTypeList.add(categoriesTypeMap);
        }

        questionType.put("categoriesTypes", categoriesTypeList);
        return questionType;
    }
}
