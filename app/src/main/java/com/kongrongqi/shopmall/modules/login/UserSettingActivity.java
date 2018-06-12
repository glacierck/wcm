package com.kongrongqi.shopmall.modules.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.ReplaceIMEIActivity;
import com.kongrongqi.shopmall.modules.login.Iview.IUserSettingView;
import com.kongrongqi.shopmall.modules.login.presenter.UserSettingPresenter;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;

/**
 * 创建日期：2017/5/17 0017 on 14:28
 * 作者:penny
 */
public class UserSettingActivity extends BaseMVPActivity<UserSettingPresenter> implements
        IUserSettingView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button mConfirm;
    private EditText mPwd;
    private EditText mConfirmPwd;
    private CheckBox mCheckPwd;
    private CheckBox mCheckPwd2;
    private EditText mName;
    private TextView mTitle;
    private String mResult;
    private FrameLayout name;

    public static void lunch(Context context, String settingPwd) {
        Intent intent = new Intent(context, UserSettingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constans.SETTING_PWD, settingPwd);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_usersetting;
    }

    @Override
    protected UserSettingPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new UserSettingPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return UserSettingActivity.this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
        if (null != mResult) {
            title.setText(R.string.setting_pwd);
        } else {
            title.setText(R.string.account_setting);
        }
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        mConfirm = (Button) findViewById(R.id.userOk);
        mPwd = (EditText) findViewById(R.id.user_pwd);
        mConfirmPwd = (EditText) findViewById(R.id.user_pwd_ok);
        mCheckPwd = (CheckBox) findViewById(R.id.userCbPwd);
        mCheckPwd2 = (CheckBox) findViewById(R.id.userCbPwd2);
        mName = (EditText) findViewById(R.id.user_phone);
        name = (FrameLayout) findViewById(R.id.name);
        GuiBuilder.builder(this)
                .setFirstRegister(true);
        //取值
        getExtra();
        mConfirm.setOnClickListener(this);
        mCheckPwd.setOnCheckedChangeListener(this);
        mCheckPwd2.setOnCheckedChangeListener(this);

    }

    private void getExtra() {
        mResult = mBundle.getString(Constans.SETTING_PWD);
        if (null != mResult) {
            name.setVisibility(View.GONE);
        } else {
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userOk:
                switchOpration();
                break;
        }
    }


    private void switchOpration() {
        if (null == mResult) {
            //注册
            getPresenter().registerUser(mName.getText().toString(), mPwd.getText().toString(), mConfirmPwd.getText().toString());
        } else {
            //修改密码
            getPresenter().updatePasswd(mPwd.getText().toString(), mConfirmPwd.getText().toString());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.userCbPwd:
                isShowPassWD(isChecked, mCheckPwd);
                break;
            case R.id.userCbPwd2:
                isShowPassWD(isChecked, mCheckPwd2);
                break;
        }
    }

    private void isShowPassWD(boolean isChecked, CheckBox checkPwd) {
        if (checkPwd.getId() == R.id.userCbPwd) {
            if (isChecked) {
                mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mPwd.setSelection(mPwd.getText().length());
        } else if (checkPwd.getId() == R.id.userCbPwd2) {
            if (isChecked) {
                mConfirmPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mConfirmPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mConfirmPwd.setSelection(mConfirmPwd.getText().length());
        }
    }

    @Override
    public void addIMEI() {
        ReplaceIMEIActivity.lunch(this, ReplaceIMEIActivity.REGISTER);
//        Intent intent = new Intent(this,ReplaceIMEIActivity.class);
//        intent.putExtra(ReplaceIMEIActivity.TYPE,ReplaceIMEIActivity.REGISTER);
//        startActivityForResult(intent, AccountFragment.ADD_RESULT_CODE);
    }

    @Override
    public void showToast(String string) {
//        SnakebarTost.makeSnackBarRed(mPwd, string);
        ToastUtil.showMessage(this, string);
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
