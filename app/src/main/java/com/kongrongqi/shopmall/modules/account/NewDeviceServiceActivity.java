package com.kongrongqi.shopmall.modules.account;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.ApiBean;
import com.kongrongqi.shopmall.modules.account.IView.INewDeviceServiceView;
import com.kongrongqi.shopmall.modules.account.presenter.NewDeviceServicePresenter;
import com.kongrongqi.shopmall.utils.GlideUtil.GlideUtils;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class NewDeviceServiceActivity extends BaseMVPActivity<NewDeviceServicePresenter> implements INewDeviceServiceView {
    private ImageView new_device_img;
    private Button buy;

//    private XRecyclerView mXrecyclerView;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, NewDeviceServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_new_device_service;
    }

    @Override
    protected NewDeviceServicePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new NewDeviceServicePresenter() :
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
        title.setText("服务");
    }
    @Override
    protected void initViews(Bundle savedInstanceState) {

        new_device_img = (ImageView) findViewById(R.id.new_device_img);
        getPresenter().getOneNewDeviceServiceInfo(true);
//        mXrecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
//        getPresenter().setAdapter(mXrecyclerView);
        buy = (Button) findViewById(R.id.buy);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().confirmUserService();
            }
        });

    }


    @Override
    public void setImage(String url) {
        if (!TextUtils.isEmpty(url)) {
            String s = ApiBean.instance().XHS_1+ "/" + url;
            GlideUtils.getInstance().loadImage(this, new_device_img, s);
        }
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void buyService() {

    }
}
