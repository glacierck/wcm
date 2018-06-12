package com.kongrongqi.shopmall.modules.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.me.bean.BuyRecord;
import com.kongrongqi.shopmall.modules.me.bean.UserSession;
import com.kongrongqi.shopmall.modules.me.holder.PushHolder;
import com.kongrongqi.shopmall.modules.me.presenter.ListPresenter;
import com.kongrongqi.shopmall.modules.me.presenter.UserSessionPresenter;

/**
 * 创建日期：2017/5/19 0019 on 17:04
 * 作者:penny
 */
public class PushAdapter extends BaseRecycleViewAdapter {

    private Context mContext;
    private UserSessionPresenter mPresenter;

    public PushAdapter(UserSessionPresenter listPresenter, Context context) {
        this.mPresenter = listPresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_push, parent, false);
        viewHolder = new PushHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PushHolder viewHolder = (PushHolder)holder;
        UserSession buyRecord = (UserSession)datas.get(position);
        viewHolder.push_describe.setText(buyRecord.getLastMsg());
        viewHolder.push_time.setText(buyRecord.getCreateTimeFormat());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
