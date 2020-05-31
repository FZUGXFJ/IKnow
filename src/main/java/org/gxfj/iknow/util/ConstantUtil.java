package org.gxfj.iknow.util;

public class ConstantUtil {
    //公共
    private final static Integer UN_LOGIN = 1;
    private final static Integer HASH_MAP_NUM = 20;
    private final static int MIN_HASH_MAP_NUM = 10;
    private final static int SUCCESS = 0;
    private final static int NO_MORE = 1;
    private final static String RESULT_CODE = "resultCode";
    private final static String SESSION_USER = "user";
    private final static String RETURN_STRING = "success";
    //Admin
    private final static String LOGIN_ADMIN_SESSION_NAME = "admin";
    private final static String NO_ADMIN = null;



    //Answer
    private final static int MISS_QUESTIONID = 2;
    private final static int MISS_ANSWER_IF = 3;
    private final static int USER_IS_NOT_QUESTIONER = 2;
    private final static int USER_IS_NOT_ANSWERER = 1;
    private final static int USER_IS_NOT_ANSWERER_TWO = 2;

    //Comment
    private final static int MISS_COMMENT_INF = 2;
    private final static int RESULT_CODE_APPROVED = 2;
    private final static int RESULT_CODE_NOT_APPROVED = 2;
    private final static int Comment_DEFAULT_SORT = 0;
    private final static int NO_COMMENTER = 2;

    //Partition
    private final static String JSON_QUESIONTS = "questions";
    private final static int RESPONSE_NUM = 20;
    private final static int PARTITION_QUESTION_COUNT = 20;

    //Question
    private static final int QUESTION_SHOW_ANSWER_NUM = 10;
    private final static int MISS_QUESTION_INF = 2;
    private final static int QUESTION_DEFAULT_SORT = 1;
    private final static int USER_IS_NOT_QUESTION_ONWER_DELETE_FAULT = 2;

    //Record

    //Reply
    private final static int NO_REPLYER = 2;

    //User
    private final static String EMAIL = "email";
    private final static String VERIFY_CODE = "verifyCode";
    private final static String LOGIN_USER_SESSION_NAME = "user";
    private final static String RESET_PASSWORD_VERIFY_CODE_SESSION_NAME = "resetPasswordVerifyCode";
    private final static String RESET_PASSWORD_VERIFY_STATE_SESSION_NAME = "resetPasswordVerifyState";
    private final static String REBIND_EMAIL_VERIFY_CODE_SESSION_NAME = "rebindEmailVerifyCode";
    private final static String REBIND_EMAIL_VERIFY_STATE_SESSION_NAME = "rebindEmailVerifyState";
    private final static String NEW_EMAIL_SESSION_NAME = "newEmail";
    private final static String NEW_EMAIL_VERIFY_CODE_SESSION_NAME = "newEmailVerifyCode";
    private final static String NEW_EMAIL_VERIFY_STATE_SESSION_NAME = "newEmailVerifyState";
    private final static String SESSION_NAME = "";
    private final static int RESET_PASSWORD_FAIL = 1;
    private final static int VERIFY_DEFAULT = 1;
    private final static int SUCCESS_LOGON = 0;
    private final static String NO_USER = null;

    public static int getMinHashMapNum() {
        return MIN_HASH_MAP_NUM;
    }

    public static int getComment_DEFAULT_SORT() {
        return Comment_DEFAULT_SORT;
    }

    public static int getMISS_ANSWER_IF() {
        return MISS_ANSWER_IF;
    }

    public static int getMISS_COMMENT_INF() {
        return MISS_COMMENT_INF;
    }

    public static int getMISS_QUESTIONID() {
        return MISS_QUESTIONID;
    }

    public static int getNO_MORE() {
        return NO_MORE;
    }

    public static int getRESULT_CODE_APPROVED() {
        return RESULT_CODE_APPROVED;
    }

    public static int getMISS_QUESTION_INF() {
        return MISS_QUESTION_INF;
    }

    public static int getNO_COMMENTER() {
        return NO_COMMENTER;
    }

    public static int getRESULT_CODE_NOT_APPROVED() {
        return RESULT_CODE_NOT_APPROVED;
    }

    public static int getPartitionQuestionCount() {
        return PARTITION_QUESTION_COUNT;
    }

    public static int getResponseNum() {
        return RESPONSE_NUM;
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public static int getUSER_IS_NOT_ANSWERER() {
        return USER_IS_NOT_ANSWERER;
    }

    public static int getUSER_IS_NOT_ANSWERER_TWO() {
        return USER_IS_NOT_ANSWERER_TWO;
    }

    public static int getUSER_IS_NOT_QUESTIONER() {
        return USER_IS_NOT_QUESTIONER;
    }

    public static int getQuestionShowAnswerNum() {
        return QUESTION_SHOW_ANSWER_NUM;
    }

    public static Integer getHashMapNum() {
        return HASH_MAP_NUM;
    }

    public static Integer getUN_LOGIN() {
        return UN_LOGIN;
    }

    public static int getNO_REPLYER() {
        return NO_REPLYER;
    }

    public static String getJsonQuesionts() {
        return JSON_QUESIONTS;
    }

    public static int getQUESTION_DEFAULT_SORT() {
        return QUESTION_DEFAULT_SORT;
    }

    public static String getLoginAdminSessionName() {
        return LOGIN_ADMIN_SESSION_NAME;
    }

    public static int getUSER_IS_NOT_QUESTION_ONWER_DELETE_FAULT() {
        return USER_IS_NOT_QUESTION_ONWER_DELETE_FAULT;
    }

    public static String getNoAdmin() {
        return NO_ADMIN;
    }

    public static String getResultCode() {
        return RESULT_CODE;
    }

    public static String getLoginUserSessionName() {
        return LOGIN_USER_SESSION_NAME;
    }

    public static String getRETURN_STRING() {
        return RETURN_STRING;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static String getSESSION_USER() {
        return SESSION_USER;
    }

    public static String getVERIFY_CODE() {
        return VERIFY_CODE;
    }

    public static int getResetPasswordFail() {
        return RESET_PASSWORD_FAIL;
    }

    public static Integer getUnLogin() {
        return UN_LOGIN;
    }

    public static int getSuccessLogon() {
        return SUCCESS_LOGON;
    }

    public static int getVerifyDefault() {
        return VERIFY_DEFAULT;
    }

    public static String getNewEmailSessionName() {
        return NEW_EMAIL_SESSION_NAME;
    }

    public static String getNewEmailVerifyCodeSessionName() {
        return NEW_EMAIL_VERIFY_CODE_SESSION_NAME;
    }

    public static String getNewEmailVerifyStateSessionName() {
        return NEW_EMAIL_VERIFY_STATE_SESSION_NAME;
    }

    public static String getNoUser() {
        return NO_USER;
    }

    public static String getRebindEmailVerifyCodeSessionName() {
        return REBIND_EMAIL_VERIFY_CODE_SESSION_NAME;
    }

    public static String getRebindEmailVerifyStateSessionName() {
        return REBIND_EMAIL_VERIFY_STATE_SESSION_NAME;
    }

    public static String getResetPasswordVerifyCodeSessionName() {
        return RESET_PASSWORD_VERIFY_CODE_SESSION_NAME;
    }

    public static String getResetPasswordVerifyStateSessionName() {
        return RESET_PASSWORD_VERIFY_STATE_SESSION_NAME;
    }

    public static String getSessionName() {
        return SESSION_NAME;
    }
}
