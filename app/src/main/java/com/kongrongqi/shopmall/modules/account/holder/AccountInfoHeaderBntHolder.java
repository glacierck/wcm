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
public class AccountInfoHeaderBntHolder extends RecyclerView.ViewHolder {

    public Button me_use;
    public Button bt_change_account;
    public Button user_service;

    public AccountInfoHeaderBntHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        me_use = (Button) view.findViewById(R.id.me_use);//我要使用
        user_service = (Button) view.findViewById(R.id.user_service);//使用服务
        bt_change_account = (Button) view.findViewById(R.id.bt_change_account);//我要换号
    }
}
