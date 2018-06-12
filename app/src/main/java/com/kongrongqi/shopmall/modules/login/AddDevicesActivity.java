package com.kongrongqi.shopmall.modules.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.login.Iview.IAddDevicesView;
import com.kongrongqi.shopmall.modules.login.presenter.AddDevicesPresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;

/**
 * 创建日期：2017/5/17 0017 on 14:58
 * 作者:penny
 */
public class AddDevicesActivity extends BaseMVPActivity<AddDevicesPresenter> implements IAddDevicesView, View.OnClickListener {

    private Button mConfirmAdd;
    private TextView mTitle;
    private ImageButton mLeftTitle;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, AddDevicesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_adddevices;
    }

    @Override
    protected AddDevicesPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AddDevicesPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return AddDevicesActivity.this;
    }



    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.add_devices);
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mConfirmAdd = (Button) findViewById(R.id.devices_confirm);
        mConfirmAdd.setOnClickListener(this);
        mLeftTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.devices_confirm:
                ContainerActivity.lunch(AddDevicesActivity.this);
                this.finish();
                break;
        }
    }

}
