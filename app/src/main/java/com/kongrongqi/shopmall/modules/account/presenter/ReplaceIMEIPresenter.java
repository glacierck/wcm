package com.kongrongqi.shopmall.modules.account.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IReplaceIMEIView;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.utils.UmengUtils;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22
 * 作者:Qiuzhiwen
 */
public class ReplaceIMEIPresenter extends BasePresenter<IReplaceIMEIView> {



    /**
     * 确认替换
     */
    public void ConfirmReplace(String machineCode,  Device device) {


        if (TextUtils.isEmpty(machineCode)) {
            ToastUtil.showMessage(getContext(), "请输入设备IMEI码");
            return;
        }
        updateUserDevice(machineCode, device);
    }

    /**
     * 确认添加
     */
    public void ConfirmAdd(String machineCode) {

        if (TextUtils.isEmpty(machineCode)) {
            ToastUtil.showMessage(getContext(), "请输入设备IMEI码");
            //友盟统计
//            UmengUtils.uMengServiceBuyCount(getContext(), Constans.GUAN_FAN_SERVICE_B,"1");
            return;
        }
        addUserDevice(machineCode);
    }


    public void clearUser(){
        clearUserData();
    }


    /**
     * 获取用户账户绑定列表
     */
    public void updateUserDevice(String machineCode, Device device) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("machineCode", machineCode);
        map.put("id", device.getId());
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);

        HttpApiService.instance().updateDevice(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> accounts) {
                        dismissDialog();
                        if (accounts.getCode() == 200) {
                            StyleDialog.showEnter(getContext(), "替换成功","您已成功替换"+device.getUserDeviceName()+"，您可以在我的设备里查看。",new StyleDialog.DialogEnterListener() {
                                @Override
                                public void onEnter() {
                                    getUI().finishAct(machineCode);
                                }
                            });
                        } else {
                            ToastUtil.showMessage(getContext(), accounts.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取用户账户绑定列表
     */
    public void addUserDevice(String machineCode) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("machineCode", machineCode);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);

        HttpApiService.instance().addUserDevice(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> accounts) {
                        dismissDialog();
                        if (accounts.getCode() == 200) {
                            SPUtils.putBoolean(getContext(), Constans.IS_HAVE_ACCOUNT, true);
                            StyleDialog.showEnter(getContext(),"添加成功","已成功添加"+accounts.getData()+"，您可以在我的设备里查看", new StyleDialog.DialogEnterListener() {
                                @Override
                                public void onEnter() {
                                    getUI().finishAct(machineCode);
                                }
                            });
                        } else {
                            ToastUtil.showMessage(getContext(), accounts.getMsg());
                        }
                    }
                });
    }


}
