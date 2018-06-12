package com.kongrongqi.shopmall.modules.service.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.login.request.RequestEmpty;
import com.kongrongqi.shopmall.modules.login.request.RequestGroup;
import com.kongrongqi.shopmall.modules.login.request.WechatGrounpRequest;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.ServiceListModel;
import com.kongrongqi.shopmall.modules.model.WechatGrounpModel;
import com.kongrongqi.shopmall.modules.model.WechatResultsBean;
import com.kongrongqi.shopmall.modules.service.IView.IJoinView;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.service.adapter.GrounpAdapter;
import com.kongrongqi.shopmall.utils.Logger;

import java.util.List;
import java.util.zip.Inflater;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/25 0025 on 18:21
 * 作者:penny
 */
public class JoinGrounpPresenter extends BaseLoadingPresenter<WechatGrounpModel,IJoinView> {
    public static final String TAG = "JoinGrounpPresenter";

    private Subscription mSubscribe;
    private String serachText="";
    private ServiceBean serviceBean;

    @Override
    public void onRefreshList() {
        requestData(serachText,true);
    }

    @Override
    public void onLoadMoreList() {
        requestData(serachText,false);
    }

    public void setAdapter(XRecyclerView recyclerView, ServiceBean serviceBean) {
        this.serviceBean= serviceBean;
        setXRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new GrounpAdapter(getContext(), this);
        recyclerView.setAdapter(mSuperAdapter);
    }

    public void serach(String serachText){
        this.serachText=serachText;
        mRecyclerView.refresh();
    }

    public void requestData(String serachText,boolean isRefresh) {
        if (TextUtils.isEmpty(serachText)) {
            showLongToast(getContext().getString(R.string.serach_is_null));
            return;
        }
        RequestGroup requestEmpty = new RequestGroup();
        requestEmpty.setGroupName(serachText);
        mSubscribe = HttpApiService.instance().wechatGrounp(requestEmpty)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<WechatGrounpModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<WechatGrounpModel>> response) {
                        if (checkApiResponse(response)) {
                            List<WechatGrounpModel> data = response.getData();
                            refreshOrLoadMore(data, isRefresh);
                        }
                    }
                });
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mSubscribe);
    }

    /**
     * 购买群组服务
     *  @param position
     * @param o
     */
    public void buyGroupService(int position, WechatGrounpModel o) {
        OrderNumRequest orderInfo = getOrderInfo(o);
        PayActivity.lunch(getContext(),orderInfo,false);
        if(isActivityExist()){
            getUI().finishAct();
        }
    }

    private OrderNumRequest getOrderInfo(WechatGrounpModel model) {
        String accountId = getUserId();
        OrderNumRequest request = new OrderNumRequest();

        request.setEntrance(serviceBean.getEntrance());//在哪个入口购买服务
        request.setDeviceWechatId(serviceBean.getDeviceWechatId());
        request.setWechatNo(serviceBean.getWechatNo());

        request.setName(serviceBean.getName());
        request.setPrice(model.getGroupWorth());
        request.setUserId(accountId);
        request.setContent(model.getTargetGroupMessage());
        request.setContentName(model.getGroupCreatorName());
        request.setId(serviceBean.getId());
        request.setGroupName(model.getGroupName());
        request.setRelationId(model.getId());
        request.setType(serviceBean.getType());
        request.setDuration(serviceBean.getDuration());
        request.setDurationUnit(serviceBean.getDurationUnit());
        return request;
    }
}
