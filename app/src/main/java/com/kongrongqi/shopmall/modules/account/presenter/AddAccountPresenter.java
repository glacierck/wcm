package com.kongrongqi.shopmall.modules.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IAddAccountView;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.utils.ToastUtil;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22
 * 作者:Qiuzhiwen
 */
public class AddAccountPresenter extends BasePresenter<IAddAccountView>{

    /**
     * 添加账号
     */
    public void addAccount(DeviceWechat deviceWechat,String wechatNo,int optType,String password) {

        if(TextUtils.isEmpty(wechatNo) || TextUtils.isEmpty(password)){
            ToastUtil.showMessage(getContext(),"请填写账号和密码");
            return;
        }

        Map<Object, Object> map = new HashMap<>();
        map.put("id",deviceWechat.getId());
        map.put("sn",deviceWechat.getSn());
        map.put("wechatNo",wechatNo);
        map.put("password",password);
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("optType",optType);//optType 操作类型 1:添加审核 2：我要换号
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().addAccount(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }
                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        if(baseResponse.getCode()==200){
                            getUI().addSuccessViewRefrash(3);
                        }else {
                            ToastUtil.showMessage(getContext(), baseResponse.getMsg());
                        }
                    }
                });
    }



    /**
     * 添加账号
     *
     * String userDeviceWechatId, String userId, String oldWechatNo,String wechatNo, String wechatPwd
     *
     */
    public void replaceUserAccount(DeviceWechat deviceWechat,String oldWechatNo ,String wechatNo,String password) {

        if(TextUtils.isEmpty(wechatNo) || TextUtils.isEmpty(password)){
            ToastUtil.showMessage(getContext(),"请填写账号和密码");
            return;
        }

        HttpApiService.instance().replaceUserAccount(deviceWechat.getId(),getUserId(),oldWechatNo,wechatNo,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }
                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        if(baseResponse.getCode()==200){
                            getUI().addSuccessViewRefrash(3);
                        }else {
                            ToastUtil.showMessage(getContext(), baseResponse.getMsg());
                        }
                    }
                });
    }




















    /**
     * 重新审核
     */
    public void reAuditAccount(DeviceWechat deviceWechat) {

        Map<Object, Object> map = new HashMap<>();
        map.put("id",deviceWechat.getId());
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().reAuditAccount(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }
                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        if(baseResponse.getCode()==200){
                            getUI().addSuccessViewRefrash(3);
                        }else {
                            ToastUtil.showMessage(getContext(), baseResponse.getMsg());
                        }
                    }
                });
    }


}
