package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.holder.NoUserServiceHolder;
import com.kongrongqi.shopmall.modules.account.presenter.AddServiceTabPresenter;
import com.kongrongqi.shopmall.modules.model.ServiceListModel;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class AddServiceTabAdaper extends BaseRecycleViewAdapter{
    private final AddServiceTabPresenter mPresenter;
    private final Context mContext;

    private NoUserServiceHolder mSholder;


    public AddServiceTabAdaper(AddServiceTabPresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_service_tab, parent, false);
        viewHolder = new NoUserServiceHolder(view);
        return viewHolder;
    }
    private boolean onBind;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBind = true;

        mSholder = (NoUserServiceHolder)holder;

        ServiceListModel serviceListModel = (ServiceListModel)datas.get(position);

        mSholder.service_name.setText(serviceListModel.getName());
        mSholder.service_connect.setText(serviceListModel.getContentName()+"："+serviceListModel.getContent()); //服务内容
        mSholder.service_time.setText(serviceListModel.getDurationName()+"："+serviceListModel.getDuration()+serviceListModel.getDurationUnit());

        mSholder.check_service.setVisibility(View.VISIBLE);
        onBind = false;

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
