package com.kongrongqi.shopmall.modules.me.presenter;

import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.account.bean.Me;
import com.kongrongqi.shopmall.modules.me.IView.IMeView;
import com.kongrongqi.shopmall.modules.model.UpdateAppModel;
import com.kongrongqi.shopmall.utils.InstallUtil;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.NotifyUtil;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 17:21
 * 作者:penny
 */
public class MePresenter extends BasePresenter<IMeView> {

    public static final String TAG = "MePresenter";
    private Subscription mLogoutSubscribe;
    private Subscription mSubscribe;

    /**
     * 获取帐号列表数据
     */
    public void getMeUserInfo() {
        String accountId = getUserId();
        HttpApiService.instance().getMeUserInfo(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<Me>>(getUI(), false) {
                    @Override
                    public void onNext(BaseResponse<Me> baseResponse) {
                        if (checkApiResponse(baseResponse) && baseResponse.getData() != null) {
                            getUI().bindData(baseResponse.getData());
                        }
                    }
                });
        checkAPPVersion();
    }

    /**
     * 退出登录
     */
    public void logout() {
        StyleDialog.showUserService(getContext(), "提示", "您即将退出登录，是否确定？", new StyleDialog.DialogUserServiceListener() {
            @Override
            public void onUser() {
                logoutSure();
            }
        });
    }

//    //  发烧友  启动Android默认浏览器
//    public void aboutMe() {
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri content_url = Uri.parse("http://sns.yysp.me");
//        intent.setData(content_url);
//        getContext().startActivity(intent);
//    }


    private void logoutSure() {
        String token = SPUtils.getString(getContext(), Constans.TOKEN, "");
        mLogoutSubscribe =HttpApiService.instance().logout(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>(getUI(), false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dismissDialog();
//                        if (checkTokenDue(e)) {
//                            clearData(false);
//                        }
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (checkApiResponse(baseResponse)) {
                            Logger.d(TAG, "=====loguot=");
                            dismissDialog();
                            clearData(false);
                        }
                    }
                });
    }


    public void checkAPPVersion() {
        int versionCode = App.getInstance().getVersionCode();
        mSubscribe = HttpApiService.instance().updateApp(versionCode + "", "0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UpdateAppModel>>(getUI(), false) {

                    @Override
                    public void onNext(BaseResponse<UpdateAppModel> response) {
                        dismissDialog();
                        if (checkApiResponse(response, false) && response.getData() != null && response.getData().getIsLatest() == 1) {
                            getUI().setVersionPoint(1);
                        } else {
                            getUI().setVersionPoint(0);
                        }
                    }
                });
    }


    public void requestAPPVersion() {
        int versionCode = App.getInstance().getVersionCode();
        mSubscribe = HttpApiService.instance().updateApp(versionCode + "", "0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UpdateAppModel>>(getUI(), false) {

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
                    public void onNext(BaseResponse<UpdateAppModel> response) {
                        dismissDialog();
                        if (checkApiResponse(response, false)) {
                            UpdateAppModel appModel = response.getData();
                            int isLatest = appModel.getIsLatest();
                            isUpdateApp(appModel, isLatest);
                        }
                    }
                });
    }

    private void isUpdateApp(UpdateAppModel appModel, int isLatest) {
        if (isLatest == 1) {
//            String title = StringUtils.jointStr("检测到新版本", appModel.getVersionNo(), "");
            String title = "检测到新版本";
            String path = appModel.getPath();
            Logger.d(TAG, "url:" + path);
            StyleDialog.showAppUpdateDialog(
                    getContext(),
                    title,
                    appModel.getContent(),
                    new StyleDialog.DialogEnterListener() {
                        @Override
                        public void onEnter() {
                            startDownLoadAPK(path);
                        }
                    });
        } else {
            ToastUtil.showMessage(getContext(), "当前已是最新版本");
        }
    }

    private void startDownLoadAPK(String path) {
        if (isActivityExist()) {
            NotifyUtil notifyUtil = getUI().startNotifyProgress();
            new InstallUtil(getContext(), path, "KRQ", new InstallUtil.DownloadCallBack() {
                @Override
                public void onStart() {
                    showLongToast(getContext().getString(R.string.apk_downloading));
                }

                @Override
                public void onComplete(String path) {
                    InstallUtil.installAPK(getContext(),
                            path,
                            getContext().getPackageName() + ".fileProvider",
                            new InstallUtil.InstallCallBack() {
                                @Override
                                public void onSuccess() {
                                    Logger.d(TAG, "安装成功");
                                }

                                @Override
                                public void onFail(Exception e) {
                                    showLongToast(getContext().getString(R.string.install_error));
                                    Logger.d(TAG, "安装失败");
                                }
                            });
                    if (notifyUtil != null) {
                        notifyUtil.setNotifyProgressComplete();
                        notifyUtil.clear();
                    }
                }

                @Override
                public void onLoading(long total, long current) {
                    int currentProgress = (int) (current * 100 / total);
                    if (notifyUtil != null) {
                        notifyUtil.setNotifyProgress(100, currentProgress, false);
                    }
                }

                @Override
                public void onFail(Exception e) {
                    if (notifyUtil != null) {
                        notifyUtil.clear();
                    }
                }
            }).downloadAPK();
        }

    }


}
