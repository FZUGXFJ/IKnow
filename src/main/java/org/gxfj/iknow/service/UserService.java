package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void emailLogin(String email) {

    }

    public void usernameLogin(String username,String password) {

    }
}
