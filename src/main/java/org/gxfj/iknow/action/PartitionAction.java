package org.gxfj.iknow.action;

import org.springframework.stereotype.Controller;

import java.io.InputStream;

/**
 * @author erniumo
 */

@Controller
public class PartitionAction {


    private InputStream inputStream;
    private final Integer UNLOGIN = 1;
    private static Integer HASH_MAP_NUM = 20;
    private final static int MIN_HASH_MAP_NUM = 10;
    private final static String RESULTCODE = "resultCode";
    private final static int SUCCESS = 0;


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
