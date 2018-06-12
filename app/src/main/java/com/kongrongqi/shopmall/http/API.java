package com.kongrongqi.shopmall.http;

/**
 * 创建日期：2017/5/16 0016 on 16:23
 * 作者:penny
 */
public interface API {

   int netType = 1;//test
// int netType = 2;//pre
   String yysp =  "http://app.yysp.me";

    /**************开发环境******************************/
    //发短信
//    String SMS = "http://192.168.1.50:6061";
////    String SMS = "http://sms.yysp.local";
//    //注册
//    String XHS = "http://192.168.1.50:6060";
////    String XHS = "http://account.yysp.local";
//    //西红柿
//    String ACCOUNT2 = "http://192.168.1.169:8080";
//    //西红柿
//    String XHS_1 = "http://192.168.1.169:8080/dis-shop-api";
//    //佛手
//    String FS = "http://192.168.1.54:6062";
//    String FS_1 = "http://192.168.1.54:6064";
//    //支付
//    String PAY = "http://pay.yysp.me/dis-pay-api";
//    String BASE_URL = ACCOUNT2;

    /**************测试环境******************************/
    //     蛇果 发短信
    String SMS = "http://192.168.1.132:6061";
    //    蛇果 注册
    String XHS = "http://192.168.1.132:6060";
    //西红柿
    String ACCOUNT2 = "http://192.168.1.131:8080";
    //西红柿
    String XHS_1 = "http://192.168.1.131:8080/dis-shop-api";
    //佛手
    String FS = "http://192.168.1.133:6062/v1/base/";
    String FS_1 = "http://192.168.1.133:6064/v1/base/";
    //支付
    String PAY = "http://paytest.yysp.me";
    String BASE_URL = yysp;


    /*************************预发环境prep**************************/
////    // 蛇果 发短信
//    String SMS = "http://118.31.51.197:6061";
//    //蛇果 注册
//    String XHS = "http://118.31.51.197:6060";
//    //西红柿
//    String ACCOUNT2 = "http://118.31.51.197:8002";
//    //西红柿
//    String XHS_1 = "http://118.31.51.197:8002";
////    String XHS_1 = "http://118.31.51.197:8002/dis-shop-api";
//    //佛手
//    String FS = "http://118.31.51.197:7002/v1/base/";
//    String FS_1 = "http://118.31.51.197:7003/v1/base/";
//    //支付
//    String PAY = "http://payprep.yysp.me";
//    String BASE_URL = ACCOUNT2;

    /*************************生成prod**************************/
//    // 蛇果 发短信
//    String SMS = "http://sms.yysp.me";
//    //蛇果 注册
//    String XHS = "http://account.yysp.me";
//
//    //西红柿
//    String ACCOUNT2 = "http://disapp.yysp.me";
//    //西红柿
//    String XHS_1 = "http://disapp.yysp.me";
//    //佛手
//    String FS = "http://disproduct.yysp.me";
//    String FS_1 = "disgroup.yysp.me";
//    //支付
//    String PAY = "http://pay.yysp.me";
//    String BASE_URL = ACCOUNT2;


//    account.yysp.me		  账号服务
//    sms.yysp.me		   	  短信服务
//    push.yysp.me		      推送服务
//
//    disproduct.yysp.me           产品服务
//    disgroup.yysp.me 	     入群服务
//    dissearch.yysp.me             搜索服务
//
//    pay.yysp.me			  支付服务
//    disapi.yysp.me		      DIS API
//    disapp.yysp.me		      DIS APP服务端

}
