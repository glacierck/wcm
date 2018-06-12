package com.kongrongqi.shopmall.http;

import android.content.Context;

import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.utils.SPUtils;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class ApiBean {
    //     蛇果 发短信
    public String SMS;
    //    蛇果 注册
    public String XHS ;
    //西红柿
    public String ACCOUNT2;
    //西红柿
    public String XHS_1;
    //佛手
    public String FS;
    public String FS_1;
    //支付
    public String PAY;

    public static ApiBean apiBean = new ApiBean();
    private ApiBean(){}
    public static ApiBean instance(){
        return apiBean;
    }

    public static void setApiBean(DisShopConfig disShopConfig){
        apiBean.ACCOUNT2 = disShopConfig.getkDeviceHost();
        apiBean.XHS_1 = disShopConfig.getkDeviceHost();

        apiBean.XHS = disShopConfig.getkAccountHost();
        apiBean.SMS= disShopConfig.getkSendMsgHost();

        apiBean.FS  = disShopConfig.getkServiceHost();
        apiBean.FS_1=disShopConfig.getkFindGroupHost();

        apiBean.PAY = disShopConfig.getkPayHost();
    }

    /**
     * 保存 配置到sp
     * @param context
     * @param disShopConfig
     */
    public static void saveDisShopConfig(Context context,DisShopConfig disShopConfig){

        if(API.netType==1){//test
            clearApi(context);
            return;
        }

        SPUtils.putString(context, Constans.HTTP_ACCOUNT2, disShopConfig.getkDeviceHost());
        SPUtils.putString(context, Constans.HTTP_XHS_1, disShopConfig.getkDeviceHost());

        SPUtils.putString(context, Constans.HTTP_XHS, disShopConfig.getkAccountHost());
        SPUtils.putString(context, Constans.HTTP_SMS, disShopConfig.getkSendMsgHost());

        SPUtils.putString(context, Constans.HTTP_FS, disShopConfig.getkServiceHost());
        SPUtils.putString(context, Constans.HTTP_FS_1, disShopConfig.getkFindGroupHost());

        SPUtils.putString(context, Constans.HTTP_PAY, disShopConfig.getkPayHost());
    }

    public static void getApiBean(Context context){
        if(API.netType==1){//test
            apiBean.ACCOUNT2 = API.ACCOUNT2;
            apiBean.XHS_1 = API.XHS_1;
            apiBean.XHS = API.XHS;
            apiBean.SMS= API.SMS;
            apiBean.FS  = API.FS;
            apiBean.FS_1=API.FS_1;
            apiBean.PAY = API.PAY;
        }else{
            apiBean.ACCOUNT2 = SPUtils.getString(context,Constans.HTTP_ACCOUNT2,API.ACCOUNT2);
            apiBean.XHS_1 = SPUtils.getString(context,Constans.HTTP_XHS_1,API.XHS_1);
            apiBean.XHS = SPUtils.getString(context,Constans.HTTP_XHS,API.XHS);
            apiBean.SMS= SPUtils.getString(context,Constans.HTTP_SMS,API.SMS);
            apiBean.FS  = SPUtils.getString(context,Constans.HTTP_FS,API.FS);
            apiBean.FS_1=SPUtils.getString(context,Constans.HTTP_FS_1,API.FS_1);
            apiBean.PAY = SPUtils.getString(context,Constans.HTTP_PAY,API.PAY);
        }
    }

    public static void clearApi(Context context){
        SPUtils.putString(context,Constans.HTTP_ACCOUNT2,"");
        SPUtils.putString(context,Constans.HTTP_XHS_1,"");
        SPUtils.putString(context,Constans.HTTP_XHS,"");
        SPUtils.putString(context,Constans.HTTP_SMS,"");
        SPUtils.putString(context,Constans.HTTP_FS,"");
        SPUtils.putString(context,Constans.HTTP_FS_1,"");
        SPUtils.putString(context,Constans.HTTP_PAY,"");
        getApiBean(context);//清除内存
    }

}
