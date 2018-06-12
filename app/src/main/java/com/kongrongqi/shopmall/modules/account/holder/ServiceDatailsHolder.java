package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.commonframe.progressbar.FlikerProgressBar;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;
import com.kongrongqi.shopmall.wedget.progressBar.TextRoundCornerProgressBar;

import org.w3c.dom.Text;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class ServiceDatailsHolder extends RecyclerView.ViewHolder {

    public TextView service_name;
    //    public FlikerProgressBar progress;
//    public TextRoundCornerProgressBar progress;
    public ProgressBar progress;
    public TextView service_status;
    public TextView failure_time;
    public TextView service;
    public LinearLayout right_today;
    public TextView add_friend_count;
    public TextView today_add_count;
    public RelativeLayout right_restart;
    public Button bt_right_restart;
    public TextView progress_text;

    public ServiceDatailsHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        service_name = (TextView) view.findViewById(R.id.service_name);
        service_status = (TextView) view.findViewById(R.id.service_status);
//        progress = (FlikerProgressBar) view.findViewById(R.id.progress);
//        progress = (TextRoundCornerProgressBar) view.findViewById(R.id.progress);ProgressBar
        progress = (ProgressBar) view.findViewById(R.id.progress);
        progress_text = (TextView) view.findViewById(R.id.progress_text);
        failure_time = (TextView) view.findViewById(R.id.failure_time);
        service = (TextView) view.findViewById(R.id.service);

        right_today = (LinearLayout) view.findViewById(R.id.right_today);
        add_friend_count = (TextView) view.findViewById(R.id.add_friend_count);
        today_add_count = (TextView) view.findViewById(R.id.today_add_count);

        right_restart = (RelativeLayout) view.findViewById(R.id.right_restart);
        bt_right_restart = (Button) view.findViewById(R.id.bt_right_restart);
    }
}
