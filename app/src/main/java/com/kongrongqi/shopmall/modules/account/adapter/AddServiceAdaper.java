package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.holder.AddServiceHolder;
import com.kongrongqi.shopmall.modules.account.presenter.AddServicePresenter;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class AddServiceAdaper extends BaseRecycleViewAdapter{
    private final AddServicePresenter mPresenter;
    private final Context mContext;
    private AddServiceHolder mSholder;

    public AddServiceAdaper(AddServicePresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        viewHolder = new AddServiceHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mSholder = (AddServiceHolder)holder;

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
