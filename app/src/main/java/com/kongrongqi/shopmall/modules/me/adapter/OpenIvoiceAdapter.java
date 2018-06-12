package com.kongrongqi.shopmall.modules.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.me.holder.HaveOpenInvoiceHolder;
import com.kongrongqi.shopmall.modules.me.holder.OpenInvoiceHolder;
import com.kongrongqi.shopmall.modules.me.presenter.OpenIvoicePresenter;
import com.kongrongqi.shopmall.modules.model.IvoiceModel;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/20 0020 on 12:51
 * 作者:penny
 */
public class OpenIvoiceAdapter extends BaseRecycleViewAdapter<IvoiceModel,OpenInvoiceHolder> {
    private Context mContext;
    private OpenIvoicePresenter mPresenter;
    private OpenInvoiceHolder mSholder;

    public OpenIvoiceAdapter(OpenIvoicePresenter openIvoicePresenter, Context context) {
        this.mPresenter = openIvoicePresenter;
        this.mContext = context;
    }

    @Override
    public OpenInvoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OpenInvoiceHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_invoice, parent, false);
        viewHolder = new OpenInvoiceHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OpenInvoiceHolder holder, int position) {
        IvoiceModel ivoiceModel = datas.get(position);
        mSholder = holder;
        mSholder.mTime.setText(ivoiceModel.getCreateTimeFormat());
        mSholder.mServiceType.setText(ivoiceModel.getOrderName());
        mSholder.mMoney.setText( StringUtils.jointStr(ivoiceModel.getPrice()));
        mSholder.mCheckbox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                ivoiceModel.setCheck(isChecked);
                mPresenter.bindCheckIvoice();
            }
        });

        mSholder.mCheckbox.setChecked(ivoiceModel.isCheck());

        mSholder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IvoiceModel o = (IvoiceModel) datas.get(position);
                o.setCheck(!o.isCheck());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
