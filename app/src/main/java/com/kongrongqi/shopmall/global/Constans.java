package com.kongrongqi.shopmall.global;

/**
 * 创建日期：2017/5/17 0017 on 17:48
 * 作者:penny
 */
public class Constans {


    public static final double BANNER_HEIGHT = 2.68; //banner 图片的长宽比

    public static final int TIMED_REFRESH = 15 * 1000; //定时刷新 时长 15秒
    public static final int TIMED_SMS = 5 * 1000; //定时刷新 时长 5秒

    public static final String ACCOUNT_PAGE = "account";
    public static final String TASK_PAGE = "task";
    public static final String SERVICE_PAGE = "service";
    public static final String ME_PAGE = "me";
    public static final String FORGET_PWD = "forget_pwd";
    public static final String SETTING_PWD = "setting_pwd";
    public static final String PRODUCT_DETAIL = "product_detail";

    /**
     * 每次调用接口需要传的值
     */
    public static final String BIZSYSCODE = "bizsysCode";
    public static final String PHONE = "phone";
    public static final String SMS = "sms";
    public static final String FORGET_CODE = "forget_code";
    public static final String FORGET_PHONE = "forget_phone";
    public static final String PAY = "pay";
    public static final String TYPE = "type";

    public static final String SERVICE_BEAN = "ServiceBean";

    public static final String CALL_TXT = "Call_txt";


    public static final String DURATION = "duration";
    public static final String DURATION_UNIT = "durationUnit";

    public static final String GROUP_PAY = "group_pay";
    public static final String USER_SERVICE_PARAM = "UserServiceParam";

    public static final String GROUP = "group";

    public static final String SERVICE_LIST_CACHE = "service_list_cache";
    public static final String PAGE_NUM = "page_num";
    public static final String IS_VISIBLE = "IS_VISIBLE";
    public static final String BUY_ID = "buy_id";

    /**
     * 是否显示guide
     */
    public static final String FIRST_GUIDE = "first_guide";
    public static final String GUIDE_DETAIL = "guide_detail";
    public static final String GUIDE_ACCOUNT_RIGHT = "guide_account";
    public static final String GUIDE_SERVICE = "guide_service";
    public static final String GUIDE_NOT_USE = "guide_not_use";
    public static final String GUIDE_ME = "guide_me";
    public static final String GUIDE_ACCOUNT_NAVIGATION = "guide_account_1";
    public static final String GUIDE_ACCOUNT_LIST = "guide_account_2";
    public static final String GUIDE_ACCOUNT_LIST_ITEM = "guide_account_3";


    /***
     * list type
     */

    public static String LIST_TYPE_KEY = "key";
    public static final int LIST_TYPE_DEAL = 0x01;
    public static final int LIST_TYPE_PUSH = 0x02;
    public static final int LIST_TYPE_HELP = 0x03;

    /**
     * 用户信息
     */
    public static final String BIZSYS_CODE = "ebb85879370a752b";
    public static final String USER_ID = "userid";
    public static final String TOKEN = "token";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String REALNAME = "realname";
    public static final String USERNAME = "username";

    /**
     * 是否添加设备
     */
    public static final String IS_HAVE_ACCOUNT = "isHaveAccount";

    /**
     * eventBus
     */
    public static final int EVENT_NOT_USE_FRG = 0x001;
    public static final int EVENT_ACCOUNT_FRG = 0x002;
    public static final int EVENT_SERVICE_FRG = 0x003;

    /**
     * eventBus
     */
    public static final int EVENT_TASK_NO_USER = 0x100;
    public static final int EVENT_TASK_GOING = 0x102;
    public static final int EVENT_TASK_FINISH = 0x103;

    public static final int EVENT_ACCOUNT_REFRESH = 0x107;


    //设备未使用服
    public static final int EVENT_ACCOUNT_NOT_USE = 0x200;

    //友盟统计 事件
    //服务购买总量
    public static final String SERVICE_BUY_COUNT = "service_buy_count";

    //灌服务A
    public static final String GUAN_FAN_SERVICE_A = "guan_fan_service_a";

    //灌粉服务B
    public static final String GUAN_FAN_SERVICE_B = "guan_fan_service_b";

    //入群服务
    public static final String IN_GROUP_SERVICE = "in_group_service";

    //养号服务
    public static final String YANG_HAO_SERVICE = "yang_hao_service";

    //==============购买 TAG======================
    /**
     * 系统加友
     */
    public static final int SYSTEM_ADD_FRIEND = 2;

    /**
     * 账号托管
     */
    public static final int TRUSTEESHIP_ACCOUNT = 5;

    /**
     * 自助加友
     */
    public static final int FANSB = 3;


    //     蛇果 发短信
    public static final String HTTP_SMS = "http_sms";
    //    蛇果 注册
    public static final String HTTP_XHS = "http_xhs";
    //西红柿
    public static final String HTTP_ACCOUNT2 = "http_account2";
    //西红柿
    public static final String HTTP_XHS_1 = "http_xhs_1";
    //佛手
    public static final String HTTP_FS = "http_fs";
    public static final String HTTP_FS_1 = "http_fs_1";
    //支付
    public static final String HTTP_PAY = "http_pay";

}
