package com.kongrongqi.shopmall.modules.account.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IMoreEquipView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.MoreEquipAdaper;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.Device_Wechat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:pen*/
public class MoreEquipListPresenter extends BaseLoadingPresenter<Device,IMoreEquipView>{


    public void setAdapter(XRecyclerView xrecyclerView) {
        setXRecyclerView(xrecyclerView);
        mSuperAdapter = new MoreEquipAdaper(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    /**
     * 获取帐号列表数据
     */
    public void getAccountNumberInfo(boolean isRefresh) {
        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().getAccountNumberInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<Device_Wechat>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<Device_Wechat> baseResponse) {
                        if(checkApiResponse(baseResponse)){
                            List<Device> userDeviceList = baseResponse.getData().getUserDeviceList();
                            refreshOrLoadMore(userDeviceList,isRefresh);
                        }
                    }
                });
    }

    public void selectPage(int page){
        getUI().selectPage(page);
    }


    @Override
    public void onRefreshList() {
        getAccountNumberInfo(true);
    }

    @Override
    public void onLoadMoreList() {
        getAccountNumberInfo(false);
    }
}
