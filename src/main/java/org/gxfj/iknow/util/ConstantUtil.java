package org.gxfj.iknow.util;

public class ConstantUtil {
    //公共
    /**
     * 返回的JSON中resultCode的值，代表请求对应的操作执行成功
     */
    public final static int SUCCESS = 0;
    /**
     * 返回的JSON中resultCode的值，代表用户未登录
     */
    public final static Integer UN_LOGIN = 1;
    /**
     * 返回的JSON中resultCode的值，代表内容存在敏感词
     */
    public final static int JSON_RESULT_CODE_VERIFY_TEXT_FAIL = 10;
    /**
     * 返回的JSON中resultCode的值，代表用户被封禁
     */
    public final static int JSON_RESULT_CODE_BAN = 11;

    public final static int NO_MORE = 2;
    public final static String JSON_RETURN_CODE_NAME = "resultCode";
    public final static String SESSION_USER = "user";
    public final static String RETURN_STRING = "success";
    public final static String ANONYMOUS_USER_AVATAR = "0.jpg";
    public final static String ANONYMOUS_USER_NAME = "匿名用户";

    public final static Integer HASH_MAP_NUM = 20;
    public final static int MIN_HASH_MAP_NUM = 10;

    //Admin
    public final static String LOGIN_ADMIN_SESSION_NAME = "admin";
    public final static String NO_ADMIN = null;
    public final static int DAILY_QUESTION_SUM_STATICS_TYPE_CODE = 0;
    public final static int DAILY_USER_SUM_STATICS_TYPE_CODE = 1;
    public final static int DAILY_ACTIVE_USER_STATICS_TYPE_CODE = 0;
    public final static int DAILY_ACTIVE_USER_STATICS_LENGTH = 7;
    public final static int MONTHLY_ACTIVATE_USER_STATICS_TYPE_CODE = 1;
    public final static int MONTHLY_ACTIVATE_USER_STATICS_LENGTH = 3;
    public final static int SCHOOL_IS_EXIST = 1;


    //Answer
    public final static int MISS_QUESTIONID = 2;
    public final static int MISS_ANSWER_IF = 3;
    public final static int USER_IS_NOT_QUESTIONER = 2;
    public final static int USER_IS_NOT_ANSWERER = 1;
    public final static int USER_IS_NOT_ANSWERER_TWO = 2;
    public final static int RECOMMEND_ANSWERS_NUM_PER_TIME = 20;
    public final static int ANSWER_IN_NOT_APPROVED = 2;

    //Comment
    public final static int MISS_COMMENT_INF = 2;
    public final static int RESULT_CODE_APPROVED = 2;
    public final static int RESULT_CODE_NOT_APPROVED = 2;
    public final static int COMMENT_DEFAULT_SORT = 0;
    public final static int NO_COMMENTER = 2;

    //Partition
    public final static String JSON_QUESIONTS = "questions";
    public final static int RESPONSE_NUM = 20;
    public final static int PARTITION_QUESTION_COUNT = 20;

    //Question
    public static final int QUESTION_SHOW_ANSWER_NUM = 10;
    public final static int MISS_QUESTION_INF = 2;
    public final static int ANSWER_DEFAULT_SORT = 0;
    public final static int USER_IS_NOT_QUESTION_ONWER_DELETE_FAULT = 2;
    public final static int SHOW_ANSWERS_NUM = 20;

    //Record
    public final static int UN_LOGIN_TWO = 2;

    //Reply
    public final static int NO_REPLYER = 2;

    //User
    public final static String EMAIL = "email";
    public final static String VERIFY_CODE = "verifyCode";
    public final static String LOGIN_USER_SESSION_NAME = "user";
    public final static String RESET_PASSWORD_VERIFY_CODE_SESSION_NAME = "resetPasswordVerifyCode";
    public final static String RESET_PASSWORD_VERIFY_STATE_SESSION_NAME = "resetPasswordVerifyState";
    public final static String REBIND_EMAIL_VERIFY_CODE_SESSION_NAME = "rebindEmailVerifyCode";
    public final static String REBIND_EMAIL_VERIFY_STATE_SESSION_NAME = "rebindEmailVerifyState";
    public final static String NEW_EMAIL_SESSION_NAME = "newEmail";
    public final static String NEW_EMAIL_VERIFY_CODE_SESSION_NAME = "newEmailVerifyCode";
    public final static String NEW_EMAIL_VERIFY_STATE_SESSION_NAME = "newEmailVerifyState";
    public final static String SESSION_NAME = "";
    public final static int RESET_PASSWORD_FAIL = 1;
    public final static int VERIFY_DEFAULT = 1;
    public final static int SUCCESS_LOGON = 0;
    public final static String NO_USER = null;
    public final static int WRONG_PASSWORD = 1;

    //IdentityAction
    //认证失败
    public final static Integer AUTHENTICATION_FAILED = 1;


    /**
     * 前端发来的数据出错
     */
    public static final Integer RESULT_CODE_PARAMS_ERROR = 2;

    //MessageUtil
    public final static int INVITE_USER_TO_ANSWER = 5;

    //JsonUtil
    public final static int IS_NOT_JSON_ARRAY = 1;
}
