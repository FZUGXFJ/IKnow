package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import static org.gxfj.iknow.util.ConstantUtil.*;

import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.SecurityUtil;
import static org.gxfj.iknow.util.ServiceConstantUtil.*;

import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 爱学习的水先生
 */
@Service("identityService")
public class IdentityServiceImpl implements IdentityService{
    @Autowired
    SchoolDAO schoolDAO;
    @Autowired
    UserIdentityDAO userIdentityDAO;
    @Autowired
    UserDAO userDAO;

    @Override
    public Map<String,Object> getSchool(String keyword){
        List<School> schools = schoolDAO.listKewordSchool(keyword);
        Map<String, Object> resultMap = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        List<Map<String, Object>> schoolsListMap = new ArrayList<>();
        Map<String, Object> schoolMap;
        for(School school:schools){
            schoolMap = new HashMap<>(HASH_MAP_NUM);
            schoolMap.put("id",school.getId());
            schoolMap.put("name",school.getName());
            schoolsListMap.add(schoolMap);
        }
        resultMap.put("schools",schoolsListMap);
        resultMap.put(RETURN_STRING, SUCCESS);
        resultMap.put(JSON_RETURN_CODE_NAME, SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> stuAuthentication(String schoolName,String realName,String studentNum){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String , Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        School school = schoolDAO.getSchoolByName(schoolName);
        Useridentity useridentity = userIdentityDAO.getStuIdentitie(school.getId(),realName,studentNum);
        if(user==null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN_TWO);
        }
        else {
            if (useridentity != null){
                user.setIsAttest((byte)1);
                user.setUseridentityByIdentityId(useridentity);
                //用户表设置已认证，标明身份信息
                userDAO.update(user);
                useridentity.setUserByUserId(user);
                //身份表标明已认证用户信息的用户
                userIdentityDAO.update(useridentity);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            }
            else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.AUTHENTICATION_FAILED);
            }
        }
       return result;
    }

    @Override
    public Map<String, Object> teaAuthentication(String schoolName,String realName,String jobNum) {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        School school = schoolDAO.getSchoolByName(schoolName);
        Useridentity useridentity = userIdentityDAO.getTeaIdentitie(school.getId(), realName, jobNum);
        if (user == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN_TWO);
        } else {
            if (useridentity != null) {
                user.setIsAttest((byte) 1);
                user.setUseridentityByIdentityId(useridentity);
                userDAO.update(user);
                useridentity.setUserByUserId(user);
                userIdentityDAO.update(useridentity);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.AUTHENTICATION_FAILED);
            }
        }
        return result;
    }
}
