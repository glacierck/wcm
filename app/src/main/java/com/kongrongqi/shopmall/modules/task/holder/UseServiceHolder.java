package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/23 0023 on 13:16
 * 作者:penny
 */
public class UseServiceHolder extends RecyclerView.ViewHolder {

    public SmoothCheckBox mCheckBox;
    public TextView mId;
    public TextView wechatNo_hint;

    public UseServiceHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        mCheckBox = (SmoothCheckBox) view.findViewById(R.id.smoothCheckBox);
        mId = (TextView) view.findViewById(R.id.use_id_num);
        wechatNo_hint = (TextView) view.findViewById(R.id.wechatNo_hint);
    }
}
