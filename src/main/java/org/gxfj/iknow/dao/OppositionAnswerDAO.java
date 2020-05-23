package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalanswer;
import org.gxfj.iknow.pojo.Oppositionanswer;

public interface OppositionAnswerDAO extends BaseDAO<Oppositionanswer> {
    /**
     * 根据用户id和回答id得到反对id
     * @param uid   用户id
     * @param aid   回答id
     * @return  反对id
     */
    Integer searchByUserIdandAnswerId(Integer uid,Integer aid);

    /**
     * 删除反对
     * @param bean  需要删除的元素
     */
    void delete(Oppositionanswer bean);
}
