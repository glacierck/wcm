package com.kongrongqi.shopmall.modules.service.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.modules.service.holder.ServiceHolder;
import com.kongrongqi.shopmall.modules.service.presenter.ServicePresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class ServiceAdaper extends BaseRecycleViewAdapter {
    public static final String TAG = "ServiceAdaper";
    private final ServicePresenter mPresenter;
    private final Context mContext;

    public ServiceAdaper(ServicePresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        viewHolder = new ServiceHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceHolder mHolder = (ServiceHolder) holder;
        ServiceBean mListData = (ServiceBean) datas.get(position);
        String serviceName = mListData.getName();
        mHolder.mServiceName.setText(serviceName);
        String str = StringUtils.jointStr(mListData.getContentName(), mListData.getContent(), "");
        mHolder.mDetail.setText(str);
        int type = mListData.getType();
        String price = mListData.getPrice();
        int lStatus = mListData.getStatus();
        String dayUnit = mListData.getDurationUnit();
        showTextOrNum(mHolder, type, price, lStatus, dayUnit);
        String jointStr = StringUtils.jointStr("有效期限", mListData.getValidDay() + "", "天");
        mHolder.mBuy.setText(App.getInstance().getString(R.string.buy));
        mHolder.mCycle.setText(jointStr);
        if (lStatus == 1) { //可以购买
            mHolder.mBuy.setBackgroundResource(R.drawable.select_logout_button_bg);
            mHolder.mBuy.setEnabled(true);
            mHolder.mBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.BuyService(position, ServiceAdaper.this, serviceName);
                }
            });
            mHolder.mRoot.setEnabled(true);
            mHolder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.startProductDetail(mListData);
                }
            });
        } else { // 敬请期待
            mHolder.mBuy.setBackgroundResource(R.drawable.not_buy_bj);
            mHolder.mBuy.setEnabled(false);
            mHolder.mRoot.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private void showTextOrNum(ServiceHolder mHolder, int type, String price, int pStatus, String pDayUnit) {
        switch (type) {
            case 2:
                if (pStatus == 2) {
                    mHolder.mMoney.setText(App.getInstance().getString(R.string.wait_hope));
                } else {
                    mHolder.mMoney.setText(price + "/" + pDayUnit);
                    mHolder.mNice.setVisibility(View.VISIBLE);
                    mHolder.mNice.setImageResource(R.drawable.finger);
                }
                break;
            case 3:
                if (pStatus == 2)
                    mHolder.mMoney.setText(App.getInstance().getString(R.string.wait_hope));
                else
                    mHolder.mMoney.setText(price + "/" + pDayUnit);
                break;
            case 6:
                if (pStatus == 2)
                    mHolder.mMoney.setText(App.getInstance().getString(R.string.wait_hope));
                else
                    mHolder.mMoney.setText(mContext.getString(R.string.service_c));
                break;
            case 4:
                if (pStatus == 2)
                    mHolder.mMoney.setText(App.getInstance().getString(R.string.wait_hope));
                else
                    mHolder.mMoney.setText(mContext.getString(R.string.service_grounp_num));
                break;
            case 5:
                if (pStatus == 2)
                    mHolder.mMoney.setText(App.getInstance().getString(R.string.wait_hope));
                else
                    mHolder.mMoney.setText(price + "/" + pDayUnit);
                break;
        }
    }
}
