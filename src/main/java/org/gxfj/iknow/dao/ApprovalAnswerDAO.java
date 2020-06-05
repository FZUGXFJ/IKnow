package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Approvalanswer;
import org.gxfj.iknow.pojo.Approvalreply;

public interface ApprovalAnswerDAO extends BaseDAO<Approvalanswer>{
    /**
     * 根据用户id和回答id得到赞同id
     * @param uid   用户id
     * @param aid   回答id
     * @return  赞同id
     */
    Integer searchByUserIdandAnswerId(Integer uid,Integer aid);

    /**
     * 删除赞同
     * @param bean  需要删除的元素
     */
    void delete(Approvalanswer bean);

}
