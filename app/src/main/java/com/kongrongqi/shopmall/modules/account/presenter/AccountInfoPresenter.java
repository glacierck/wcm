package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IAccountInfoView;
import com.kongrongqi.shopmall.modules.account.adapter.AccountInfoAdaper;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.bean.AccountInfo;
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
public class AccountInfoPresenter extends BaseLoadingPresenter<AccountInfo,IAccountInfoView> {

    private AccountBind accountBind;
    public void setAdapter(XRecyclerView xrecyclerView, AccountBind accountBind) {
        this.accountBind=accountBind;
        setXRecyclerView(xrecyclerView,new LinearLayoutManager(getContext()));
        mSuperAdapter = new AccountInfoAdaper(this, getContext(),accountBind);
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    public void getWechatDetail(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("deviceWechatId", accountBind.getId());
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);

        mSuperSubscription = HttpApiService.instance().getWechatDetail(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<AccountInfo>>>(getUI(),true) {

                    @Override
                    public void onNext(BaseResponse<List<AccountInfo>> fanOrGroups) {
                        if(checkApiResponse(fanOrGroups)){
                            refreshOrLoadMore(fanOrGroups.getData(),isRefresh);
                        }
                    }
                });
    }

    public void starVipInfo(AccountInfo accountInfo) {
        getUI().starVipInfo(accountInfo);
    }

    @Override
    public void onRefreshList() {
        getWechatDetail( true);
    }

    @Override
    public void onLoadMoreList() {
        getWechatDetail(false);
    }
}
