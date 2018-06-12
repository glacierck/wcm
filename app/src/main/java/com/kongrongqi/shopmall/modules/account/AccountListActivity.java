package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.IView.IAccountListView;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.AccountListPresenter;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class AccountListActivity extends BaseMVPActivity<AccountListPresenter> implements IAccountListView {

    private XRecyclerView mXrecyclerView;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, AccountListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_account_list;
    }

    @Override
    protected AccountListPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AccountListPresenter() :
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
        title.setText("账户列表");
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        mXrecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        getPresenter().setAdapter(mXrecyclerView);
    }

    @Override
    public void lookInfo(AccountBind accountBind) {
//        AccountInfoActivity.lunch(this,accountBind);
        ServiceDetailsActivity.lunch(getContext(), getPresenter().getDeviceWechatBuAccountBind(accountBind));
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_account_list_hint))
                    .setEmptyImage(R.drawable.empty_account_list)
                    .create()
                    .showEmpty();
        }
    }
}
