package com.kongrongqi.shopmall.modules.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.me.IView.IOpenIvoiceView;
import com.kongrongqi.shopmall.modules.me.presenter.OpenIvoicePresenter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

import java.util.List;

/**
 * 创建日期：2017/5/19 0019 on 18:21
 * 作者:penny
 */
public class OpenIvoiceActivity extends BaseMVPActivity<OpenIvoicePresenter> implements
        IOpenIvoiceView, SmoothCheckBox.OnCheckedChangeListener, View.OnClickListener {
    public static final String TAG = "OpenIvoiceActivity";
    private XRecyclerView mXRecyclerView;
    private Button mNext;
    private TextView mChooseText;
    private SmoothCheckBox mCheckBox;
    public static final int BILL = 0; //未开票

    public static void lunch(Context context) {

        Intent intent = new Intent(context, OpenIvoiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activty_not_open_invoice;
    }

    @Override
    protected OpenIvoicePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new OpenIvoicePresenter(BILL) :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        right_tv2.setVisibility(View.VISIBLE);
        right_tv2.setText(R.string.hava_invoice);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HaveOpenInvoiceActivity.lunch(OpenIvoiceActivity.this);
            }
        });
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.not_invoice);
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        mXRecyclerView = (XRecyclerView) findViewById(R.id.xrecyclerView);
        mNext = (Button) findViewById(R.id.invoice_next);
        mChooseText = (TextView) findViewById(R.id.invoice_choose_text);
        mCheckBox = (SmoothCheckBox) findViewById(R.id.invoice_all_checkbox);
        mCheckBox.setOnCheckedChangeListener(this);
        mNext.setOnClickListener(this);
        getPresenter().setAdapter(mXRecyclerView);
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        Logger.d(TAG, "==" + isChecked);
        getPresenter().allChecked(isChecked);
    }

    public String price = "0.00";

    @Override
    public void setCheckPice(int count,String price) {
        this.price=price;
        mChooseText.setText(StringUtils.jointStr(price+""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invoice_next:
                List<IvoiceModel> checkIvoice = getPresenter().getCheckIvoice();
                if(checkIvoice.size()>0){
                    PostMailNewActivity.lunch(this,checkIvoice);
                }else{
                    ToastUtil.showMessage(this,"请选择要开票的订单");
                }
                break;
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
    protected void onRestart() {
        super.onRestart();
        mXRecyclerView.refresh();
        mChooseText.setText("0.00");
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_buy_record_hint))
                    .setEmptyImage(R.drawable.empty_open_ivoice)
                    .create()
                    .showEmpty();
        }
    }
}
