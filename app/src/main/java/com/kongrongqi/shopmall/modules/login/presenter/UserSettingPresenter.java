package com.kongrongqi.shopmall.modules.login.presenter;

import android.text.TextUtils;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.login.Iview.IUserSettingView;
import com.kongrongqi.shopmall.modules.login.LoginActivity;
import com.kongrongqi.shopmall.modules.login.request.AccountBean;
import com.kongrongqi.shopmall.modules.login.request.RequestUser;
import com.kongrongqi.shopmall.modules.model.RegisterModel;
import com.kongrongqi.shopmall.modules.model.UserModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import org.apache.commons.pennycodec.binary.Base64;
import org.apache.commons.pennycodec.digest.DigestUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 创建日期：2017/5/17 0017 on 14:28
 * 作者:penny
 */
public class UserSettingPresenter extends BasePresenter<IUserSettingView> {
    public static final String TAG = "UserSettingPresenter";
    private Subscription mRegisterSubscribe;
    private Subscription mUserSubscribe;
    private String mMobile;
    private String mRealName;
    private String mToken;
    private String mAccountId;
    private Subscription mUpdateSubscribe;

    public void registerUser(String name, String pwd, String pwd2) {
        if (isEmpty(name, pwd, pwd2)) {
            if (TextUtils.equals(pwd, pwd2)) {
                //TODO 正式调用注册接口
                requestRegister(name, pwd);
            } else {
                if (isActivityExist())
                    getUI().showToast(getContext().getString(R.string.passwd_not_sync));
            }
        }
    }

    /**
     * 注册
     *
     * @param name
     * @param pwd
     */
    private void requestRegister(String name, String pwd) {

        /*
        *   mobile 手机号（必填，String） //获取手机在sp里面取
            smsCode验证码（必填，String） //获取验证码在sp里面取
            realName真实姓名（必填，String）
            password 密码（必填，String）
            （和上一个字段有点不一样）
        * */

        String phone = SPUtils.getString(getContext(), Constans.PHONE, "");
        String sms = SPUtils.getString(getContext(), Constans.SMS, "");
        Logger.d(TAG, "PHONE:" + phone);
        Logger.d(TAG, "sms:" + sms);
        String base64Pwd = StringUtils.encryption(pwd);
        mRegisterSubscribe = HttpApiService.instance().registerUser(phone, sms, name, base64Pwd, Constans.BIZSYS_CODE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<RegisterModel>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<RegisterModel> response) {
                        if (checkApiResponse(response)) {
                            mToken = response.getData().getToken();
                            SPUtils.putString(getContext(), Constans.TOKEN, mToken);
                            Logger.d(TAG, "token" + mToken);
                            dismissDialog();
                            requestUserDetails(mToken);
                        }
                    }
                });


    }

    /**
     * 获取用户详细信息
     *
     * @param accountId
     * @param token
     */
    private void requestUserDetails(String token) {
        mUserSubscribe = HttpApiService.instance().userDetails(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<UserModel>>() {
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
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse<UserModel> userModelBaseResponse) {
                        if (checkApiResponse(userModelBaseResponse)) {
                            Logger.d(TAG, "==========4=========");
                            mMobile = userModelBaseResponse.getData().getMobile();
                            mRealName = userModelBaseResponse.getData().getReal_name();
                            mAccountId = userModelBaseResponse.getData().getAccountId();
                            updateUserInfo();
                        }
                    }
                });
    }

    /**
     * 获取用户信息
     */
    private void updateUserInfo() {
        RequestUser requestUser = new RequestUser();
        requestUser.setUserId(mAccountId);
        requestUser.setNickName(mRealName);
        requestUser.setMobile(mMobile);
        mUpdateSubscribe =HttpApiService.instance().updateUserInfo(requestUser)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
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
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (checkApiResponse(baseResponse)) {
                            storageKey();
                            dismissDialog();
                            if (isActivityExist()) {
                                getUI().addIMEI();
                                getUI().finishAct();
                            }
                        }
                    }
                });

    }


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
    }

    private boolean isEmpty(String name, String pwd, String pwd2) {
        if (TextUtils.isEmpty(name) || !StringUtils.checkUserName(name)) {
            if (isActivityExist()) {
                getUI().showToast(getContext().getResources().getString(R.string.nick_name_empty));
                return false;
            }
        }
        if (TextUtils.isEmpty(pwd) || !StringUtils.checkPassWord(pwd)) {
            if (isActivityExist()) {
                getUI().showToast(getContext().getResources().getString(R.string.toast_msg_passwd_empty));
                return false;
            }
        }
        return true;
    }


    /**
     * 修改密码并访问服务器
     *
     * @param pwd
     * @param pwd2
     */
    public void updatePasswd(String pwd, String pwd2) {

        if (TextUtils.isEmpty(pwd) || !StringUtils.checkPassWord(pwd)) {
            if (isActivityExist()) {
                getUI().showToast(getContext().getResources().getString(R.string.toast_msg_passwd_empty));
                return;
            }
        }
        if (TextUtils.equals(pwd, pwd2)) {
            requestUpdatePasswd(pwd, pwd2);
        } else {
            if (isActivityExist()) {
                getUI().showToast(getContext().getResources().getString(R.string.passwd_not_sync));
                return;
            }
        }
    }

    /**
     * 修改密码
     *
     * @param pwd
     * @param pwd2
     */
    private void requestUpdatePasswd(String pwd, String pwd2) {
        //TODO 请求接口 成功弹窗
        String phone = SPUtils.getString(getContext(), Constans.PHONE, "");
        String sms = SPUtils.getString(getContext(), Constans.SMS, "");
        String encryptionPwd = StringUtils.encryption(pwd);
        HttpApiService.instance().updatePwd(phone, sms, encryptionPwd)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showDialog();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (checkApiResponse(baseResponse)) {
                            showDialog();
                            StyleDialog.showEnterAPP(getContext(), new StyleDialog.DialogEnterListener() {
                                @Override
                                public void onEnter() {
                                    LoginActivity.lunch(getContext());
                                    closeAct();
                                }
                            });
                        }
                    }
                });
    }

    public void closeAct() {
        if (isActivityExist()) {
            getUI().finishAct();
        }
    }

    public void showToast(int id) {
        if (isActivityExist()) {
            getUI().showToast(getContext().getString(id));
        }
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mRegisterSubscribe);
        releaseSubscription(mUserSubscribe);
    }
}
