package com.kongrongqi.shopmall.modules.me.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/20 0020 on 14:38
 * 作者:penny
 */
public class HaveOpenInvoiceHolder extends RecyclerView.ViewHolder {


    public TextView item_invoces_time;
    public TextView item_invoces_service_type;
    public TextView item_invoces_money;

    public HaveOpenInvoiceHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {

        item_invoces_time = (TextView) view.findViewById(R.id.item_invoces_time);

        item_invoces_service_type = (TextView) view.findViewById(R.id.item_invoces_service_type);

        item_invoces_money = (TextView) view.findViewById(R.id.item_invoces_money);

    }
}

