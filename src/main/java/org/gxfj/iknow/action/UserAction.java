package org.gxfj.iknow.action;

import org.gxfj.iknow.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author herokilito
 */
@Controller
@Scope("prototype")
public class UserAction {

    String username;
    String email;
    String password;

    @Autowired
    UserServiceImpl userService;

    void login() {

    }

    void logon() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
