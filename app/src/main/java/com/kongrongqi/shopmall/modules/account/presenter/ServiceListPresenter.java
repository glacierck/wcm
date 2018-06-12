package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.IAddServiceTabView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.ServiceAdaper;
import com.kongrongqi.shopmall.modules.account.adapter.ServiceAdaper2;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.ProductDetailActivity;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class ServiceListPresenter extends BaseLoadingPresenter<ServiceBean, IAddServiceTabView> {


    public ServiceBean currentCheckUserService;//当前选择的服务
    DeviceWechat deviceWechat;
    @Override
    public void onRefreshList() {
        getServiceList(true);
    }

    @Override
    public void onLoadMoreList() {
        getServiceList(false);
    }

    public void setAdapter(XRecyclerView xrecyclerView, DeviceWechat deviceWechat) {
        this.deviceWechat=deviceWechat;
        setXRecyclerView(xrecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new ServiceAdaper2(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    /**
     * 获取服务列表
     */
    public void getServiceList(boolean isRefresh) {

        HttpApiService.instance().serviceList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<ServiceBean>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<ServiceBean>> accounts) {
                        if (checkApiResponse(accounts) && accounts.getData()!=null) {
                            refreshOrLoadMore(accounts.getData(), isRefresh);
                        }
                    }
                });
    }
    /**
     * ITEM 点击事件
     *
     * @param position
     * @param serviceAdaper
     */
    public void startProductDetail(ServiceBean mListData) {
        mListData.setEntrance(ServiceSuper.ACCOUNT);
        ProductDetailActivity.lunch(getContext(), mListData);
    }


    public void BuyService(int position, ServiceAdaper2 serviceAdaper, String serviceName) {
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
        model.setEntrance(ServiceSuper.ACCOUNT);
        model.setDeviceWechatId(deviceWechat.getId());
        model.setWechatNo(deviceWechat.getWechatNo());
        ServiceSuper serviceSuper = new ServiceSuper(getContext(), model).getServiceSuper();
        serviceSuper.buyService();

    }

    public void buyServiceB(){
        getUI().buyServiceB();
    }
}
