package com.kongrongqi.shopmall.modules.service.presenter;

import android.content.Context;

import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.model.FansBListModel;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.task.FansBServiceActivity;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.UserUtil;

/**
 * Created by Administrator on 2017/6/26 0026.
 * 加友A
 */

public class ServiceA extends ServiceSuper {

    public ServiceA(Context context, ServiceBean serviceBean) {
        super(context, serviceBean);
    }

    public ServiceA(Context context, UserServiceParam userServiceParam) {
        super(context, userServiceParam);
    }


    @Override
    public void buyService() {
        PayActivity.lunch(getContext(), getOrderInfo(getServiceBean()), false);
    }

    @Override
    public void useService() {
        FansBServiceActivity.lunch(getContext(), getUserServiceParam());
    }


    @Override
    public ServiceBean getDetailServiceBean() {
        ServiceBean serviceBean = getServiceBean();
        serviceBean.setDurationText(serviceBean.getDuration() + serviceBean.getDurationUnit()); //服务周期
        serviceBean.setPriceText(StringUtils.jointStr(serviceBean.getPrice()));//服务价格 +“￥”
        serviceBean.setPrice(serviceBean.getPrice());//服务价格 +“￥”
        serviceBean.setNotice("注意事项：" + serviceBean.getNotice());
        serviceBean.setButtonText("立即购买");

        return serviceBean;
    }

    /**
     * 组装获取订单信息的bean
     *
     * @param model
     * @return
     */
    private OrderNumRequest getOrderInfo(ServiceBean model) {
        String accountId = UserUtil.getUserId(getContext());
        OrderNumRequest request = new OrderNumRequest();
        request.setEntrance(model.getEntrance());//在哪个入口购买服务
        request.setDeviceWechatId(model.getDeviceWechatId());
        request.setWechatNo(model.getWechatNo());

        request.setName(model.getName());
//        request.setPrice(model.getPrice());
        request.setUserId(accountId);
        request.setContent(model.getContent());
        request.setContentName(model.getContentName());
        request.setDurationName(model.getDurationName());
        request.setId(model.getId());
        request.setType(model.getType());
        request.setDurationUnit(model.getDurationUnit());
        request.setDuration(model.getDuration());
        return request;
    }

}
