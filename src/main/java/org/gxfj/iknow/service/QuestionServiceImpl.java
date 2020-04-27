package org.gxfj.iknow.service;


import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
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

    @Override
    public Map<String, Object> getQuestion(Integer questionId){
        Map<String, Object> question = new HashMap<>(MAP_NUM);

        //根据问题id查询到的问题
        Question question1 = questionDAO.get(questionId);
        //根据问题id查询到的题主
        User user = question1.getUserByUserId();
        //问题的回答
        List<Answer> answers = answerDAO.getAnswersbyQid(question1.getId());
        //JSON成员
        Map<String, Object> owner = new HashMap<>(MAP_NUM);//题主
        //JSON中的数组
        List<Map<String, Object>> questionAnswsers = new ArrayList<>();
        //JSON中的数组成员
        Map<String, Object> questionAnswerMap;

        question.put("isAnonymous",question1.getIsAnonymous());
        question.put("isSolved",questionDAO.getQuestionStateId(question1.getId()));
        question.put("title",question1.getTitle());
        question.put("content",question1.getContent());
        question.put("collectionCount",collectionProblemDAO.getCollectionCount(question1.getId()));
        question.put("browsingCount",browsingHistoryDAO.getBrowsingCount(question1.getId()));
        question.put("answerCount",answers.size());

        //owner的JSON数据
        if(question1.getIsAnonymous() == 1)
            owner = null;
        else{
            owner.put("id",question1.getUserByUserId().getId());
            owner.put("username",question1.getUserByUserId().getName());
            owner.put("userhead",question1.getUserByUserId().getHead());
        }
        question.put("owner",owner);

        //回答数组
        Iterator<Answer> it = answers.iterator();//回答迭代器
        List<Answer> its = new ArrayList<Answer>();//采纳回答第一位的列表
        int i = 10;//填充十个回答
        if(question1.getAnswerByAdoptId() != null){//将采纳的回答放入列表第一位
            its.add(question1.getAnswerByAdoptId());
            i = 9;
        }
        while(i > 0 && it.hasNext()){//填充剩余的回答
            Answer answer = it.next();
            if(question1.getAnswerByAdoptId() != null && question1.getAnswerByAdoptId().getId() == answer.getId()){//将采纳的回答放入列表第一位
                continue;
            }
            else {
                its.add(answer);
                i--;
            }
        }

        Iterator<Answer> listIt = its.iterator();//包含采纳回答的最终迭代器
        for(int j = 0;j < 10 && listIt.hasNext();j++){
            Answer answer = listIt.next();
            questionAnswerMap = new HashMap<>(MAP_NUM);
            if(answer.getIsAnonymous() == 1) {//匿名设置
                questionAnswerMap.put("answererName","匿名用户");
                questionAnswerMap.put("answererLevel",0);
                questionAnswerMap.put("answererBadge",0);
                questionAnswerMap.put("answererId",0);
            }
            else{
                questionAnswerMap.put("answererName",answer.getUserByUserId().getName());
                questionAnswerMap.put("answererLevel",levelDAO.getLevelByExp(answer.getUserByUserId().getExp()));////用户等级
                questionAnswerMap.put("answererBadge",answer.getUserByUserId().getBadgeNum());
                questionAnswerMap.put("answererId",answer.getUserByUserId().getId());
            }
            //非匿名设置
            questionAnswerMap.put("answerId",answer.getId());
            questionAnswerMap.put("answererHead",answer.getUserByUserId().getHead());
            questionAnswerMap.put("answerView",answer.getContent());//回答
            questionAnswerMap.put("answerApprove",answer.getApprovalCount());
            questionAnswerMap.put("answerComment",commentDAO.getCount(answer.getId()));//回答评论数
            questionAnswerMap.put("answerState",0);//回答的状态
            questionAnswerMap.put("answerTime",answer.getDate());
            questionAnswerMap.put("isAnonymous",answer.getIsAnonymous());
            questionAnswsers.add(questionAnswerMap);
        }
        question.put("questionAnswsers",questionAnswsers);
        return question;

    }

}
