package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.INoUserServiceView;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.NoUserServicePresenter;
import com.kongrongqi.shopmall.modules.account.presenter.NotUsePresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.me.HaveOpenInvoiceActivity;
import com.kongrongqi.shopmall.modules.me.OpenIvoiceActivity;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import org.greenrobot.eventbus.EventBus;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class NoUseServiceActivity extends BaseMVPActivity<NotUsePresenter> implements INoUserServiceView {

    private XRecyclerView mXrecyclerView;
    private Button user_service;
    public static String DEVICEWECHAT = "deviceWechat";
    private DeviceWechat deviceWechat;

    public static void lunch(Context context, DeviceWechat deviceWechat) {
        Intent intent = new Intent(context, NoUseServiceActivity.class);
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
    protected NotUsePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new NotUsePresenter() :
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
        title.setText("未使用服务");

        right_tv2.setVisibility(View.VISIBLE);
        right_tv2.setText(R.string.buy);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyService();
            }
        });
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
    public void buyService() {
        ServiceListActivity.lunch(this,deviceWechat);
    }

    @Override
    public void closeAct() {
        this.finish();
    }

    @Override
    public void buyServiceB() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_NOT_USE_FRG));
        startActivity(new Intent(this, ContainerActivity.class));
    }

    /**
     * 使用服务成功
     */
    @Override
    public void userServiceSuccess() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_NOT_USE_FRG));
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_TASK_NO_USER));
        ContainerActivity.lunch(getContext());
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyButtonIsShow()
                    .setEmptyButtonText("马上购买服务")
                    .setEmptyButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buyService();
                        }
                    })
                    .setEmptyText(getString(R.string.empty_no_user_service))
                    .setEmptyImage(R.drawable.empty_no_user_service)
                    .create()
                    .showEmpty();
        }
    }

}
