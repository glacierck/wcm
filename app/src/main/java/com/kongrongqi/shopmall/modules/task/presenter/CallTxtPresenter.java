package com.kongrongqi.shopmall.modules.task.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.IView.ITabView;
import com.kongrongqi.shopmall.modules.account.ServiceDetailsActivity;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.TabFragmentAdaper;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.login.request.RequestUser;
import com.kongrongqi.shopmall.modules.model.BindWechatAccountModel;
import com.kongrongqi.shopmall.modules.model.CallModel;
import com.kongrongqi.shopmall.modules.model.FansBListModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;
import com.kongrongqi.shopmall.modules.task.IView.CallTxtView;
import com.kongrongqi.shopmall.modules.task.IView.IFansBView;
import com.kongrongqi.shopmall.modules.task.UseServiceActivity;
import com.kongrongqi.shopmall.modules.task.adapter.CallTextAdaper;
import com.kongrongqi.shopmall.modules.task.adapter.FansBAdapter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/26 0026 on 17:15
 * 作者:penny
 */
public class CallTxtPresenter extends BaseLoadingPresenter<CallModel, ITabView> {


    public void setAdapter(XRecyclerView xrecyclerView) {
        setXRecyclerView(xrecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new CallTextAdaper(this, getContext());
        xrecyclerView.setAdapter(mSuperAdapter);
        queryCallTemplate(true);
    }

    @Override
    public void onRefreshList() {
        queryCallTemplate(true);
    }

    @Override
    public void onLoadMoreList() {

    }


    /**
     * 检测是否有相同类型的服务
     */
    public void queryCallTemplate(boolean isRefresh) {
        HttpApiService.instance().queryCallTemplate(getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<CallModel>>>(getUI(), false) {
                    @Override
                    public void onCompleted() {
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onNext(BaseResponse<List<CallModel>> baseResponse) {
                        if (checkApiResponse(baseResponse, false)) {
                            refreshOrLoadMore(baseResponse.getData(), isRefresh);
                        }
                    }
                });
    }


    public CallModel getSelectItem(){
        for (int i=0;i<mSuperAdapter.getDatas().size();i++){
            CallModel o = (CallModel) mSuperAdapter.getDatas().get(i);
            if (o.getIsSelect()==1){
                return  o;
            }
        }
        return null;
    }

    public Observable<BaseResponse<String>> useService(UserServiceParam userServiceParam) {
        String infoGson = new Gson().toJson(userServiceParam);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        return HttpApiService.instance().useService(requestBody);
    }

    public void userService(UserServiceParam param) {
        useService(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(), false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        if (checkApiResponse(baseResponse)) {
                            DeviceWechat deviceWechat = new DeviceWechat();
                            deviceWechat.setId(param.getDeviceWechatId());
                            deviceWechat.setWechatNo(param.getWechatNo());
                            ServiceDetailsActivity.lunch(getContext(), deviceWechat);
                        }
                        dismissDialog();
                    }
                });
    }


    /**
     * 检测是否有相同类型的服务
     */
    public void checkSameService(UserServiceParam param) {
        HttpApiService.instance().checkSameService(param.getDeviceWechatId(), param.getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<BindWechatAccountModel>>(getUI(), false) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<BindWechatAccountModel> baseResponse) {
                        if (checkApiResponse(baseResponse, false)) {
                            if (baseResponse.getData() != null) {
                                showErroyDevice(baseResponse.getData(), param);
                            } else {
                                ToastUtil.showMessage(getContext(), "使用服务失败，请重试");
                            }
                        }
                        dismissDialog();
                    }
                });
    }


    /**
     * 异常设备弹框
     */
    public void showErroyDevice(BindWechatAccountModel bindWechatAccountModel, UserServiceParam param) {

        StringBuilder sb = new StringBuilder();
        sb.append("您即将执行" + param.getServiceName() + "服务，");
        if (bindWechatAccountModel.getIsSameTypeService() == 1) {//如果又相同类型服务
            sb.append("继续执行将暂停" + bindWechatAccountModel.getSameTypeServiceName() + "，");
            sb.append("该操作次日生效，是否确定？");
        } else {
            sb.append("请确认该账号已在设备上登录。");
        }

        StyleDialog.showUserService(getContext(), "注意", sb.toString(), "确定", "取消", new StyleDialog.DialogUserServiceListener() {
            @Override
            public void onUser() {
                userService(param);
            }
        }, null);
    }


}
