package com.kongrongqi.shopmall.modules.service.presenter;

import android.content.Context;

import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.IView.IServiceManage;

abstract class ServiceManage implements IServiceManage {

    protected UserServiceParam userParam;//使用服务参数
    protected ServiceBean serviceBean;//服务
    protected OrderNumRequest orderNumRequest;//购买订单参数
    protected Class[] jumpPageForBuy; //购买服务跳转页面 集合
    protected Class[] jumpPageForUser; //使用服务跳转页面 集合
    protected Context mContext;


    /**
     * 获取对应的服务类型
     *
     * @return
     */
    public static  ServiceManage  ServiceManage(Context context,ServiceBean serviceBean) {
        ServiceManage serviceManage ;
        switch (serviceBean.getType()) {
            case 2://系统加友A
                serviceManage = new SystemAddFriends(context, serviceBean);
                break;
            case 3://加友B
                serviceManage = new SelfHelpAddFriends(context, serviceBean);
                break;
            case 5://账号托管
                serviceManage = new Trusteeship(context, serviceBean);
                break;
            case 4://入群服务
            case 6://加友C
            case 1://号槽
            default:
                serviceManage = null;
        }
        return serviceManage;
    }


    /**
     *  购买服务 页面跳转
     */
    @Override
    public void jumpPageForBuy() {

    }

    /**
     *  使用服务 页面跳转
     */
    @Override
    public void jumpPageForUser() {

    }

    /**
     * 得到购买参数
     */
    @Override
    public OrderNumRequest getBuyParam() {
        return orderNumRequest;
    }

    /**
     * 得到使用参数
     */
    @Override
    public void getUserParam() {

    }
}
