package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.AddAccountActivity;
import com.kongrongqi.shopmall.modules.account.IView.ITabView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.TabFragmentAdaper;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.GrooveInfo;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class TabPresenter extends BaseLoadingPresenter<DeviceWechat, ITabView> {

    private String deviceID;
    private Device device;

    public void setAdapter(XRecyclerView xrecyclerView, Device device) {
        this.device = device;
        setXRecyclerView(xrecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new TabFragmentAdaper(this, getContext(), device);
        deviceID = device.getId();
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    /**
     * 获取帐号列表数据
     */
    public void getAccountData(boolean isRefresh) {

        Map<Object, Object> map = new HashMap<>();
        map.put("id", deviceID);
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        mSuperSubscription = HttpApiService.instance().getAccountEachNumberInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<DeviceWechat>>>(getUI(), false) {
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
                    public void onNext(BaseResponse<List<DeviceWechat>> baseResponse) {
                        if (checkApiResponse(baseResponse, false))
                            refreshOrLoadMore(baseResponse.getData(), isRefresh);
                    }
                });
    }

    public void setEmpty(){
        getUI().showEmpty();
    }


    /**
     * w未使用  选择操作 使用服务  我要换号
     */
    public void showSelectOprator(DeviceWechat deviceWechat) {
        StyleDialog.showSelectOprator(getContext(),
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//使用服务
                        userService(deviceWechat);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//我要换号
                        addAccount(deviceWechat, AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
                    }
                }
        );
    }

//    /**
//     * 脚本响应超时（审核失败）4
//     */
//    public void showAccountLoginException4(DeviceWechat deviceWechat,boolean isChangeNumber, boolean isRecovery) {
//        StyleDialog.showAccountLoginException4(getContext(),isChangeNumber, isRecovery,
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {////重新审核
//                        reAuditAccount(deviceWechat);
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//重新添加
//                        addAccount(deviceWechat,isRecovery?AddAccountActivity.CHANGE_ACCOUNT:AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//恢复原号
//                        recoverUserAccount(deviceWechat);
//                    }
//                });
//    }
//
//    /**
//     * 重新添加账号 5 (审核登录失败)
//     */
//    public void showAccountLoginException5(DeviceWechat deviceWechat,boolean isChangeNumber, boolean isRecovery) {
//        StyleDialog.showAccountLoginException5(getContext(),isChangeNumber, isRecovery,
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//继续审核
//                        reAuditAccount(deviceWechat);
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//我要换号
//                        addAccount(deviceWechat, isRecovery?AddAccountActivity.CHANGE_ACCOUNT:AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//恢复原号
//                        recoverUserAccount(deviceWechat);
//                    }
//                });
//    }
//
//    /**
//     * 没有银行卡（审核失败） 6
//     */
//    public void userFailureNoMonney(DeviceWechat deviceWechat,boolean isChangeNumber, boolean isRecovery) {
//        StyleDialog.showAuditFailure(getContext(),isChangeNumber, isRecovery,
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//重新审核
//                        reAuditAccount(deviceWechat);
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//我要换号
//                        addAccount(deviceWechat, isRecovery?AddAccountActivity.CHANGE_ACCOUNT:AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//强制执行
//                        userFailureSuccess(deviceWechat);
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//恢复原号
//                        recoverUserAccount(deviceWechat);
//                    }
//                }
//        );
//    }

    /**
     * 服务进行中 出现 的所有异常
     * <p>
     * 账号异常  7
     *
     * @param deviceWechat
     */
    public void accountException(DeviceWechat deviceWechat) {

        StyleDialog.showAccountException(getContext(), "已处理，恢复使用", "我要换号/重输账号",
                new StyleDialog.DialogUserServiceListener() {
                    @Override
                    public void onUser() {
                        reUserservice(deviceWechat);
                    }
                }, new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {
                        addAccount(deviceWechat, AddAccountActivity.CHANGE_ACCOUNT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
                    }
                });
    }

    /**
     * 未审核  立即审核
     */
//    public void showReAuditAccount(DeviceWechat deviceWechat) {
//
//        StyleDialog.showUserService(getContext(), "提示", "是否立即审核账号。", "取消", "立即审核",
//                new StyleDialog.DialogUserServiceListener() {
//                    @Override
//                    public void onUser() {//联系客服
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//替换设备
//                        reAuditAccount(deviceWechat);
//                    }
//                });
//    }


    /**
     *  审核成功 我要换号
     */
    public void showChangeAccount(DeviceWechat deviceWechat) {

        StyleDialog.showUserService(getContext(), "提示", "是否立即审核账号。", "取消", "我要换号",
                new StyleDialog.DialogUserServiceListener() {
                    @Override
                    public void onUser() {//取消

                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//我要换号
                        addAccount(deviceWechat, AddAccountActivity.CHANGE_ACCOUNT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
                    }
                });
    }




    ///user/recoverUserAccount.do

//    /**
//     * 恢复原号
//     * @param deviceWechat
//     * @param oldWechatNo
//     * @param wechatNo
//     * @param password
//     */
//    public void recoverUserAccount(DeviceWechat deviceWechat) {
//
//        HttpUtils.getApiService().recoverUserAccount(deviceWechat.getId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse<String>>(getUI(),false) {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        showDialog();
//                    }
//                    @Override
//                    public void onNext(BaseResponse<String> baseResponse) {
//                        if(baseResponse.getCode()==200){
//                            getUI().refrashIview();
//                        }else {
//                            ToastUtil.showMessage(getContext(), baseResponse.getMsg());
//                        }
//                    }
//                });
//    }


    /**
     * 获取号槽信息
     */
    public void getNumberGrooveInfo(DeviceWechat deviceWechat) {

        Map<Object, Object> map = new HashMap<>();
        map.put("id", deviceWechat.getId());
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        mSuperSubscription = HttpApiService.instance().getNumberGrooveInfo(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<GrooveInfo>>(getUI(), false) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<GrooveInfo> baseResponse) {
                        if (checkApiResponse(baseResponse, false)) {
                            buySlot2(baseResponse.getData());
                        }
                    }
                });
    }


    @Override
    public void onRefreshList() {
        getAccountData(true);
    }

    @Override
    public void onLoadMoreList() {
        getAccountData(false);
    }

    public void replace(Device device) {
        getUI().replace();
    }

    /**
     * 异常设备弹框
     */
    public void showErroyDevice() {

        StyleDialog.showUserService(getContext(), "注意", "您的设备异常，请查看设备是否通电联网，如无法恢复请尽快致电客服：027-59303512，或替换设备。", "联系客服", "替换设备",
                new StyleDialog.DialogUserServiceListener() {
                    @Override
                    public void onUser() {//联系客服
                        getUI().deviceErroy();
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//替换设备
                        replace(device);
                    }
                });

//        StyleDialog.showEnter(getContext(), "注意", "您的设备异常，请尽快致电客服：027-59303512", "联系客服", new StyleDialog.DialogEnterListener() {
//            @Override
//            public void onEnter() {
//                getUI().deviceErroy();
//            }
//        });
    }


    /**
     * 输入参数: WechatNo 账号， state 状态：1 表示还没绑定微信号 2 审核中 3 审核通过 4审核失败, failureReasons 失败原因
     */
    public void updateUserDeviceWecht(DeviceWechat deviceWechat) {

        mSuperSubscription = HttpApiService.instance().updateUserDeviceWecht(1, deviceWechat.getWechatNo(), 3, "", getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d("设备", "完成Fragment");
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("设备", "异常");
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        if (checkApiResponse(baseResponse, false)) {
                            getUI().refrashIview();
                        }
                    }
                });
    }


