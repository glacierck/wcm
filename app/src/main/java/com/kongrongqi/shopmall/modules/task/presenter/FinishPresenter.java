package com.kongrongqi.shopmall.modules.task.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.RequestEmpty;
import com.kongrongqi.shopmall.modules.model.WorkingModel;
import com.kongrongqi.shopmall.modules.task.IView.IFinishView;
import com.kongrongqi.shopmall.modules.task.adapter.FinishAdapter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.ListDialog;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22 0022 on 17:43
 * 作者:penny
 */
public class FinishPresenter extends BaseLoadingPresenter<WorkingModel, IFinishView> {

    private static final String TAG = "FinishPresenter";
    private final RequestEmpty mRequestEmpty;
    private XRecyclerView mRecyclerView;

    public FinishPresenter() {
        Logger.d(TAG, "NotUsePresenter:onCreate===");
        mRequestEmpty = new RequestEmpty();
        mRequestEmpty.setStatus(2);
    }

    public void setAdapter(XRecyclerView recylerView) {
        super.setXRecyclerView(recylerView,new LinearLayoutManager(getContext()));
        mRecyclerView = recylerView;
        mSuperAdapter = new FinishAdapter(getContext(), this);
        mRecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }


    private void requestFinishList(RequestEmpty requestEmpty, boolean isRefresh) {
        String userId = getUserId();
        mRequestEmpty.setUserId(userId);
        mSuperSubscription = HttpApiService.instance().scheduleList(requestEmpty)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<WorkingModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<WorkingModel>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            List<WorkingModel> data = listBaseResponse.getData();
                            refreshOrLoadMore(data, isRefresh);
                        }
                    }
                });
    }

    public void showUseNoListDialog(String[] data){
        if(isActivityExist()){
            try{
                ListDialog dialog = new ListDialog(getContext(), data);
                dialog.show();
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onRefreshList() {
        requestFinishList(mRequestEmpty, true);
    }

    @Override
    public void onLoadMoreList() {
        Logger.d(TAG, "page:" + currentPage);
        mRequestEmpty.setCurrentPage(currentPage);
        requestFinishList(mRequestEmpty, false);

    }
}
