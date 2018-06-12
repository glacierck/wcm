package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/22 0022 on 20:13
 * 作者:penny
 */
public class IdHolder extends RecyclerView.ViewHolder {

    public TextView id;

    public IdHolder(View view) {
        super(view);
        findView(view);
    }

    private void findView(View view) {
        id = (TextView) view.findViewById(R.id.text);
    }
}
