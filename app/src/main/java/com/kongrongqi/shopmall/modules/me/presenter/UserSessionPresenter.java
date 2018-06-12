package com.kongrongqi.shopmall.modules.me.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.me.IView.IBuyRecordView;
import com.kongrongqi.shopmall.modules.me.IView.IUserSessionView;
import com.kongrongqi.shopmall.modules.me.adapter.PushAdapter;
import com.kongrongqi.shopmall.modules.me.bean.UserSession;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/19 0019 on 14:46
 * 作者:penny
 */
public class UserSessionPresenter extends BaseLoadingPresenter<UserSession, IUserSessionView> {


    /**
     * 交易记录
     *
     * @param xRecyclerView
     */
    public void setAdapter(XRecyclerView xRecyclerView) {
        setXRecyclerView(xRecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new PushAdapter(this, getContext());
        xRecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }


    /**
     * 获取用户账户绑定列表
     */
    public void getUserOrderList(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        String accountId = getUserId();
        map.put("userId", accountId);
        map.put("pageNumber",currentPage);
        map.put("pageSize",pageSize);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);

        HttpApiService.instance().getUserSession(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<UserSession>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<UserSession>> accounts) {
                        if (checkApiResponse(accounts) && accounts.getData() != null) {
                            refreshOrLoadMore(accounts.getData(), isRefresh);
                        }
                    }
                });
    }

    /**
     * 更新消息为已读
     */
    public void updateUserSession() {
        mSuperSubscription = HttpApiService.instance().updateUserSession(getUserId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(), false) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if (checkApiResponse(response, false)) {
                        }
                    }
                });
    }



    @Override
    public void onRefreshList() {
        getUserOrderList(true);
    }

    @Override
    public void onLoadMoreList() {
        getUserOrderList(false);
    }
}
