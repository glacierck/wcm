package com.kongrongqi.shopmall.modules.service.presenter;

import android.text.TextUtils;

import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UpLoadExcelModel;
import com.kongrongqi.shopmall.modules.service.IView.IServiceSetView;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.utils.CommonUtils;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;

/**
 * 创建日期：2017/5/31 0031 on 16:22
 * 作者:penny
 */
public class ServiceSetPresenter extends BasePresenter<IServiceSetView> {


    public void payService(ServiceBean mServiceModel, UpLoadExcelModel upLoadExcelModel, String serviceName, String serviceNum) {
        if (TextUtils.isEmpty(serviceNum) || StringUtils.getStrToInt(serviceNum) < 1) {
            ToastUtil.showMessage(getContext(), "请划分服务份数");
            return;
//            serviceNum="1";
        }
        OrderNumRequest orderInfo = getOrderInfo(upLoadExcelModel, mServiceModel, serviceName, serviceNum);
        PayActivity.lunch(getContext(), orderInfo, false);
    }


    private OrderNumRequest getOrderInfo(UpLoadExcelModel model, ServiceBean pBean, String serviceName, String serviceNum) {
        String accountId = getUserId();
        OrderNumRequest request = new OrderNumRequest();

        request.setEntrance(pBean.getEntrance());//在哪个入口购买服务
        request.setDeviceWechatId(model.getDeviceWechatId());
        request.setWechatNo(model.getWechatNo());

        request.setSplitCopies(StringUtils.getStrToInt(serviceNum));
        request.setName(TextUtils.isEmpty(serviceName) ? model.getName() : serviceName);

        request.setPrice(model.getPrice() + "");
        request.setUserId(accountId);
        request.setContent(model.getContent());
        request.setContentName(model.getContentName());
        request.setId(model.getId());
        request.setType(pBean.getType());
        request.setDuration(model.getDuration());
        int duration = StringUtils.getStrToInt(TextUtils.isEmpty(model.getDuration()) ? "0" : model.getDuration());
        request.setExpireDays(duration + pBean.getValidDay() + "");
        request.setDurationUnit(model.getDurationUnit());
        request.setDurationFansNum(model.getDurationFansNum());
        return request;
    }


}
