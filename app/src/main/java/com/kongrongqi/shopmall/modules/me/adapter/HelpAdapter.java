package com.kongrongqi.shopmall.modules.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.me.holder.helpHolder;
import com.kongrongqi.shopmall.modules.me.presenter.ListPresenter;

/**
 * 创建日期：2017/5/19 0019 on 17:37
 * 作者:penny
 */
public class HelpAdapter extends BaseRecycleViewAdapter {

    private  Context mContext;
    private  ListPresenter mPresenter;

    public HelpAdapter(ListPresenter listPresenter, Context context) {
        this.mPresenter = listPresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help, parent, false);
        viewHolder = new helpHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
