package com.kongrongqi.shopmall.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.BaseResponse;
import com.kongrongqi.shopmall.modules.login.LoginActivity;
import com.kongrongqi.shopmall.modules.login.WelcomeActivity;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.AccountBean;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import retrofit2.Response;
import rx.Subscription;

/**
 * Presneter的基类.
 *
 * @author penny
 */
public abstract class BasePresenter<U extends IUI> implements IPresenter {

    private U mUI;
    private Context mContext;
    private boolean isFirst = false;

    public BasePresenter() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IUI> void init(Context context, T ui) {
        this.mContext = context;
        this.mUI = (U) ui;
    }

    protected final U getUI() {
        return mUI;
    }

    protected final Context getContext() {
        return mContext;
    }

    @Override
    public void onUICreate(Bundle savedInstanceState) {

    }

    @Override
    public void onUIStart() {

    }

    @Override
    public void onUIResume() {
        Logger.e("BasePresenter", "======onUIResume====");
    }

    @Override
    public void onUIPause() {

    }

    @Override
    public void onUIStop() {

    }

    @Override
    public void onUIDestory() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    public void releaseSubscription(Subscription sb) {
        if (sb != null) {
            sb.unsubscribe();
            sb = null;
        }
    }

    public boolean checkApiResponse(BaseResponse response) {
        return checkApiResponse2(response, true);
    }

    /**
     * 检查网络请求回来的错误码
     *
     * @param response
     * @return
     */
    public boolean checkApiResponse(BaseResponse response, boolean pageType) {
        return checkApiResponse2(response, pageType);
    }

    /**
     * 检查网络请求回来的错误码
     *
     * @param response
     * @return
     */
    public boolean checkApiResponse2(BaseResponse response, boolean pageType) {

        int code = response.getCode();

        if (code != 200) {
            if (response.bizCode == 2301) {
                showLongToast(getContext().getString(R.string.get_authcode_failure));
            } else if (response.bizCode == 1003) {
                showLongToast(getContext().getString(R.string.get_authcode_failure_1));
            } else {
                showLongToast(response.getMsg());
            }
            if (pageType) getUI().showError();
        }
        return code == 200;
    }

    /***
     * 检查token过期
     *
     * @param text
     * @param response
     */
    public boolean checkTokenDue(Throwable e) {
        if (e.getMessage() != null && e.getMessage().contains("401")) {
            return true;
        } else {
            return false;
        }
    }


    public void showLongToast(String text) {

        ToastUtil.showMessage(
                getContext(),
                text,
                Toast.LENGTH_LONG
        );
    }

    /**
     * 显示进度条
     */
    public void showDialog() {
        if (isActivityExist())
            getUI().showWaitingDialog();
    }

    public void dismissDialog() {
        if (isActivityExist())
            getUI().dismissWaitingDialogIfShowing();
    }

    public String getUserId() {
        String accountId = null;
        AccountBean accountBean = LoginInfoManager.getInstance().getmAccountBean();
        if (accountBean != null) {
            try {
                accountId = accountBean.getAccountId();
                if (TextUtils.isEmpty(accountId)) {
                    accountId = SPUtils.getString(getContext(), Constans.USER_ID, "");
                }
                return accountId;
            } catch (Exception e) {
                return SPUtils.getString(getContext(), Constans.USER_ID, "");
            }
        } else {
            accountId = SPUtils.getString(getContext(), Constans.USER_ID, "");
            return accountId;
        }
    }

    public static String getToken() {
        String token = null;
        try {
            AccountBean accountBean = LoginInfoManager.getInstance().getmAccountBean();
            if (accountBean != null) {
                token = accountBean.getToken();
                return token;
            } else {
                token = SPUtils.getString(App.getInstance().getContext(), Constans.TOKEN, "");
                return token;
            }
        } finally {
            return SPUtils.getString(App.getInstance().getContext(), Constans.TOKEN, "");
        }
    }

    /**
     * 清除缓存
     *
     * @param b
     */
    public void clearData(boolean isToken) {
        clearUserData();
        if (isToken) {
            if (!isActivityExist()) return;
            StyleDialog.showTokenDialog(getContext(), new StyleDialog.DialogEnterListener() {
                @Override
                public void onEnter() {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getContext().startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent();
            intent.setClass(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(intent);
        }
    }


    public void clearUserData() {
        LoginInfoManager.getInstance().clearAccount();
        SPUtils.putString(getContext(), Constans.USER_ID, "");
        SPUtils.putString(getContext(), Constans.TOKEN, "");
        SPUtils.putString(getContext(), Constans.SMS, "");
    }


    public boolean isActivityExist() {
        return !(getUI() == null || getUI().isDestoryed());
    }


    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean pFirst) {
        isFirst = pFirst;
    }


    @Override
    public void refreshView() {

    }
}
