package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.login.Iview.IAgreementView;
import com.kongrongqi.shopmall.modules.login.presenter.AgreementPresenter;

/**
 * 创建日期：2017/5/17 0017 on 13:59
 * 作者:penny
 */
public class AgreementMeActivity extends BaseMVPActivity<AgreementPresenter> implements IAgreementView {

    private Button mAgree;
    private TextView mText;
    private WebView wb;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, AgreementMeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_agreenment_me;
    }

    @Override
    protected AgreementPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AgreementPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return AgreementMeActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.user_agreenment_title2);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        wb = (WebView) findViewById(R.id.web);
        wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wb.loadUrl(ApiBean.instance().XHS_1+ URLConstans.agreement);
    }

    @Override
    public void showTextAgreement(String text) {
        mText.setText(text);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }

}
