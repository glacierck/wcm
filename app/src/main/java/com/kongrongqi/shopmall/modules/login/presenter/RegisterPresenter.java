package com.kongrongqi.shopmall.modules.login.presenter;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.http.DisShopConfig;
import com.kongrongqi.shopmall.http.HttpApiService;
import com.kongrongqi.shopmall.http.HttpUtils;
import com.kongrongqi.shopmall.modules.login.AgreementActivity;
import com.kongrongqi.shopmall.modules.login.Iview.IRegisterView;
import com.kongrongqi.shopmall.modules.login.UserSettingActivity;
import com.kongrongqi.shopmall.modules.model.SMSModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2017/5/17 0017 on 13:15
 * 作者:penny
 */
public class RegisterPresenter extends BasePresenter<IRegisterView> {
    public static final String TAG = "RegisterPresenter";
    private CountDownTimer mTimer;
    private Subscription mSubscription;
    private String mSmsCode;
    private Subscription mForgetSubscribe;
    private Subscription mSubscribe;
    private String mPhone;

    /**
     * 检查手机
     *
     * @param phone    电话
     * @param isForget true:正常注册 false:忘记密码
     */
    public void checkPhone(String phone, boolean isForget) {
        if (checkISPhone(phone)) {
            startCountDownTimer(60);
            requestAuthCode(phone, isForget);
        }
    }

    /**
     * 请求验证码
     *
     * @param phone
     * @param isForget
     */
    private void requestAuthCode(String phone, boolean isForget) {
        Logger.d(TAG, "forget:" + isForget);
        //TODO 请求后台获得验证码
        if (isForget) {
            requestRegisterCode(phone);
        } else {
            requestForgetPwd(phone);
        }

    }

