package com.kongrongqi.shopmall.modules.account;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.account.IView.IReplaceIMEIView;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.presenter.ReplaceIMEIPresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;
import com.kongrongqi.shopmall.wedget.guide.GuideView;

import org.w3c.dom.Text;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 创建日期：2017/5/20 0020 on 20:34
 * 作者:penny
 */
public class ReplaceIMEIActivity extends BaseMVPActivity<ReplaceIMEIPresenter> implements IReplaceIMEIView, EasyPermissions.PermissionCallbacks {


    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private Button mPay;
    private Button replace_enter;
    private TextView mTitle;


    public static String TYPE = "Type";
    public static String REPLACE = "replace";
    public static int SCAN = 10001;

    public static String ADD = "add";
    public static String REGISTER = "register"; //注册进来

    public static String LOGIN = "login"; //登录进来

    private String type;
    private TextView machineCode;
    private Device device;
    private ImageView scan;
    private TextView please_text_imei;
    private TextView please_text_IMEI2;
    private TextView please_text_imei3;
    private LinearLayout mImei_root;
    private boolean isFoucus = false;

    public static void lunch(Context context, String type) {
        Intent intent = new Intent(context, ReplaceIMEIActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_replace_imei;
    }

    @Override
    protected ReplaceIMEIPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ReplaceIMEIPresenter() :
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
        title.setText("replace".equals(type) ? "替换设备" : "添加设备");
    }

    @Override
    protected void setNavigationIcon() {
        super.setNavigationIcon();
        if (type.equals(LOGIN)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!getPresenter().isFirst()) {
            Logger.d("isFirst","isFrist"+getPresenter().isFirst());
            showGuide();
            getPresenter().setFirst(true);
        }
    }

    private void showGuide() {
        GuiBuilder.builder(this)
                .showGuiView(
                        mImei_root,
                        true,
                        R.drawable.scanne_2_code,
                        R.drawable.i_know,
                        GuideView.VIEWSTYLE_RECT,
                        GuiBuilder.DETAIL, new GuiBuilder.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                GuiBuilder.builder(ReplaceIMEIActivity.this)
                                        .setDetail(false);
                            }
                        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //添加或替换
        type = getIntent().getStringExtra(TYPE);
        device = (Device) getIntent().getSerializableExtra(TabFragment.DEVICE);
        replace_enter = (Button) findViewById(R.id.replace_enter);
        machineCode = (TextView) findViewById(R.id.machineCode);
        scan = (ImageView) findViewById(R.id.scan);//扫描
        mImei_root = (LinearLayout) findViewById(R.id.imei_root);
        RelativeLayout rl_scan = (RelativeLayout) findViewById(R.id.rl_scan);


        please_text_imei = (TextView) findViewById(R.id.please_text_IMEI);
        please_text_IMEI2 = (TextView) findViewById(R.id.please_text_IMEI2);
        please_text_imei3 = (TextView) findViewById(R.id.please_text_IMEI3);

        switch (type) {
            case "replace":
                please_text_IMEI2.setVisibility(View.VISIBLE);
                please_text_imei3.setVisibility(View.VISIBLE);
                please_text_imei.setText(R.string.please_enter_IMEI_3);
                replace_enter.setText("确定替换");
                replace_enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = machineCode.getText().toString();
                        getPresenter().ConfirmReplace(s, device);
                    }
                });

                break;
            case "add":
                please_text_IMEI2.setVisibility(View.GONE);
                please_text_imei3.setVisibility(View.GONE);
                please_text_imei.setText(R.string.please_enter_IMEI_1);
                replace_enter.setText("确定添加");
                replace_enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = machineCode.getText().toString();
                        getPresenter().ConfirmAdd(s);
                    }
                });
                break;

            case "register":
                please_text_IMEI2.setVisibility(View.GONE);
                please_text_imei3.setVisibility(View.GONE);
                please_text_imei.setText(R.string.please_enter_IMEI_1);
                replace_enter.setText("确定添加");
                replace_enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = machineCode.getText().toString();
                        getPresenter().ConfirmAdd(s);
                    }
                });
                break;

            case "login":
                please_text_IMEI2.setVisibility(View.GONE);
                please_text_imei3.setVisibility(View.GONE);
                please_text_imei.setText(R.string.please_enter_IMEI_1);
                replace_enter.setText("确定添加");
                replace_enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = machineCode.getText().toString();
                        getPresenter().ConfirmAdd(s);
                    }
                });
                break;
        }

        rl_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReplaceIMEIActivity.this, AccountScanActivity.class);
                startActivityForResult(intent, SCAN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SCAN)  { //替换设备返回
            String machineCode = data.getStringExtra(ReplaceIMEIActivity.MACHINE_CODE);
            this.machineCode.setText(machineCode);
        }
    }

    public static String MACHINE_CODE = "machineCode";

    @Override
    public void finishAct(String machineCode) {
        if (type.equals(REGISTER) || type.equals(LOGIN)) { //注册 登录进来的
            type=REGISTER;//登录 首次添加设备 成功后 该页面finish 不会清除用户信息
            ContainerActivity.lunch(this);
        } else {
            Intent intent = new Intent();
            intent.putExtra(MACHINE_CODE, machineCode);
            setResult(RESULT_OK, intent);
        }
        this.finish();
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        if (type.equals(LOGIN)) { //登录进来的  下次重新登录
            getPresenter().clearUser();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }
}
