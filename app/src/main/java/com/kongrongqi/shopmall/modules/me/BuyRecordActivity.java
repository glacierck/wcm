package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.commonframe.Constant;
import com.frame.commonframe.viewtype.BuilderProgress;
import com.frame.commonframe.viewtype.ProgressActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.me.IView.IBuyRecordView;
import com.kongrongqi.shopmall.modules.me.IView.IListView;
import com.kongrongqi.shopmall.modules.me.presenter.BuyRecordPresenter;
import com.kongrongqi.shopmall.modules.me.presenter.ListPresenter;

/**
 * 创建日期：2017/5/19 0019 on 14:46
 * 作者:penny
 */
public class BuyRecordActivity extends BaseMVPActivity<BuyRecordPresenter> implements IBuyRecordView {


    private XRecyclerView mXRecyclerView;

    public static void lunch(Context con) {
        Intent intent = new Intent(con, BuyRecordActivity.class);
        con.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_user_session;
    }

    @Override
    protected BuyRecordPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new BuyRecordPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return BuyRecordActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        right_tv2.setVisibility(View.VISIBLE);
        right_tv2.setText(R.string.open_invoice);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenIvoiceActivity.lunch(BuyRecordActivity.this);
            }
        });
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.deal);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mXRecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        getPresenter().uiDeal(mXRecyclerView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_buy_record_hint))
                    .setEmptyImage(R.drawable.empty_record)
                    .create()
                    .showEmpty();
        }
    }
}
