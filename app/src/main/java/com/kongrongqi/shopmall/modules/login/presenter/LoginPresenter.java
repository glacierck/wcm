package com.kongrongqi.shopmall.modules.login.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.DisShopConfig;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.account.ReplaceIMEIActivity;
import com.kongrongqi.shopmall.modules.login.Iview.ILoginUI;
import com.kongrongqi.shopmall.modules.login.request.AccountBean;
import com.kongrongqi.shopmall.modules.login.request.RequestUser;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.LoginModel;
import com.kongrongqi.shopmall.modules.model.UserModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.utils.UmengUtils;
import com.umeng.message.PushAgent;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 09:21
 * 作者:penny
 */
public class LoginPresenter extends BasePresenter<ILoginUI> {

    public static final String TAG = "LoginPresenter";

    long mLastClickTime = 0;
    private Subscription mLoginSubscription;
    private Subscription mUserSubscribe;
    private String mUsername;
    private String mRealName;
    private String mMobile;
    private String mEmail;
    private String mToken;
    private Subscription mUpdateSubscribe;
    private String mAccountId;

    public void login(String phone, String passwd) {

        if (!checkISPhone(phone)) {
            return;
        }
        if (TextUtils.isEmpty(passwd) || !StringUtils.checkPassWord(passwd)) {
            if (isActivityExist()) {
                showLongToast(getContext().getString(R.string.toast_msg_passwd_empty));
            }
            return;
        }
        //双击或单击 都只访问一次
        if (isDoubleClick()) {
            if (isActivityExist()) {
                showLongToast(getContext().getString(R.string.toast_msg_double_click));
                loginHttp(phone, passwd);
            }
        } else {
            if (isActivityExist()) {
                loginHttp(phone, passwd);
            }
        }
    }

    /**
     * 验证手机
     *
     * @param phone num
     * @return 手机号无误 返回true
     */
    public boolean checkISPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            if (isActivityExist()) {
                showLongToast(getContext().getString(R.string.toast_msg_phone));
                return false;
            }
        }
        if (!StringUtils.isMobileNum(phone)) {
            if (isActivityExist()) {
                showLongToast(getContext().getString(R.string.toast_msg_phone_error));
                return false;
            }
        }
        return true;
    }


    public void loginHttp(String phone, String passwd) {

        String pwd = StringUtils.encryption(passwd);

        mLoginSubscription = HttpApiService.instance().getDisShopConfig(1)
                .flatMap(new Func1<BaseResponse<DisShopConfig>, Observable<BaseResponse<LoginModel>>>() {
                    @Override
                    public Observable<BaseResponse<LoginModel>> call(BaseResponse<DisShopConfig> disShopConfigBaseResponse) {
                        if (checkApiResponse(disShopConfigBaseResponse)) {
                            ApiBean.saveDisShopConfig(getContext(),disShopConfigBaseResponse.getData());
                            ApiBean.getApiBean(getContext());
                        }
                        return HttpApiService.instance().login(phone, pwd);
                    }
                })
//        mLoginSubscription = HttpApiService.instance().login(phone, pwd)
                .flatMap(new Func1<BaseResponse<LoginModel>, Observable<BaseResponse<UserModel>>>() {
                    @Override
                    public Observable<BaseResponse<UserModel>> call(BaseResponse<LoginModel> loginModelBaseResponse) {
                        if (checkApiResponse(loginModelBaseResponse)) {
                            mToken = loginModelBaseResponse.getData().getToken();
                        } else {
                            throw new RuntimeException(loginModelBaseResponse.getMsg());
                        }
                        return HttpApiService.instance().userDetails(mToken);
                    }
                })
                .map(new Func1<BaseResponse<UserModel>, RequestUser>() {
                    @Override
                    public RequestUser call(BaseResponse<UserModel> userModelBaseResponse) {
                        RequestUser requestUser = new RequestUser();
                        if (checkApiResponse(userModelBaseResponse)) {
                            mMobile = userModelBaseResponse.getData().getMobile();
                            mRealName = userModelBaseResponse.getData().getReal_name();
                            mAccountId = userModelBaseResponse.getData().getAccountId();
                            requestUser.setUserId(mAccountId);
                            requestUser.setNickName(mRealName);
                            requestUser.setMobile(mMobile);
                            requestUser.setDeviceType(1);//设备类型 1：Android  2：IOS
                        }
                        return requestUser;
                    }
                })
                .flatMap(new Func1<RequestUser, Observable<BaseResponse<String>>>() {
                    @Override
                    public Observable<BaseResponse<String>> call(RequestUser requestUser) {
                        return HttpApiService.instance().updateUserInfo(requestUser);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {

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
                        try {
                            if (e instanceof ConnectException || e instanceof NoRouteToHostException || e instanceof SocketTimeoutException) {
                                ToastUtil.showMessage(getContext(), "网络异常");
                            } else {
                                ToastUtil.showMessage(getContext(), TextUtils.isEmpty(e.getMessage()) ? "登录失败" : e.getMessage());
                            }
                            dismissDialog();
                        } catch (Throwable w) {
                            ToastUtil.showMessage(getContext(), "网络异常");
                        }
                    }

                    @Override
                    public void onNext(BaseResponse<String> baseResponse) {
                        dismissDialog();
                        if (checkApiResponse(baseResponse, false)) {
                            String data = baseResponse.getData();
                            storageKey();
                            if ("0".equals(data)) { //没有设备
                                ReplaceIMEIActivity.lunch(getContext(), ReplaceIMEIActivity.LOGIN);
                                getUI().closeAct();
                            } else {
                                SPUtils.putBoolean(getContext(), Constans.IS_HAVE_ACCOUNT, true);
                                ContainerActivity.lunch(getContext());
                                getUI().closeAct();
                            }
                        }
                    }
                });
    }


    /**
     * 存储用户信息
     *
     * @param email
     * @param mobile
     * @param realName
     * @param username
     */
    private void storageKey() {
        AccountBean bean = new AccountBean();
        bean.setAccountId(mAccountId);
        bean.setMobile(mMobile);
        bean.setReal_name(mRealName);
        bean.setToken(mToken);
        SPUtils.putString(getContext(), Constans.TOKEN, mToken);
        SPUtils.putString(getContext(), Constans.PHONE, mMobile);
        SPUtils.putString(getContext(), Constans.USER_ID, mAccountId);
        LoginInfoManager.getInstance().saveAccountInfo(bean);

        //友盟别名设定
//        UmengUtils.removeAlias(PushAgent.getInstance(getContext()), mAccountId);
        UmengUtils.addAlias(PushAgent.getInstance(getContext()), mAccountId);
    }

    private boolean isDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - mLastClickTime < 500) {
            return true;
        }
        mLastClickTime = time;

        return false;
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mLoginSubscription);
        releaseSubscription(mUserSubscribe);
        releaseSubscription(mUpdateSubscribe);
    }

}
