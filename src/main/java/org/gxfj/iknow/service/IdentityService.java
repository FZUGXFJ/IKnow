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
     * @return 包含学校列表的map数据
     */
    Map<String,Object> getSchool(String keyword);

    /**
     * 学生认证
     * @return 是否认证成功
     */
    boolean stuAuthentication(User user,Integer schoolId, String realName, String studentNum);

    /**
     * 教师认证
     * @return 是否认证成功
     */
    boolean teaAuthentication(User user,Integer schoolId, String realName, String jobNum);
}
