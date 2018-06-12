package com.kongrongqi.shopmall.modules.me.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/19 0019 on 17:27
 * 作者:penny
 */
public class PushHolder extends RecyclerView.ViewHolder {

    public TextView push_describe;
    public TextView push_time;

    public PushHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        push_describe = (TextView) view.findViewById(R.id.push_describe);
        push_time = (TextView) view.findViewById(R.id.push_time);
    }
}
