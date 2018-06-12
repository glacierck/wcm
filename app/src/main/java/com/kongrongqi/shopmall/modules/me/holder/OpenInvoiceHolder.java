package com.kongrongqi.shopmall.modules.me.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/19 0019 on 17:27
 * 作者:penny
 */
public class OpenInvoiceHolder extends RecyclerView.ViewHolder {


    public TextView mMoney;
    public TextView mServiceType;
    public TextView mTime;
    public TextView mUse;
    public SmoothCheckBox mCheckbox;
    public RelativeLayout root;

    public OpenInvoiceHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        mMoney = (TextView) view.findViewById(R.id.item_invoces_money);
        mServiceType = (TextView) view.findViewById(R.id.item_invoces_service_type);
        mTime = (TextView) view.findViewById(R.id.item_invoces_time);
        mUse = (TextView) view.findViewById(R.id.item_invoces_use);
        mCheckbox = (SmoothCheckBox) view.findViewById(R.id.item_invoice_checkbox);
        root = (RelativeLayout) view.findViewById(R.id.rl_root);
    }
}

