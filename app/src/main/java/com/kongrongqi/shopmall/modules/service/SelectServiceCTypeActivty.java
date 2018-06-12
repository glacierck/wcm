package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.model.ServiceListModel;
import com.kongrongqi.shopmall.modules.service.IView.IJoinView;
import com.kongrongqi.shopmall.modules.service.IView.ISelectServiceCtypeView;
import com.kongrongqi.shopmall.modules.service.presenter.JoinGrounpPresenter;
import com.kongrongqi.shopmall.modules.service.presenter.SelectServiceCtypePresenter;
import com.kongrongqi.shopmall.utils.CommonUtils;
import com.kongrongqi.shopmall.utils.StringUtils;

import java.io.Serializable;

/**
 * 创建日期：2017/5/25 0025 on 18:20
 * 作者:penny
 */
public class SelectServiceCTypeActivty extends BaseMVPActivity<SelectServiceCtypePresenter> implements ISelectServiceCtypeView{

    private XRecyclerView mRecyclerView;
    public static  String SERVICE_BEAN  = "ServiceListModel";
    private ServiceBean serviceBean;
    private TextView pay_money;
    private Button pay_pay;


    public static void lunch(Context context, ServiceBean serviceBean) {
        Intent intent = new Intent(context, SelectServiceCTypeActivty.class);
        intent.putExtra(SERVICE_BEAN,serviceBean);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_servicec_type;
    }

    @Override
    protected SelectServiceCtypePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new SelectServiceCtypePresenter() :
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
        title.setText("选择服务类型");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        serviceBean = (ServiceBean)getIntent().getSerializableExtra(SERVICE_BEAN);
        mRecyclerView = (XRecyclerView) findViewById(R.id.xRecyclerView);
        pay_money = (TextView) findViewById(R.id.pay_money);
        pay_pay = (Button) findViewById(R.id.pay_pay);

        getPresenter().setAdapter(mRecyclerView, serviceBean);

        pay_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().buyGroupService();
            }
        });
    }


    @Override
    public void setCheckPice(int count,Double price) {
        pay_money.setText(StringUtils.jointStr(price+""));
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
