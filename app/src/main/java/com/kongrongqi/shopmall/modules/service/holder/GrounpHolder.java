package com.kongrongqi.shopmall.modules.service.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/31 0031 on 13:40
 * 作者:penny
 */
public class GrounpHolder extends RecyclerView.ViewHolder {

    public Button mBuy;
    public TextView mPersonTotal;
    public TextView mMoney;
    public TextView mGroupName;

    public GrounpHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        mGroupName = (TextView) view.findViewById(R.id.item_group_name);
        mMoney = (TextView) view.findViewById(R.id.item_group_money);
        mPersonTotal = (TextView) view.findViewById(R.id.item_group_person);
        mBuy = (Button) view.findViewById(R.id.item_group_buy);

    }
}
