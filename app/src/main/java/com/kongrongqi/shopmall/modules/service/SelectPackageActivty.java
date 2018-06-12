package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.IView.ISelectPackageView;
import com.kongrongqi.shopmall.modules.service.presenter.SelectPackagePresenter;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.materialspinner.MaterialSpinner;

/**
 * 创建日期：2017/5/25 0025 on 18:20
 * 作者:penny
 */
public class SelectPackageActivty extends BaseMVPActivity<SelectPackagePresenter> implements ISelectPackageView {

    private XRecyclerView mRecyclerView;
    public static String SERVICE_BEAN = "ServiceListModel";
    private ServiceBean serviceBean;
    private TextView pay_money;
    private Button pay_pay;
    private TextView determine_area;
    private MaterialSpinner determine_industry;
    private RadioGroup rg_bnt;

    private String sex = "";
    private String category = "";
    private String address = "";
    private String addressAll = "";

    public static void lunch(Context context, ServiceBean serviceBean) {
        Intent intent = new Intent(context, SelectPackageActivty.class);
        intent.putExtra(SERVICE_BEAN, serviceBean);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_package;
    }

    @Override
    protected SelectPackagePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new SelectPackagePresenter() :
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
        title.setText("选择定向包");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getPresenter().getAddressJsonData();//初始化选择地区控价json数据
        serviceBean = (ServiceBean) getIntent().getSerializableExtra(SERVICE_BEAN);
        //行业
        determine_industry = (MaterialSpinner) findViewById(R.id.determine_industry);
        getPresenter().queryPageForUserDict(determine_industry);
        determine_industry.setHint("请选择");
        //区域
        determine_area = (TextView) findViewById(R.id.determine_area);
        determine_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().showPicker();
            }
        });

        rg_bnt = (RadioGroup) findViewById(R.id.rg_bnt);
        rg_bnt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.non:
                        sex = "";
                        break;
                    case R.id.man:
                    case R.id.wo_man:
                        sex = radioButton.getText().toString();
                        break;
                }
            }
        });

        Button get_package = (Button) findViewById(R.id.get_package);

        get_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(address) || TextUtils.isEmpty(category)) {
                    ToastUtil.showMessage(getContext(), "请选择定向条件");
                    return;
                }
                serviceBean.setGender(sex);
                serviceBean.setAddress(address);
                serviceBean.setAddressAll(addressAll);
                serviceBean.setCategory(category);
                CreatePackageActivty.lunch(getContext(), serviceBean);
            }
        });
    }

    @Override
    public void spinnerChooseText(String pItem) {
        category = pItem;
        determine_industry.setText(pItem);

    }

    @Override
    public void chooseText(String address, String mProvince, String city) {
        if (!TextUtils.isEmpty(mProvince)) {
            this.address = mProvince;
        }
        if (!TextUtils.isEmpty(city)) {
            this.address = city;
        }
        addressAll = address;
        determine_area.setText(address);
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
