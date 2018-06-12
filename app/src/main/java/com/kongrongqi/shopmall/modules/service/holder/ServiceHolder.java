package com.kongrongqi.shopmall.modules.service.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

import org.w3c.dom.Text;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class ServiceHolder extends RecyclerView.ViewHolder {
    /**
     * 金额
     */
    public TextView mMoney;
    /**
     * 时间周期
     */
    public TextView mCycle;
    /**
     * 购买
     */
    public Button mBuy;
    /**
     * rootView
     */
    public LinearLayout mRoot;
    /**
     * 商品详情
     */
    public TextView mDetail;
    /**
     * 商品名称
     */
    public TextView mServiceName;
    /**
     * 大拇指view
     */
    public ImageView mNice;

    public ServiceHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        mNice = (ImageView) view.findViewById(R.id.item_nice);
        mServiceName = (TextView) view.findViewById(R.id.item_service_list_name);
        mDetail = (TextView) view.findViewById(R.id.item_service_detail);
        mMoney = (TextView) view.findViewById(R.id.item_money);
        mCycle = (TextView) view.findViewById(R.id.item_cycle);
        mBuy = (Button) view.findViewById(R.id.item_buy);
        mRoot = (LinearLayout) view.findViewById(R.id.item_ll_root);
    }
}
