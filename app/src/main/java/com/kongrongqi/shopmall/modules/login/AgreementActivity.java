package com.kongrongqi.shopmall.modules.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.login.Iview.IAgreementView;
import com.kongrongqi.shopmall.modules.login.presenter.AgreementPresenter;

/**
 * 创建日期：2017/5/17 0017 on 13:59
 * 作者:penny
 */
public class AgreementActivity extends BaseMVPActivity<AgreementPresenter> implements View.OnClickListener,IAgreementView {

    private Button mAgree;
    private TextView mText;
    private WebView wb;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, AgreementActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_agreenment;
    }

    @Override
    protected AgreementPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AgreementPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return AgreementActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.user_agreenment_title2);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAgree = (Button) findViewById(R.id.agreenment_agree);
        mAgree.setOnClickListener(this);
        wb = (WebView) findViewById(R.id.web);
        wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wb.loadUrl(ApiBean.instance().XHS_1+ URLConstans.agreement);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agreenment_agree:
                UserSettingActivity.lunch(AgreementActivity.this, null);
                finish();
                break;
        }
    }

    @Override
    public void showTextAgreement(String text) {
        mText.setText(text);
    }
}
