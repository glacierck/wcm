package com.kongrongqi.shopmall.modules.me.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/19 0019 on 18:10
 * 作者:penny
 */
public class DealHolder extends RecyclerView.ViewHolder {

    public TextView mUse;
    public TextView mTime;
    public TextView mServiceTime;
    public TextView mMoney;

    public DealHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        mUse = (TextView) view.findViewById(R.id.deal_use);
        mTime = (TextView) view.findViewById(R.id.deal_time);
        mServiceTime = (TextView) view.findViewById(R.id.deal_service_type);
        mMoney = (TextView) view.findViewById(R.id.deal_money);
    }
}
