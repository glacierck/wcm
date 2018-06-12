package com.kongrongqi.shopmall.modules.task.presenter;

import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.ServiceDetailsActivity;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.login.request.RequestUser;
import com.kongrongqi.shopmall.modules.model.BindWechatAccountModel;
import com.kongrongqi.shopmall.modules.model.FansBListModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.service.holder.RecycleViewDivider;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSuper;
import com.kongrongqi.shopmall.modules.task.CallTxtActivity;
import com.kongrongqi.shopmall.modules.task.IView.IFansBView;
import com.kongrongqi.shopmall.modules.task.UseServiceActivity;
import com.kongrongqi.shopmall.modules.task.adapter.FansBAdapter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class FansBPresenter extends BaseLoadingPresenter<FansBListModel, IFansBView> {


    public static final String TAG = "FansBPresenter";
    private UserServiceParam param;

    public void setAdapter(XRecyclerView recyclerView, UserServiceParam param) {
        this.param = param;
        setXRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new FansBAdapter(getContext(), this);
        recyclerView.setAdapter(mSuperAdapter);
        recyclerView.refresh();
    }

    private void requestData(boolean isRefresh) {
        Map map = new HashMap();
        map.put("userId", getUserId());
        map.put("type", param.getType());
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        mSuperSubscription = HttpApiService.instance().fansBList(requestBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<FansBListModel>>>(getUI(), true) {
                    @Override
                    public void onNext(BaseResponse<List<FansBListModel>> listBaseResponse) {
                        if (checkApiResponse(listBaseResponse)) {
                            List<FansBListModel> data = listBaseResponse.getData();
                            refreshOrLoadMore(data, isRefresh);
                        }
                    }
                });
    }

    public void ItemClick(int position, FansBListModel model) {
        param.setId(model.getId());//只能获取id
        if (param.getType() == 3) {
            param.setServiceName(model.getName());
            getUI().gotoCallTxt(param);
        } else {
            if (ServiceSuper.ACCOUNT == param.getEntrance()) {//设备入口使用服务
                checkSameService(param,model);
            } else if (ServiceSuper.TASK == param.getEntrance()) {//进程入口使用服务
                getUI().gotoUseService(param);
            }
        }
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
     * 检测是否有相同类型的服务
     */
    public void checkSameService(UserServiceParam param,FansBListModel model) {
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
                                showErroyDevice(baseResponse.getData(),model);
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
    public void showErroyDevice(BindWechatAccountModel bindWechatAccountModel,FansBListModel model) {

        StringBuilder sb = new StringBuilder();
        sb.append("您即将执行" + model.getName() + "服务，");
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








    /**
     * @param userServiceParam
     * @return
     */
    public Observable<BaseResponse<String>> useService(UserServiceParam userServiceParam) {
        String infoGson = new Gson().toJson(userServiceParam);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        return HttpApiService.instance().useService(requestBody);
    }

    public void userService(UserServiceParam param) {
        useService(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        if (checkApiResponse(baseResponse,false)) {
                            DeviceWechat deviceWechat = new DeviceWechat();
                            deviceWechat.setId(param.getDeviceWechatId());
                            deviceWechat.setWechatNo(param.getWechatNo());
                            ServiceDetailsActivity.lunch(getContext(), deviceWechat);
                        }
                        dismissDialog();
                    }
                });
    }


}
