package org.gxfj.iknow.dao;

        import org.gxfj.iknow.pojo_tmp.User;


public interface UserDAO {

    /**
     * 根据用户id获得用户对象
     * @param id 用户的id
     * @return 用户对象
     */
    public User get(Integer id);

    /**
     * 更新用户记录，需要
     * @param user 需要更新的内容
     * @return 更新是否成功
     */
    public boolean update(User user);

    /**
     * 添加用户到数据库
     * @param user 要添加的用户
     * @return
     */
    public boolean add(User user);

    /**
     * 根据用户id删除用户
     * @param id 要删除的用户
     * @return 成功返回true，失败返回false
     */
    public boolean delete(Integer id);


}
