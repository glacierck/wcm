package com.kongrongqi.shopmall.modules.service.presenter;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.service.IView.IReceiverAddressView;
import com.kongrongqi.shopmall.modules.service.adapter.ReceiverAddressAdapter;
import com.kongrongqi.shopmall.utils.StringUtils;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22 0022 on 13:28
 * 作者:penny
 */
public class ReceiverAddressPresenter extends BaseLoadingPresenter<AddressModel, IReceiverAddressView> {
    public static final String TAG = "ReceiverAddressPresenter";
    private Subscription mSubscription;
    private Subscription msubscription2;

    public void setAdapter(XRecyclerView recyclerView) {
        setXRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new ReceiverAddressAdapter(this, getContext());
        recyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    @Override
    public void onRefreshList() {
        requestAddress(true);
    }

    @Override
    public void onLoadMoreList() {
        requestAddress(false);
    }

    private void requestAddress(boolean refresh) {
        String accountId = getUserId();
        mSubscription = HttpApiService.instance().addressList(accountId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<AddressModel>>>(getUI(), true) {
                    @Override
                    public void onNext(BaseResponse<List<AddressModel>> response) {
                        if (checkApiResponse(response)) {
                            List<AddressModel> data = response.getData();
                            refreshOrLoadMore(data, refresh);
//                            if (data != null && data.size() > 0) {
//                                AddressModel addressModel = data.get(0);
//                                if (addressModel.getIsDefault() == 1) {
//                                    chooseAddress(addressModel);
//                                }
//                            }
                        }
                    }
                });
    }

    ///user/deleteConsigneeAddress
    public void deleteConsigneeAddress(String userReceiveAddressId) {
        String accountId = getUserId();
        msubscription2 = HttpApiService.instance().deleteConsigneeAddress(userReceiveAddressId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(), false) {

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
                    public void onNext(BaseResponse<String> response) {
                        dismissDialog();
                        if (checkApiResponse(response)) {
//                            mRecyclerView.refresh();
                            requestAddress(true);
                        }
                    }
                });
    }


    public void detDefaultConsigneeAddress(AddressModel addressModel) {
        String accountId = getUserId();
        msubscription2 = HttpApiService.instance().detDefaultConsigneeAddress(accountId, addressModel.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(), false) {

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
                    public void onNext(BaseResponse<String> response) {
                        dismissDialog();
                        if (checkApiResponse(response)) {
//                            requestAddress(true);
                            setDefaultAddress(addressModel);
                        }
                    }
                });
    }

    private void setDefaultAddress(AddressModel address){

        address.setIsDefault(address.getIsDefault()==1?0:1);
        for (int i= 0;i<mSuperAdapter.getItemCount();i++){
            AddressModel addressModel =(AddressModel) mSuperAdapter.getDatas().get(i);
            if(!addressModel.getId().equals(address.getId()) && addressModel.getIsDefault()==1){
                addressModel.setIsDefault(0);
            }
        }
        mSuperAdapter.notifyDataSetChanged();
    }



    @Override
    public void onUIDestory() {
        releaseSubscription(mSubscription);
        releaseSubscription(msubscription2);
        super.onUIDestory();
    }


    /**
     * 更新UI
     */
    public void updateUI() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mSuperAdapter.notifyDataSetChanged();
            }
        });
    }


    public void chooseAddress() {
        if (mSuperAdapter.getDatas() != null && mSuperAdapter.getDatas().size() > 0) {
            for ( int i=0; i<mSuperAdapter.getDatas().size();i++ ){
                AddressModel addressModel = (AddressModel) (mSuperAdapter.getDatas().get(i));
                if (addressModel.getIsDefault() == 1) {
                    String address = StringUtils.jointAddress(addressModel.getProvince(),
                            addressModel.getCity(), addressModel.getDistrict(), addressModel.getAddress());
                    if (isActivityExist()) {
                        getUI().showAddress(addressModel, address);
                    }
                    return;
                }
            }
        }
//        String address = StringUtils.jointAddress(addressModel.getProvince(),
//                addressModel.getCity(), addressModel.getDistrict(), addressModel.getAddress());
//        if (isActivityExist()) {
//            getUI().showAddress(addressModel,address);
//        }
    }
}

