package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.presenter.NotUsePresenter;
import com.kongrongqi.shopmall.modules.model.NotUseModel;
import com.kongrongqi.shopmall.modules.task.holder.NotUseHolder;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/22 0022 on 20:10
 * 作者:penny
 */
public class NotUseAdapter extends BaseRecycleViewAdapter {
    private final Context mContext;
    private final NotUsePresenter mPresenter;
    private NotUseHolder mHolder;

    public NotUseAdapter(Context context, NotUsePresenter notUsePresenter) {
        this.mPresenter = notUsePresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_not_use, parent, false);
        viewHolder = new NotUseHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mHolder = (NotUseHolder) holder;
        NotUseModel notUseModel = (NotUseModel) datas.get(position);
        int type = notUseModel.getType();

        mHolder.mServiceName.setText(notUseModel.getName());
        mHolder.mNum.setText(notUseModel.getUnUsedNum()+"份");

        String str = StringUtils.jointStr(notUseModel.getContentName(),notUseModel.getContent(), "");
        mHolder.mDescribe.setText(str);

        String jointStr = StringUtils.jointStr(
                "有效期限",
                notUseModel.getDuration(),
                notUseModel.getDurationUnit());
        mHolder.mCycle.setText(jointStr);

        mHolder.mUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.itemUseService(position, notUseModel, type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
