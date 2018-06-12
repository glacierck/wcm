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

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.me.IView.IHaveInvoiceView;
import com.kongrongqi.shopmall.modules.me.presenter.HaveOpenInvoicePresenter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 14:28
 * 作者:penny
 */
public class HaveOpenInvoiceActivity extends BaseMVPActivity<HaveOpenInvoicePresenter> implements IHaveInvoiceView{

    private XRecyclerView mXrecyclerView;
    private TextView mTitle;
    public static final int BILL = 1; //已开票


    public static void lunch(Context context) {
        Intent intent = new Intent(context, HaveOpenInvoiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_have_invoice;
    }

    @Override
    protected HaveOpenInvoicePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new HaveOpenInvoicePresenter(BILL) :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return HaveOpenInvoiceActivity.this;
    }
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.hava_invoice);
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mXrecyclerView = (XRecyclerView) findViewById(R.id.xrecyclerView);
        mXrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getPresenter().setAdapter(mXrecyclerView);
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
                    .setEmptyImage(R.drawable.empty_have_open_invoice)
                    .create()
                    .showEmpty();
        }
    }

}
