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
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        School school = schoolDAO.getSchoolByName(schoolName);
        Useridentity useridentity = userIdentityDAO.getStuIdentitie(user.getId(),school.getId(),realName,studentNum);
        if(user==null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN_TWO);
        }
        else {
            if (useridentity != null){
                user.setIsAttest((byte)1);
                user.setUseridentityByIdentityId(useridentity);
                userDAO.update(user);
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
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        School school = schoolDAO.getSchoolByName(schoolName);
        Useridentity useridentity = userIdentityDAO.getTeaIdentitie(user.getId(), school.getId(), realName, jobNum);
        if (user == null) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN_TWO);
        } else {
            if (useridentity != null) {
                user.setIsAttest((byte) 1);
                user.setUseridentityByIdentityId(useridentity);
                userDAO.update(user);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.AUTHENTICATION_FAILED);
            }
        }
        return result;
    }
}
