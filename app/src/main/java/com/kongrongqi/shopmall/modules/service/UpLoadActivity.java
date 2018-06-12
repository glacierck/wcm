package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.IUpLoadView;
import com.kongrongqi.shopmall.modules.service.presenter.UpLoadPresenter;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * 创建日期：2017/5/31 0031 on 16:22
 * 作者:penny
 */
public class UpLoadActivity extends BaseMVPActivity<UpLoadPresenter> implements IUpLoadView, View.OnClickListener {

    private TextView mTitle;
    private ImageButton mLeft;
    private Button mUpload;
    private int mType;
    private TextView url;
    private ServiceBean mServiceModel;

    public static void lunch(Context context, ServiceBean pBean) {
        Intent intent = new Intent(context, UpLoadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constans.SERVICE_BEAN,pBean);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_upload_file;
    }

    @Override
    protected UpLoadPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new UpLoadPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return UpLoadActivity.this;
    }
    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.upload_title);
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        mServiceModel = (ServiceBean) mBundle.getSerializable(Constans.SERVICE_BEAN);
        mUpload = (Button) findViewById(R.id.upload);
        url = (TextView) findViewById(R.id.url);
        mUpload.setOnClickListener(this);
    }


    @Override
    public void bindUrl(String urls) {
        url.setText(ApiBean.instance().XHS_1+urls);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:
                //是否上传数据
                getPresenter().requestUpLoadData(mServiceModel);
                break;
        }
    }

    @Override
    public void closeAct() {
        this.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }



}
