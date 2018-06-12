package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.UpLoadExcelModel;
import com.kongrongqi.shopmall.modules.service.IView.IServiceSetView;
import com.kongrongqi.shopmall.modules.service.presenter.ServiceSetPresenter;
import com.kongrongqi.shopmall.modules.service.presenter.UpLoadPresenter;
import com.kongrongqi.shopmall.utils.CommonUtils;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/31 0031 on 16:22
 * 作者:penny
 */
public class ServiceSetActivity extends BaseMVPActivity<ServiceSetPresenter> implements IServiceSetView {

    private ServiceBean mServiceModel;
    private EditText service_number;
    private EditText service_name;
    private TextView tatal_data;
    private Button create_service;
    public static String UPLOAD_EXCEL_MODEL = "UpLoadExcelModel";
    private UpLoadExcelModel upLoadExcelModel;
    private TextView max_hint;

    public static void lunch(Context context, UpLoadExcelModel model, ServiceBean pBean) {
        Intent intent = new Intent(context, ServiceSetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constans.SERVICE_BEAN,pBean);
        bundle.putSerializable(UPLOAD_EXCEL_MODEL,model);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_service_set;
    }

    @Override
    protected ServiceSetPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ServiceSetPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return ServiceSetActivity.this;
    }
    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.service_set);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mServiceModel = (ServiceBean) mBundle.getSerializable(Constans.SERVICE_BEAN);
        upLoadExcelModel = (UpLoadExcelModel) mBundle.getSerializable(UPLOAD_EXCEL_MODEL);

        service_number = (EditText) findViewById(R.id.service_number);//订单份数
        int strToInt = StringUtils.getStrToInt(upLoadExcelModel.getDuration());
        CommonUtils.setRegion(service_number,1,strToInt>0?strToInt:1);//设置输入范围
        service_name = (EditText) findViewById(R.id.service_name);//服务名称
        service_name.setHint(strToInt>0?strToInt+"":"1");
        tatal_data = (TextView) findViewById(R.id.tatal_data);//数据总量
        tatal_data.setText(upLoadExcelModel!=null? upLoadExcelModel.getTotal()+"":"0");
        create_service = (Button) findViewById(R.id.create_service);
        max_hint = (TextView) findViewById(R.id.max_hint);
        max_hint.setText("注意：最大可拆分"+(strToInt>0?strToInt:1)+"份");

        create_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = service_name.getText().toString();
                String serviceNumber = service_number.getText().toString();
                getPresenter().payService(mServiceModel,upLoadExcelModel,serviceName,serviceNumber);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }
}
