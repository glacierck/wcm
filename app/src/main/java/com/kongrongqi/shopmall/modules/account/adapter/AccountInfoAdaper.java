package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.AccountBind;
import com.kongrongqi.shopmall.modules.account.bean.AccountInfo;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.FanOrGroup;
import com.kongrongqi.shopmall.modules.account.holder.AccountHolder;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderHolder;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHolder;
import com.kongrongqi.shopmall.modules.account.holder.AccountTabHolder;
import com.kongrongqi.shopmall.modules.account.holder.BottomViewHolder;
import com.kongrongqi.shopmall.modules.account.holder.HeaderViewHolderTab;
import com.kongrongqi.shopmall.modules.account.presenter.AccountInfoPresenter;
import com.kongrongqi.shopmall.modules.account.presenter.AccountListPresenter;
import com.kongrongqi.shopmall.modules.model.NotUseModel;

import java.util.List;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class AccountInfoAdaper extends BaseRecycleViewAdapter{
    private final AccountInfoPresenter mPresenter;
    private final Context mContext;
    private AccountInfoHolder mSholder;


    //item类型
    public static final int ITEM_TYPE_HEADER = 100;
    public static final int ITEM_TYPE_CONTENT = 101;
    public static final int ITEM_TYPE_BOTTOM = 102;


    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private AccountBind accountBind;

    public AccountInfoAdaper(AccountInfoPresenter presenter, Context context, AccountBind accountBind) {
        this.mPresenter = presenter;
        this.mContext = context;
        this.accountBind=accountBind;
    }

    //内容长度
    public int getContentItemCount() {
        return datas!=null? datas.size():0;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {

        int dataItemCount = getContentItemCount();

        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_info, parent, false);
            viewHolder = new AccountInfoHeaderHolder(view);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_info, parent, false);
            viewHolder = new AccountInfoHolder(view);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_service_foot, parent, false);
            viewHolder = new BottomViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AccountInfoHeaderHolder) {
            AccountInfoHeaderHolder headerViewHolderTab =  (AccountInfoHeaderHolder)holder;
            headerViewHolderTab.band_info.setText(accountBind.getWechatNo());

        } else if (holder instanceof AccountInfoHolder) {
            mSholder = (AccountInfoHolder)holder;
            AccountInfo accountInfo = (AccountInfo) datas.get(position-mHeaderCount);
//            AccountInfo accountInfo = accountInfos.get(position - mHeaderCount);
            mSholder.account.setText(accountInfo.getName());

//            accountInfo.setTotal(accountInfo.getTotal()==null ? 0:accountInfo.getTotal());
//            accountInfo.setUsedNum(accountInfo.getUsedNum()==null ? 0:accountInfo.getUsedNum());
//            mSholder.band_info.setText((accountInfo.getTotal()-accountInfo.getUsedNum())+"份");
            mSholder.band_info.setText((accountInfo.getCount()==null ? 0:accountInfo.getCount())+"次");
            //vip服务才有点击和下划线
//            mSholder.band_info.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );//下划线
            mSholder.band_info.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
            mSholder.band_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mPresenter.starVipInfo(accountInfo);
                }
            });

        } else if (holder instanceof BottomViewHolder) {
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;

        }

    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }
}
