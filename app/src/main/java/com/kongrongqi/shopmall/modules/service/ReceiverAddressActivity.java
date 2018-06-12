package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.AccountListActivity;
import com.kongrongqi.shopmall.modules.login.request.EditAddressRequest;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.service.IView.IReceiverAddressView;
import com.kongrongqi.shopmall.modules.service.holder.RecycleViewDivider;
import com.kongrongqi.shopmall.modules.service.presenter.ReceiverAddressPresenter;
import com.kongrongqi.shopmall.utils.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 创建日期：2017/5/22 0022 on 13:28
 * 作者:penny
 */
public class ReceiverAddressActivity extends BaseMVPActivity<ReceiverAddressPresenter> implements IReceiverAddressView, View.OnClickListener {

    private Button mAddAddress;
    private XRecyclerView mRecyclerView;
    private TextView mTitle;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, ReceiverAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_receiver_address;
    }

    @Override
    protected ReceiverAddressPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ReceiverAddressPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.choose_address);
//        activityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPresenter().chooseAddress();
//                finish();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void finishActivity() {
        getPresenter().chooseAddress();
        super.finishActivity();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mAddAddress = (Button) findViewById(R.id.receiver_add_address);
        mRecyclerView = (XRecyclerView) findViewById(R.id.receiver_xRecyclerView);
        mAddAddress.setOnClickListener(this);
        getPresenter().setAdapter(mRecyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.receiver_add_address:
                EditAddressActivity.lunch(this);
                break;
        }
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        getPresenter().chooseAddress();
        this.finish();
    }

    @Override
    public void showAddress(AddressModel addressModel, String address) {
        EventBus.getDefault().post(addressModel);
        this.finish();
    }

    @Subscribe
    public void onEventMainThread(AddressModel event) {
        if (null != event) {
            mRecyclerView.refresh();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_receiver_address_hint))
                    .setEmptyImage(R.drawable.empty_address)
                    .create()
                    .showEmpty();
        }
    }

}
