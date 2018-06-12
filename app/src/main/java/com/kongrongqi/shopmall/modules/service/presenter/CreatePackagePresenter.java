package com.kongrongqi.shopmall.modules.service.presenter;

import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.me.bean.JsonBean;
import com.kongrongqi.shopmall.modules.model.FansModel;
import com.kongrongqi.shopmall.modules.model.IndustryModel;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.ISelectPackageView;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.OptionsPickerUtil;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;
import com.kongrongqi.shopmall.wedget.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import retrofit2.http.Field;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/25 0025 on 18:21
 * 作者:penny
 */
public class CreatePackagePresenter extends BasePresenter<ISelectPackageView> {

    public void createServiceForOrienteering(ServiceBean serviceBean, String daysEachPart, String parts) {
        int daysEachPartInt = StringUtils.getStrToInt(daysEachPart);
        int partsInt = StringUtils.getStrToInt(parts);

        if(daysEachPartInt<=0){
            ToastUtil.showMessage(getContext(),"请填写天数");
            return;
        }

        if(partsInt<=0){
            ToastUtil.showMessage(getContext(),"请填写份数");
            return;
        }

        HttpApiService.instance().createServiceForOrienteering(6,getUserId(),daysEachPartInt,partsInt)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ServiceBean>>(getUI(), false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<ServiceBean> response) {
//                        dismissDialog();
                        if (checkApiResponse(response, false)) {
                            ServiceBean data = response.getData();
                            buyGroupService(serviceBean,data,partsInt,daysEachPartInt);
                        }
                    }
                });
    }


    /**
     * 购买群组服务
     *  @param position
     * @param o
     */
    public void buyGroupService(ServiceBean serviceBean,ServiceBean data,int parts,int daysEachPartInt) {

        OrderNumRequest orderInfo = getOrderInfo(serviceBean,data,parts,daysEachPartInt);
        PayActivity.lunch(getContext(),orderInfo,false);
        if(isActivityExist()){
            getUI().finishAct();
        }
    }

    private OrderNumRequest getOrderInfo(ServiceBean serviceBean,ServiceBean data,int partsInt,int daysEachPartInt) {
        String accountId = getUserId();
        OrderNumRequest request = new OrderNumRequest();

        request.setEntrance(serviceBean.getEntrance());//在哪个入口购买服务
        request.setDeviceWechatId(serviceBean.getDeviceWechatId());
        request.setWechatNo(serviceBean.getWechatNo());

        String address = StringUtils.joint(serviceBean.getCategory(), serviceBean.getAddress(),serviceBean.getGender());

        request.setName(address);
        request.setPrice(data.getPrice());
        request.setUserId(accountId);
        request.setContent(serviceBean.getContent());
        request.setContentName(serviceBean.getContentName());
        request.setId(data.getId());
        request.setRelationId(serviceBean.getId());
        request.setType(serviceBean.getType());
        request.setDuration(data.getDuration());
        request.setDurationUnit(data.getDurationUnit());

        //address  地址 category 行业 gender 性别
        request.setAddress(serviceBean.getAddress());
        request.setCategory(serviceBean.getCategory());
        request.setGender(serviceBean.getGender());
        request.setSplitCopies(partsInt);
        request.setExpireDays((daysEachPartInt+serviceBean.getValidDay())+"");
        return request;
    }





    @Override
    public void onUIDestory() {
        super.onUIDestory();
    }


}
