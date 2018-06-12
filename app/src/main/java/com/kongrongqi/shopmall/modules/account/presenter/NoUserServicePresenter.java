package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.INoUserServiceView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.NoUserServiceAdaper;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.model.BindWechatAccountModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;
import com.kongrongqi.shopmall.utils.ToastUtil;
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
public class NoUserServicePresenter extends BaseLoadingPresenter<UserService, INoUserServiceView> {

    public UserService currentCheckUserService;//当前选择的服务

    public String deviceWechatId;
    public String wechatNo;

    public void setAdapter(XRecyclerView xrecyclerView, DeviceWechat deviceWechat) {
        setXRecyclerView(xrecyclerView, new LinearLayoutManager(getContext()));
        this.deviceWechatId = deviceWechat.getId();
        this.wechatNo = deviceWechat.getWechatNo();
        mSuperAdapter = new NoUserServiceAdaper(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }


    @Override
    public void onRefreshList() {
        getUnusedService(true);
    }

    @Override
    public void onLoadMoreList() {
        getUnusedService(false);
    }

    /**
     * 获取未使用服务列表
     */
    public void getUnusedService(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("status", 0);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().getUnusedService(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<UserService>>>(getUI(), true) {
                    @Override
                    public void onNext(BaseResponse<List<UserService>> baseResponse) {
                        if (checkApiResponse(baseResponse)) {
                            refreshOrLoadMore(baseResponse.getData(), isRefresh);
                        }
                    }
                });
    }

    public void ConfirmUserService() {

        UserService currentCheckUserService = getCurrentCheckUserService();

        if (currentCheckUserService == null) {
            ToastUtil.showMessage(getContext(), "请选择要使用的服务");
            return;
        }

        itemUseService(currentCheckUserService);

//        StyleDialog.showUserService(getContext(), "确定使用", "您即将使用" + currentCheckUserService.getName() + ",是否确定?", new StyleDialog.DialogUserServiceListener() {
//            @Override
//            public void onUser() {
//                  itemUseService(currentCheckUserService);
//            }
//        });
    }

    private void itemUseService(UserService currentCheckUserService) {
        UserServiceParam userServiceParam = new UserServiceParam();
        userServiceParam.setType(currentCheckUserService.getType());
        userServiceParam.setUserId(getUserId());
        userServiceParam.setDeviceWechatId(deviceWechatId);
        userServiceParam.setWechatNo(wechatNo);
        userServiceParam.setEntrance(ServiceSuper.ACCOUNT);//设备入口
        ServiceSuper serviceSuper = new ServiceSuper(getContext(), userServiceParam).getServiceSuperUser();
        serviceSuper.useService();
    }

    public UserService getCurrentCheckUserService() {
        boolean ischeck = false;
        for (int i = 0; i < mSuperAdapter.getDatas().size(); i++) {
            UserService u = (UserService) mSuperAdapter.getDatas().get(i);
            if (u.getCheck()) {
                ischeck = true;
                currentCheckUserService = u;
            }
        }
        if (!ischeck) {
            currentCheckUserService = null;
        }
        return currentCheckUserService;
    }

    public void buyService() {
        getUI().buyService();
    }

    public void buyServiceB() {
        getUI().buyServiceB();
    }

}
