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
public class NotUseHolder extends RecyclerView.ViewHolder {

    public TextView mCycle;
    public TextView mServiceName;
    public TextView mDescribe;
    public Button mUse;
    public TextView mNum;

    public NotUseHolder(View view) {
        super(view);
        findView(view);
    }

    private void findView(View view) {
        mCycle = (TextView) view.findViewById(R.id.not_use_cycle);
        mNum = (TextView) view.findViewById(R.id.not_item_num);
        mServiceName = (TextView) view.findViewById(R.id.not_use_service_name);
        mDescribe = (TextView) view.findViewById(R.id.not_use_describe);
        mUse = (Button) view.findViewById(R.id.not_use_use);
    }
}
