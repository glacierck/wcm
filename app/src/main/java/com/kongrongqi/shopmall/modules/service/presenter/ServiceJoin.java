package com.kongrongqi.shopmall.modules.service.presenter;

import android.content.Context;

import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.JoinGrounpActivty;
import com.kongrongqi.shopmall.modules.task.WaitForGrounpActivity;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ServiceJoin extends ServiceSuper{

    public ServiceJoin(Context context, ServiceBean serviceBean) {
        super(context, serviceBean);
    }

    public ServiceJoin(Context context, UserServiceParam userServiceParam) {
        super(context,userServiceParam);
    }

    @Override
    public void buyService() {
        JoinGrounpActivty.lunch(getContext(), getServiceBean());
    }

    @Override
    public void useService() {
        WaitForGrounpActivity.lunch(getContext(), getUserServiceParam());
    }

    @Override
    public ServiceBean getDetailServiceBean() {
        ServiceBean serviceBean = getServiceBean();

        serviceBean.setDuration("选择执行操作后两个工作日内"); //服务周期
        serviceBean.setPrice("根据群价值定价");//根据数据量定
        serviceBean.setPriceText("根据群价值定价");//服务价格 +“￥”
        serviceBean.setNotice("");
        serviceBean.setButtonText("马上选择群");
        serviceBean.setJoin(true);
        return serviceBean;
    }

}
