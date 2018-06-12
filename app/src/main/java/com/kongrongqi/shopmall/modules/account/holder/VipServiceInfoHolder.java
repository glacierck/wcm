package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class VipServiceInfoHolder extends RecyclerView.ViewHolder {

    public TextView service_name;
    public TextView time_status;
    public TextView info;

    public VipServiceInfoHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        service_name = (TextView) view.findViewById(R.id.service_name);
        time_status = (TextView) view.findViewById(R.id.time_status);
        info = (TextView) view.findViewById(R.id.info);
    }
}
