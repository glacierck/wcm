package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.me.IView.IListView;
import com.kongrongqi.shopmall.modules.me.presenter.ListPresenter;

/**
 * 创建日期：2017/5/19 0019 on 14:46
 * 作者:penny
 */
public class ListActivity extends BaseMVPActivity<ListPresenter> implements IListView, View.OnClickListener {


    private TextView mTitle;
    private XRecyclerView mXRecyclerView;
    private TextView mRightTitle;
    private ImageButton mLeftTool;
    private WebView wb;

    public static void lunch(Context con, String key, int listType) {
        Intent intent = new Intent(con, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(key, listType);
        intent.putExtras(bundle);
        con.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_push;
    }

    @Override
    protected ListPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ListPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return ListActivity.this;
    }
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.help_and_service);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        wb = (WebView) findViewById(R.id.wb);
        wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wb.loadUrl(ApiBean.instance().XHS_1+ URLConstans.help);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inlucde_tv_right_bt:
                OpenIvoiceActivity.lunch(this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }
}
