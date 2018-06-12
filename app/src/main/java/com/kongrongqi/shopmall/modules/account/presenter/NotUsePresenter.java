package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.INoUserServiceView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.NotUseAdapter;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.login.request.RequestEmpty;
import com.kongrongqi.shopmall.modules.model.NotUseModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;
import com.kongrongqi.shopmall.modules.task.IView.INotUseView;
import com.kongrongqi.shopmall.utils.Logger;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/22 0022 on 17:41
 * 作者:penny
 */
public class NotUsePresenter extends BaseLoadingPresenter<NotUseModel, INoUserServiceView> {

    private final RequestEmpty mRequestEmpty;

    DeviceWechat deviceWechat;

    public NotUsePresenter() {
        mRequestEmpty = new RequestEmpty();
        mRequestEmpty.setStatus(0);
    }


    public void setAdapter(XRecyclerView recyclerView,DeviceWechat deviceWechat) {
        super.setXRecyclerView(recyclerView,new LinearLayoutManager(getContext()));
        mSuperAdapter = new NotUseAdapter(getContext(), this);
        this.deviceWechat=deviceWechat;
        recyclerView.setAdapter(mSuperAdapter);
        recyclerView.refresh();
    }

    /**
     * item点击事件
     *  @param position      点击索引
     * @param notUseAdapter adpter
     * @param type          2灌粉服务A   3灌粉服务B  4入群服务  5养号服务
     */
    public void itemUseService(int position, NotUseModel model, int type) {
        UserServiceParam userServiceParam = new UserServiceParam();
        userServiceParam.setType(type);
        userServiceParam.setUserId(getUserId());
        userServiceParam.setDeviceWechatId(deviceWechat.getId());
        userServiceParam.setWechatNo(deviceWechat.getWechatNo());
        userServiceParam.setEntrance(ServiceSuper.ACCOUNT);//进程入口
        ServiceSuper serviceSuper = new ServiceSuper(getContext(),userServiceParam).getServiceSuperUser();
        serviceSuper.useService();
    }

    @Override
    public void onRefreshList() {
        requestNotUserList(mRequestEmpty, true);
    }

    @Override
    public void onLoadMoreList() {
        mRequestEmpty.setCurrentPage(currentPage);
        requestNotUserList(mRequestEmpty, false);
    }

    /**
     * 获取未使用列表
     */
    public void requestNotUserList(RequestEmpty requestEmpty, boolean isRefresh) {
        String userId = getUserId();
        mRequestEmpty.setUserId(userId);
        mSuperSubscription = HttpApiService.instance().notUseServiceList(requestEmpty)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<NotUseModel>>>(getUI(),true) {
                    @Override
                    public void onNext(BaseResponse<List<NotUseModel>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            List<NotUseModel> data = listBaseResponse.getData();
                            refreshOrLoadMore(data, isRefresh);
                        }
                    }
                });
    }
}
