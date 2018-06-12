package com.kongrongqi.shopmall.modules.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.holder.AccountHolder;
import com.kongrongqi.shopmall.modules.me.bean.BuyRecord;
import com.kongrongqi.shopmall.modules.me.holder.DealHolder;
import com.kongrongqi.shopmall.modules.me.presenter.BuyRecordPresenter;
import com.kongrongqi.shopmall.modules.me.presenter.ListPresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/19 0019 on 17:04
 * 作者:penny
 */
public class DealAdapter extends BaseRecycleViewAdapter {

    private Context mContext;
    private BuyRecordPresenter mPresenter;
    private DealHolder mSholder;

    public DealAdapter(BuyRecordPresenter listPresenter, Context context) {
        this.mPresenter = listPresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal, parent, false);
        viewHolder = new DealHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BuyRecord buyRecord = (BuyRecord) datas.get(position);
        mSholder = (DealHolder) holder;
        mSholder.mTime.setText(buyRecord.getCreateTimeFormat());
        String str =StringUtils.jointStr(buyRecord.getPrice());
        mSholder.mMoney.setText(str);
//        mSholder.mUse.setText(buyRecord.getIsUsed() == 1 ? mContext.getString(R.string.use) : mContext.getString(R.string.not_use));
        mSholder.mServiceTime.setText(buyRecord.getOrderName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
