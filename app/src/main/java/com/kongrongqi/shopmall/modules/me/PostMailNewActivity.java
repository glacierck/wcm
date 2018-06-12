package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.me.IView.IPostMailNewView;
import com.kongrongqi.shopmall.modules.me.bean.UserOrderBill;
import com.kongrongqi.shopmall.modules.me.presenter.PostMailNewPresenter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.CommonUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;
import com.kongrongqi.shopmall.wedget.materialspinner.MaterialSpinner;

import java.io.Serializable;
import java.util.List;

/**
 * 新发票
 */
public class PostMailNewActivity extends BaseMVPActivity<PostMailNewPresenter> implements IPostMailNewView {

    public static String IVOICE_MODEL = "IvoiceModel";
    private RadioGroup rg_type;
    private FrameLayout fl_count;
    private View electronicView;
    private View paperView;

    private int invoiceType = 1;//发票类型
    private EditText invoice_company_name;
    private EditText taxpayer;
    private EditText registered_address;
    private EditText registered_phone;
    private EditText bank;
    private EditText bank_number;
    private MaterialSpinner invoice_content;
    private EditText invoice_money;
    private List<IvoiceModel> ivoiceModels;
    private EditText name;
    private EditText phone;
    private TextView address;
    private EditText address_details;
    private EditText remarks;
    private EditText email;
    private EditText remarks1;
    private Button submit;
    private TextView mExplain;
    private TextView mElectronicExplain;


    public static void lunch(Context context) {
        Intent intent = new Intent(context, PostMailNewActivity.class);
        context.startActivity(intent);
    }

    public static void lunch(Context context, List<IvoiceModel> ivoiceModels) {
        Intent intent = new Intent(context, PostMailNewActivity.class);
        intent.putExtra(IVOICE_MODEL, (Serializable) ivoiceModels);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activty_post_mail_new;
    }

