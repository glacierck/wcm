package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.holder.MoreEquipHolder;
import com.kongrongqi.shopmall.modules.account.presenter.MoreEquipListPresenter;

import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class MoreEquipAdaper extends BaseRecycleViewAdapter<Device,MoreEquipHolder>{
    private final MoreEquipListPresenter mPresenter;
    private final Context mContext;
    private MoreEquipHolder mSholder;

    public MoreEquipAdaper(MoreEquipListPresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    @Override
    public MoreEquipHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MoreEquipHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_equip, parent, false);
        viewHolder = new MoreEquipHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoreEquipHolder holder, int position) {
        mSholder = (MoreEquipHolder)holder;
        Device device = datas.get(position);
        mSholder.equip_name.setText(device.getUserDeviceName());
        if(device.getState()==1){
            mSholder.band_info.setImageResource(R.drawable.shebei_normal_big);
        }else {
            mSholder.band_info.setImageResource(R.drawable.shebei_unnormal_small);
        }
        mSholder.item_ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selectPage(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


}
