package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.IServiceDatailsView;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderBntHolder;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderBntHolder2;
import com.kongrongqi.shopmall.modules.account.presenter.ServiceDatailsPresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;
import com.kongrongqi.shopmall.wedget.guide.GuideView;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class ServiceDetailsActivity extends BaseMVPActivity<ServiceDatailsPresenter> implements IServiceDatailsView {

    private XRecyclerView mXrecyclerView;
    public static String DEVICE_WECHAT = "deviceWechat"; //
    private DeviceWechat deviceWechat;
    private boolean isFoucus = false;
    private static final String TAG = "ServiceDetailsActivity";
    private AccountInfoHeaderBntHolder viewHolder;
    private AccountInfoHeaderBntHolder2 viewHolder2;

    /**
     * @param context
     * @param deviceWechat 号槽对象 主要就这两个值  wechatNo;微信账号   id; 设备账号id
     */
    public static void lunch(Context context, DeviceWechat deviceWechat) {
        Intent intent = new Intent(context, ServiceDetailsActivity.class);
        intent.putExtra(DEVICE_WECHAT, deviceWechat);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_service_details;
    }

    @Override
    protected ServiceDatailsPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ServiceDatailsPresenter() :
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
        title.setText(deviceWechat.getWechatNo());

        right_tv2.setVisibility(View.VISIBLE);
        right_tv2.setText(R.string.change_account);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showMeChangeDialogEnter(deviceWechat, AddAccountActivity.CHANGE_ACCOUNT,AddAccountActivity.CHANGE_ACCOUNT_TITLE);
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        deviceWechat = (DeviceWechat) getIntent().getSerializableExtra(DEVICE_WECHAT);
        mXrecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        initAccountInfoBntHeader();
        getPresenter().setAdapter(mXrecyclerView, deviceWechat);
        getPresenter().setServiceDetailHeaderData(viewHolder,deviceWechat);
        getPresenter().setServiceDetailHeaderData(viewHolder2,deviceWechat);
    }

    private void initAccountInfoBntHeader() {
        View view = findViewById(R.id.item_service_detail_header);
        View view2 = findViewById(R.id.item_service_detail_header_2);
        viewHolder = new AccountInfoHeaderBntHolder(view);
        viewHolder2 = new AccountInfoHeaderBntHolder2(view2);
    }

    @Override
    public void refrashIview() {
        if (mXrecyclerView != null)
            mXrecyclerView.refresh();
    }

    @Override
    public void startAddServiceTab() {
//        AddServiceTabActivity.lunch(this, deviceWechat);
        NoUseServiceActivity.lunch(this,deviceWechat);
    }

    @Override
    public void addAccount(DeviceWechat deviceWechat, int optType, String title) {
        AddAccountActivity.lunch(getContext(),deviceWechat,optType,title);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
        ContainerActivity.lunch(this);
        this.finish();
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
        ContainerActivity.lunch(this);
    }

    @Override
    public AccountInfoHeaderBntHolder getAccountInfoHeader() {
        return viewHolder;
    }


    @Override
    public AccountInfoHeaderBntHolder2 getAccountInfoHeader2() {
        return viewHolder2;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!getPresenter().isFirst()) {
            getPresenter().setFirst(true);
        }
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_working_hint))
                    .setEmptyImage(R.drawable.empty_tasking)
                    .create()
                    .showEmpty();
        }
    }
}
