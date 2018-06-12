package com.kongrongqi.shopmall.modules.service.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.WechatGrounpModel;
import com.kongrongqi.shopmall.modules.model.WechatResultsBean;
import com.kongrongqi.shopmall.modules.service.holder.GrounpHolder;
import com.kongrongqi.shopmall.modules.service.presenter.JoinGrounpPresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/31 0031 on 13:27
 * 作者:penny
 */
public class GrounpAdapter extends BaseRecycleViewAdapter{
    private final Context mContext;
    private final JoinGrounpPresenter mPresenter;
    private GrounpHolder mHolder;

    public GrounpAdapter(Context context, JoinGrounpPresenter joinGrounpPresenter) {
        this.mContext = context;
        this.mPresenter = joinGrounpPresenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grounp, parent, false);
        viewHolder = new GrounpHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mHolder = (GrounpHolder) holder;
        WechatGrounpModel o = (WechatGrounpModel) datas.get(position);
        mHolder.mGroupName.setText(o.getGroupName());
        String str = StringUtils.jointStr("群内人数", o.getCurrentSize()+"", "人");
        mHolder.mPersonTotal.setText(str);
        mHolder.mMoney.setText(StringUtils.jointStr(o.getGroupWorth()));
        mHolder.mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.buyGroupService(position,o);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