    @Override
    protected PostMailNewPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new PostMailNewPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return PostMailNewActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.open_invoice);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getPresenter().getAddressJsonData();//初始化选择地区控价json数据
        ivoiceModels = (List<IvoiceModel>) getIntent().getSerializableExtra(IVOICE_MODEL);
        rg_type = (RadioGroup) findViewById(R.id.rg_type);
        fl_count = (FrameLayout) findViewById(R.id.fl_count); //类型不同，底部布局不同
        initElectronic();
        initPaper();
        initCompanyInfo();

        showTypeView(invoiceType);//默认电子发票
        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.electronic_invoice:
                        invoiceType = 1;
                        break;
                    case R.id.paper_invoice:
                        invoiceType = 2;
                        break;
                }
                showTypeView(invoiceType);
            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().requestSubmit(getSubmitpPram());
            }
        });
    }

    private void initCompanyInfo() {
        //公司抬头
        invoice_company_name = (EditText) findViewById(R.id.invoice_company_name);
        //纳税人识别号
        taxpayer = (EditText) findViewById(R.id.taxpayer);
        //注册地址
        registered_address = (EditText) findViewById(R.id.registered_address);
        //注册电话
        registered_phone = (EditText) findViewById(R.id.registered_phone);
        //开户行
        bank = (EditText) findViewById(R.id.bank);
        //账号
        bank_number = (EditText) findViewById(R.id.bank_number);
        //发票内容
        invoice_content = (MaterialSpinner) findViewById(R.id.invoice_content);
        //发票金额
        invoice_money = (EditText) findViewById(R.id.invoice_money);
        invoice_money.setText(getPresenter().getOrderPrice(ivoiceModels) + "");

        getPresenter().initSpinner(invoice_content);
    }

    /**
     * 初始化纸质发票布局
     */
    private void initPaper() {

        paperView = getLayoutInflater().inflate(R.layout.include_conpany_info_zhizhi, null);
        //收件人
        name = (EditText) paperView.findViewById(R.id.name);
        //收件人电话
        phone = (EditText) paperView.findViewById(R.id.phone);
        //所在地区
        address = (TextView) paperView.findViewById(R.id.address);
        //详细地址
        address_details = (EditText) paperView.findViewById(R.id.address_details);
        //备注说明
        remarks = (EditText) paperView.findViewById(R.id.remarks);
        //电子说明
        mExplain = (TextView) paperView.findViewById(R.id.open_invoice_explain);

        mExplain.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            StyleDialog.showInvoiceExplain(PostMailNewActivity.this);
                                        }
                                    }
        );

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isSHowKeyboard(PostMailNewActivity.this, address)) {
                    CommonUtils.hideSoftInput(PostMailNewActivity.this, address);
                }
                getPresenter().showPicker();
            }
        });
    }

    /**
     * 初始化电子发票布局
     */
    private void initElectronic() {
        electronicView = getLayoutInflater().inflate(R.layout.include_conpany_info_dianzi, null);
        //邮箱
        email = (EditText) electronicView.findViewById(R.id.email);
        //备注说明
        remarks1 = (EditText) electronicView.findViewById(R.id.remarks);

        mElectronicExplain = (TextView) electronicView.findViewById(R.id.open_bill_explain);
        mElectronicExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StyleDialog.showInvoiceExplain(PostMailNewActivity.this);
            }
        });
    }

    public UserOrderBill getSubmitpPram() {
        String companyName = "";//公司抬头
        String taxpayer = "";//纳税人识别号
        String registered_address = "";//注册地址
        String registered_phone = "";//注册电话
        String bank = "";//开户行
        String bank_number = "";//账号
        String invoice_content = "";//发票内容
        String invoice_money = "";//发票金额
        //纸质发票
        String name = "";//收件人
        String phone = "";//收件人电话
        String address = "";//所在地区
        String address_details = "";//详细地址
        String remarks = "";//备注说明
        //电子发票
        String email = "";//邮箱
//        String remarks1 = "";//备注说明

        UserOrderBill userOrderBill = new UserOrderBill();
        companyName = invoice_company_name.getText().toString();//公司抬头
        taxpayer = this.taxpayer.getText().toString();//纳税人识别号
        registered_address = this.registered_address.getText().toString();//注册地址
        registered_phone = this.registered_phone.getText().toString();//注册电话
        bank = this.bank.getText().toString();//开户行
        bank_number = this.bank_number.getText().toString();//账号
        invoice_content = this.invoice_content.getText().toString();//发票内容
        invoice_money = this.invoice_money.getText().toString();//发票金额

        if (invoiceType == 2) {//纸质发票

            name = this.name.getText().toString();//收件人
            phone = this.phone.getText().toString();//收件人电话
            address = this.address.getText().toString();//所在地区
            address_details = this.address_details.getText().toString();//详细地址
            remarks = this.remarks.getText().toString();//备注说明
        } else {//电子发票
            email = this.email.getText().toString();//邮箱
            remarks = this.remarks1.getText().toString();//备注说明
        }

        userOrderBill.setType(invoiceType);
        userOrderBill.setCompany(companyName);//公司抬头
        userOrderBill.setTaxpayerRegNo(taxpayer);//纳税人识别号
        userOrderBill.setRegAddress(registered_address);//注册地址
        userOrderBill.setRegPhone(registered_phone);//注册电话
        userOrderBill.setOpeningBank(bank);//开户行
        userOrderBill.setBank_account(bank_number);//账号
        userOrderBill.setBillContent(invoice_content);//发票内容
        userOrderBill.setPrice(invoice_money);//发票金额
        //纸质发票
        userOrderBill.setReceiver(name);//收件人
        userOrderBill.setReceivePhone(phone);//收件人电话
        userOrderBill.setProvince(province);
        userOrderBill.setCity(city);
        userOrderBill.setDistrict(district);
        userOrderBill.setAddress(address_details);//详细地址
//        userOrderBill.setRemark(remarks);//备注说明
        //电子发票
        userOrderBill.setEmail(email);//邮箱
        userOrderBill.setRemark(remarks);//详细地址

        userOrderBill.setOrderId(getPresenter().getOrderId(ivoiceModels));//订单id
        return userOrderBill;
    }


    public String province;
    public String city;
    public String district;

    @Override
    public void chooseText(String address, String province, String city, String district) {
        if (null != address && address.length() != 0)
            this.address.setText(address);

        this.province = province;
        this.city = city;
        this.district = district;
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.showMessage(this, toast);
    }

    private void showTypeView(int type) {
        fl_count.removeAllViews();
        if (type == 1) {//电子发票
            fl_count.addView(electronicView);
        } else {//纸质发票
            fl_count.addView(paperView);
        }
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

    @Override
    public void spinnerChooseText(String pItem) {
        invoice_content.setText(pItem);
    }
}
