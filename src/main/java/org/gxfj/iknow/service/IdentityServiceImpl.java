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
            schoolMap = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
            schoolMap.put("id",school.getId());
            schoolMap.put("name",school.getName());
            schoolsListMap.add(schoolMap);
        }
        resultMap.put("schools",schoolsListMap);
        return resultMap;
    }

    @Override
    public boolean stuAuthentication(User user,Integer schoolId,String realName,String studentNum){
        Useridentity useridentity = userIdentityDAO.getStuIdentitie(user.getId(),schoolId,realName,studentNum);
        if(useridentity == null){
            return false;
        }else {
            user.setIsAttest((byte)1);
            user.setUseridentityByIdentityId(useridentity);
            userDAO.update(user);
            return true;
        }
    }

    @Override
    public boolean teaAuthentication(User user,Integer schoolId,String realName,String jobNum){
        Useridentity useridentity = userIdentityDAO.getTeaIdentitie(user.getId(),schoolId,realName,jobNum);
        if(useridentity == null){
            return false;
        }else {
            user.setIsAttest((byte)1);
            user.setUseridentityByIdentityId(useridentity);
            userDAO.update(user);
            return true;
        }
    }
}
