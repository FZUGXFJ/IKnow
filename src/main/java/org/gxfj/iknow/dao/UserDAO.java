package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.User;

public interface UserDAO extends BaseDAO<User>{
    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return  用户
     */
    User getUserByUsername(String username);

    /**
     * 更具邮箱获取用户
     * @param email 邮箱
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 用户id存在
     * @param id id
     * @return 是否
     */
    boolean hasUserId(Integer id);

    /**
     * 用户名存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean hasUsername(String username);

    /**
     *是否存在email
     * @param email 邮箱
     * @return  是否存在
     */
    boolean hasUserEmail(String email);
}
