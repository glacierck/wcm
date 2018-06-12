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
public class AccountInfoHeaderBntHolder2 extends RecyclerView.ViewHolder {

    public TextView today_add_fans;
    public TextView guanfan_zongliang;

    public AccountInfoHeaderBntHolder2(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        today_add_fans = (TextView) view.findViewById(R.id.today_add_fans);//今日新增
        guanfan_zongliang = (TextView) view.findViewById(R.id.guanfan_zongliang);//加友总量
    }
}
