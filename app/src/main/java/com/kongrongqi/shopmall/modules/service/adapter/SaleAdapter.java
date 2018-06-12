package com.kongrongqi.shopmall.modules.service.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.SaleModel;
import com.kongrongqi.shopmall.modules.service.holder.SaleHolder;
import com.kongrongqi.shopmall.modules.service.presenter.PayPresenter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * Created on 2017/7/24 0024.
 * by penny
 */

public class SaleAdapter extends BaseRecycleViewAdapter {

    private final Context mContext;
    private final PayPresenter mPresenter;
    private final int mType;

    public SaleAdapter(Context pContext, PayPresenter pPayPresenter, int pType) {
        this.mContext = pContext;
        this.mPresenter = pPayPresenter;
        this.mType = pType;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder lHolder = null;
        View lView = LayoutInflater.from(mContext).inflate(R.layout.item_viewstub_list, parent, false);
        if (mType == Constans.SYSTEM_ADD_FRIEND
                || mType == Constans.TRUSTEESHIP_ACCOUNT) {
            lHolder = new SaleHolder(lView);
        }
        return lHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SaleHolder) {
            saleUi((SaleHolder) holder, position);
        }
    }

    private void saleUi(SaleHolder holder, int position) {
        SaleHolder lHolder = holder;
        SaleModel lO = (SaleModel) datas.get(position);
        if (mType == Constans.TRUSTEESHIP_ACCOUNT) {
            trusteeshipAccount(lHolder, lO);
        } else if (mType == Constans.SYSTEM_ADD_FRIEND) {
            systemAddFriend(lHolder, lO);
        }
        lHolder.mCheckBox.setChecked(lO.isCheck());
        lHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lO.isCheck()) {//选中
                    int size = datas.size();
                    for (int i = 0; i < size; i++) {
                        SaleModel sale = (SaleModel) datas.get(i);
                        sale.setCheck(false);
                    }
                    lO.setCheck(true);
                    notifyAllUI();
                    mPresenter.choose(lO);
                } else {//取消
                    Logger.d("saleAdapeter", "取消");
                    mPresenter.choose(null);
                }
            }
        });

        lHolder.item_ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lO.isCheck()) {//选中
                    int size = datas.size();
                    for (int i = 0; i < size; i++) {
                        SaleModel sale = (SaleModel) datas.get(i);
                        sale.setCheck(false);
                    }
                    lO.setCheck(true);
                    notifyAllUI();
                    mPresenter.choose(lO);
                } else {//取消
                    Logger.d("saleAdapeter", "取消");
                    mPresenter.choose(null);
                }
            }
        });



    }

    private void trusteeshipAccount(SaleHolder pHolder, SaleModel pO) {
        pHolder.mDay.setText(pO.getServiceDuration() + pO.getServiceDurationUnit());
        pHolder.mDiscountPrice.setText("¥" + pO.getServicePrice());
        pHolder.mPrice.setVisibility(View.GONE);
        pHolder.mHot.setVisibility(View.INVISIBLE);
        pHolder.mSale.setVisibility(View.INVISIBLE);
    }

    private void systemAddFriend(SaleHolder pHolder, SaleModel pO) {
        String sale = pO.getServiceExplain();
        String lPriceUnit = pO.getPriceUnit();
        int lIsHot = pO.getIsHot();
        String price = String.valueOf(pO.getServiceHisPrice());
        String discountPrice = String.valueOf(pO.getServicePrice());
        pHolder.mDay.setText(pO.getServiceDuration() + pO.getServiceDurationUnit());
        pHolder.mDiscountPrice.setText("¥" + pO.getServicePrice());
        if (sale.equals("无")) {
            pHolder.mSale.setVisibility(View.INVISIBLE);
            pHolder.mPrice.setVisibility(View.GONE);
        } else {
            pHolder.mSale.setVisibility(View.VISIBLE);
            pHolder.mPrice.setText("¥" + pO.getServiceHisPrice());
            pHolder.mPrice.setVisibility(View.VISIBLE);
            pHolder.mPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            pHolder.mSale.setText(sale);
        }

        if (lIsHot == 0) {
            pHolder.mHot.setVisibility(View.INVISIBLE);
        } else {
            pHolder.mHot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void notifyAllUI() {
        this.notifyDataSetChanged();
    }
}
