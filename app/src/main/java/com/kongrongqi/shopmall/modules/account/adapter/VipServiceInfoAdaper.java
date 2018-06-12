package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.FanOrGroup;
import com.kongrongqi.shopmall.modules.account.holder.VipServiceInfoHolder;
import com.kongrongqi.shopmall.modules.account.presenter.VipServiceInfoPresenter;
import com.kongrongqi.shopmall.utils.CommonUtils;

import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class VipServiceInfoAdaper extends BaseRecycleViewAdapter<FanOrGroup,VipServiceInfoHolder>{
    private final VipServiceInfoPresenter mPresenter;
    private final Context mContext;

    public VipServiceInfoAdaper(VipServiceInfoPresenter presenter, Context context) {
        this.mPresenter = presenter;
        this.mContext = context;
    }

    @Override
    public VipServiceInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VipServiceInfoHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vip_service, parent, false);
        viewHolder = new VipServiceInfoHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VipServiceInfoHolder holder, int position) {

        FanOrGroup fanOrGroup = datas.get(position);
        holder.service_name.setText(fanOrGroup.getName());
        holder.time_status.setText(fanOrGroup.getCreateTime()+"");

        switch (fanOrGroup.getStatus()){//状态 0 未使用 1 进行中 2 已完成 3 异常
            case 0:
                holder.time_status.setText("未使用");
                break;
            case 1:
                holder.time_status.setText("正在服务");
                break;
            case 2:
                holder.time_status.setText(CommonUtils.getDataStrToMillis(fanOrGroup.getUpdateTime()));
                break;
            case 3:
                holder.time_status.setText("服务异常");
                break;
        }

        holder.info.setText(fanOrGroup.getCondition());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
