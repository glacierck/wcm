package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class BottomViewHolder  extends RecyclerView.ViewHolder {

    public TextView time_status;

    public BottomViewHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        time_status = (TextView) view.findViewById(R.id.time_status);
    }
}
