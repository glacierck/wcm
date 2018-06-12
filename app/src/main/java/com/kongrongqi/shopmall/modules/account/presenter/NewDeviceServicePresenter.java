package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.INewDeviceServiceView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.NewDeviceServiceAdaper;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:pen
 */
public class NewDeviceServicePresenter extends BasePresenter<INewDeviceServiceView> {
//    public class NewDeviceServicePresenter extends BaseLoadingPresenter<UserService,INewDeviceServiceView>{

    private UserService userService;


//    public void setAdapter(XRecyclerView xrecyclerView) {
//        setXRecyclerView(xrecyclerView,new LinearLayoutManager(getContext()));
//        mSuperAdapter = new NewDeviceServiceAdaper(this, getContext());
//        xrecyclerView.setAdapter(mSuperAdapter);
//        mRecyclerView.refresh();
//    }


//    @Override
//    public void onRefreshList() {
//        getNewDeviceServiceInfo(true);
//    }
//
//    @Override
//    public void onLoadMoreList() {
//        getNewDeviceServiceInfo(false);
//    }

    /**
     * 设备帐号已满，请购买新服务
     */
    public void getOneNewDeviceServiceInfo(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().getOneNewDeviceServiceInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UserService>>(getUI(), true) {
                    @Override
                    public void onNext(BaseResponse<UserService> baseResponse) {
                        if (checkApiResponse(baseResponse)) {
                            if (baseResponse.getData() != null) {
                                userService = baseResponse.getData();
                                getUI().setImage(baseResponse.getData().getImgUrl());
                            }
//                            refreshOrLoadMore(baseResponse.getData(),isRefresh);
                        }
                    }
                });
    }


    public void confirmUserService(UserService userService) {

//        if (userService != null) {
            buyNewDevice(userService);
//        }
//        StyleDialog.showUserService(getContext(),"确认购买","您即将购买"+userService.getName()+"，是否确定？",new StyleDialog.DialogUserServiceListener(){
//            @Override
//            public void onUser() {
//
//            }
//        });
    }


    public void confirmUserService() {
//        public void confirmUserService(UserService userService){

        if (userService != null) {
            buyNewDevice(userService);
        }
//        StyleDialog.showUserService(getContext(),"确认购买","您即将购买"+userService.getName()+"，是否确定？",new StyleDialog.DialogUserServiceListener(){
//            @Override
//            public void onUser() {
//
//            }
//        });
    }

    private void buyNewDevice(UserService userService) {
        String accountId = getUserId();
        OrderNumRequest request = new OrderNumRequest();
        request.setName(userService.getName());
        request.setPrice(userService.getPrice() + "");
        request.setUserId(accountId);
        request.setContent(userService.getContent());
        request.setContentName(userService.getContentName());
        request.setDurationName(userService.getDurationName());
        request.setId(userService.getId());
        request.setType(userService.getType());

        request.setDuration(userService.getDuration());
        request.setDurationUnit(userService.getDurationUnit());
        PayActivity.lunch(getContext(), request, true);
    }
}
