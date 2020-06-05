package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.CollectionProblemDAO;
import org.gxfj.iknow.dao.QuestionDAO;
import org.gxfj.iknow.pojo.Collectionproblem;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("CollectionService")
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionProblemDAO collectionProblemDAO;
    @Autowired
    private QuestionDAO questionDAO;

    @Override
    public Collectionproblem getCollectionQuestion(Integer userId, Integer questionId) {
        return collectionProblemDAO.getCollectionQuestion(userId,questionId);
    }

    @Override
    public Map<String, Object> cancelCollect(Integer questionId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        } else if(getCollectionQuestion(user.getId(),questionId) == null){
            //resultCode = 2 表示未收藏无法取消收藏
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, 2);
        } else {
            Collectionproblem collectionproblem = collectionProblemDAO.getCollectionQuestion(user.getId(),questionId);
            collectionProblemDAO.delete(collectionproblem);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> collectProblem(Integer questionId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        } else if(getCollectionQuestion(user.getId(),questionId) != null){
            //resultCode = 2 表示已收藏无法再次收藏
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, 2);
        } else {
            Collectionproblem collectionproblem = new Collectionproblem();
            collectionproblem.setDate(new Date());
            collectionproblem.setQuestionByQuestionId(questionDAO.getNotDelete(questionId));
            collectionproblem.setUserByUserId(user);
            collectionProblemDAO.add(collectionproblem);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        return result;
    }
}