//    /**
//     * 使用
//     */
//    public void userFailureSuccess(DeviceWechat deviceWechat) {
//        StyleDialog.showUserService(getContext(), "注意", getContext().getResources().getString(R.string.failuresuccess), "取消", "继续",
//                new StyleDialog.DialogUserServiceListener() {
//                    @Override
//                    public void onUser() {//取消
//
//                    }
//                },
//                new StyleDialog.DialogEnterListener() {
//                    @Override
//                    public void onEnter() {//继续
//                        updateUserDeviceWecht(deviceWechat);
//                    }
//                });
//    }


    public void addAccount(DeviceWechat deviceWechat, int optType, String tilte) {
        getUI().addAccount(deviceWechat, optType, tilte);
    }

    /**
     * 购买号槽
     */
    public void buySlot(DeviceWechat deviceWechat) {
        getNumberGrooveInfo(deviceWechat);
    }

    public void buySlot2(GrooveInfo grooveInfo) {
        String accountId = getUserId();
        OrderNumRequest request = new OrderNumRequest();
        request.setUserId(accountId);
        request.setContent("");
        request.setContentName("");
        request.setDeviceWechatId(grooveInfo.getAccountId());
        request.setDurationName("");
        request.setId("");
        request.setName(grooveInfo.getName()); //名称
        request.setPrice(grooveInfo.getPrice()); //价格
        request.setReceiveAddress("");
        request.setReceiveAddressId("");
        request.setType(1);//号槽
        request.setDuration("");
        request.setDurationUnit("");
        getUI().buySlot(request);
    }

    /**
     * 使用服务
     */
    public void userService(DeviceWechat deviceWechat) {
        getUI().userService(deviceWechat);
    }


    public void buyNewService() {
        getUI().buyNewService();
    }


    public void lookServiceDetails(DeviceWechat deviceWechat) {
        getUI().lookServiceDetails(deviceWechat);
    }


    /**
     * 账号异常  再次启动服务
     */

    public void reUserservice(DeviceWechat deviceWechat) {
        mSuperSubscription = HttpApiService.instance().reUserservice(deviceWechat.getWechatNo(), getUserId(), getToken())
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
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
                        ToastUtil.showMessage(getContext(), "操作失败");
                    }

                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        if (checkApiResponse(baseResponse, false)) {
                            getUI().refrashIview();
                        }
                    }
                });
    }




    /**
     * 账号任务状态：1天服务（一个账号下的多个服务）的状态  user_device_wechat    service_msg 服务消息
     * 1 进行中  2 已完成 3暂停 4 已取消 5 等待中  today_finish
     * <p>
     * 服务状态：多天的总任务状态   user_service_record  service_msg 服务消息
     * 0 未使用  1 服务中 2 已完成 3 异常  5 暂停  status
     * <p>
     * 账号状态    user_device_wechat
     * 1 未添加
     *
     *
     * (2 审核中 3 审核通过  4 脚本响应超时 5 审核失败（ 微信在设备未登录，微信账号密码错误，封号） 6 未绑定银行卡，未实名)
     *
     * state  1 未添加  2 已添加  7 账号异常（执行服务的时候） 9 账号登出  msg: xxx
     *
     * 7 账号异常
     * 9 账号登出  status
     *
     *
     * <p>
     * <p>
     * setBntStatus
     * 1.添加账号  2等待审核 3.选择操作 4.查看详情(服务中,空闲中,账号登出)
     * <p>
     * 5.查看原因(1脚本响应超时，2登录异常，3未绑定银行卡，4账号异常)
     * <p>
     * 5.查看原因(脚本响应超时) 6.查看原因(登录异常)  7.查看原因(未绑定银行卡，未实名)  8.查看原因 （账号异常）
     * <p>
     * 6.我要换号 （使用服务）  （1换号审核中  2脚本响应超时，3登录异常，4未绑定银行卡）
     *
     * @param deviceWechat
     * @return
     */
    public DeviceWechat getDeviceWechat(DeviceWechat deviceWechat) {
        DeviceWechat newDeviceWechat = deviceWechat;
        int serviceNum = deviceWechat.getServiceNum();//所有服务总数

        switch (deviceWechat.getState()) {

            case 1:// 1 未添加
                newDeviceWechat.setAccountName("尚未添加账号");
                newDeviceWechat.setBntText("添加账号");
                newDeviceWechat.setBntStatus(1);
                newDeviceWechat.setServiceCount("");
                newDeviceWechat.setServiceStatus("");
                break;

            case 2:// 已添加
            case 3:// 审核通过
            case 4:// 脚本响应超时
            case 5:// 登录异常
            case 6:// 未绑定银行卡，未实名

                newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
                newDeviceWechat.setBntText("账号详情");
                newDeviceWechat.setBntStatus(4);
                newDeviceWechat.setServiceCount(serviceNum + "个");
                newDeviceWechat.setServiceStatus(deviceWechat.getMsg());

//                if(serviceNum>0){
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("查看详情");
//                    newDeviceWechat.setBntStatus(4);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }else{
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("选择操作");
//                    newDeviceWechat.setBntStatus(3);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
////                    newDeviceWechat.setServiceStatus("未使用");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }
                break;

//            case 2://2 审核中
//                newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                newDeviceWechat.setBntText("选择操作");
//                newDeviceWechat.setBntStatus(2);
//                newDeviceWechat.setServiceCount(serviceNum + "个");
////                newDeviceWechat.setServiceStatus("等待审核中，请稍候");
//                newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//
//                if (!TextUtils.isEmpty(newDeviceWechat.getOldWechatNo())) {
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("选择操作");
//                    newDeviceWechat.setBntStatus(6);
//                    newDeviceWechat.setAccountStatus(1);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
////                    newDeviceWechat.setServiceStatus("换号审核中，请稍候");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }
//
//                break;
//            case 3:// 审核通过
//                if(serviceNum>0){
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("查看详情");
//                    newDeviceWechat.setBntStatus(4);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }else{
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("选择操作");
//                    newDeviceWechat.setBntStatus(3);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
////                    newDeviceWechat.setServiceStatus("未使用");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }
//                break;
//            //审核失败   按钮状态 5 6 7
//            case 4:// 脚本响应超时
//                newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                newDeviceWechat.setBntText("查看原因");
//                newDeviceWechat.setBntStatus(5);
//                newDeviceWechat.setAccountStatus(1);
//                newDeviceWechat.setServiceCount(serviceNum + "个");
////                newDeviceWechat.setServiceStatus("审核失败");
//                newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//
//                if (!TextUtils.isEmpty(newDeviceWechat.getOldWechatNo())) {
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("查看原因");
//                    newDeviceWechat.setBntStatus(6);
//                    newDeviceWechat.setAccountStatus(2);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
////                    newDeviceWechat.setServiceStatus("换号审核失败");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }
//
//                break;
//            case 5:// 登录异常
//                newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                newDeviceWechat.setBntText("查看原因");
//                newDeviceWechat.setBntStatus(5);
//                newDeviceWechat.setAccountStatus(2);
//                newDeviceWechat.setServiceCount(serviceNum + "个");
////                newDeviceWechat.setServiceStatus("审核失败");
//                newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//
//                if (!TextUtils.isEmpty(newDeviceWechat.getOldWechatNo())) {
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("查看原因");
//                    newDeviceWechat.setBntStatus(6);
//                    newDeviceWechat.setAccountStatus(3);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
////                    newDeviceWechat.setServiceStatus("换号审核失败");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }
//                break;
//            case 6:// 未绑定银行卡，未实名
//                newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                newDeviceWechat.setBntText("查看原因");
//                newDeviceWechat.setBntStatus(5);
//                newDeviceWechat.setAccountStatus(3);
//                newDeviceWechat.setServiceCount(serviceNum + "个");
////                newDeviceWechat.setServiceStatus("审核失败");
//                newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//
//                if (!TextUtils.isEmpty(newDeviceWechat.getOldWechatNo())) {
//                    newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
//                    newDeviceWechat.setBntText("查看原因");
//                    newDeviceWechat.setBntStatus(6);
//                    newDeviceWechat.setAccountStatus(4);
//                    newDeviceWechat.setServiceCount(serviceNum + "个");
////                    newDeviceWechat.setServiceStatus("换号审核失败");
//                    newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
//                }
//                break;
            case 7:// 账号异常
                newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
                newDeviceWechat.setBntText("查看原因");
                newDeviceWechat.setBntStatus(5);
                newDeviceWechat.setAccountStatus(4);
                newDeviceWechat.setServiceCount(serviceNum + "个");
//                newDeviceWechat.setServiceStatus("账号异常");
                newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
                break;
            case 9:// 账号登出
                newDeviceWechat.setAccountName(deviceWechat.getWechatNo());
                newDeviceWechat.setBntText("账号详情");
                newDeviceWechat.setBntStatus(4);
                newDeviceWechat.setServiceCount(serviceNum + "个");
//                newDeviceWechat.setServiceStatus("账号登出");
                newDeviceWechat.setServiceStatus(deviceWechat.getMsg());
                break;
        }
        return newDeviceWechat;
    }
}
