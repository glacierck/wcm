package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.ICreatePackageView;
import com.kongrongqi.shopmall.modules.service.presenter.CreatePackagePresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/25 0025 on 18:20
 * 作者:penny
 */
public class CreatePackageActivty extends BaseMVPActivity<CreatePackagePresenter> implements ICreatePackageView {

    public static String SERVICE_BEAN = "ServiceListModel";
    private ServiceBean serviceBean;
    private TextView rule;
    private EditText sure_number;
    private EditText sure_number_day;
    private Button create_package;

    public static void lunch(Context context, ServiceBean serviceBean) {
        Intent intent = new Intent(context, CreatePackageActivty.class);
        intent.putExtra(SERVICE_BEAN, serviceBean);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_create_package;
    }

    @Override
    protected CreatePackagePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new CreatePackagePresenter() :
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
        title.setText("生成服务");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        serviceBean = (ServiceBean) getIntent().getSerializableExtra(SERVICE_BEAN);
        rule = (TextView) findViewById(R.id.rule);
        sure_number = (EditText) findViewById(R.id.sure_number);
        sure_number_day = (EditText) findViewById(R.id.sure_number_day);
        create_package = (Button) findViewById(R.id.create_package);
        String address = StringUtils.jointAddress(serviceBean.getCategory(), serviceBean.getAddressAll(),serviceBean.getGender());
        rule.setText(address);
        create_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().createServiceForOrienteering(serviceBean,sure_number_day.getText().toString(),sure_number.getText().toString());
            }
        });

    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void finishAct() {
        this.finish();
    }

}