    /**
     * 忘记密码验证码请求
     *
     * @param phone
     */
    private void requestForgetPwd(String phone) {
        mSubscribe = HttpApiService.instance().getDisShopConfig(1)
                .flatMap(new Func1<BaseResponse<DisShopConfig>, Observable<BaseResponse<Boolean>>>() {
                    @Override
                    public Observable<BaseResponse<Boolean>> call(BaseResponse<DisShopConfig> disShopConfigBaseResponse) {
                        if (checkApiResponse(disShopConfigBaseResponse)) {
                            ApiBean.saveDisShopConfig(getContext(),disShopConfigBaseResponse.getData());
                            ApiBean.getApiBean(getContext());
                        }
                        return HttpApiService.instance().mobileExisted(phone);
                    }
                })
//        mSubscribe = HttpApiService.instance().mobileExisted(phone)
                .flatMap(new Func1<BaseResponse<Boolean>, Observable<BaseResponse<SMSModel>>>() {
                    @Override
                    public Observable<BaseResponse<SMSModel>> call(BaseResponse<Boolean> smsModelBaseResponse) {
                        if (!smsModelBaseResponse.getData()) {
                            throw new RuntimeException(phoneExis);
                        }
                        return HttpApiService.instance().forgetPwdMsg(phone);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<SMSModel>>() {
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
                        if (e instanceof RuntimeException && phoneExis.equals(e.getMessage())) {
                            ToastUtil.showMessage(getContext(), "该用户不存在");
                        } else {
                            ToastUtil.showMessage(getContext(), "获取验证码失败");
                        }
                        getUI().showCountTime(true, "");
                        stopCountDownTimer();
                    }

                    @Override
                    public void onNext(BaseResponse<SMSModel> smsReponseBaseResponse) {
                        if (checkApiResponse(smsReponseBaseResponse, false)) {
                            mSmsCode = smsReponseBaseResponse.getData().getSmsCode();
                            storageKey(phone, mSmsCode);
                            Logger.d(TAG, "sms" + mSmsCode);
                        } else {
                            if (isActivityExist()) {
                                showLongToast(getContext().getString(R.string.get_authcode_failure));
                                getUI().showCountTime(true, "");
                                stopCountDownTimer();
                            }
                        }
                    }
                });
    }


    public static String phoneExis = "phoneExis";

    /**
     * 获取注册验证码
     *
     * @param phone
     */
    private void requestRegisterCode(final String phone) {

        HttpApiService.instance().getDisShopConfig(1)
                .flatMap(new Func1<BaseResponse<DisShopConfig>, Observable<BaseResponse<Boolean>>>() {
                    @Override
                    public Observable<BaseResponse<Boolean>> call(BaseResponse<DisShopConfig> disShopConfigBaseResponse) {
                        if (checkApiResponse(disShopConfigBaseResponse)) {
                            ApiBean.saveDisShopConfig(getContext(),disShopConfigBaseResponse.getData());
                            ApiBean.getApiBean(getContext());
                        }
                        return HttpApiService.instance().mobileExisted(phone);
                    }
                })
//        HttpApiService.instance().mobileExisted(phone)
                .flatMap(new Func1<BaseResponse<Boolean>, Observable<BaseResponse<SMSModel>>>() {
                    @Override
                    public Observable<BaseResponse<SMSModel>> call(BaseResponse<Boolean> smsModelBaseResponse) {
                        if (smsModelBaseResponse.getData()) {
                            throw new RuntimeException(phoneExis);
                        }
                        return HttpApiService.instance().sendMsg(phone);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<SMSModel>>() {
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
                        if (e instanceof RuntimeException && phoneExis.equals(e.getMessage())) {
                            ToastUtil.showMessage(getContext(), "该手机号已注册");
                        } else {
                            ToastUtil.showMessage(getContext(), "获取验证码失败");
                        }
                        getUI().showCountTime(true, "");
                        stopCountDownTimer();
                    }

                    @Override
                    public void onNext(BaseResponse<SMSModel> smsReponseBaseResponse) {
                        if (checkApiResponse(smsReponseBaseResponse)) {
                            mSmsCode = smsReponseBaseResponse.getData().getSmsCode();
                            storageKey(phone, mSmsCode);
                            Logger.d(TAG, "sms" + mSmsCode);
                        } else {
                            if (isActivityExist()) {
                                showLongToast(getContext().getString(R.string.get_authcode_failure));
                                getUI().showCountTime(true, "");
                                stopCountDownTimer();
                            }
                        }
                    }
                });
    }

    /**
     * 存储值
     *
     * @param phone
     * @param smsCode
     */
    private void storageKey(String phone, String smsCode) {
        SPUtils.putString(getContext(), Constans.PHONE, phone);
        SPUtils.putString(getContext(), Constans.SMS, smsCode);

    }

    /**
     * 检测手机和验证是否正确
     *
     * @param phone
     * @param code
     * @param forget
     */
    public void checkPhoneAndAuthCode(String phone, String code, String forget) {
        if (checkISPhone(phone)) {
            if (!TextUtils.isEmpty(code) || StringUtils.checkcCode(code)) {
//                storageKey(phone, code);
                mPhone = SPUtils.getString(getContext(), Constans.PHONE, "");
                if (!TextUtils.equals(phone, mPhone)) {
                    showLongToast(getContext().getResources().getString(R.string.authcode));
                    return;
                }
                mSmsCode = SPUtils.getString(getContext(), Constans.SMS, "");
                //这里对比验证码
                if (null == forget) {//正常注册
                    if (TextUtils.equals(code, mSmsCode)) {
                        switchOpration(forget);
                    } else {
                        if (isActivityExist())
                            showLongToast(getContext().getResources().getString(R.string.code_not_sync));
                    }
                } else {//忘记密码
                    if (TextUtils.equals(code, mSmsCode)) {
                        switchOpration(forget);
                    } else {
                        if (isActivityExist())
                            showLongToast(getContext().getResources().getString(R.string.code_not_sync));
                    }
                }
            } else {
                showLongToast(getContext().getResources().getString(R.string.authcode_empty));
            }
        }
    }

    /**
     * @param forget null 正常注册  不为null 重置密码
     */
    private void switchOpration(String forget) {
        if (null == forget) {
            //TODO 正常注册
            AgreementActivity.lunch(getContext());
            closeAct();
        } else {
            // TODO 重置密码
            UserSettingActivity.lunch(getContext(), Constans.SETTING_PWD);
            closeAct();
        }
    }

    /**
     * 倒计时
     *
     * @param time 秒
     */
    private void startCountDownTimer(int time) {
        if (mTimer == null) {
            mTimer = new CountDownTimer(time * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long time = millisUntilFinished / 1000;
                    String timeStr = String.format(getContext().getResources()
                            .getString(R.string.mobile_resend_authcode_msg), time);
                    if (isActivityExist()) {
                        Logger.d("onTick", "===============time:" + time);
                        getUI().showCountTime(false, timeStr);
                    }
                }

                @Override
                public void onFinish() {
                    Logger.d("finish", "===============");
                    if (isActivityExist()) {
                        getUI().showCountTime(true, null);
                        stopCountDownTimer();
                    }
                }
            };
            mTimer.start();
        }
    }

    private void stopCountDownTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
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
    public void onUIPause() {
        super.onUIPause();
//        stopCountDownTimer();
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mSubscribe);
        releaseSubscription(mForgetSubscribe);
    }
}
