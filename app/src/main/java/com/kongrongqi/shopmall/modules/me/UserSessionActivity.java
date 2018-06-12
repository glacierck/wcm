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
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.me.IView.IListView;
import com.kongrongqi.shopmall.modules.me.IView.IUserSessionView;
import com.kongrongqi.shopmall.modules.me.presenter.ListPresenter;
import com.kongrongqi.shopmall.modules.me.presenter.UserSessionPresenter;

/**
 * 创建日期：2017/5/19 0019 on 14:46
 * 作者:penny
 */
public class UserSessionActivity extends BaseMVPActivity<UserSessionPresenter> implements IUserSessionView {


    private XRecyclerView mXRecyclerView;

    public static void lunch(Context con) {
        Intent intent = new Intent(con, UserSessionActivity.class);
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
    protected UserSessionPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new UserSessionPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return UserSessionActivity.this;
    }

    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.push_msg);
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mXRecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        getPresenter().setAdapter(mXRecyclerView);
        getPresenter().updateUserSession();
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
                    .setEmptyText(getString(R.string.empty_msg_hint))
                    .setEmptyImage(R.drawable.empty_msg)
                    .create()
                    .showEmpty();
        }
    }
}
