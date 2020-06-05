package org.gxfj.iknow.service;


import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.*;
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
    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    ExpUtil expUtil;

    final static private int QUESTION_STATE_UNSOLVE = 1;
    final static private int QUESTION_STATE_SOLVE = 2;
    final static private int QUESTION_SCENARIO_STUDENT = 1;
    final static private int MILLIS_PER_YEAR = 366*24*60*60;
    final static private int MILLIS_PER_MONTH = 30*24*60*60;
    final static private int MILLIS_PER_DAY = 24*60*60;
    final static private int MILLIS_PER_HOUR = 60*60;
    final static private int MILLIS_PER_MINUTE = 60;

    @Override
    public Map<String, Object> postQuestion(String title, String context, Integer categoryType, Integer subjectType
            , Integer majorType, Byte isAnonymous) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        }
        else if (title == null || context == null || categoryType == null || subjectType == null
                || majorType == null || isAnonymous == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.MISS_QUESTION_INF);
        }
        else {
            if (TextVerifyUtil.verifyCompliance(title) && TextVerifyUtil.verifyCompliance(context)) {
                Question question = new Question();
                Questiontype questiontype = questionTypeDAO.get(categoryType,subjectType,majorType);
                Questionstate questionstate = new Questionstate();
                Questionscenario questionscenario = new Questionscenario();

                questionstate.setId(QUESTION_STATE_UNSOLVE);
                questionscenario.setId(QUESTION_SCENARIO_STUDENT);

                question.setUserByUserId(user);
                question.setTitle(title);
                question.setContentHtml(context);
                question.setContentText(HtmlUtil.html2Text(context));
                question.setQuestiontypeByTypeId(questiontype);
                question.setQuestionstateByStateId(questionstate);
                question.setQuestionscenarioByScenarioId(questionscenario);
                question.setDate(new Date());
                question.setIsDelete((byte)0);
                question.setIsAnonymous(isAnonymous);
                result.put("questionId",questionDAO.add(question));
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
            }
        }
        return result;
    }

    final static private int MAP_NUM = 20;

    @Override
    public Map<String, Object> getQuestionType() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if(user == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        } else {
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
            result.put("categoriesTypes", categoriesTypeList);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> getQuestion(Integer questionId, Integer length,Integer sort){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (sort == null){
            sort=ConstantUtil.QUESTION_DEFAULT_SORT;
        }
        ActionContext.getContext().getSession().put("answersort",sort);
        //题主
        User viewUser =get(questionId);
        boolean isQuestionUser = (user != null && user.getId().equals(viewUser.getId()));
        result.put("question",getQuestion(user, questionId, length, sort));
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
        if(user == null || !isQuestionUser){
            result.put("viewerIsOwner",0);
        }
        if(isQuestionUser){
            result.put("viewerIsOwner",1);
        }
        return result;
    }

    /**
     * 将获取问题分离出来
     * @param user 用户
     * @param questionId 问题id
     * @param length 长度
     * @param sort 排序方法
     * @return 问题map
     */
    private Map<String, Object> getQuestion(User user, Integer questionId, Integer length,Integer sort){
        Map<String, Object> questionMap = new HashMap<>(MAP_NUM);
        if(user!=null){
            insertBrowsing(user.getId(),questionId);
        }
        //根据问题id查询到的问题
        Question question = questionDAO.getNotDelete(questionId);
        //问题的回答
        List<Answer> answers = answerDAO.listByQuestionIdSort(question.getId(),0, length,sort);
        //JSON成员
        //题主
        Map<String, Object> owner = new HashMap<>(MAP_NUM);
        //JSON中的数组
        List<Map<String, Object>> questionAnswers;
        //JSON中的数组成员
        Map<String, Object> questionAnswerMap;
        //Question的owner的JSON数据
        questionMap.put("owner",setQueOwnerByIfAnonymous(question));
        questionMap.put("isAnonymous",question.getIsAnonymous());
        questionMap.put("isSolved",question.getQuestionstateByStateId().getId()  == QUESTION_STATE_SOLVE ? 1 : 0);
        questionMap.put("title",question.getTitle());
        questionMap.put("content",question.getContentHtml());
        questionMap.put("collectionCount",collectionProblemDAO.getCollectionCount(question.getId()));
        questionMap.put("browsingCount",browsingHistoryDAO.getBrowsingCount(question.getId()));
        if (answers == null) {
            questionMap.put("answerCount", 0);
            answers = new ArrayList<>();
        } else {
            questionMap.put("answerCount", answers.size());
        }
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
        //获取回答者json数据数组即map类型的list
        questionAnswers = setAnswersJson(nAnswers,question);
        questionMap.put("questionAnswers",questionAnswers);
        //查看问题的用户是否收藏了问题
        if(user == null || collectionProblemDAO.getCollectionQuestion(user.getId(),questionId) == null){
            questionMap.put("isCollected",0);
        }else {
            questionMap.put("isCollected",1);
        }
        return questionMap;
    }

    /**
     * 通过判断问题是否匿名发表设置题主相关信息并返回json数据
     * @param question 问题
     * @return Map<String, Object>型的问题题主相关json数据
     */
    private Map<String, Object> setQueOwnerByIfAnonymous(Question question){
        Map<String, Object> owner = new HashMap<>(MAP_NUM);
        if(question.getIsAnonymous() == 1) {
            owner.put("id",0);
            owner.put("username", ConstantUtil.ANONYMOUS_USER_NAME);
            owner.put("head","0.jpg");
        }
        else{
            owner.put("id",question.getUserByUserId().getId());
            owner.put("username",question.getUserByUserId().getName());
            owner.put("head",question.getUserByUserId().getHead());
        }
        return owner;
    }

    /**
     * 通过判断问题回答是否匿名设置回答者相关信息并返回json数据
     * @param answer 问题
     * @return Map<String, Object>型的问题回答者相关json数据
     */
    private Map<String, Object> setQueAnswererByIfAnonymous(Answer answer){
        Map<String, Object> questionAnswerMap = new HashMap<>(MAP_NUM);
        //匿名设置
        if(answer.getIsAnonymous() == 1) {
            questionAnswerMap.put("answererName",ConstantUtil.ANONYMOUS_USER_NAME);
            questionAnswerMap.put("answererLevel",0);
            questionAnswerMap.put("answererBadge",0);
            questionAnswerMap.put("answererId",0);
            questionAnswerMap.put("answererHead", ImgUtil.changeAvatar(ConstantUtil.ANONYMOUS_USER_AVATAR, 2));
        }
        //非匿名设置
        else{
            questionAnswerMap.put("answererName",answer.getUserByUserId().getName());
            ////用户等级
            questionAnswerMap.put("answererLevel",expUtil.getLevelLabel(answer.getUserByUserId().getExp()));
            questionAnswerMap.put("answererBadge",answer.getUserByUserId().getBadgeNum());
            questionAnswerMap.put("answererId",answer.getUserByUserId().getId());
            questionAnswerMap.put("answererHead",ImgUtil.changeAvatar(answer.getUserByUserId().getHead(), 2));
        }
        return questionAnswerMap;
    }

    /**
     * 通过判断问题回答是否匿名设置回答者相关信息并返回json数据
     * @param nAnswers 问题下回答列表
     * @param question 问题
     * @return Map<String, Object>型的问题回答者相关json数据
     */
    private List<Map<String, Object>> setAnswersJson(List<Answer> nAnswers, Question question){
        Map<String, Object> questionAnswerMap;
        List<Map<String, Object>> questionAnswers = new ArrayList<>();
        for(Answer answer : nAnswers) {
            //通过判断回答是否匿名获取回答者相关信息
            questionAnswerMap = setQueAnswererByIfAnonymous(answer);

            //所有设置
            questionAnswerMap.put("answerId",answer.getId());
            //回答
            questionAnswerMap.put("answerView", HtmlUtil.changeImgTag(answer.getContentHtml()));
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
            int isAdopt = 0;
            Answer adoptAnswer = answer.getQuestionByQuestionId().getAnswerByAdoptId();
            if (adoptAnswer != null && answer.getId().equals(adoptAnswer.getId())) {
                isAdopt = 1;
            }
            questionAnswerMap.put("isAdopt",isAdopt);
            questionAnswers.add(questionAnswerMap);
            /*
            answererIdentity：回答者的身份（α版本非必须）
            */
        }
        return questionAnswers;
    }

    @Override
    public Map<String, Object> cancelAnonymous(Integer questionId){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        User viewUser = get(questionId);
        boolean isQuestionUser = (user != null && user.getId().equals(viewUser.getId()));
        if(user == null || !isQuestionUser){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,1);
        }else{
            Question question = questionDAO.getNotDelete(questionId);
            question.setIsAnonymous((byte)0);
            questionDAO.update(question);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
        }
        return result;
    }

    @Override
    public User get(Integer questionId) {
        return questionDAO.get(questionId).getUserByUserId();
    }

    @Override
    public Map<String, Object> moreAnswers(Integer questionId,int start, int length) {
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        Integer sort =(Integer)session.get("answersort");
        if(sort ==null){
            sort = ConstantUtil.QUESTION_DEFAULT_SORT;
        }
        //根据问题id查询到的问题
        Question question = questionDAO.getNotDelete(questionId);
        //问题的回答
        List<Answer> answers = answerDAO.listByQuestionIdSort(question.getId(),start, length,sort);
        if (answers.size() == 0){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_MORE);
        }
        else {
            List<Map<String, Object>> questionAnswers;
            questionAnswers = setAnswersJson(answers,question);
            result.put("answers",questionAnswers);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
        }
        return result;
    }

    @Override
    public void insertBrowsing(Integer userId, Integer questionId) {
        Browsinghistory browsinghistory=new Browsinghistory();
        browsinghistory.setDate(new Date());
        browsinghistory.setUserByUserId(userDAO.get(userId));
        browsinghistory.setQuestionByQuestionId(questionDAO.get(questionId));

        browsingHistoryDAO.add(browsinghistory);
    }

    @Override
    public Map<String, Object> deleteQuestion(Integer questionId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        } else {
            Question question = questionDAO.get(questionId);
            if (question.getUserByUserId().getId().equals(user.getId())) {
                Collection<Answer> answerCollection = question.getAnswersById();
                for (Answer answer : answerCollection) {
                    Collection<Comment> commentCollection = answer.getCommentsById();
                    for (Comment comment : commentCollection) {
                        Collection<Reply> replyCollection = comment.getRepliesById();
                        for (Reply reply : replyCollection) {
                            replyDAO.delete(reply);
                        }
                        commentDAO.delete(comment);
                    }
                    answerDAO.delete(answer);
                }
                questionDAO.delete(question);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.USER_IS_NOT_QUESTION_ONWER_DELETE_FAULT);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getQuestioninf(Integer questionId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String,Object> result = getQuestionType();
        if(user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,1);;
        }
        else {
            Question question = questionDAO.get(questionId);
            result.put("questionTitle",question.getTitle());
            result.put("questionContent",question.getContentHtml());
            result.put("categoryId",question.getQuestiontypeByTypeId().getCategoriestypeByCategoryId().getId());
            result.put("subjectId",question.getQuestiontypeByTypeId().getSubjecttypeBySubjectId().getId());
            result.put("majorId",question.getQuestiontypeByTypeId().getMajortypeByMajorId().getId());
            User user1 = question.getUserByUserId();
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,user1.getId().equals(user.getId()) ? 0 : 2);
        }
        return result;

    }

    @Override
    public Map<String,Object> updateQuesiton(Integer questionId, String newQuestionTitle, String newQuestionContent,
                                             Integer newCategoriesType, Integer newSubjectType, Integer newMajorType) {
        Map<String,Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);

        if (!TextVerifyUtil.verifyCompliance(newQuestionTitle) && !TextVerifyUtil.verifyCompliance(newQuestionContent)) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
            return result;
        }

        Questiontype questiontype = questionTypeDAO.get(newCategoriesType, newSubjectType, newMajorType);
        Question question = questionDAO.get(questionId);
        question.setTitle(newQuestionTitle);
        question.setContentHtml(newQuestionContent);
        question.setContentText(HtmlUtil.delHtmlTag(newQuestionContent));
        question.setQuestiontypeByTypeId(questiontype);
        question.setId(questionId);
        questionDAO.update(question);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }
}
