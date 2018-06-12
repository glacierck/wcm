package com.kongrongqi.shopmall.modules.service.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.AddressModel;
import com.kongrongqi.shopmall.modules.service.EditAddressActivity;
import com.kongrongqi.shopmall.modules.service.holder.ReceiverAddressHolder;
import com.kongrongqi.shopmall.modules.service.presenter.ReceiverAddressPresenter;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/22 0022 on 13:52
 * 作者:penny
 */
public class ReceiverAddressAdapter extends BaseRecycleViewAdapter {
    private final ReceiverAddressPresenter mPresenter;
    private final Context mContext;

    public ReceiverAddressAdapter(ReceiverAddressPresenter receiverAddressPresenter, Context context) {
        this.mPresenter = receiverAddressPresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        viewHolder = new ReceiverAddressHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReceiverAddressHolder mAddressHolder = (ReceiverAddressHolder) holder;
        AddressModel o = (AddressModel) datas.get(position);
        String address = StringUtils.jointAddress(o.getProvince(), o.getCity(), o.getDistrict(), o.getAddress());

        mAddressHolder.addressee_man.setText(o.getReceiver());//收件人
        mAddressHolder.addressee_phone.setText(o.getReceivePhone());
        mAddressHolder.address.setText(address);

        mAddressHolder.default_address_checkbox.setChecked(o.getIsDefault()==1?true:false);

        mAddressHolder.default_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.detDefaultConsigneeAddress(o);

//                o.setIsDefault(o.getIsDefault()==1?0:1);
//                setDefaultAddress(o);
            }
        });

        mAddressHolder.default_address_checkbox.setEnabled(false);
        mAddressHolder.default_address_checkbox.setClickable(false);

        mAddressHolder.edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddressActivity.lunch(mContext,o);
            }
        });
        mAddressHolder.delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteConsigneeAddress(o.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }




}
