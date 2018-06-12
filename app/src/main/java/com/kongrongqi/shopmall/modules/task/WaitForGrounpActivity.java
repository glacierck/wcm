package com.kongrongqi.shopmall.modules.task;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.task.IView.IWaitForGrounpView;
import com.kongrongqi.shopmall.modules.task.presenter.WaitForGrounpPresenter;

import static android.R.attr.type;

/**
 * 创建日期：2017/5/26 0026 on 17:47
 * 作者:penny
 */
public class WaitForGrounpActivity extends BaseMVPActivity<WaitForGrounpPresenter> implements IWaitForGrounpView {

    private XRecyclerView mRecyclerView;
    private UserServiceParam param;

    public static void lunch(Context context, UserServiceParam param) {
        Intent intent = new Intent(context, WaitForGrounpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constans.USER_SERVICE_PARAM, param);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_wait_for_grounp;
    }

    @Override
    protected WaitForGrounpPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new WaitForGrounpPresenter() :
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
        title.setText(R.string.wait_for_grounp);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        param = (UserServiceParam) mBundle.getSerializable(Constans.USER_SERVICE_PARAM);
        mRecyclerView = (XRecyclerView) findViewById(R.id.wait_for_RecyclerView);
        getPresenter().setAdapter(mRecyclerView,param);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }
}
