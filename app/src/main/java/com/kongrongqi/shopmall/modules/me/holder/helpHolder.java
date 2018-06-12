package com.kongrongqi.shopmall.modules.me.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/19 0019 on 17:46
 * 作者:penny
 */
public class helpHolder extends RecyclerView.ViewHolder {

    public TextView mAsk;
    public TextView mAnswer;

    public helpHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        mAsk = (TextView) view.findViewById(R.id.help_ask);
        mAnswer = (TextView) view.findViewById(R.id.help_answer);
    }
}
