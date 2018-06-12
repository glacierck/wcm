package com.kongrongqi.shopmall.modules.service.presenter;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.login.LoginActivity;
import com.kongrongqi.shopmall.modules.login.WelcomeActivity;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.LoadingDialog;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ServiceSuper {

    private ServiceBean serviceBean;
    private ServiceSuper serviceSuper;
    private Context context;

    public static int ACCOUNT = 0x001; //设备  系统加友 和 账号托管  可以走设备使用
    public static int TASK = 0x002;//进程

    private UserServiceParam userServiceParam; //使用服务参数类

    public ServiceSuper(Context context, ServiceBean serviceBean) {
        setServiceBean(serviceBean);
        setContext(context);
    }

    public ServiceSuper(Context context, UserServiceParam userServiceParam) {
        setContext(context);
        setUserServiceParam(userServiceParam);
    }


    /**
     * 获取对应的服务类型
     *
     * @return
     */
    public ServiceSuper getServiceSuper() {
        switch (serviceBean.getType()) {
            case 1://号槽
                serviceSuper = new ServiceAccount(context, serviceBean);
                break;
            case 2://系统加友A
                serviceSuper = new ServiceA(context, serviceBean);
                break;
            case 3://加友B
                serviceSuper = new ServiceB(context, serviceBean);
                break;
            case 4://入群服务
                serviceSuper = new ServiceJoin(context, serviceBean);
                break;
            case 5://账号托管
                serviceSuper = new ServiceTrusteeship(context, serviceBean);
                break;
            case 6://加友C
                serviceSuper = new ServiceC(context, serviceBean);
                break;
            default:
                serviceSuper = null;
        }
        return serviceSuper;
    }

    /**
     * 获取对应的服务类型
     *
     * @return
     */
    public ServiceSuper getServiceSuperUser() {
        switch (userServiceParam.getType()) {
            case 1://号槽
                serviceSuper = new ServiceAccount(context, userServiceParam);
                break;
            case 2://加友A
                serviceSuper = new ServiceA(context, userServiceParam);
                break;
            case 3://加友B
                serviceSuper = new ServiceB(context, userServiceParam);
                break;
            case 4://入群服务
                serviceSuper = new ServiceJoin(context, userServiceParam);
                break;
            case 5://账号托管
                serviceSuper = new ServiceTrusteeship(context, userServiceParam);
                break;
            case 6://加友C
                serviceSuper = new ServiceC(context, userServiceParam);
                break;
            default:
                serviceSuper = null;
        }
        return serviceSuper;
    }


    /**
     * 购买服务，流程
     */
    public void buyService() {

    }

    /**
     * 使用服务流程
     */
    public void useService() {

    }

    /**
     * 获取服务详情bean
     * @return
     */
    public ServiceBean getDetailServiceBean(){
        return getServiceBean();
    }


    private void setServiceBean(ServiceBean serviceBean) {
        this.serviceBean = serviceBean;
    }

    public ServiceBean getServiceBean() {
        return serviceBean;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserServiceParam getUserServiceParam() {
        return userServiceParam;
    }

    public void setUserServiceParam(UserServiceParam userServiceParam) {
        this.userServiceParam = userServiceParam;
    }

}
