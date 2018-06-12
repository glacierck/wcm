package com.kongrongqi.shopmall.modules.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.login.Iview.ILoginUI;
import com.kongrongqi.shopmall.modules.login.presenter.LoginPresenter;
import com.kongrongqi.shopmall.utils.CheckEditForButton;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.SPUtils;
import com.kongrongqi.shopmall.utils.SnakebarTost;
import com.kongrongqi.shopmall.utils.permission.PermissionManager;
import com.kongrongqi.shopmall.utils.permission.Permissions;

/**
 * 创建日期：2017/5/17 0017 on 09:20
 * 作者:penny
 */
public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements ILoginUI,
        View.OnClickListener {


    private Button mEnter;
    private TextView mForget;
    private TextView mRegister;
    private EditText mPhone;
    private EditText mPassWd;
    private CheckBox userCbPwd;
    private LinearLayout ll_login_forget;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new LoginPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return LoginActivity.this;
    }


    @Override
    public void setToolBar() {
        super.setToolBar();
        activityToolbar.setTitle("");
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.kongrongqi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String phone = SPUtils.getString(this, Constans.PHONE, "");
        mEnter = (Button) findViewById(R.id.login_enter);
        mForget = (TextView) findViewById(R.id.login_forget);
        mRegister = (TextView) findViewById(R.id.login_register);
        mPhone = (EditText) findViewById(R.id.login_phone);
        mPassWd = (EditText) findViewById(R.id.login_pass_wd);
        userCbPwd = (CheckBox) findViewById(R.id.userCbPwd);
        ll_login_forget = (LinearLayout) findViewById(R.id.ll_login_forget);

        mRegister.setOnClickListener(this);
        ll_login_forget.setOnClickListener(this);
        mEnter.setOnClickListener(this);
        userCbPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mPassWd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mPassWd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mPassWd.setSelection(mPassWd.getText().length());
            }
        });


        //1.创建工具类对象 设置监听空间
        CheckEditForButton checkEditForButton = new CheckEditForButton(mEnter);

        //2.把所有被监听的EditText设置进去
        checkEditForButton.addEditText(mPhone, mPassWd);

        //3.根据接口回调的方法,分别进行操作
        checkEditForButton.setListener(new CheckEditForButton.EditTextChangeListener() {
            @Override
            public void allHasContent(boolean isHasContent) {
                if (!isHasContent) {
                    mEnter.setBackgroundResource(R.drawable.shape_login_enabled_bg);
                } else {
                    mEnter.setBackgroundResource(R.drawable.select_logout_button_bg);
                }
            }
        });

        setPhone(phone);
    }

    private void setPhone(String phone) {
        if (!TextUtils.isEmpty(phone))
            mPhone.setText(phone);
        if (!TextUtils.isEmpty(mPhone.getText().toString())) ;
//            mPassWd.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_enter:
                getPresenter().login(mPhone.getText().toString(), mPassWd.getText().toString());
                break;
            case R.id.ll_login_forget:
                RegisterActivity.lunch(getApplicationContext(), Constans.FORGET_PWD);
                break;
            case R.id.login_register:
                RegisterActivity.lunch(getApplicationContext(), null);
                break;
        }
    }


    @Override
    public void showToast(String string) {
        SnakebarTost.makeSnackBarBlue(mEnter, string);
    }

    @Override
    public void closeAct() {
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onUIDestory();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!PermissionManager.getInstance().hasPermission(this,
                Permissions.READ_EXTERNAL_STORAGE, Permissions.WRITE_EXTERNAL_STORAGE)) {
            PermissionManager.getInstance()
                    .inActivityPermission(LoginActivity.this,
                            Permissions.READ_EXTERNAL_STORAGE, Permissions.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PermissionManager.REQUEST_MORE_CODE_PERMISSION_ACT) {

            Logger.d("LoginActivity", "onActivityResult=============");
        }
    }
}
