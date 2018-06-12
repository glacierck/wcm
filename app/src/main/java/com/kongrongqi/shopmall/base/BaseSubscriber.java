package com.kongrongqi.shopmall.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.modules.login.LoginActivity;
import com.kongrongqi.shopmall.modules.login.WelcomeActivity;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.request.AccountBean;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.UmengUtils;
import com.kongrongqi.shopmall.wedget.StyleDialog;
import com.umeng.message.PushAgent;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private IUI iui;
    private boolean isTypePage;

    public BaseSubscriber(IUI iui, boolean isTypePage) {
        this.iui = iui;
        this.isTypePage = isTypePage;
    }

    @Override
    public void onStart() {
        super.onStart();
//        if(isTypePage) iui.showLoading();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (checkTokenDue(e)) {
            clearData(true);
        }
        if (isTypePage) iui.showError();
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

    public boolean isActivityExist() {
        return !(iui == null || iui.isDestoryed());
    }

    /**
     * 清除缓存
     *
     * @param b
     */
    public void clearData(boolean isToken) {
        if (getContext() == null) {
            return;
        }
        try {
            UmengUtils.removeAlias(PushAgent.getInstance(getContext()), getUserId());
            LoginInfoManager.getInstance().clearAccount();
            SPUtils.putString(getContext(), Constans.USER_ID, "");
            SPUtils.putString(getContext(), Constans.TOKEN, "");
            SPUtils.putString(getContext(), Constans.SMS, "");
            ApiBean.clearApi(getContext());//清除网络配置
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
        } catch (Exception e) {

        }
    }

    private Context getContext() {
        return iui.getContext();
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

}
