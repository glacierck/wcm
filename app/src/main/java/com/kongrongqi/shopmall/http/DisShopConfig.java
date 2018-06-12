package com.kongrongqi.shopmall.http;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class DisShopConfig {

    /**
     *  "kServiceHost": "http://118.31.51.197:7002/v1/base/",
     "kSendMsgHost": "http://118.31.51.197:6061/",

     "kFindGroupHost": "http://118.31.51.197:7003/v1/base/",
     "kPayHost": "http://payprep.yysp.me/",

     "kPushHost": "http://139.196.93.211:6062/",
     "kDeviceHost": "http://118.31.51.197:8002/",

     "env": "prep",
     "kAccountHost": "http://118.31.51.197:6060/"
     */


    ////    // 蛇果 发短信
//    String SMS = "http://118.31.51.197:6061";
//    //蛇果 注册
//    String XHS = "http://118.31.51.197:6060";
//    //西红柿
//    String ACCOUNT2 = "http://118.31.51.197:8002";
//    //西红柿
//    String XHS_1 = "http://118.31.51.197:8002";
//    //佛手
//    String FS = "http://118.31.51.197:7002";
//    String FS_1 = "http://118.31.51.197:7003";
//    //支付
//    String PAY = "http://payprep.yysp.me";
//    String BASE_URL = ACCOUNT2;


// 蛇果 发短信
    private String kSendMsgHost;//SMS
    //蛇果 注册
    private String kAccountHost;//XHS
    //西红柿
    private String kDeviceHost;//ACCOUNT2  XHS_1
    //佛手
    private String kServiceHost;//FS
    private String kFindGroupHost;//FS_1
    //支付
    private String kPayHost;//PAY

    private String env;//版本


    public String getkServiceHost() {
        return kServiceHost;
    }

    public void setkServiceHost(String kServiceHost) {
        this.kServiceHost = kServiceHost;
    }

    public String getkSendMsgHost() {
        return kSendMsgHost;
    }

    public void setkSendMsgHost(String kSendMsgHost) {
        this.kSendMsgHost = kSendMsgHost;
    }

    public String getkFindGroupHost() {
        return kFindGroupHost;
    }

    public void setkFindGroupHost(String kFindGroupHost) {
        this.kFindGroupHost = kFindGroupHost;
    }

    public String getkPayHost() {
        return kPayHost;
    }

    public void setkPayHost(String kPayHost) {
        this.kPayHost = kPayHost;
    }


    public String getkDeviceHost() {
        return kDeviceHost;
    }

    public void setkDeviceHost(String kDeviceHost) {
        this.kDeviceHost = kDeviceHost;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getkAccountHost() {
        return kAccountHost;
    }

    public void setkAccountHost(String kAccountHost) {
        this.kAccountHost = kAccountHost;
    }
}
