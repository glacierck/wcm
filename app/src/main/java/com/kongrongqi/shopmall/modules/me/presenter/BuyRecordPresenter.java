package com.kongrongqi.shopmall.modules.me.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.me.IView.IBuyRecordView;
import com.kongrongqi.shopmall.modules.me.adapter.DealAdapter;
import com.kongrongqi.shopmall.modules.me.bean.BuyRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/19 0019 on 14:46
 * 作者:penny
 */
public class BuyRecordPresenter extends BaseLoadingPresenter<BuyRecord, IBuyRecordView> {


    /**
     * 交易记录
     *
     * @param xRecyclerView
     */
    public void uiDeal(XRecyclerView xRecyclerView) {
        setXRecyclerView(xRecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new DealAdapter(this, getContext());
        xRecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }


    /**
     * 获取用户账户绑定列表
     */
    public void getUserOrderList(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("pageNumber",currentPage);
        map.put("pageSize",pageSize);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().getUserOrderList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<BuyRecord>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<BuyRecord>> accounts) {
                        if (checkApiResponse(accounts)) {
                            refreshOrLoadMore(accounts.getData(), isRefresh);
                        }
                    }
                });
    }
    @Override
    public void onRefreshList() {
        getUserOrderList(true);
    }

    @Override
    public void onLoadMoreList() {
        getUserOrderList(false);
    }

}
