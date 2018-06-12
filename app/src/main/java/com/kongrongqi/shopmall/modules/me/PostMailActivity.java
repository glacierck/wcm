package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.me.IView.IPostMailView;
import com.kongrongqi.shopmall.modules.me.bean.UserOrderBill;
import com.kongrongqi.shopmall.modules.me.presenter.PostMailPresenter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.SnakebarTost;
import com.kongrongqi.shopmall.utils.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 14:28
 * 作者:penny
 */
public class PostMailActivity extends BaseMVPActivity<PostMailPresenter> implements IPostMailView, View.OnClickListener {

    private TextView mTitle;
    private EditText mCompanyName;
    private EditText mName;
    private EditText mPhone;
    private TextView mAddress;
    private Button mSubmit;
    private TextView mPickerAddress;
    private TextView totalMoney;

    private List<IvoiceModel> ivoiceModels;
    public static String IVOICE_MODEL = "IvoiceModel";
    private TextView content;
    private String orderPrice;


    public static void lunch(Context context, List<IvoiceModel> ivoiceModels) {
        Intent intent = new Intent(context, PostMailActivity.class);
        intent.putExtra(IVOICE_MODEL, (Serializable) ivoiceModels);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activty_post_mail;
    }

    @Override
    protected PostMailPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new PostMailPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return PostMailActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.open_invoice);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ivoiceModels = (List<IvoiceModel>) getIntent().getSerializableExtra(IVOICE_MODEL);
        mCompanyName = (EditText) findViewById(R.id.invoice_company_name);//公司抬头
        totalMoney = (TextView) findViewById(R.id.invoice_money);//价格
        content = (TextView) findViewById(R.id.content);//服务内容
        mName = (EditText) findViewById(R.id.invoice_receiver_name);//收件人
        mPhone = (EditText) findViewById(R.id.addressee_phone);//电话
        mPickerAddress = (TextView) findViewById(R.id.invoice_address_info); //地址 省市区
        mAddress = (TextView) findViewById(R.id.address_details);//详细地址
        mSubmit = (Button) findViewById(R.id.submit);
        mPickerAddress.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        getPresenter().initJsonData();
        orderPrice = getPresenter().getOrderPrice(ivoiceModels) + "";
        totalMoney.setText( StringUtils.jointStr(orderPrice));

    }

    public UserOrderBill getSubmitpPram() {
        UserOrderBill userOrderBill = new UserOrderBill();
        String companyName = mCompanyName.getText().toString();//公司抬头
        String money = totalMoney.getText().toString();//价格
        String content1 = content.getText().toString();//发票内容
        String name = mName.getText().toString();//收件人
        String phone = mPhone.getText().toString();//电话
        String pickerAddress = mPickerAddress.getText().toString();//地址 省市区
        String address = mAddress.getText().toString();//详细地址
        userOrderBill.setCompany(companyName);//公司抬头
        userOrderBill.setPrice(orderPrice);
        userOrderBill.setReceivePhone(phone);
        userOrderBill.setAddress(address);
        userOrderBill.setBillContent(content1);
        userOrderBill.setProvince(province);
        userOrderBill.setCity(city);
        userOrderBill.setDistrict(district);
        userOrderBill.setReceiver(name);
        userOrderBill.setOrderId(getPresenter().getOrderId(ivoiceModels));//订单id

        return userOrderBill;
    }


    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invoice_address_info:
                getPresenter().showPikerView();
                break;
            case R.id.submit:
                getPresenter().requestSubmit(getSubmitpPram());
                break;
        }
    }

    @Override
    public void showToast(String string) {
        SnakebarTost.makeSnackBarRed(mSubmit, string);
    }

    public String province;
    public String city;
    public String district;


    @Override
    public void chooseText(String text, String province, String city, String district) {
        if (null != text && text.length() != 0)
            mPickerAddress.setText(text);

        this.province = province;
        this.city = city;
        this.district = district;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onUIDestory();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return false;
    }

    @Override
    public void finishAt() {
        HaveOpenInvoiceActivity.lunch(this);
        finish();
    }
}
