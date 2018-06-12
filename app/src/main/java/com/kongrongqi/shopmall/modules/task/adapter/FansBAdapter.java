package com.kongrongqi.shopmall.modules.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.FansBListModel;
import com.kongrongqi.shopmall.modules.task.holder.FansBHolder;
import com.kongrongqi.shopmall.modules.task.presenter.FansBPresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/22 0022 on 20:10
 * 作者:penny
 */
public class FansBAdapter extends BaseRecycleViewAdapter {
    private final Context mContext;
    private final FansBPresenter mPresenter;
    private FansBHolder mHolder;

    public FansBAdapter(Context context, FansBPresenter notUsePresenter) {
        this.mPresenter = notUsePresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fansb, parent, false);
        viewHolder = new FansBHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mHolder = (FansBHolder) holder;
        FansBListModel model = (FansBListModel) datas.get(position);
        mHolder.mServiceName.setText(model.getName());

        String str = StringUtils.jointStr(model.getContentName(),
                model.getContent(), "");
        mHolder.mDescribe.setText(str);

        String jointStr = StringUtils.jointStr("失效时间",
                model.getExpireTime(), "");
        mHolder.mCycle.setText(jointStr);


        if (model.getType() == 6) {//服务C
            mHolder.mTime.setText(model.getFansTypeName());
            mHolder.mTime.setTextColor(mContext.getResources().getColor(R.color.login_button_bj));
        } else if (model.getType() == 2) {
            mHolder.mTime.setText(model.getDuration() + model.getDurationUnit());
        } else {
            mHolder.mTime.setText(model.getCreateTimeFormat());
        }
        mHolder.mUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.ItemClick(position, model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
