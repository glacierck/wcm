package com.kongrongqi.shopmall.modules.me.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.Device_Wechat;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.me.ChangeDevicesActivity;
import com.kongrongqi.shopmall.modules.me.ChangeDevicesActivity2;
import com.kongrongqi.shopmall.modules.me.IView.IChangeDevicesView;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/20 0020 on 17:27
 * 作者:penny
 */
public class ChangeDevicesPresenter extends BasePresenter<IChangeDevicesView> {

    /**
     * 更换设备
     *
     * @param num
     */
    public void requestChangeDevice(String num, String num2) {

        if(TextUtils.isEmpty(num) || TextUtils.isEmpty(num2)){
            ToastUtil.showMessage(getContext(), "请输入设备码");
            return;
        }

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("oldMachineCode", num);
        map.put("machineCode", num2);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);

        HttpApiService.instance().updateUserDeviceByOldImei(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        Log.d("设备", "完成");
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> accounts) {
                        if(checkApiResponse(accounts,false)){
                            EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
                            EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_REFRESH));
                            ContainerActivity.lunch(getContext());
                        }
                    }
                });
    }


    /**
     * 获取设备列表数据
     */
    public void getCheckMachineCode(String num) {
        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().getAccountNumberInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<Device_Wechat>>(getUI(),false) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtil.showMessage(getContext(),"网络异常");
                    }

                    @Override
                    public void onNext(BaseResponse<Device_Wechat> baseResponse) {
                        if(checkApiResponse(baseResponse)){
                            check(baseResponse,num);
                        }
                        dismissDialog();
                    }
                });
    }


    public void check(BaseResponse<Device_Wechat> baseResponse,String num){
        List<Device> userDeviceList = baseResponse.getData().getUserDeviceList();
        if (userDeviceList == null || userDeviceList.size() <= 0) {
            ToastUtil.showMessage(getContext(),"您未绑定该设备");
            return ;
        }
        for (Device device :userDeviceList ){
            if(num.equals(device.getMachineCode())){
                ChangeDevicesActivity2.lunch(getContext(),num);
                return;
            }
        }
        ToastUtil.showMessage(getContext(),"您未绑定该设备");
    }
}
