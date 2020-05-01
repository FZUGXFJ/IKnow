package org.gxfj.iknow.service;


import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    @Autowired
    AnswerDAO answerDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    LevelDAO levelDAO;
    @Autowired
    QuestionStateDAO questionStateDAO;
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;
    @Autowired
    CollectionProblemDAO collectionProblemDAO;

    final static private int QUESTION_STATE_UNSOLVE = 1;
    final static private int QUESTION_STATE_SOLVE = 2;
    final static private int QUESTION_SCENARIO_STUDENT = 1;
    final static private int MILLIS_PER_YEAR = 366*24*60*60;
    final static private int MILLIS_PER_MONTH = 30*24*60*60;
    final static private int MILLIS_PER_DAY = 24*60*60;
    final static private int MILLIS_PER_HOUR = 60*60;
    final static private int MILLIS_PER_MINUTE = 60;

    @Override
    public void postQuestion(User user, String title, String context, Integer categoryType, Integer subjectType
            , Integer majorType, Byte isAnonymous) {
        Question question = new Question();
        Questiontype questiontype = questionTypeDAO.get(categoryType,subjectType,majorType);
        Questionstate questionstate = new Questionstate();
        Questionscenario questionscenario = new Questionscenario();

        questionstate.setId(QUESTION_STATE_UNSOLVE);
        questionscenario.setId(QUESTION_SCENARIO_STUDENT);

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

    @Override
    public Map<String, Object> getQuestion(Integer questionId, int length){
        Map<String, Object> questionMap = new HashMap<>(MAP_NUM);

        //根据问题id查询到的问题
        Question question = questionDAO.get(questionId);
        //问题的回答
        List<Answer> answers = answerDAO.listByQuestionId(question.getId(),0, length);
        //JSON成员
        //题主
        Map<String, Object> owner = new HashMap<>(MAP_NUM);
        //JSON中的数组
        List<Map<String, Object>> questionAnswers = new ArrayList<>();
        //JSON中的数组成员
        Map<String, Object> questionAnswerMap;

        questionMap.put("isAnonymous",question.getIsAnonymous());
        questionMap.put("isSolved",questionDAO.getQuestionStateId(question.getId()) == QUESTION_STATE_SOLVE ? 1 : 0);
        questionMap.put("title",question.getTitle());
        questionMap.put("content",question.getContent());
        questionMap.put("collectionCount",collectionProblemDAO.getCollectionCount(question.getId()));
        questionMap.put("browsingCount",browsingHistoryDAO.getBrowsingCount(question.getId()));
        if (answers == null) {
            questionMap.put("answerCount", 0);
            answers = new ArrayList<>();
        } else {
            questionMap.put("answerCount", answers.size());
        }
        //owner的JSON数据
        if(question.getIsAnonymous() == 1) {
            owner.put("id",0);
            owner.put("username","匿名用户");
            owner.put("head","0.jpg");
        }
        else{
            owner.put("id",question.getUserByUserId().getId());
            owner.put("username",question.getUserByUserId().getName());
            owner.put("head",question.getUserByUserId().getHead());
        }
        questionMap.put("owner",owner);
        //包含采纳回答的列表
        List<Answer> nAnswers = new ArrayList<>();
        //将采纳的回答放入列表第一位
        if(question.getAnswerByAdoptId() != null){
            nAnswers.add(question.getAnswerByAdoptId());
        }
        //填充剩余的回答
        for(Answer answer : answers) {
            //将采纳的回答放入列表第一位
            if(!(question.getAnswerByAdoptId() != null && question.getAnswerByAdoptId().getId().equals(answer.getId()))) {
//                continue;
//            } else {
                nAnswers.add(answer);
            }
        }

        for(Answer answer : nAnswers) {
            questionAnswerMap = new HashMap<>(MAP_NUM);
            //匿名设置
            if(answer.getIsAnonymous() == 1) {
                questionAnswerMap.put("answererName","匿名用户");
                questionAnswerMap.put("answererLevel",0);
                questionAnswerMap.put("answererBadge",0);
                questionAnswerMap.put("answererId",0);
                questionAnswerMap.put("answererHead","<img src='../../head/0.jpg' width='100%' height='100%' alt=''>");
            }
            //非匿名设置
            else{
                questionAnswerMap.put("answererName",answer.getUserByUserId().getName());
                ////用户等级
                questionAnswerMap.put("answererLevel",levelDAO.getLevelByExp(answer.getUserByUserId().getExp()));
                questionAnswerMap.put("answererBadge",answer.getUserByUserId().getBadgeNum());
                questionAnswerMap.put("answererId",answer.getUserByUserId().getId());
                questionAnswerMap.put("answererHead","<img src='../../head/" + answer.getUserByUserId().getHead() + "' width='100%' height='100%' alt=''>");
            }
            //所有设置
            questionAnswerMap.put("answerId",answer.getId());
            //回答
            questionAnswerMap.put("answerView",answer.getContent());
            questionAnswerMap.put("answerApprove",answer.getApprovalCount());
            //回答评论数
            questionAnswerMap.put("answerComment",commentDAO.getCount(answer.getId()));
            if(question.getAnswerByAdoptId() != null && question.getAnswerByAdoptId().getId().equals(answer.getId())){
                //置顶回答的状态
                questionAnswerMap.put("answerState",1);
            }else{
                //一般回答的状态
                questionAnswerMap.put("answerState",0);
            }
            questionAnswerMap.put("answerTime",Time.getTime(answer.getDate()));
            questionAnswerMap.put("isAnonymous",answer.getIsAnonymous());
            questionAnswers.add(questionAnswerMap);
        }
        questionMap.put("questionAnswers",questionAnswers);
        return questionMap;

    }

}
