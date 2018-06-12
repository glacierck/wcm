package com.kongrongqi.shopmall.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.utils.DeviceUtils;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StatusBarUtil;
import com.kongrongqi.shopmall.utils.UmengUtils;
import com.kongrongqi.shopmall.wedget.LoadingDialog;
import com.kongrongqi.shopmall.wedget.ProgressDialog;
import com.kongrongqi.shopmall.zhy.autolayout.AutoLayoutActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;


/**
 * Activity基类，所有的Activity必须继承这个类.
 *
 * @author penny
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AutoLayoutActivity implements IUI {

    protected boolean mIsActivityDestoryed = false;
    private boolean isPaused;
    private boolean isStoped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.login_button_bj), 0);
        }
        if (DeviceUtils.isEMUI()) {
            Logger.i("BaseAct", "==华为手机==");
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        //友盟推送
        UmengUtils.starUmengActivity(this);
        //友盟统计
        UmengUtils.setDebugMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;

        //友盟统计 session的统计
        UmengUtils.uMengOnResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        //友盟统计 session的统计
        UmengUtils.uMengOnPause(this);
    }

    @Override
    protected void onDestroy() {
        mIsActivityDestoryed = true;
        dismissWaitingDialogIfShowing();
        super.onDestroy();
    }


    /**
     * isActivityDestoryed:Activity是否已经Destory了. <br/>
     *
     * @return true, Activity已经销毁了，不要在执行任何Fragment事务、显示Dialog等操作
     */
    public boolean isActivityDestoryed() {
        return mIsActivityDestoryed;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 一定不要干掉这段代码
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isStoped = false;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isStoped = false;
    }

    @Override
    protected void onStop() {
        isStoped = true;
        super.onStop();
    }


    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public boolean isDestoryed() {
        return isActivityDestoryed();
    }

    @Override
    public boolean isDetached() {
        return isDestoryed();
    }

    @Override
    public boolean isStoped() {
        return isStoped;
    }

    @Override
    public boolean isFragmentHidden() {
        return isDestoryed();
    }

    @Override
    public boolean isVisibleToUser() {
        return !isPaused();
    }


    @Override
    public void showWaitingDialog() {
        dismissWaitingDialogIfShowing();
        if (!isFinishing() && !isActivityDestoryed()) {
            LoadingDialog.showInfoMessage(this,getResources().getString(R.string.wait));
        }
    }

    @Override
    public void dismissWaitingDialogIfShowing() {
        if (!isActivityDestoryed()) {
            LoadingDialog.dismiss();
        }
    }


}
