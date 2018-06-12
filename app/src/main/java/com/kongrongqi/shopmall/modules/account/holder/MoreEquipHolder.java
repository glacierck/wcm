package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class MoreEquipHolder extends RecyclerView.ViewHolder {

    public TextView equip_name;
    public ImageView band_info;
    public LinearLayout item_ll_root;

    public MoreEquipHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        item_ll_root = (LinearLayout) view.findViewById(R.id.item_ll_root);
        equip_name = (TextView) view.findViewById(R.id.equip_name);
        band_info = (ImageView) view.findViewById(R.id.equip_status);

    }
}
