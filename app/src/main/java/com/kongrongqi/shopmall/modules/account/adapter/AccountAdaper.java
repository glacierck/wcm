package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.holder.AccountHolder;
import com.kongrongqi.shopmall.modules.account.presenter.AccountListPresenter;
import com.kongrongqi.shopmall.modules.service.holder.ServiceHolder;
import com.kongrongqi.shopmall.modules.service.presenter.ServicePresenter;
import com.kongrongqi.shopmall.utils.ToastUtil;

import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class AccountAdaper extends BaseRecycleViewAdapter{
    private final AccountListPresenter mPresenter;
    private final Context mContext;
    private AccountHolder mSholder;

    public AccountAdaper(AccountListPresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        viewHolder = new AccountHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountBind accountBind = (AccountBind)datas.get(position);
        mSholder = (AccountHolder)holder;
        mSholder.account.setText(accountBind.getWechatNo());
        mSholder.band_info.setText(accountBind.getBingDeviceState());
        mSholder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.lookInfo(accountBind);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
