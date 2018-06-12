package com.kongrongqi.shopmall.modules.task;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.task.IView.IUserServiceView;
import com.kongrongqi.shopmall.modules.task.presenter.UseServicePresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建日期：2017/5/23 0023 on 13:02
 * 作者:penny
 */
public class UseServiceActivity extends BaseMVPActivity<UseServicePresenter> implements IUserServiceView, View.OnClickListener {

    private XRecyclerView mRecyclerView;
    private TextView mBuyService;
    private Button mUseService;
    public static final String TAG = "UseServiceActivity";
    private UserServiceParam param;



    public static void lunch(Context context, UserServiceParam param) {
        Intent intent = new Intent(context, UseServiceActivity.class);
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
        return R.layout.activity_use_service;
    }

    @Override
    protected UseServicePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new UseServicePresenter() :
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
//        title.setText(param.getType() == 3 ? R.string.fansA : R.string.choose_account_list);
        title.setText(R.string.user_account);
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        param = (UserServiceParam) mBundle.getSerializable(Constans.USER_SERVICE_PARAM);

        mBuyService = (TextView) findViewById(R.id.use_buy_service);
        mRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        mUseService = (Button) findViewById(R.id.use_service);
        mUseService.setOnClickListener(this);
        mBuyService.setOnClickListener(this);
        StringUtils.underLineText(mBuyService);
        getPresenter().setAdapter(mRecyclerView, param);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void buyAccount(){
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
        this.finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.use_buy_service:
                EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
                this.finish();
                break;
            case R.id.use_service://使用
                getPresenter().useService();
                break;
        }
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_use_weixin_hint))
                    .setEmptyImage(R.drawable.empty_use_weixin)
                    .create()
                    .showEmpty();
        }
    }

}
