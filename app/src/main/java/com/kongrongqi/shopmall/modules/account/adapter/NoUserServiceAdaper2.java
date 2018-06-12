package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.account.holder.AddServiceTabHolder;
import com.kongrongqi.shopmall.modules.account.holder.BottomViewHolder;
import com.kongrongqi.shopmall.modules.account.holder.HeaderViewHolder;
import com.kongrongqi.shopmall.modules.account.presenter.NoUserServiceDatailsPresenter;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class NoUserServiceAdaper2 extends BaseRecycleViewAdapter {
    private final NoUserServiceDatailsPresenter mPresenter;
    private final Context mContext;
    private AddServiceTabHolder mSholder;

    //item类型
    public static final int ITEM_TYPE_HEADER = 100;
    public static final int ITEM_TYPE_CONTENT = 101;
    public static final int ITEM_TYPE_BOTTOM = 102;


    private int mHeaderCount = 0;//头部View个数
    private int mBottomCount = 0;//底部View个数

    private boolean onBind;

    public NoUserServiceAdaper2(NoUserServiceDatailsPresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    //内容长度
    public int getContentItemCount() {
        return datas.size();
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

        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_user_service, parent, false);
            viewHolder = new AddServiceTabHolder(view);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_service_foot, parent, false);
            viewHolder = new BottomViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        onBind = true;
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof AddServiceTabHolder) {
            UserService userService = (UserService) datas.get(position);
            AddServiceTabHolder mSholder = (AddServiceTabHolder) holder;
            mSholder.service_name.setText(userService.getName());
            mSholder.service_connect.setText(userService.getContentName() + "：" + userService.getContent());
            mSholder.service_time.setText(userService.getDurationName() + "：" + userService.getDuration() + userService.getDurationUnit());
            mSholder.count.setText(userService.getCount() + "次");
            mSholder.guanfanB.setVisibility(View.GONE);
            mSholder.check_service.setVisibility(View.VISIBLE);
            mSholder.check_service.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                    userService.setCheck(isChecked);
                    if (!onBind && isChecked) {
                        isCheckRefresh(userService, position);
                    }
                }
            });
            mSholder.check_service.setChecked(userService.getCheck());

            switch (userService.getType()){  //type 服务类型 1 账号号槽 2 灌粉服务A 3 灌粉服务B 4 入群服务 5 养号服务
//                case 3://3 灌粉服务B
                case 6://4 灌粉服务c
                    mSholder.check_service.setVisibility(View.INVISIBLE);
                    mSholder.guanfanB.setVisibility(View.VISIBLE);
                    mSholder.service_time.setText(userService.getDurationName() + "：" + userService.getDuration());
                    mSholder.guanfanB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.buyServiceB();
                        }
                    });
                    break;
            }

            mSholder.item_ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userService.setCheck(!userService.getCheck());
                    isCheckRefresh(userService,position);
                    notifyDataSetChanged();
                }
            });



        } else if (holder instanceof BottomViewHolder) {

        }
        onBind = false;
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    public void isCheckRefresh(UserService userService, int position) {
        for (int i = 0; i < datas.size(); i++) {
            UserService userService1 = (UserService) datas.get(i);
            if (i != position && userService1.getCheck()) {
                userService1.setCheck(false);
            }
        }
        notifyDataSetChanged();
    }


}
