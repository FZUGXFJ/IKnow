package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.pojo.Useridentity;

import java.util.Map;

/**
 * @author 爱学习的水先生
 */
public interface IdentityService {
    /**
     * 根据关键字获取相应的学校列表
     * @param keyword 要搜索的关键字
     * @return 包含学校列表的map数据
     */
    Map<String,Object> getSchool(String keyword);

    /**
     * 学生认证
     * @param schoolName 学校名称
     * @param realName 真实姓名
     * @param studentNum 学号
     * @return 是否认证成功
     */
    Map<String, Object> stuAuthentication(User user, String schoolName, String realName, String studentNum);

    /**
     * 教师认证
     * @param schoolName 学校名称
     * @param realName 姓名
     * @param jobNum 工号
     * @return 是否认证成功
     */
    Map<String, Object> teaAuthentication(User user, String schoolName, String realName, String jobNum);
}
