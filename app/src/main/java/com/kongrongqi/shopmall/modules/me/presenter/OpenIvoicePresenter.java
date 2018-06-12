package com.kongrongqi.shopmall.modules.me.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.me.IView.IOpenIvoiceView;
import com.kongrongqi.shopmall.modules.me.adapter.OpenIvoiceAdapter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/19 0019 on 18:22
 * 作者:penny
 */
public class OpenIvoicePresenter extends BaseLoadingPresenter<IvoiceModel, IOpenIvoiceView> {

    private  int isBill;
    public static final String TAG = "OpenIvoicePresenter";

    public OpenIvoicePresenter(int isBill) {
        this.isBill= isBill;
    }

    public void setAdapter(XRecyclerView xRecyclerView) {
        setXRecyclerView(xRecyclerView,new LinearLayoutManager(getContext()));
        mSuperAdapter = new OpenIvoiceAdapter(this, getContext());
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
                        if(checkApiResponse(response)){
                            refreshOrLoadMore(response.getData(),isRefresh);
                        }
                    }
                });
    }


    public void allChecked(boolean isChecked){
        BigDecimal price =  new BigDecimal(0);
        for (int i = 0; i< mSuperAdapter.getDatas().size(); i++){
            IvoiceModel ivoiceModel = (IvoiceModel) mSuperAdapter.getDatas().get(i);
            ivoiceModel.setCheck(isChecked);
            BigDecimal b1 = new BigDecimal(ivoiceModel.getPrice().toString());
            price = price.add(b1);
        }
        DecimalFormat myformat=new DecimalFormat("0.00");
        String f1 = myformat.format(price);
//        double  f1 = price.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        mSuperAdapter.notifyDataSetChanged();
        getUI().setCheckPice(mSuperAdapter.getDatas().size(),f1);
    }



    public void bindCheckIvoice(){
        List<IvoiceModel> checkIvoice = getCheckIvoice();
        int count = checkIvoice.size();
        BigDecimal price =  new BigDecimal(0.00);
        for (IvoiceModel ivoiceModel : checkIvoice){
            BigDecimal b1 = new BigDecimal(ivoiceModel.getPrice().toString());
            price = price.add(b1);
        }
        DecimalFormat myformat=new DecimalFormat("0.00");
        String f1 = myformat.format(price);
//        double   f1   =   price.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        getUI().setCheckPice(count,f1);
    }




    public List<IvoiceModel> getCheckIvoice(){
        List<IvoiceModel> ivoiceModelList = new ArrayList<>();
        for (int i = 0; i< mSuperAdapter.getDatas().size(); i++){
            IvoiceModel ivoiceModel = (IvoiceModel) mSuperAdapter.getDatas().get(i);
            if(ivoiceModel.isCheck()){
                ivoiceModelList.add(ivoiceModel);
            }
        }
        return ivoiceModelList;
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
