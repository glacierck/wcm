package com.kongrongqi.shopmall.modules.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.login.Iview.IRegisterView;
import com.kongrongqi.shopmall.modules.login.presenter.RegisterPresenter;
import com.kongrongqi.shopmall.utils.CommonUtils;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SnakebarTost;
import com.kongrongqi.shopmall.utils.ToastUtil;

/**
 * 创建日期：2017/5/17 0017 on 13:14
 * 作者:penny
 */
public class RegisterActivity extends BaseMVPActivity<RegisterPresenter> implements IRegisterView, View.OnClickListener {

    public static final String TAG = "RegisterActivity";
    private EditText mPhone;
    private EditText mVerifyCode;
    private Button mSendCode;
    private Button mConfirm;
    private TextView mTitle;
    private String mForget;

    public static void lunch(Context context, String forgetPwd) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString(Constans.FORGET_PWD, forgetPwd);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new RegisterPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return RegisterActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        if (null == mForget) {
            title.setText(R.string.phone_verify);
        } else {
            title.setText(R.string.find_passwd);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mConfirm = (Button) findViewById(R.id.regitser_ok);
        mPhone = (EditText) findViewById(R.id.register_phone);
        mVerifyCode = (EditText) findViewById(R.id.register_verify_code);
        mSendCode = (Button) findViewById(R.id.register_sendmsg);
        getExtra();
        mSendCode.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    private void getExtra() {
        mForget = (String) mBundle.get(Constans.FORGET_PWD);
        if (null == mForget) {
            mConfirm.setText(R.string.next);
        } else {
            mConfirm.setText(R.string.next);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_sendmsg:
                sendAuthCode();
                break;
            case R.id.regitser_ok:
                if (null == mForget) {
                    //正常注册
                    getPresenter().checkPhoneAndAuthCode(mPhone.getText().toString(), mVerifyCode.getText().toString(), null);
                } else {
                    //重置密码
                    getPresenter().checkPhoneAndAuthCode(mPhone.getText().toString(), mVerifyCode.getText().toString(), mForget);
                }
                break;
        }
    }

    private void sendAuthCode() {
        if (CommonUtils.isSHowKeyboard(this, mVerifyCode)) {
            CommonUtils.hideSoftInput(this, mVerifyCode);
            getPresenter().checkPhone(mPhone.getText().toString(),null == mForget);
        } else {
            getPresenter().checkPhone(mPhone.getText().toString(),null == mForget);
        }
    }

    @Override
    public void showToast(String string) {
//        SnakebarTost.makeSnackBarRed(mTitle, string);
        ToastUtil.showMessage(this,string);
    }

    @Override
    public void showCountTime(boolean canClick, String timeStr) {
        if (!canClick) {
            mSendCode.setText(timeStr);
            mSendCode.setClickable(false);
        } else {
            mSendCode.setText(R.string.authcode_resend);
            mSendCode.setClickable(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onUIPause();
    }

    @Override
    public void finishAct() {
        this.finish();
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }
}
