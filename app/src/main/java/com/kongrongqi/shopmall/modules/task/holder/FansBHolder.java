package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/22 0022 on 20:13
 * 作者:penny
 */
public class FansBHolder extends RecyclerView.ViewHolder {

    public TextView mCycle;
    public TextView mServiceName;
    public TextView mDescribe;
    public Button mUse;
    public TextView mTime;

    public FansBHolder(View view) {
        super(view);
        findView(view);
    }

    private void findView(View view) {
        mCycle = (TextView) view.findViewById(R.id.item_fansb_cycle);
        mTime = (TextView) view.findViewById(R.id.item_fansb_time);
        mServiceName = (TextView) view.findViewById(R.id.item_fansb_service_name);
        mDescribe = (TextView) view.findViewById(R.id.item_fansb_describe);
        mUse = (Button) view.findViewById(R.id.item_fansb_use);
    }
}
