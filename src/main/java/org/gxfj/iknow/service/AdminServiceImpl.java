package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.AdminDAO;
import org.gxfj.iknow.pojo.Admin;
import org.gxfj.iknow.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hhj
 */
@Service("adminService")
@Transactional(readOnly = false)
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public Admin login(Admin adminInf) {
        Admin admin = adminDAO.getAdminByCount(adminInf.getAccount());
        if(admin == null){
            return null;
        }
        else{
            if (adminInf.getPasswd().length() == 32) {
                if (adminInf.getPasswd().equals(admin.getPasswd())) {
                    return admin;
                } else {
                    return null;
                }
            } else {
                if (SecurityUtil.md5Compare(adminInf.getPasswd(),admin.getPasswd())) {
                    return admin;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public Map<String, Object> getData(String dateNow, Integer typeSum) {
        Map<String,Object> record;
        List<Map<String,Object>> records=new ArrayList<>();
        Map<String,Object> result=new HashMap<>(5);
        if(typeSum==1){
            List<Integer> date=adminDAO.getQuestionSum(dateNow);
            for (Integer i:date){
                record=new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("questionSums",records);
        }
        else {
            List<Integer> date=adminDAO.getUserSum(dateNow);
            for (Integer i:date){
                record=new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("userSums",records);
        }
        return result;
    }
}
