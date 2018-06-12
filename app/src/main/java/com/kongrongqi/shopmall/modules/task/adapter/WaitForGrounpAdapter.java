package com.kongrongqi.shopmall.modules.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.UnUseGrounpServiceModel;
import com.kongrongqi.shopmall.modules.task.holder.WaitForGrounpHolder;
import com.kongrongqi.shopmall.modules.task.presenter.FansBPresenter;
import com.kongrongqi.shopmall.modules.task.presenter.WaitForGrounpPresenter;

/**
 * 创建日期：2017/5/22 0022 on 20:10
 * 作者:penny
 */
public class WaitForGrounpAdapter extends BaseRecycleViewAdapter {
    private final Context mContext;
    private final WaitForGrounpPresenter mPresenter;
    private WaitForGrounpHolder mHolder;

    public WaitForGrounpAdapter(Context context, WaitForGrounpPresenter presenter) {
        this.mPresenter = presenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wait_for_grounp, parent, false);
        viewHolder = new WaitForGrounpHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mHolder = (WaitForGrounpHolder) holder;
        UnUseGrounpServiceModel model = (UnUseGrounpServiceModel) datas.get(position);
        mHolder.mGrounpName.setText(model.getGroupName());
        mHolder.mInGrounp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.itemClickInGrounp(position, model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
