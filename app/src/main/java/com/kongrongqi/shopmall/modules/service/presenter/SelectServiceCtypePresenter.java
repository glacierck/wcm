package com.kongrongqi.shopmall.modules.service.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.login.request.RequestGroup;
import com.kongrongqi.shopmall.modules.model.FansModel;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.ServiceListModel;
import com.kongrongqi.shopmall.modules.model.WechatGrounpModel;
import com.kongrongqi.shopmall.modules.service.IView.IJoinView;
import com.kongrongqi.shopmall.modules.service.IView.ISelectServiceCtypeView;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.modules.service.adapter.GrounpAdapter;
import com.kongrongqi.shopmall.modules.service.adapter.SelectServiceCtypeAdapter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/25 0025 on 18:21
 * 作者:penny
 */
public class SelectServiceCtypePresenter extends BaseLoadingPresenter<FansModel,ISelectServiceCtypeView> {
    public static final String TAG = "JoinGrounpPresenter";

    private Subscription mSubscribe;
    private ServiceBean serviceBean;

    @Override
    public void onRefreshList() {
        requestData(true);
    }

    @Override
    public void onLoadMoreList() {
        requestData(false);
    }

    public void setAdapter(XRecyclerView recyclerView,ServiceBean serviceBean) {
        this.serviceBean=serviceBean;
        setXRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new SelectServiceCtypeAdapter(getContext(), this);
        recyclerView.setAdapter(mSuperAdapter);
        recyclerView.refresh();
    }


    public void requestData(boolean isRefresh) {

        mSubscribe = HttpApiService.instance().findAllFans()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<FansModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<FansModel>> response) {
                        if (checkApiResponse(response)) {
                            List<FansModel> data = response.getData();
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
    public void buyGroupService() {

        List<FansModel> checkIvoice = getCheckIvoice();

        if(checkIvoice.size()<=0){
            ToastUtil.showMessage(getContext(),"请选择服务类型");
            return;
        }

        OrderNumRequest orderInfo = getOrderInfo(serviceBean);
        PayActivity.lunch(getContext(),orderInfo,false);
        if(isActivityExist()){
            getUI().finishAct();
        }
    }

    private OrderNumRequest getOrderInfo(ServiceBean model) {
        String accountId = getUserId();
        OrderNumRequest request = new OrderNumRequest();

        request.setEntrance(model.getEntrance());//在哪个入口购买服务
        request.setDeviceWechatId(model.getDeviceWechatId());
        request.setWechatNo(model.getWechatNo());

        request.setName(model.getName());
        request.setPrice(getPrice()+"");
        request.setUserId(accountId);
        request.setContent(model.getContent());
        request.setContentName(model.getContentName());
        request.setId(model.getId());
        request.setRelationId(model.getId());
        request.setType(model.getType());
        request.setDuration(model.getDuration());
        //粉丝类型
        request.setFansTypeId(getOrderId(getCheckIvoice()));
        request.setDurationUnit(model.getDurationUnit());
        request.setFansTypeName(getOrderName(getCheckIvoice()));
        return request;
    }


    /**
     * 获取订单id 已","分割
     *
     * @param ivoiceModels
     * @return
     */
    public String getOrderId(List<FansModel> ivoiceModels) {
        String orderId = "";
        for (int i = 0; i < ivoiceModels.size(); i++) {
            if (i == ivoiceModels.size() - 1) {
                orderId += ivoiceModels.get(i).getId();
            } else {
                orderId += ivoiceModels.get(i).getId();
                orderId += ",";
            }
        }
        return orderId;
    }


    /**
     * 获取订单id 已","分割
     *
     * @param ivoiceModels
     * @return
     */
    public String getOrderName(List<FansModel> ivoiceModels) {
        String orderName = "";
        for (int i = 0; i < ivoiceModels.size(); i++) {
            if (i == ivoiceModels.size() - 1) {
                orderName += ivoiceModels.get(i).getFansName();
            } else {
                orderName += ivoiceModels.get(i).getFansName();
                orderName += ",";
            }
        }
        return orderName;
    }




    public double getPrice(){
        List<FansModel> checkIvoice = getCheckIvoice();
        BigDecimal price =  new BigDecimal("0.00");
        for (FansModel ivoiceModel : checkIvoice){
            BigDecimal b1 = new BigDecimal(ivoiceModel.getPrice().toString());
            price = price.add(b1);
        }
        double   f1   =   price.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }





    public void bindCheckIvoice(){
        List<FansModel> checkIvoice = getCheckIvoice();
        int count = checkIvoice.size();
        BigDecimal price =  new BigDecimal("0.00");
        for (FansModel ivoiceModel : checkIvoice){
            BigDecimal b1 = new BigDecimal(ivoiceModel.getPrice().toString());
            price = price.add(b1);
        }
        double   f1   =   price.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        getUI().setCheckPice(count,f1);
    }

    public List<FansModel> getCheckIvoice(){
        List<FansModel> ivoiceModelList = new ArrayList<>();
        for (int i = 0; i< mSuperAdapter.getDatas().size(); i++){
            FansModel ivoiceModel = (FansModel) mSuperAdapter.getDatas().get(i);
            if(ivoiceModel.isCheck()){
                ivoiceModelList.add(ivoiceModel);
            }
        }
        return ivoiceModelList;
    }


}
