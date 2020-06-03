package org.gxfj.iknow.util;

import org.gxfj.iknow.dao.UserStateDAO;
import org.gxfj.iknow.pojo.Level;
import org.gxfj.iknow.pojo.Useridentity;
import org.gxfj.iknow.pojo.Userstate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserStateUtil {
    public static final String BAN = "停封";
    public static final String ESTOPPEL = "禁言";

    private Userstate banState;
    private Userstate estoppelState;
    private List<Userstate> userstateList;

    @Autowired
    UserStateDAO userstateDAO;

    public  List<Userstate> getUserstateList() {
        if (userstateList == null) {
            userstateList = userstateDAO.list();
        }
        return userstateList;
    }

    public Userstate getStateByName(String stateName) {
        for (Userstate userstate : getUserstateList()) {
            if (userstate.getState().equals(stateName)) {
                banState = userstate;
                break;
            }
        }
        return null;
    }

    public Userstate getBanState() {
        if (banState == null) {
            for (Userstate userstate : getUserstateList()) {
                if (userstate.getState().equals(BAN)) {
                    banState = userstate;
                    break;
                }
            }
        }
        return banState;
    }

    public Userstate getEstoppelState() {
        if (estoppelState == null) {
            for (Userstate userstate : getUserstateList()) {
                if (userstate.getState().equals(ESTOPPEL)) {
                    estoppelState = userstate;
                    break;
                }
            }
        }
        return estoppelState;
    }

}
