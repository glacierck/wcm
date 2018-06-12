package com.kongrongqi.shopmall.modules.service.presenter;

import android.content.Context;

import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.UpLoadActivity;
import com.kongrongqi.shopmall.modules.task.FansBServiceActivity;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ServiceB extends ServiceSuper{

    public ServiceB(Context context, ServiceBean serviceBean) {
        super(context, serviceBean);
    }

    public ServiceB(Context context, UserServiceParam userServiceParam) {
        super(context,userServiceParam);
    }

    @Override
    public void buyService() {
        UpLoadActivity.lunch(getContext(), getServiceBean());
    }

    @Override
    public void useService() {
        FansBServiceActivity.lunch(getContext(), getUserServiceParam());
    }

    @Override
    public ServiceBean getDetailServiceBean() {
        ServiceBean serviceBean = getServiceBean();
        serviceBean.setDuration(serviceBean.getDuration()); //服务周期
        serviceBean.setPriceText(serviceBean.getDuration());//根据数据量定
        serviceBean.setNotice("注意事项："+serviceBean.getNotice());
        serviceBean.setButtonText("马上上传数据");

        return serviceBean;
    }
}
