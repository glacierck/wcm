package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class ServiceHolder extends RecyclerView.ViewHolder {

    public TextView account;
    public TextView band_info;
    public Button info;

    public ServiceHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        account = (TextView) view.findViewById(R.id.tv_account);
        band_info = (TextView) view.findViewById(R.id.tv_band_info);
        info = (Button) view.findViewById(R.id.bt_info);
    }
}
