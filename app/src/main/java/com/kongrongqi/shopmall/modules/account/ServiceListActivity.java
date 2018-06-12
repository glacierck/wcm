package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.IAddServiceTabView;
import com.kongrongqi.shopmall.modules.account.IView.IServiceListView;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.ServiceListPresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.JumpEvent;

import org.greenrobot.eventbus.EventBus;

import static com.kongrongqi.shopmall.modules.account.NoUseServiceActivity.DEVICEWECHAT;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class ServiceListActivity extends BaseMVPActivity<ServiceListPresenter> implements IAddServiceTabView {

    private XRecyclerView mXrecyclerView;
    private TextView mTitle;
    private Button user_service;
    private DeviceWechat deviceWechat;

    public static void lunch(Context context,DeviceWechat deviceWechat) {
        Intent intent = new Intent(context, ServiceListActivity.class);
        intent.putExtra(DEVICEWECHAT, deviceWechat);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_no_user_service;
    }

    @Override
    protected ServiceListPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ServiceListPresenter() :
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
        title.setText("服务");
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        deviceWechat = (DeviceWechat) getIntent().getSerializableExtra(DEVICEWECHAT);

        mXrecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        mXrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getPresenter().setAdapter(mXrecyclerView,deviceWechat);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void buyServiceB() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_SERVICE_FRG));
        startActivity(new Intent(this, ContainerActivity.class));
    }
}
