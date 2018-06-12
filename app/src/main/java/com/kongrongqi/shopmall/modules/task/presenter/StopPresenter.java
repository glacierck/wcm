package com.kongrongqi.shopmall.modules.task.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.request.RequestEmpty;
import com.kongrongqi.shopmall.modules.model.WorkingModel;
import com.kongrongqi.shopmall.modules.task.IView.IStopView;
import com.kongrongqi.shopmall.modules.task.IView.IWorkingView;
import com.kongrongqi.shopmall.modules.task.adapter.StopAdapter;
import com.kongrongqi.shopmall.modules.task.adapter.WorkingAdapter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.ListDialog;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22 0022 on 17:43
 * 作者:penny
 */
public class StopPresenter extends BaseLoadingPresenter<WorkingModel, IStopView> {

    private final RequestEmpty mRequestEmpty;
    public static final String TAG = "WorkingPresenter";

    public StopPresenter() {
        Logger.d(TAG, "NotUsePresenter:onCreate===");
        mRequestEmpty = new RequestEmpty();
    }


    public void setAdapter(XRecyclerView recylerView) {
        super.setXRecyclerView(recylerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new StopAdapter(getContext(), this);
        recylerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    /**
     * 请求进行中列表
     *
     * @param requestEmpty
     * @param b
     */
    public void requestWorkingList(boolean isRefresh) {
        String userId = getUserId();
        mRequestEmpty.setUserId(userId);
        mRequestEmpty.setStatus(3);
        mSuperSubscription = HttpApiService.instance().scheduleList(mRequestEmpty)
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


    //暂停服务   isRestart 是否重启  1 重启   2 暂停
    public void stopServiceDialog(WorkingModel model) {
        if (getContext() == null) {
            return;
        }
        try {
            if (!isActivityExist()) return;
            StyleDialog.showRestartService(getContext(), "提示", "重启服务将于次日生效，是否确定？", new StyleDialog.DialogEnterListener() {
                @Override
                public void onEnter() {
                    restartService(model,1);
                }
            });
        }catch (Exception e){

        }
    }

    /**
     * 重启服务
     */
    public void restartService(WorkingModel model,int restart) {
        HttpApiService.instance().restartService(model.getId(),restart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(), false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> accounts) {
                        dismissDialog();
                        if (checkApiResponse(accounts, false)) {
                            mRecyclerView.refresh();
                        }
                    }
                });
    }

    @Override
    public void onRefreshList() {
        requestWorkingList(true);
    }

    @Override
    public void onLoadMoreList() {
        mRequestEmpty.setCurrentPage(currentPage);
        requestWorkingList(false);
    }
}
