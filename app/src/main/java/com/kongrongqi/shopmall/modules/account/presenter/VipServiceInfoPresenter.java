package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IVipInfoServiceView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.VipServiceInfoAdaper;
import com.kongrongqi.shopmall.modules.account.bean.FanOrGroup;
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
public class VipServiceInfoPresenter extends BaseLoadingPresenter<FanOrGroup, IVipInfoServiceView> {
    Integer type;
    String deviceWechatId;

    public void setAdapter(XRecyclerView xrecyclerView, Integer type, String deviceWechatId) {
        this.type = type;
        this.deviceWechatId = deviceWechatId;
        setXRecyclerView(xrecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new VipServiceInfoAdaper(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    public void getAddFanOrGroupList(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("type", type);
        map.put("deviceWechatId", deviceWechatId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);


        HttpApiService.instance().getAddFanOrGroupList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<FanOrGroup>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<FanOrGroup>> fanOrGroups) {
                        if (checkApiResponse(fanOrGroups)) {
                            refreshOrLoadMore(fanOrGroups.getData(), isRefresh);
                        }
                    }
                });
    }

    @Override
    public void onRefreshList() {
        getAddFanOrGroupList(true);
    }

    @Override
    public void onLoadMoreList() {
        getAddFanOrGroupList(false);
    }
}
