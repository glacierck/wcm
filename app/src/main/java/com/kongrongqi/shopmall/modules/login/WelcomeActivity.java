package com.kongrongqi.shopmall.modules.login;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.modules.login.Iview.IWelcomeView;
import com.kongrongqi.shopmall.modules.login.presenter.LoginInfoManager;
import com.kongrongqi.shopmall.modules.login.presenter.WelcomePresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.utils.GlideUtil.GlideUtils;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;

import java.util.List;

/**
 * 创建日期：2017/6/2 0002 on 19:52
 * 作者:penny
 */
public class WelcomeActivity extends BaseMVPActivity<WelcomePresenter> implements IWelcomeView {
    private static final long DURATION_SPLASH = 2000;
    private ImageView mSplash;

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected WelcomePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new WelcomePresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return WelcomeActivity.this;
    }

    @Override
    protected void onStart() {
        App.getInstance().getHandler().postDelayed(mSplashTask, DURATION_SPLASH);

        super.onStart();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mSplash = (ImageView) findViewById(R.id.splash);
        getPresenter().requestSplashPic();
        getPresenter().getNewConfig();
    }

    @Override
    protected void onStop() {
        App.getInstance().getHandler().removeCallbacks(mSplashTask);
        super.onStop();
    }

    private Runnable mSplashTask = new Runnable() {
        @Override
        public void run() {
            if (!TextUtils.isEmpty(SPUtils.getString(WelcomeActivity.this, Constans.USER_ID, "")) || (LoginInfoManager.getInstance().getmAccountBean() != null)) {
                ContainerActivity.lunch(WelcomeActivity.this);
            } else {
                LoginActivity.lunch(WelcomeActivity.this);
            }
            WelcomeActivity.this.finish();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void showSplashPic(List<String> data) {
        String url = data.get(0);
        if (!TextUtils.isEmpty(url)) {
            String s = API.yysp + "/" + url;
            GlideUtils.getInstance().loadImage(this, mSplash, s);
        }
    }
}
