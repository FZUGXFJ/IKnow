package org.gxfj.iknow.action;

import org.gxfj.iknow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class UserAction {

    @Autowired
    UserService userService;

    void login() {

    }

    void logon() {

    }
}
