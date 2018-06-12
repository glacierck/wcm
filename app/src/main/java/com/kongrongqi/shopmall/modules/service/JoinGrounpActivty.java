package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.IJoinView;
import com.kongrongqi.shopmall.modules.service.holder.RecycleViewDivider;
import com.kongrongqi.shopmall.modules.service.presenter.JoinGrounpPresenter;
import com.kongrongqi.shopmall.utils.CommonUtils;

import java.io.Serializable;

/**
 * 创建日期：2017/5/25 0025 on 18:20
 * 作者:penny
 */
public class JoinGrounpActivty extends BaseMVPActivity<JoinGrounpPresenter> implements IJoinView, View.OnClickListener {

    private Button mSerach;
    private XRecyclerView mRecyclerView;
    private EditText mEdit;
    private ServiceBean serviceBean;

    public static void lunch(Context context, ServiceBean serviceBean) {
        Intent intent = new Intent(context, JoinGrounpActivty.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constans.SERVICE_BEAN, serviceBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_join_grounp;
    }

    @Override
    protected JoinGrounpPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new JoinGrounpPresenter() :
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
        title.setText("入群服务");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initResult();
        mSerach = (Button) findViewById(R.id.join_bt);
        mRecyclerView = (XRecyclerView) findViewById(R.id.join_recyclerView);
        mEdit = (EditText) findViewById(R.id.join_search);
        getPresenter().setAdapter(mRecyclerView, serviceBean);
        mSerach.setOnClickListener(this);

    }

    private void initResult() {
        serviceBean = (ServiceBean) mBundle.getSerializable(Constans.SERVICE_BEAN);
    }


    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_bt:
                if (CommonUtils.isSHowKeyboard(this, mEdit)) {
                    CommonUtils.hideSoftInput(this, mEdit);
                }
                getPresenter().serach(mEdit.getText().toString());
                break;
        }
    }

    @Override
    public void finishAct() {
        this.finish();
    }


    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_join_grounp_hint))
                    .setEmptyImage(R.drawable.empty_join_group)
                    .create()
                    .showEmpty();
        }
    }
}
