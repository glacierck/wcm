package com.kongrongqi.shopmall.modules.main.presenter;

import android.os.Bundle;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseSubscriber;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.main.Iview.IContainerUI;
import com.kongrongqi.shopmall.modules.model.UpdateAppModel;
import com.kongrongqi.shopmall.modules.model.WxLoginSMSModel;
import com.kongrongqi.shopmall.utils.InstallUtil;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.NetUtils;
import com.kongrongqi.shopmall.utils.NotifyUtil;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/16 0016 on 17:04
 * 作者:penny
 */
public class ContainerPresenter extends BasePresenter<IContainerUI> {
    public static final String TAG = "ContainerPresenter";
    private Subscription mSubscribe;
    private boolean flag = true;

    @Override
    public void onUICreate(Bundle savedInstanceState) {
        super.onUICreate(savedInstanceState);
        if (NetUtils.hasNetWorkConection(getContext())) {
            Logger.d(TAG, "====请求版本升级=====");
            requestAPPVersion();
        }
    }


    public void getDeviceRequest() {
        if(!flag) return;
        mSubscribe = HttpApiService.instance().getDeviceRequest(getUserId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<WxLoginSMSModel>>(getUI(),false) {
                    @Override
                    public void onNext(BaseResponse<WxLoginSMSModel> response) {
                        if (checkApiResponse(response,false)) {
                            WxLoginSMSModel data = response.getData();
                            //状态 1 设备需要验证码  2 已回写验证码 3 已经取了验证码 4 表示需要扫描二维码
                            switch (data.getStatus()){
                                case 1:
                                    showSMSDialog();
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    showSMSDialog();
                                    break;
                            }
                        }
                    }
                });
    }

    private void showSMSDialog(){
        StyleDialog.showWxLongSMSDialog(getContext(), new StyleDialog.DialogEnterListener() {
            @Override
            public void onEnter() {
                flag=false;
            }
        });
    }







    private void requestAPPVersion() {
        int versionCode = App.getInstance().getVersionCode();
        mSubscribe = HttpApiService.instance().updateApp(versionCode + "", "0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UpdateAppModel>>(getUI(),false) {
                    @Override
                    public void onNext(BaseResponse<UpdateAppModel> response) {
                        if (checkApiResponse(response,false)) {
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
            String title="检测到新版本";
            String path = appModel.getPath();
            Logger.d(TAG,"url:"+path);
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
                    ToastUtil.showMessage(getContext(),"更新失败");
                }
            }).downloadAPK();
        }

    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mSubscribe);
    }
}
