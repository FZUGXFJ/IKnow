package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.AdminDAO;
import org.gxfj.iknow.dao.BrowsingHistoryDAO;
import org.gxfj.iknow.dao.CategoriesTypeDAO;
import org.gxfj.iknow.pojo.Admin;
import org.gxfj.iknow.pojo.Categoriestype;
import org.gxfj.iknow.pojo.Questiontype;
import org.gxfj.iknow.pojo.Subjecttype;
import org.gxfj.iknow.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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

    private final static int MD5_PASSWORD_LENGTH = 32;
    @Autowired
    private AdminDAO adminDAO;

    @Override
    public Admin login(Admin adminInf) {
        Admin admin = adminDAO.getAdminByCount(adminInf.getAccount());
        if(admin == null){
            return null;
        }
        else{
            if (adminInf.getPasswd().length() == MD5_PASSWORD_LENGTH) {
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
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(5);
        if(typeSum == 1){
            List<Integer> date = adminDAO.getQuestionSum(dateNow);
            for (Integer i : date){
                record = new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("questionSums",records);
        }
        else {
            List<Integer> date = adminDAO.getUserSum(dateNow);
            for (Integer i : date){
                record = new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("userSums",records);
        }
        return result;
    }
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;

    @Override
    public Map<String, Object> getActiveData(String dateNow, Integer typeSum) {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(5);
        if(typeSum == 0){
            List<List> date = browsingHistoryDAO.getUserDailyActives(dateNow, 7);

            //初始化日活跃列表
            for (int i = 0; i < 7; i++) {
                record = new HashMap<>(1);
                record.put("sum", 0);
                records.add(record);
            }

            for (List i : date){
                record = records.get(7 - 1 - (Integer)i.get(1));
                record.put("sum",i.get(0));
            }
            result.put("userDayActives",records);
        }
        else {
            List<Object[]> date = browsingHistoryDAO.getUserMonthlyActives(dateNow,3);

            //初始化月活跃列表
            for (int i = 0; i < 3; i++) {
                record = new HashMap<>(1);
                record.put("sum", 0);
                records.add(record);
            }

            for (Object[] i : date){
                record = records.get(3 - 1 - ((BigInteger)i[1]).intValue());
                record.put("sum",i[0]);
            }
            result.put("userMonActives",records);
        }
        return result;
    }
    @Autowired
    CategoriesTypeDAO categoriesTypeDAO;

    @Override
    public Map<String, Object> getQuestionTypeSumData() {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(5);
        List<Categoriestype> categoriestypeList = categoriesTypeDAO.list();
        for (Categoriestype categoriestype : categoriestypeList){
            record = new HashMap<>(2);
            int sum = 0;
            for (Questiontype questiontype : categoriestype.getQuestiontypesById()) {
                sum += questiontype.getQuestionsById().size();
            }

            record.put("sum", sum);
            record.put("type", categoriestype.getName());
            records.add(record);
        }
        result.put("questionTypeSums",records);
        return result;
    }
}
