package com.kongrongqi.shopmall.modules.service.presenter;

import android.content.Context;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.SelectPackageActivty;
import com.kongrongqi.shopmall.modules.service.SelectServiceCTypeActivty;
import com.kongrongqi.shopmall.modules.task.FansBServiceActivity;
import com.kongrongqi.shopmall.modules.task.UseServiceActivity;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ServiceC extends ServiceSuper{

    public ServiceC(Context context, ServiceBean serviceBean) {
        super(context, serviceBean);
    }

    public ServiceC(Context context, UserServiceParam userServiceParam) {
        super(context,userServiceParam);
    }

    @Override
    public void buyService() {
        SelectPackageActivty.lunch(getContext(),getServiceBean());
    }

    @Override
    public void useService() {
        FansBServiceActivity.lunch(getContext(), getUserServiceParam());
    }

    @Override
    public ServiceBean getDetailServiceBean() {
        ServiceBean serviceBean = getServiceBean();
        serviceBean.setDuration(serviceBean.getDuration()+serviceBean.getDurationUnit()); //服务周期
        serviceBean.setPrice(getContext().getResources().getString(R.string.service_c));//根据数据量定
        serviceBean.setPriceText(getContext().getResources().getString(R.string.service_c));//根据数据量定
        serviceBean.setNotice("注意事项："+serviceBean.getNotice());
        serviceBean.setButtonText("选择定向包");
        return serviceBean;
    }
}
