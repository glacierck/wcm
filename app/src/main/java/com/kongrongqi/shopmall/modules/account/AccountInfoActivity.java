package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.IView.IAccountInfoView;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.bean.AccountInfo;
import com.kongrongqi.shopmall.modules.account.presenter.AccountInfoPresenter;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class AccountInfoActivity extends BaseMVPActivity<AccountInfoPresenter> implements IAccountInfoView {

    private XRecyclerView mXrecyclerView;
    public static String ACCOUNT_BIND = "AccountBind";
    private AccountBind accountBind;

    public static void lunch(Context context,AccountBind accountBind) {
        Intent intent = new Intent(context, AccountInfoActivity.class);
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
    protected AccountInfoPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AccountInfoPresenter() :
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
        title.setText("账户详情");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        accountBind = (AccountBind)getIntent().getSerializableExtra(ACCOUNT_BIND);
        mXrecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        getPresenter().setAdapter(mXrecyclerView,accountBind);
    }

    @Override
    public void starVipInfo(AccountInfo accountInfo) {
        VipServiceInfoActivity.lunch(this,accountInfo,accountBind);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }
}
