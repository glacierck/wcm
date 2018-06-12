package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.IAddServiceView;
import com.kongrongqi.shopmall.modules.account.adapter.AddServicePageAdapter;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.AddServicePresenter;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class AddServiceTabActivity extends BaseMVPActivity<AddServicePresenter> implements IAddServiceView {

    private TextView mTitle;
    private TabLayout tl_tab;
    private ViewPager vp_content;
    private ArrayList<String> tabIndicators;
    private ArrayList<Fragment> tabFragments;
    private AddServicePageAdapter contentAdapter;

    private DeviceWechat deviceWechat;

    public static void lunch(Context context,DeviceWechat deviceWechat) {
        Intent intent = new Intent(context, AddServiceTabActivity.class);
        intent.putExtra(ServiceDetailsActivity.DEVICE_WECHAT,deviceWechat);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_add_service;
    }

    @Override
    protected AddServicePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AddServicePresenter() :
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
        title.setText("添加服务");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        deviceWechat = (DeviceWechat) getIntent().getSerializableExtra(ServiceDetailsActivity.DEVICE_WECHAT);
        tl_tab = (TabLayout) findViewById(R.id.tl_tab);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        initTab();
        initContent();
    }

    protected void initTab() {
        tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tl_tab.setTabTextColors(ContextCompat.getColor(this, R.color.cl_ff9d9d9d), ContextCompat.getColor(this, R.color.cl_ffff0000));
        tl_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.cl_ffff0000));
        ViewCompat.setElevation(tl_tab, 10);
        tl_tab.setupWithViewPager(vp_content);
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabFragments = new ArrayList<>();

        tabIndicators.add("购买服务");
        tabIndicators.add("未使用服务");

        Bundle args = new Bundle();
        args.putSerializable(ServiceDetailsActivity.DEVICE_WECHAT,deviceWechat);
        AddServiceTabFragment addServiceTabFragment = AddServiceTabFragment.newInstance(args);

        Bundle args2 = new Bundle();
        args2.putSerializable(ServiceDetailsActivity.DEVICE_WECHAT,deviceWechat);
//        AddServiceTabFragment2 addServiceTabFragment2 = AddServiceTabFragment2.newInstance(args2);
        NotUseFragment notUseFragment = new NotUseFragment().newInstance(args2);

        tabFragments.add(addServiceTabFragment);
        tabFragments.add(notUseFragment);
        contentAdapter = new AddServicePageAdapter(getSupportFragmentManager(), tabFragments, tabIndicators);
        vp_content.setAdapter(contentAdapter);
        contentAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEventMainThread(JumpEvent event) {
        int mJumpType = event.codeJump();
        if (mJumpType == Constans.EVENT_ACCOUNT_NOT_USE) {//进程
            vp_content.setCurrentItem(1, false);
        }
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
