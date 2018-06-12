package com.kongrongqi.shopmall.modules.me.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.me.IView.IHaveInvoiceView;
import com.kongrongqi.shopmall.modules.me.adapter.HaveOpenInvoiceAdapter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/20 0020 on 14:29
 * 作者:penny
 */
public class HaveOpenInvoicePresenter extends BaseLoadingPresenter<IvoiceModel, IHaveInvoiceView> {

    private int isBill;
    public static final String TAG = "HaveOpenInvoicePresenter";

    public HaveOpenInvoicePresenter(int isBill) {
        this.isBill = isBill;
    }

    public void setAdapter(XRecyclerView xRecyclerView) {
        setXRecyclerView(xRecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new HaveOpenInvoiceAdapter(this, getContext());
        mRecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    private void requestIvoice(boolean isRefresh) {
        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("isBill", isBill);// isBill 是否开票 1 是 0 否
        map.put("pageNumber",currentPage);
        map.put("pageSize",pageSize);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        mSuperSubscription = HttpApiService.instance().queryIvoiceList(requestBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<IvoiceModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<IvoiceModel>> response) {
                        if (checkApiResponse(response) && response.getData() != null) {
                            refreshOrLoadMore(response.getData(), isRefresh);
                        }
                    }
                });
    }


    @Override
    public void onRefreshList() {
        requestIvoice(true);
    }

    @Override
    public void onLoadMoreList() {
        requestIvoice(false);
    }
}
