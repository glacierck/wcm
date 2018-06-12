package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.login.request.EditAddressRequest;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.service.IView.IEditAddressView;
import com.kongrongqi.shopmall.modules.service.presenter.EditAddressPresenter;
import com.kongrongqi.shopmall.utils.CommonUtils;
import com.kongrongqi.shopmall.utils.SnakebarTost;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.StyleDialog;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * 创建日期：2017/5/22 0022 on 14:21
 * 作者:penny
 */
public class EditAddressActivity extends BaseMVPActivity<EditAddressPresenter> implements
        View.OnClickListener, IEditAddressView, StyleDialog.DialogClickListener {

    private EditText mMan;
    private TextView mAddress;
    private EditText mPhone;
    private EditText mDetail;
    private Button mSave;
    public static String ADDRESS_MODEL="AddressModel";
    private AddressModel addressModel;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        context.startActivity(intent);
    }

    public static void lunch(Context context,AddressModel addressModel) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        intent.putExtra(ADDRESS_MODEL,addressModel);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected EditAddressPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new EditAddressPresenter() :
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
        title.setText(R.string.edit_address_title);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        addressModel = (AddressModel) getIntent().getSerializableExtra(ADDRESS_MODEL);

        mMan = (EditText) findViewById(R.id.edit_man);
        mAddress = (TextView) findViewById(R.id.edit_address);
        mPhone = (EditText) findViewById(R.id.edit_phone);
        mDetail = (EditText) findViewById(R.id.edit_address_details);
        mSave = (Button) findViewById(R.id.edit_save);
        mAddress.setOnClickListener(this);
        mSave.setOnClickListener(this);

        if(addressModel!=null){
            setData(addressModel);
        }
        getPresenter().test();
    }


    private void setData(AddressModel addressModel){
        mMan.setText(addressModel.getReceiver());
        String address = StringUtils.jointAddress(addressModel.getProvince(), addressModel.getCity(), addressModel.getDistrict(), "");
        mAddress.setText(address);
        mPhone.setText(addressModel.getReceivePhone());
        mDetail.setText(addressModel.getAddress());
    }


    @Override
    public AddressModel getAddressModel() {
        return addressModel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_address:
                if (CommonUtils.isSHowKeyboard(this, mAddress)) {
                    CommonUtils.hideSoftInput(this, mAddress);
                }
                getPresenter().showPicker();
                break;
            case R.id.edit_save:
                getPresenter().save(mMan.getText().toString(),
                        mAddress.getText().toString(), mPhone.getText().toString(),
                        mDetail.getText().toString());
                break;
        }
    }

    @Override
    public void showToast(String string) {
        ToastUtil.showMessage(this, string);
    }

    @Override
    public void chooseText(String text) {
        if (null != text && text.length() != 0)
            mAddress.setText(text);
    }

    @Override
    public void transmit(AddressModel request) {
        EventBus.getDefault().post(request);
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onUIDestory();
    }

    @Override
    public void onBuy(int position) {
        this.finish();
    }



    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        StyleDialog.showCancelEditService(this, 0, this);
    }

    public void finishActivity(){
        StyleDialog.showCancelEditService(this, 0, this);
    }
}
