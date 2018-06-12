package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.IView.IMoreEquipView;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.presenter.MoreEquipListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class MoreEquipListActivity extends BaseMVPActivity<MoreEquipListPresenter> implements IMoreEquipView {

    private XRecyclerView mXrecyclerView;
    private TextView mTitle;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, MoreEquipListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_more_equip_list;
    }

    @Override
    protected MoreEquipListPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new MoreEquipListPresenter() :
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
        title.setText("设备列表");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mXrecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        mXrecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        getPresenter().setAdapter(mXrecyclerView);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void selectPage(int page) {
        Intent intent = new Intent();
        intent.putExtra(AccountFragment.PAGE_INDEX,page);
        setResult(RESULT_OK, intent);
        finish();

    }
}
