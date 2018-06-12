package com.kongrongqi.shopmall.modules.task;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.FansBListModel;
import com.kongrongqi.shopmall.modules.model.UserServiceParam;
import com.kongrongqi.shopmall.modules.task.IView.IFansBView;
import com.kongrongqi.shopmall.modules.task.presenter.FansBPresenter;

/**
 * 创建日期：2017/5/26 0026 on 17:15
 * 作者:penny
 */
public class FansBServiceActivity extends BaseMVPActivity<FansBPresenter> implements IFansBView {

    private XRecyclerView mRecyclerView;
    private UserServiceParam param;

    public static void lunch(Context context, UserServiceParam param) {
        Intent intent = new Intent(context, FansBServiceActivity.class);
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
        return R.layout.activity_fansb;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        setTitleName();
    }

    private void setTitleName() {
        if (param.getType() == 2) {
            title.setText(R.string.system_add_friend);
        } else if (param.getType() == 3) {
            title.setText(R.string.fansB);
        } else if (param.getType() == 6) {
            title.setText(R.string.fansC);
        }else if(param.getType() == 5){
            title.setText(R.string.trusteeship_account);
        }
    }

    @Override
    protected FansBPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new FansBPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        param = (UserServiceParam) mBundle.getSerializable(Constans.USER_SERVICE_PARAM);
        mRecyclerView = (XRecyclerView) findViewById(R.id.fansbRecyclerView);
        getPresenter().setAdapter(mRecyclerView, param);
    }

    @Override
    public void gotoCallTxt(UserServiceParam param) {
        CallTxtActivity.lunch(this, param);
    }

    @Override
    public void gotoUseService(UserServiceParam param) {
        UseServiceActivity.lunch(this, param);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }
}
