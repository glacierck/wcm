package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class NewDeviceServiceHolder extends RecyclerView.ViewHolder {

    public TextView service_name;
    public TextView service_connect;
    public TextView service_time;
    public TextView count;
    public Button bt_buy;

    public NewDeviceServiceHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        service_name = (TextView) view.findViewById(R.id.service_name);
        service_connect = (TextView) view.findViewById(R.id.service_connect);
        service_time = (TextView) view.findViewById(R.id.service_time);
        count = (TextView) view.findViewById(R.id.count);
        bt_buy = (Button) view.findViewById(R.id.bt_buy);
    }
}
