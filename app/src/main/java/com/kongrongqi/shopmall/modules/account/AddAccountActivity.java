package com.kongrongqi.shopmall.modules.account;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.IAddAccountView;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.AddAccountPresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.JumpEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class AddAccountActivity extends BaseMVPActivity<AddAccountPresenter> implements IAddAccountView {

    private FrameLayout fl_count;
    private Button replace_enter;
    private TextView one;
    private TextView two;
    private TextView three;
    public static String DEVICEWECHAT = "DeviceWechat";
    private DeviceWechat deviceWechat;
    private EditText wechatNo;
    private EditText password;
    private String machinecode;
    private View inflate2;
    private View inflate3;
    private View inflate;
    private View[] views;

    public static int ADD_OR_EDIT=1;//添加
    public static int CHANGE_ACCOUNT=2;//我要换号


    public static String ADD_ACCOUNT_TITLE="添加账号";//添加
    public static String CHANGE_ACCOUNT_TITLE="我要换号";//我要换号

    private  static  String ACCOUNT_TITLE="title";

    public static String OPT_TYPE="optType";//optType 操作类型 1:初次审核 2：重新审核
    private int optType;
    private CheckBox et_password_icon;
    private String account_title;
    private TextView tesp3;
    private LinearLayout ll_change_hint;
    private LinearLayout ll_change_hint2;

    public static void lunch(Context context,DeviceWechat deviceWechat,int optType,String title) {
        Intent intent = new Intent(context, AddAccountActivity.class);
        intent.putExtra(DEVICEWECHAT,deviceWechat);
        intent.putExtra(OPT_TYPE,optType);
        intent.putExtra(ACCOUNT_TITLE,title);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_add_account;
    }

    @Override
    protected AddAccountPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AddAccountPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    private int currentProgress;


    @Override
    public void setToolBar() {
        super.setToolBar();
        title.setVisibility(View.VISIBLE);
//        title.setText(account_title);
        title.setText(getResources().getString(R.string.add_account_set1));
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        deviceWechat = (DeviceWechat) getIntent().getSerializableExtra(DEVICEWECHAT);
        optType = getIntent().getIntExtra(OPT_TYPE,ADD_OR_EDIT);
        account_title = getIntent().getStringExtra(ACCOUNT_TITLE);


        currentProgress = 1;
        fl_count = (FrameLayout) findViewById(R.id.fl_count);
        inflate = getLayoutInflater().inflate(R.layout.view_add_account_step1, null);
        inflate2 = getLayoutInflater().inflate(R.layout.view_add_account_step2, null);
        inflate3 = getLayoutInflater().inflate(R.layout.view_add_account_step3, null);

        views = new View[]{inflate,inflate2,inflate3};

        fl_count.addView(inflate);
        replace_enter = (Button) findViewById(R.id.replace_enter);


        wechatNo = (EditText) inflate2.findViewById(R.id.wechatNo);
        et_password_icon = (CheckBox) inflate2.findViewById(R.id.et_password_icon);

        password = (EditText) inflate2.findViewById(R.id.et_password);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);

        tesp3 = (TextView) findViewById(R.id.tesp3);

        ll_change_hint = (LinearLayout) inflate3.findViewById(R.id.ll_change_hint);
        ll_change_hint2 = (LinearLayout) inflate3.findViewById(R.id.ll_change_hint2);

        if(optType==ADD_OR_EDIT){ //添加 或未使用服务 换号
            tesp3.setText("等待审核");
            ll_change_hint2.setVisibility(View.VISIBLE);
            ll_change_hint.setVisibility(View.GONE);

        }else if(optType==CHANGE_ACCOUNT){ //使用服务后换号
            tesp3.setText("换号成功");
            ll_change_hint2.setVisibility(View.GONE);
            ll_change_hint.setVisibility(View.VISIBLE);
        }

        replace_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentProgress==1){
                    addSuccessViewRefrash(2);
                }else if(currentProgress==2){
                    String wechat_No = wechatNo.getText().toString();
                    String pass_word = password.getText().toString();

                    if(optType==ADD_OR_EDIT){ //添加 或未使用服务 换号
                        getPresenter().addAccount(deviceWechat,wechat_No,optType,pass_word);
                    }else if(optType==CHANGE_ACCOUNT){ //使用服务后换号
                        getPresenter().replaceUserAccount(deviceWechat,deviceWechat.getWechatNo(),wechat_No,pass_word);
                    }
                }else{
                    finishAcResult();
                }
            }
        });

        et_password_icon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                password.setSelection(password.getText().length());
            }
        });
    }


    @Override
    public void addSuccessViewRefrash(int currentProgress) {
        fl_count.removeAllViews();
        fl_count.addView(views[currentProgress-1]);
        replace_enter.setText(currentProgress==3?"确定":"下一步");
        press123(currentProgress);
        this.currentProgress=currentProgress;
        getPresenter().dismissDialog();
    }

    @Override
    public void finishAct() {
        this.finish();
    }

    @Override
    public void finishAcResult(){
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_ACCOUNT_FRG));
        ContainerActivity.lunch(getContext());
//        Intent intent = new Intent();
//        setResult(RESULT_OK, intent);
//        this.finish();
    }


    @Override
    public void press123(int step) {
        switch (step){
            case 1:
                one.setBackgroundResource(R.drawable.shape_add_account_press);
                two.setBackgroundResource(R.drawable.shape_add_account_normal);
                three.setBackgroundResource(R.drawable.shape_add_account_normal);

                if(optType==ADD_OR_EDIT){ //添加 或未使用服务 换号
                    title.setText(getResources().getString(R.string.add_account_set1));
                }else if(optType==CHANGE_ACCOUNT){ //使用服务后换号
                    title.setText(getResources().getString(R.string.add_account_set1));
                }

                break;
            case 2:
                replace_enter.setText("提交");
                one.setBackgroundResource(R.drawable.shape_add_account_normal);
                two.setBackgroundResource(R.drawable.shape_add_account_press);
                three.setBackgroundResource(R.drawable.shape_add_account_normal);

                if(optType==ADD_OR_EDIT){ //添加 或未使用服务 换号
                    title.setText(getResources().getString(R.string.add_account_set2));
                }else if(optType==CHANGE_ACCOUNT){ //使用服务后换号
                    title.setText(getResources().getString(R.string.add_account_set2));
                }
                break;
            case 3:
                one.setBackgroundResource(R.drawable.shape_add_account_normal);
                two.setBackgroundResource(R.drawable.shape_add_account_normal);
                three.setBackgroundResource(R.drawable.shape_add_account_press);
                if(optType==ADD_OR_EDIT){ //添加 或未使用服务 换号
                    title.setText(getResources().getString(R.string.add_account_set3));
                }else if(optType==CHANGE_ACCOUNT){ //使用服务后换号
                    title.setText("换号成功");
                }
                break;
        }
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }
}
