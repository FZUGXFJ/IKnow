package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.AdminDAO;
import org.gxfj.iknow.pojo.Admin;
import org.gxfj.iknow.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
