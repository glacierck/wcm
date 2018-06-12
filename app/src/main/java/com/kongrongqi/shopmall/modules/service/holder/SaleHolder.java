package com.kongrongqi.shopmall.modules.service.holder;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.modules.model.SaleModel;
import com.kongrongqi.shopmall.modules.service.presenter.PayPresenter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * Created on 2017/7/24 0024.
 * by penny
 */

public class SaleHolder extends RecyclerView.ViewHolder{

    public TextView mDay;
    public SmoothCheckBox mCheckBox;
    public TextView mSale;
    public ImageView mHot;
    public TextView mPrice;
    public TextView mDiscountPrice;
    public LinearLayout item_ll_root;

    public SaleHolder(View itemView) {
        super(itemView);
        ViewStub lView = (ViewStub) itemView.findViewById(R.id.item_viewstub);
        inflateUI(lView);
    }

    private void inflateUI(ViewStub pView) {
        pView.setLayoutResource(R.layout.item_sale_list);
        View lView = pView.inflate();
        findView(lView);
}

    private void findView(View pView) {
        mDay = (TextView) pView.findViewById(R.id.item_sale_day);
        mCheckBox = (SmoothCheckBox) pView.findViewById(R.id.item_check_box);
        mSale = (TextView) pView.findViewById(R.id.item_sale);
        mHot = (ImageView) pView.findViewById(R.id.item_hot);
        mDiscountPrice = (TextView) pView.findViewById(R.id.item_price);
        mPrice = (TextView) pView.findViewById(R.id.item_discount_price);
        item_ll_root = (LinearLayout) pView.findViewById(R.id.item_ll_root);
    }

}
