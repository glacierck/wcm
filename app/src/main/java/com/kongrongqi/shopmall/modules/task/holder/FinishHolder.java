package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.frame.commonframe.progressbar.FlikerProgressBar;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.progressBar.TextRoundCornerProgressBar;

/**
 * 创建日期：2017/5/22 0022 on 20:13
 * 作者:penny
 */
public class FinishHolder extends RecyclerView.ViewHolder {


    public TextView mAccountName;
    public TextView devices;
//    public FlikerProgressBar mPregressBar;
    public TextView mData;
    public TextView mFansTotal;
    public TextView mServiceName;
    public LinearLayout item;
    public ProgressBar mPregressBar;
    public TextView progress_text;

    public FinishHolder(View view) {
        super(view);
        findView(view);
    }

    private void findView(View view) {
        item = (LinearLayout) view.findViewById(R.id.item);
        mAccountName = (TextView) view.findViewById(R.id.finish_account);
        devices = (TextView) view.findViewById(R.id.finish_devices);
//        mPregressBar = (FlikerProgressBar) view.findViewById(R.id.schduler);
        mData = (TextView) view.findViewById(R.id.finish_data);
        mFansTotal = (TextView) view.findViewById(R.id.finish_fans_total);
        mServiceName = (TextView) view.findViewById(R.id.finish_service_name);

        mPregressBar = (ProgressBar) view.findViewById(R.id.progress);
        progress_text = (TextView) view.findViewById(R.id.progress_text);
    }
}
