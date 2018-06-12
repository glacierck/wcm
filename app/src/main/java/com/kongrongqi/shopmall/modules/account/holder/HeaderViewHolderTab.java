package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class HeaderViewHolderTab extends RecyclerView.ViewHolder {
    public TextView account_name;
    public TextView account_status;
    public TextView service_status;
    public Button bt_use_service;
    public ImageView device_status; //设备状态

    public HeaderViewHolderTab(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        account_name = (TextView) view.findViewById(R.id.account_name);
        account_status = (TextView) view.findViewById(R.id.account_status);
        service_status = (TextView) view.findViewById(R.id.service_status);
        bt_use_service = (Button) view.findViewById(R.id.bt_use_service);
        device_status = (ImageView) view.findViewById(R.id.device_status);
    }
}

