package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IAddServiceTabView;
import com.kongrongqi.shopmall.modules.account.adapter.AddServiceTabAdaper;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.ServiceListModel;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class AddServiceTabPresenter extends BaseLoadingPresenter<ServiceBean,IAddServiceTabView> {

    public void setAdapter(XRecyclerView xrecyclerView) {

        setXRecyclerView(xrecyclerView,new LinearLayoutManager(getContext()));
        mSuperAdapter =  new AddServiceTabAdaper(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }


    @Override
    public void onRefreshList() {
        getServiceList(true);
    }

    @Override
    public void onLoadMoreList() {
        getServiceList(false);
    }

    /**
     * 请求服务商品列表
     */
    public void getServiceList(boolean isRefresh) {
        mSuperSubscription = HttpApiService.instance().serviceList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<ServiceBean>>>(getUI(),false) {
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
                    public void onNext(BaseResponse<List<ServiceBean>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            refreshOrLoadMore(listBaseResponse.getData(), isRefresh);
                        }
                    }
                });
    }


}
