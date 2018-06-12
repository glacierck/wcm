package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
public class WorkingHolder extends RecyclerView.ViewHolder {


    public TextView mAccountName;
    public TextView devices;
    public TextView accountError;
    public TextView mServiceName;
//    public FlikerProgressBar mProgressBar;
    public TextView mWorkingData;
    public LinearLayout item;
    public ProgressBar mProgressBar;
    public TextView progress_text;
    public Button bt_right_restart;

    public WorkingHolder(View view) {
        super(view);
        findView(view);
    }

    private void findView(View view) {
        mAccountName = (TextView) view.findViewById(R.id.working_account);
        mServiceName = (TextView) view.findViewById(R.id.wroking_service_name);
        devices = (TextView) view.findViewById(R.id.working_devices);
        accountError = (TextView) view.findViewById(R.id.working_account_error);
        mWorkingData = (TextView) view.findViewById(R.id.working_data);


//        mProgressBar = (FlikerProgressBar) view.findViewById(R.id.working_item_schduler);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        progress_text = (TextView) view.findViewById(R.id.progress_text);

        item = (LinearLayout) view.findViewById(R.id.item);


        bt_right_restart = (Button) view.findViewById(R.id.bt_right_restart);
    }
}
