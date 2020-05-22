package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.CollectionProblemDAO;
import org.gxfj.iknow.dao.QuestionDAO;
import org.gxfj.iknow.pojo.Collectionproblem;
import org.gxfj.iknow.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public void cancelCollect(User user, Integer questionId) {
        Collectionproblem collectionproblem = collectionProblemDAO.getCollectionQuestion(user.getId(),questionId);
        collectionProblemDAO.delete(collectionproblem);
    }

    @Override
    public void collectProblem(User user, Integer questionId) {
        Collectionproblem collectionproblem = new Collectionproblem();
        collectionproblem.setDate(new Date());
        collectionproblem.setQuestionByQuestionId(questionDAO.getNotDelete(questionId));
        collectionproblem.setUserByUserId(user);
        collectionProblemDAO.add(collectionproblem);
    }
}
