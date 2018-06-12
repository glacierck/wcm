package com.kongrongqi.shopmall.modules.task.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.request.RequestUser;
import com.kongrongqi.shopmall.modules.model.UnUseGrounpServiceModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.task.IView.IWaitForGrounpView;
import com.kongrongqi.shopmall.modules.task.UseServiceActivity;
import com.kongrongqi.shopmall.modules.task.adapter.WaitForGrounpAdapter;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/26 0026 on 17:47
 * 作者:penny
 */
public class WaitForGrounpPresenter extends BaseLoadingPresenter<UnUseGrounpServiceModel,IWaitForGrounpView> {

    private int mType;
    private Subscription mSubscribe;
    public static final String TAG = "WaitForGrounpPresenter";
    private UserServiceParam param;

    public void setAdapter(XRecyclerView recyclerView, UserServiceParam param) {
        this.param = param;
        setXRecyclerView(recyclerView,new LinearLayoutManager(getContext()));
        mSuperAdapter = new WaitForGrounpAdapter(getContext(), this);
        mRecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }


    @Override
    public void onRefreshList() {
        requestData(true);
    }

    @Override
    public void onLoadMoreList() {
        requestData(false);
    }

    /**
     * 请求未使用群列表
     */
    private void requestData(boolean refresh) {
        String userId = getUserId();
        RequestUser requestUser = new RequestUser();
        requestUser.setUserId(userId);
        mSubscribe = HttpApiService.instance().unUseGrounpService(requestUser)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<UnUseGrounpServiceModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<UnUseGrounpServiceModel>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            List<UnUseGrounpServiceModel> data = listBaseResponse.getData();
                            refreshOrLoadMore(data,refresh);
                        }
                    }
                });

    }

    /**
     * in grounp
     *
     * @param position
     * @param model
     */
    public void itemClickInGrounp(int position, UnUseGrounpServiceModel model) {
        param.setGroupName(model.getGroupName());
        param.setId(model.getId());
        UseServiceActivity.lunch(getContext(),param);
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mSubscribe);
    }
}
