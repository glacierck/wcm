package com.kongrongqi.shopmall.modules.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.me.bean.BuyRecord;
import com.kongrongqi.shopmall.modules.me.holder.DealHolder;
import com.kongrongqi.shopmall.modules.me.holder.HaveOpenInvoiceHolder;
import com.kongrongqi.shopmall.modules.me.presenter.HaveOpenInvoicePresenter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/20 0020 on 14:36
 * 作者:penny
 */
public class HaveOpenInvoiceAdapter extends BaseRecycleViewAdapter<IvoiceModel,HaveOpenInvoiceHolder> {
    private final Context mContext;
    private HaveOpenInvoicePresenter mPresenter;

    public HaveOpenInvoiceAdapter(HaveOpenInvoicePresenter presenter, Context context) {
        this.mPresenter = presenter;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(HaveOpenInvoiceHolder holder, int position) {
        IvoiceModel ivoiceModel = datas.get(position);
        holder.item_invoces_time.setText(ivoiceModel.getCreateTimeFormat());
        holder.item_invoces_service_type.setText(ivoiceModel.getOrderName());
        holder.item_invoces_money.setText(StringUtils.jointStr(ivoiceModel.getPrice()));
    }

    @Override
    public HaveOpenInvoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HaveOpenInvoiceHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_have_invoice, parent, false);
        viewHolder = new HaveOpenInvoiceHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
