package com.kongrongqi.shopmall.modules.service;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.modules.model.SaleModel;
import com.kongrongqi.shopmall.modules.service.IView.IPayView;
import com.kongrongqi.shopmall.modules.service.presenter.PayPresenter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class PayActivity extends BaseMVPActivity<PayPresenter> implements IPayView, View.OnClickListener, SmoothCheckBox.OnCheckedChangeListener {

    public static final String TAG = "PayActivity";
    private Button mPay;
    private LinearLayout mReceiveAddress;
    private TextView mServiceNamePay;
    private LinearLayout mLeftButton;
    private SmoothCheckBox mWeChatPay;
    private SmoothCheckBox mAliPay;
    private OrderNumRequest mResult;
    /**
     * 支付类型
     */
    public int type = 0x111;
    private TextView mAddress;
    private TextView mReceive;
    private LinearLayout mAddressRootView;
    private boolean mIsvisible;
    private String addressId;//收货地址id
    private TextView mMoney;
    private TextView payTotalMoney;
    private ViewStub mViewStub;

    public static void lunch(Context context, OrderNumRequest orderInfo, boolean isVisible) {
        Intent intent = new Intent(context, PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constans.PAY, orderInfo);
        bundle.putBoolean(Constans.IS_VISIBLE, isVisible);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_pay;
    }

    @Override
    protected PayPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new PayPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.online_pay);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mPay = (Button) findViewById(R.id.pay_pay);
        mReceiveAddress = (LinearLayout) findViewById(R.id.ll_3);
        mReceive = (TextView) findViewById(R.id.pay_tv_address);
        mWeChatPay = (SmoothCheckBox) findViewById(R.id.we_chat_pay);
        mAliPay = (SmoothCheckBox) findViewById(R.id.ali_pay);
        mAddress = (TextView) findViewById(R.id.pay_service_address);
        payTotalMoney = (TextView) findViewById(R.id.pay_money);
        mViewStub = (ViewStub) findViewById(R.id.viewStub);
        initResult();
        mPay.setOnClickListener(this);
        mReceiveAddress.setOnClickListener(this);
        mWeChatPay.setOnCheckedChangeListener(this);
        mAliPay.setOnCheckedChangeListener(this);
        //默认选中
        mAliPay.setChecked(true);
    }

    private void initResult() {
        mResult = (OrderNumRequest) mBundle.getSerializable(Constans.PAY);
        mIsvisible = mBundle.getBoolean(Constans.IS_VISIBLE);
        if (mIsvisible) {
            getPresenter().requestAddress();
            mReceiveAddress.setVisibility(View.VISIBLE);
            mReceive.setVisibility(View.VISIBLE);
        }
        getPresenter().showDifferentUI(mResult.getType());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_pay:
                payType();
                break;
            case R.id.ll_3:
                if (mIsvisible) {
                    ReceiverAddressActivity.lunch(this);
                }
                break;
        }
    }

    /**
     * 支付类型
     */
    private void payType() {
        if (mIsvisible) {
            if (TextUtils.isEmpty(mAddress.getText().toString()) && TextUtils.isEmpty(addressId)) {
                showLongToast("请填写收件地址");
                return;
            }
            mResult.setReceiveAddressId(addressId);
            mResult.setReceiveAddress(mAddress.getText().toString());
        }

        if (type == mPresenter.WECHATPAY) {
            getPresenter().requestPay(PayActivity.this, mPresenter.WECHATPAY, mResult);
        } else if (type == mPresenter.ALIPAY) {
            getPresenter().requestPay(PayActivity.this, mPresenter.ALIPAY, mResult);
        } else {
            showLongToast(getString(R.string.pay_is_null));
        }
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        if (checkBox.getId() == R.id.ali_pay) {
            if (isChecked) {
                type = mPresenter.ALIPAY;
                Logger.d(TAG, "支付宝");
//                mWeChatPay.setChecked(false);
            } else {
                type = 0x111;
                Logger.d(TAG, "支付宝 取消");
            }

        } else if (checkBox.getId() == R.id.we_chat_pay) {
            if (isChecked) {
                type = mPresenter.WECHATPAY;
                Logger.d(TAG, "微信");
//                mAliPay.setChecked(false);
            } else {
                type = 0x111;
                Logger.d(TAG, "微信 取消");
            }
        }
    }

    @Override
    public void jumpNotUse() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_NOT_USE_FRG));
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_TASK_NO_USER));
        ContainerActivity.lunch(getContext());
    }

    @Override
    public void jumpAccount() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
        ContainerActivity.lunch(getContext());
    }

    @Override
    public void jumpAccountNotUse() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_NOT_USE));
        finishAt();
    }



    @Override
    public void showSystemAddFriendUI() {
        mViewStub.setLayoutResource(R.layout.viewstub_list);
        View list = mViewStub.inflate();
        XRecyclerView recyclerView = (XRecyclerView) list.findViewById(R.id.xrecyclerView);
        getPresenter().showSaleListView(mResult.getType(), recyclerView);
    }

    @Override
    public void showCommonUI() {
        mViewStub.setLayoutResource(R.layout.viewstub_common);
        View commonView = mViewStub.inflate();
        mServiceNamePay = (TextView) commonView.findViewById(R.id.pay_service_name);
        mMoney = (TextView) commonView.findViewById(R.id.pay_service_money);
        String fansTypeName = mResult.getFansTypeName();
        String price = mResult.getPrice();
        String serviceName = mResult.getName();
        if (!TextUtils.isEmpty(fansTypeName)) {
            mServiceNamePay.setText("  " + fansTypeName);
        } else {
            mServiceNamePay.setText(serviceName + "  " + fansTypeName);
        }
        String joinPrice = StringUtils.jointStr(price);
        mMoney.setText(joinPrice);
        payTotalMoney.setText(joinPrice);
    }

    @Override
    public void updatePrice(SaleModel pSaleModel) {
        if (pSaleModel != null) {
            Logger.d(TAG, "pSaleModel:" + pSaleModel.toString());
            payTotalMoney.setText("¥" + pSaleModel.getServicePrice());
            mResult.setExpireDays(StringUtils.valueString(mResult.getDuration(), pSaleModel.getServiceDuration()));
            mResult.setPrice(pSaleModel.getServicePrice() + "");
            mResult.setDuration(pSaleModel.getServiceDuration() + "");
        } else {
            payTotalMoney.setText("¥0.00");
            mResult.setPrice("");
        }
    }

    @Subscribe
    public void onEventMainThread(AddressModel event) {
        setDefault(event);
    }


    @Override
    public void setDefault(AddressModel addressModel) {
        if (null != addressModel) {
            addressId = addressModel.getId();
            String address = StringUtils.jointAddress(addressModel.getProvince(),
                    addressModel.getCity(), addressModel.getDistrict(), addressModel.getAddress());
            mAddress.setText(address);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void finishAt() {
        finish();
    }
}
