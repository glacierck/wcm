package com.kongrongqi.shopmall.modules.account.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.AddAccountActivity;
import com.kongrongqi.shopmall.modules.account.IView.IServiceDatailsView;
import com.kongrongqi.shopmall.modules.account.adapter.BaseLoadingPresenter;
import com.kongrongqi.shopmall.modules.account.adapter.ServiceDatailsAdaper;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.ServiceDatails;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderBntHolder;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderBntHolder2;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:pen
 */
public class ServiceDatailsPresenter extends BaseLoadingPresenter<UserService, IServiceDatailsView> {
    public DeviceWechat deviceWechat;
    private DeviceWechat userWechat;

    public void setAdapter(XRecyclerView xrecyclerView, DeviceWechat deviceWechat) {
        this.deviceWechat = deviceWechat;
        setXRecyclerView(xrecyclerView, new LinearLayoutManager(getContext()));
        mSuperAdapter = new ServiceDatailsAdaper(this, getContext(), deviceWechat);
        xrecyclerView.setAdapter(mSuperAdapter);
        mRecyclerView.refresh();
    }

    public DeviceWechat getDeviceWechat() {
        return userWechat;
    }

    public BaseRecycleViewAdapter getAdapter() {
        return super.mSuperAdapter;
    }

    @Override
    public void onRefreshList() {
        getUserWechatDetail(true);
    }

    @Override
    public void onLoadMoreList() {
        getUserWechatDetail(false);
    }

    public void finishActivity() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
        ContainerActivity.lunch(getContext());
    }


    public void setServiceDetailHeaderData(AccountInfoHeaderBntHolder headerViewHolderTab, DeviceWechat deviceWechat) {

        //1 进行中  2 已完成 3暂停 4 已取消 5 等待中  today_finish
        if (deviceWechat != null) {
            if (deviceWechat.getHasTodayInProgress() != null && deviceWechat.getHasTodayInProgress() == 1) {//是否有今日进行中服务  1 表示有
                headerViewHolderTab.me_use.setText("我要用号");
                headerViewHolderTab.me_use.setBackgroundResource(R.drawable.select_logout_button_bg);  //红色
            } else {
                headerViewHolderTab.me_use.setText("我要用号");
                headerViewHolderTab.me_use.setBackgroundResource(R.drawable.select_service_details_blue_bg);//蓝色
                headerViewHolderTab.me_use.setEnabled(true);
            }
            headerViewHolderTab.user_service.setText("使用服务");
            headerViewHolderTab.user_service.setBackgroundResource(R.drawable.select_login_button_bg);  //灰色
            headerViewHolderTab.user_service.setEnabled(true);
            if (deviceWechat.getAuditStatus() != null) {
                switch (deviceWechat.getAuditStatus()) {
                    case 1://1 未审核
                        headerViewHolderTab.bt_change_account.setText(R.string.wei_shenhe);
                        headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.select_logout_button_bg);//红色
                        headerViewHolderTab.bt_change_account.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showReAuditAccount(deviceWechat);
                            }
                        });
                        break;
                    case 2://2 审核中
                        headerViewHolderTab.bt_change_account.setText(R.string.shenhezhong);
                        headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.shape_login_edit_bg);
                        headerViewHolderTab.bt_change_account.setEnabled(true);
                        break;
                    case 3://3 审核通过
                        headerViewHolderTab.bt_change_account.setText(R.string.shenhe_seccess);
                        headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.shape_login_edit_bg);
                        headerViewHolderTab.bt_change_account.setEnabled(true);
                        break;
                    case 4://4 脚本响应超时
                        headerViewHolderTab.bt_change_account.setText(R.string.shenhe_fail);
                        headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.select_logout_button_bg);//红色
                        headerViewHolderTab.bt_change_account.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAccountLoginException4(deviceWechat, false, false);
                            }
                        });
                        break;
                    case 5:// 5 审核失败
                        headerViewHolderTab.bt_change_account.setText(R.string.shenhe_fail);
                        headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.select_logout_button_bg);//红色
                        headerViewHolderTab.bt_change_account.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAccountLoginException5(deviceWechat, false, false);
                            }
                        });
                        break;
                    case 6://6 未绑定银行卡，未实名
                        headerViewHolderTab.bt_change_account.setText(R.string.shenhe_fail);
                        headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.select_logout_button_bg);//红色
                        headerViewHolderTab.bt_change_account.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userFailureNoMonney(deviceWechat, false, false);
                            }
                        });
                        break;
                    default:
                }
            }

            if (deviceWechat.getState() != null && deviceWechat.getState() == 9) {//账号登出
                headerViewHolderTab.me_use.setText("账号登出");
                headerViewHolderTab.me_use.setBackgroundResource(R.drawable.shape_login_edit_bg);
                headerViewHolderTab.me_use.setEnabled(false);

                headerViewHolderTab.user_service.setText("使用服务");
                headerViewHolderTab.user_service.setBackgroundResource(R.drawable.service_dails_user_bg);  //灰色
                headerViewHolderTab.user_service.setEnabled(false);
            }
            //账号异常  跳转至设备
            if (deviceWechat.getState() != null && deviceWechat.getState() == 7) {//账号异常
                finishActivity();
            }
            if (deviceWechat.getServiceNum() != null && deviceWechat.getServiceNum() <= 0) {
                headerViewHolderTab.me_use.setText("我要用号");
                headerViewHolderTab.me_use.setBackgroundResource(R.drawable.shape_login_edit_bg);
                headerViewHolderTab.me_use.setEnabled(false);
            }
        }
        //我要换号
