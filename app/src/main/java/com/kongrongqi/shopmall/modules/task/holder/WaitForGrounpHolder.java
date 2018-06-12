package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * 创建日期：2017/5/26 0026 on 17:59
 * 作者:penny
 */
public class WaitForGrounpHolder extends RecyclerView.ViewHolder {

    public TextView mGrounpName;
    public Button mInGrounp;

    public WaitForGrounpHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        mGrounpName = (TextView) view.findViewById(R.id.grounp_name);
        mInGrounp = (Button) view.findViewById(R.id.grounp_bt);
    }
}
