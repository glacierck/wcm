package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IAccountListView;
import com.kongrongqi.shopmall.modules.account.adapter.AccountAdaper;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;

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
public class AccountListPresenter extends BaseLoadingPresenter<AccountBind,IAccountListView> {


    public void setAdapter(XRecyclerView xrecyclerView) {
        setXRecyclerView(xrecyclerView,new LinearLayoutManager(getContext()));
        mSuperAdapter = new AccountAdaper(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    /**
     * 获取用户账户绑定列表
     */
    public void getAccountBindingList(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);

        HttpApiService.instance().getAccountBindingList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<AccountBind>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<AccountBind>> accounts) {
                        if(checkApiResponse(accounts)){
                            refreshOrLoadMore(accounts.getData(),isRefresh);
                        }
                    }
                });
    }

    @Override
    public void onRefreshList() {
        getAccountBindingList(true);
    }

    @Override
    public void onLoadMoreList() {
        getAccountBindingList(false);
    }

    /**
     * 查看详情
     */
    public void lookInfo(AccountBind accountBind) {
        getUI().lookInfo(accountBind);
    }


    public DeviceWechat getDeviceWechatBuAccountBind(AccountBind accountBind){
        DeviceWechat deviceWechat = new DeviceWechat();
        deviceWechat.setWechatNo(accountBind.getWechatNo());
        deviceWechat.setId(accountBind.getId());
        return deviceWechat;

    }


}
