package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class AddServiceTabHolder extends RecyclerView.ViewHolder {

    public SmoothCheckBox check_service;
    public TextView service_name;
    public TextView service_connect;
    public TextView service_time;
    public TextView count;
    public TextView guanfanB;
    public LinearLayout item_ll_root;

    public AddServiceTabHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        check_service = (SmoothCheckBox) view.findViewById(R.id.check_service);
        service_name = (TextView) view.findViewById(R.id.service_name);
        service_connect = (TextView) view.findViewById(R.id.service_connect);
        service_time = (TextView) view.findViewById(R.id.service_time);
        count = (TextView) view.findViewById(R.id.count);
        guanfanB = (TextView) view.findViewById(R.id.guanfanB);
        item_ll_root = (LinearLayout) view.findViewById(R.id.item_ll_root);
    }
}
