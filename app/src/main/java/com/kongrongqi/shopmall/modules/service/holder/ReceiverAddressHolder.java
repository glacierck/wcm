package com.kongrongqi.shopmall.modules.service.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/22 0022 on 13:55
 * 作者:penny
 */
public class ReceiverAddressHolder extends RecyclerView.ViewHolder {

    public TextView addressee_man;
    public TextView addressee_phone;
    public TextView address;
    public SmoothCheckBox default_address_checkbox;
    public LinearLayout default_address;
    public TextView edit_address;
    public TextView delete_address;

    public ReceiverAddressHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        addressee_man = (TextView) view.findViewById(R.id.addressee_man);//收件人
        addressee_phone = (TextView) view.findViewById(R.id.addressee_phone);//收件人电话
        address = (TextView) view.findViewById(R.id.address);//收件地址

        default_address = (LinearLayout) view.findViewById(R.id.default_address);//默认地址
        default_address_checkbox = (SmoothCheckBox) view.findViewById(R.id.default_address_checkbox);//默认地址选择框

        edit_address = (TextView) view.findViewById(R.id.edit_address);//编辑地址
        delete_address = (TextView) view.findViewById(R.id.delete_address);//删除地址

    }
}
