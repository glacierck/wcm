package com.kongrongqi.shopmall.modules.service.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.me.bean.Banner;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.IServiceView;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.service.ProductDetailActivity;
import com.kongrongqi.shopmall.modules.service.adapter.ServiceAdaper;
import com.kongrongqi.shopmall.modules.service.holder.LocalImageHolderView;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:26
 * 作者:penny
 */
public class ServicePresenter extends BaseLoadingPresenter<ServiceBean, IServiceView> implements CBViewHolderCreator {
    public static final String TAG = "ServicePresenter";

    List<Banner> mBannerList = new ArrayList();
    private Subscription mServicesSubscribe;
    private Subscription mSubscribe;

    public void setAdapter(XRecyclerView xrecyclerView) {
        setXRecyclerView(xrecyclerView, new LinearLayoutManager(getContext()));
        setLoadingMoreEnabled(false);
        mSuperAdapter = new ServiceAdaper(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        xrecyclerView.refresh();
    }

    public List<Banner> getmBannerList() {
        return mBannerList;
    }

    /**
     * banner
     *
     * @param convenientBanner
     */
    public void setBanner(ConvenientBanner convenientBanner) {
        mSubscribe = HttpApiService.instance().banner(2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<Banner>>>(getUI(), false) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(BaseResponse<List<Banner>> response) {
                        if (checkApiResponse(response, false)) {
                            Logger.d("splash", "===========");
                            List<Banner> data = response.getData();
                            int size = data.size();
                            convenientBanner.setPages(ServicePresenter.this, data)
                                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //居中
                                    .setCanLoop(true);
                        }
                    }
                });
    }

    @Override
    public LocalImageHolderView createHolder() {
        return new LocalImageHolderView(mBannerList, getContext(),this);
    }


    public void BuyService(int position, ServiceAdaper serviceAdaper, String serviceName) {
        ServiceBean datas = (ServiceBean) serviceAdaper.getDatas().get(position);
        lunchDifferentUI(datas);
    }

    /**
     * 跳转到不同的界面
     *
     * @param type
     * @param model
     */
    private void lunchDifferentUI(ServiceBean model) {
        ServiceSuper serviceSuper = new ServiceSuper(getContext(), model).getServiceSuper();
        serviceSuper.buyService();
    }

    /**
     * ITEM 点击事件
     *
     * @param position
     * @param serviceAdaper
     */
    public void startProductDetail(ServiceBean mListData) {
        ProductDetailActivity.lunch(getContext(), mListData);
    }

    /**
     * 请求服务商品列表
     *
     * @param isRefresh
     */
    public void getServiceList(boolean isRefresh) {
        mServicesSubscribe = HttpApiService.instance().serviceList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<ServiceBean>>>(getUI(), true) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        onErrorOpration(isRefresh);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<List<ServiceBean>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            List<ServiceBean> data = listBaseResponse.getData();
                            refreshOrLoadMore(data, isRefresh);
                            SPUtils.saveList(data, getContext(), Constans.SERVICE_LIST_CACHE);
                        }
                    }
                });

    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mServicesSubscribe);
        releaseSubscription(mSubscribe);
    }

    @Override
    public void onRefreshList() {
        getServiceList(true);
    }

    @Override
    public void onLoadMoreList() {

    }
}

