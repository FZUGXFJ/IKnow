package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalreply;


public interface ApprovalReplyDAO extends BaseDAO<Approvalreply>{
    /**
     * 根据用户id和回复id得到赞同id
     * @param uid   用户id
     * @param rid   回复id
     * @return  赞同id
     */
    Integer searchByUserIdandReplyId(Integer uid,Integer rid);

    /**
     * 删除赞同
     * @param bean  需要删除的元素
     */
    void delete(Approvalreply bean);

}