//        headerViewHolderTab.bt_change_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMeChangeDialogEnter(deviceWechat, AddAccountActivity.CHANGE_ACCOUNT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
//            }
//        });
        //我要用号
        headerViewHolderTab.me_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServerStart(deviceWechat);
            }
        });
        //使用服务
        headerViewHolderTab.user_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddServiceTab();
            }
        });
    }


    public void setServiceDetailHeaderData(AccountInfoHeaderBntHolder2 headerViewHolderTab, DeviceWechat deviceWechat) {
        //今日新增
        headerViewHolderTab.today_add_fans.setText(deviceWechat.getTodayFansNum() != null ? deviceWechat.getTodayFansNum() : "0");
        //好友总量
        headerViewHolderTab.guanfan_zongliang.setText(deviceWechat.getFansNum() != null ? deviceWechat.getFansNum() : "0");
    }


    /**
     * 用户的账户详情【账号列表（首页）-查看详情】
     */
    public void getUserWechatDetail(boolean isRefresh) {
        HttpApiService.instance().getUserWechatDetail(deviceWechat.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<ServiceDatails>>(getUI(), false) {
                    @Override
                    public void onNext(BaseResponse<ServiceDatails> accounts) {
                        if (checkApiResponse(accounts, false)) {
                            List<UserService> userServiceList = accounts.getData().getUserServiceList();
                            userWechat = accounts.getData().getUserWechat();
                            setServiceDetailHeaderData(getUI().getAccountInfoHeader(), userWechat);
                            setServiceDetailHeaderData(getUI().getAccountInfoHeader2(), userWechat);
                            if (userServiceList != null) {
                                refreshOrLoadMore(userServiceList, isRefresh);
                            }
                        }
                    }
                });
    }

    public void startAddServiceTab() {
        getUI().startAddServiceTab();
    }

    /**
     * 未审核  立即审核
     */
    public void showReAuditAccount(DeviceWechat deviceWechat) {
        if (!isActivityExist()) return;
        StyleDialog.showUserService(getContext(), "提示", "是否立即审核账号。", "取消", "立即审核",
                new StyleDialog.DialogUserServiceListener() {
                    @Override
                    public void onUser() {//联系客服
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//替换设备
                        reAuditAccount(deviceWechat);
                    }
                });
    }

    /**
     * 脚本响应超时（审核失败）4
     */
    public void showAccountLoginException4(DeviceWechat deviceWechat, boolean isChangeNumber, boolean isRecovery) {
        if (!isActivityExist()) return;
        StyleDialog.showAccountLoginException4(getContext(), isChangeNumber, isRecovery,
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {////重新审核
                        reAuditAccount(deviceWechat);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//重新添加
                        addAccount(deviceWechat, isRecovery ? AddAccountActivity.CHANGE_ACCOUNT : AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//恢复原号
                        recoverUserAccount(deviceWechat);
                    }
                });
    }

    /**
     * 重新添加账号 5 (审核登录失败)
     */
    public void showAccountLoginException5(DeviceWechat deviceWechat, boolean isChangeNumber, boolean isRecovery) {
        if (!isActivityExist()) return;
        StyleDialog.showAccountLoginException5(getContext(), isChangeNumber, isRecovery,
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//继续审核
                        reAuditAccount(deviceWechat);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//我要换号
                        addAccount(deviceWechat, isRecovery ? AddAccountActivity.CHANGE_ACCOUNT : AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//恢复原号
                        recoverUserAccount(deviceWechat);
                    }
                });
    }

    /**
     * 没有银行卡（审核失败） 6
     */
    public void userFailureNoMonney(DeviceWechat deviceWechat, boolean isChangeNumber, boolean isRecovery) {
        if (!isActivityExist()) return;
        StyleDialog.showAuditFailure(getContext(), isChangeNumber, isRecovery,
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//重新审核
                        reAuditAccount(deviceWechat);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//我要换号
                        addAccount(deviceWechat, isRecovery ? AddAccountActivity.CHANGE_ACCOUNT : AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//强制执行
                        userFailureSuccess(deviceWechat);
                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//恢复原号
                        recoverUserAccount(deviceWechat);
                    }
                }
        );
    }

    /**
     * 恢复原号
     *
     * @param deviceWechat
     * @param oldWechatNo
     * @param wechatNo
     * @param password
     */
    public void recoverUserAccount(DeviceWechat deviceWechat) {

        HttpApiService.instance().recoverUserAccount(deviceWechat.getId())
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
                        if (baseResponse.getCode() == 200) {
                            getUI().refrashIview();
                        } else {
                            ToastUtil.showMessage(getContext(), baseResponse.getMsg());
                        }
                    }
                });
    }

    /**
     * 使用
     */
    public void userFailureSuccess(DeviceWechat deviceWechat) {
        if (!isActivityExist()) return;
        StyleDialog.showUserService(getContext(), "注意", getContext().getResources().getString(R.string.failuresuccess), "取消", "继续",
                new StyleDialog.DialogUserServiceListener() {
                    @Override
                    public void onUser() {//取消

                    }
                },
                new StyleDialog.DialogEnterListener() {
                    @Override
                    public void onEnter() {//继续
                        updateUserDeviceWecht(deviceWechat);
                    }
                });
    }

    /**
     * 重新审核
     */
    public void reAuditAccount(DeviceWechat deviceWechat) {

        Map<Object, Object> map = new HashMap<>();
        map.put("id", deviceWechat.getId());
        String accountId = getUserId();
        map.put("userId", accountId);
        String infoGson = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), infoGson);
        HttpApiService.instance().reAuditAccount(requestBody)
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
                        if (checkApiResponse(baseResponse, false)) {
                            getUI().refrashIview();
                        } else {
                            ToastUtil.showMessage(getContext(), baseResponse.getMsg());
                        }
                    }
                });
    }


    public void showDialog(DeviceWechat deviceWechat, String time) {
        String tomorrowTime = !TextUtils.isEmpty(time) ? time : "0";
        if (deviceWechat.getHasTodayInProgress() == 1) {//进行中
            if (!isActivityExist()) return;
            StyleDialog.showServiceDialogEnter(getContext(), "注意", "2、明日" + tomorrowTime + "点后，系统将登录您的账号进行自检，请于此前完成使用；", new StyleDialog.DialogEnterListener() {
                @Override
                public void onEnter() {
                    updateUserDeviceWecht(deviceWechat);
                }
            });
        } else {//空闲中
            if (!isActivityExist()) return;
            StyleDialog.showServiceDialogEnter2(getContext(), "注意", "1、明日" + tomorrowTime + "点后，系统将登录您的账号进行自检，请于此前完成使用；", new StyleDialog.DialogEnterListener() {
                @Override
                public void onEnter() {
                    updateUserDeviceWecht(deviceWechat);
                }
            });
        }
    }

    /**
     * 登出    9
     */
    public void updateUserDeviceWecht(DeviceWechat deviceWechat) {
        mSuperSubscription = HttpApiService.instance().updateUserDeviceWecht(1, deviceWechat.getWechatNo(), 9, "", getToken())
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
                        if (checkApiResponse(baseResponse, false)) {
                            mRecyclerView.refresh();
                        }
                    }
                });
    }


    public void showRestartServiceDialog(UserService userService) {
        if (!isActivityExist()) return;
        StyleDialog.showRestartService(getContext(), "注意", "您即将重启" + userService.getName() + "，此操作次日生效，是否确定？", new StyleDialog.DialogEnterListener() {
            @Override
            public void onEnter() {
                restartService(userService,1);
            }
        });
    }

    //暂停服务   isRestart 是否重启  1 重启   2 暂停
    public void stopServiceDialog(UserService userService) {

        if (getContext() == null) {
            return;
        }
        try {
            if (!isActivityExist()) return;
            StyleDialog.showRestartService(getContext(), "提示", "您即将暂停该服务，确定后该服务今日将不再执行，是否确定？", new StyleDialog.DialogEnterListener() {
                @Override
                public void onEnter() {
                    restartTodayService(userService,2);
                }
            });
        }catch (Exception e){

        }
    }

    //重启服务  isRestart 是否重启  1 重启   2 暂停
    public void restartServiceDialog(UserService userService) {
        if (getContext() == null) {
            return;
        }
        try {
            if (!isActivityExist()) return;
            StyleDialog.showRestartService(getContext(), "提示", "重启服务将在今日本轮4个账号服务结束后执行，是否确定？", new StyleDialog.DialogEnterListener() {
                @Override
                public void onEnter() {
                    restartTodayService(userService,1);
                }
            });
        }catch (Exception e){

        }
    }

    /**
     * 暂停或重启服务
     */
    public void restartTodayService(UserService userService,int isRestart) {
        HttpApiService.instance().restartTodayService(userService.getId(),isRestart)
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




    //我要换号
    public void showMeChangeDialogEnter(DeviceWechat deviceWechat, int optType, String tilte) {
        if (!isActivityExist()) return;
        StyleDialog.showMeChangeDialogEnter(getContext(), null, new StyleDialog.DialogEnterListener() {
            @Override
            public void onEnter() {
                addAccount(deviceWechat, optType, tilte);
            }
        });
//        if (deviceWechat.getHasTodayInProgress() == 1) {//进行中 换号
//        }
//        else {  //空闲中
//            StyleDialog.showMeChangeDialogEnter2(getContext(), null, new StyleDialog.DialogEnterListener() {
//                @Override
//                public void onEnter() {
//                    addAccount(deviceWechat, optType, tilte);
//                }
//            });
//        }
    }

    public void addAccount(DeviceWechat deviceWechat, int optType, String tilte) {
        getUI().addAccount(deviceWechat, optType, tilte);
    }

    /**
     * 重启服务 isRestart 是否重启  1 重启   2 暂停
     */
    public void restartService(UserService userService,int restart) {
        HttpApiService.instance().restartService(userService.getId(),restart)
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

    /**
     * 我要用号
     */
    public void getServerStart(DeviceWechat deviceWechat) {
        HttpApiService.instance().getServerStart(getUserId())
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
                            String time = accounts.getData();
                            showDialog(deviceWechat, time);
                        }
                    }
                });
    }


}
