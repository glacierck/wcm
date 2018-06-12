package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.IView.IVipInfoServiceView;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.bean.AccountInfo;
import com.kongrongqi.shopmall.modules.account.bean.FanOrGroup;
import com.kongrongqi.shopmall.modules.account.presenter.VipServiceInfoPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class VipServiceInfoActivity extends BaseMVPActivity<VipServiceInfoPresenter> implements IVipInfoServiceView {

    private XRecyclerView mXrecyclerView;
    public static String ACCOUNT_INFO ="accountInfo";

    public static String ACCOUNT_BIND ="accountBind";

    private AccountInfo accountInfo;
    private AccountBind accountBind;


    public static void lunch(Context context, AccountInfo accountInfo,AccountBind accountBind) {
        Intent intent = new Intent(context, VipServiceInfoActivity.class);
        intent.putExtra(ACCOUNT_INFO,accountInfo);
        intent.putExtra(ACCOUNT_BIND,accountBind);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_account_info;
    }

    @Override
    protected VipServiceInfoPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new VipServiceInfoPresenter() :
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
        title.setText(accountInfo.getName());
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        accountInfo = (AccountInfo)getIntent().getSerializableExtra(ACCOUNT_INFO);
        accountBind = (AccountBind)getIntent().getSerializableExtra(ACCOUNT_BIND);
        mXrecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        getPresenter().setAdapter(mXrecyclerView,accountInfo.getType(),accountBind.getId());
        View header1 =   LayoutInflater.from(this).inflate(R.layout.item_vip_service_head, (ViewGroup)findViewById(android.R.id.content),false);
        TextView service_name = (TextView) header1.findViewById(R.id.service_name);
        TextView time_status = (TextView) header1.findViewById(R.id.time_status);
        service_name.setText(R.string.weixin_no);
        time_status.setText(accountBind.getWechatNo());
        mXrecyclerView.addHeaderView(header1);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }
}
