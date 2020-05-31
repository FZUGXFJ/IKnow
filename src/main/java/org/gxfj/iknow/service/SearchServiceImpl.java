package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("searchService")
public class SearchServiceImpl implements SearchService{

    @Autowired
    SearchHistoryDAO searchHistoryDAO;
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    AnswerDAO answerDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;
    @Autowired
    CollectionProblemDAO collectionProblemDAO;
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    UserIdentityDAO userIdentityDAO;
    final static private int RESPONSE_NUM = 20;

    @Override
    public Map<String, Object> searchHistory() {
        List<String> hotSearch=searchHistoryDAO.getHotSearch();
        List<Map<String,Object>> hotSearchContents=new ArrayList<>();
        Map<String,Object> hotSearchContent;
        for (String content:hotSearch) {
            hotSearchContent=new HashMap<>(1);
            hotSearchContent.put("content",content);
            hotSearchContents.add(hotSearchContent);
        }
        Map<String,Object> response=new HashMap<>(RESPONSE_NUM);
        response.put("hotSearch",hotSearchContents);
        return response;
    }

    @Override
    public Map<String, Object> searchResult(String keyword,Integer userId) {
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        if (userId!=-1) {
            User user=userDAO.get(userId);
            postSearchHistory(user,keyword);
        }
        response.put("questionResult",getQuestionsByKeyword(keyword));
        response.put("answerResult",getAnswersByKeyword(keyword));
        response.put("userResult",getUsersByKeyword(keyword));
        return response;
    }

    /**
     *  插入搜索记录
     *  @param user 搜索用户
     *  @param context 搜索文本
     */
    private Integer postSearchHistory(User user, String context) {
        Searchhistory searchhistory=new Searchhistory();
        searchhistory.setContent(context);
        searchhistory.setDate(new Date());
        searchhistory.setUserByUserId(user);
        return searchHistoryDAO.add(searchhistory);
    }

    /**
     *  根据关键字获取对应的问题列表的json格式
     *  @param keyword 关键字
     *  @return 问题对应的json格式
     */
    private List<Map<String,Object>> getQuestionsByKeyword(String keyword) {
        List<Question> questions=questionDAO.listByKeyword(keyword);
        List<Map<String,Object>> res=new ArrayList<>();
        Map<String,Object> question;
        for (Question q:questions) {
            List<Answer> answers = answerDAO.listByQuestionIdSort(q.getId(),0, 2,0);;
            question=new HashMap<>(8);
            question.put("id",q.getId());
            question.put("title",q.getTitle());
            if(q.getIsAnonymous() == 1) {
                question.put("ownerId",0);
                question.put("ownerHead","<img src='../../head/0.jpg' width='100%' height='100%' alt=''>");
                question.put("ownerName","匿名用户");
            }
            else{
                question.put("ownerId",q.getUserByUserId().getId());
                question.put("ownerHead","<img src='../../head/"+
                        q.getUserByUserId().getHead()+"' width='100%'");
                question.put("ownerName",q.getUserByUserId().getName());
            }
            question.put("collectNum",collectionProblemDAO.getCollectionCount(q.getId()));
            if (answers == null) {
                question.put("answerNum", 0);
            } else {
                question.put("answerNum", answers.size());
            }
            question.put("browsingNum",browsingHistoryDAO.getBrowsingCount(q.getId()));
            res.add(question);
        }
        return res;
    }

    /**
     *  根据关键字获取对应的回答列表的json格式
     *  @param keyword 关键字
     *  @return 回答对应的json格式
     */
    private List<Map<String,Object>> getAnswersByKeyword(String keyword) {
        List<Answer> answers=answerDAO.listByKeyword(keyword);
        List<Map<String,Object>> res=new ArrayList<>();
        Map<String,Object> answer;
        for (Answer answer1:answers) {
            answer=new HashMap<>(9);
            answer.put("id",answer1.getId());
            answer.put("content", HtmlUtil.Html2Text(HtmlUtil.changeImgTag(answer1.getContent())));
            if(answer1.getIsAnonymous() == 1){
                answer.put("answererId" , 0);
                answer.put("answererHead" , "<img src='../../head/0.jpg' width='100%' height='100%' alt=''>");
                answer.put("answererName" , "匿名用户");
            }
            else {
                answer.put("answererId" , answer1.getId());
                answer.put("answererHead" , "<img src='../../head/" + answer1.getUserByUserId().getHead() + "' width='100%'" +
                        " height='100%' alt=''>");
                answer.put("answererName" , answer1.getUserByUserId().getName());
            }
            answer.put("approveNum" , answer1.getApprovalCount());
            answer.put("commentNum" , commentDAO.getCount(answer1.getId()));
            Question question=answer1.getQuestionByQuestionId();
            answer.put("questionId" , question.getId());
            answer.put("questionTitle" , question.getTitle());
            res.add(answer);
        }
        return res;
    }

    /**
     *  根据关键字获取对应的用户列表的json格式
     *  @param keyword 关键字
     *  @return 用户对应的json格式
     */
    private List<Map<String,Object>> getUsersByKeyword(String keyword) {
        List<User> users=userDAO.listByKeyword(keyword);
        List<Map<String,Object>> res=new ArrayList<>();
        Map<String,Object> user;
        for (User user1:users) {
            user=new HashMap<>(5);
            user.put("userId",user1.getId());
            user.put("userName",user1.getName());
            user.put("userHead" , "<img src='../head/" + user1.getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
            user.put("userIntroduction" ,user1.getIntroduction());
            user.put("userIdentity" ,getUserIdentity(user1.getId()));
            res.add(user);
        }
        return res;
    }

    private Map<String, Object> getUserIdentity(Integer userId){
        Map<String, Object> userIdentityMap = new HashMap<>(2);
        List<Useridentity> userIdentities = userIdentityDAO.listUserIdentitiesByUserId(userId);
        if(userIdentities == null || userIdentities.size() ==0){
            return null;
        }
        else{
            Useridentity useridentity = userIdentities.get(0);
            userIdentityMap.put("realName" , useridentity.getName());
            String type=useridentity.getType();
            if (type.equals("教师")) {
                userIdentityMap.put("identityType" , 0);
            }
            else {
                userIdentityMap.put("identityType" , 1);
            }
            return userIdentityMap;
        }
    }
}
