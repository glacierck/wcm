package com.kongrongqi.shopmall.modules.account.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.progressBar.TextRoundCornerProgressBar;

import org.w3c.dom.Text;

/**
 * 创建日期：2017/5/20 0020 on 18:43
 * 作者:penny
 */
public class AccountTabHolder extends RecyclerView.ViewHolder {

    public TextView account_name;
    public TextView account_status;
    public TextView service_status;
    public Button bt_use_service;
    public LinearLayout ll_progress;
//    public ImageView device_status;
    public TextView service_count;
    public LinearLayout ll_service_status;
    public TextView tv_service_count;
    public TextView tv_service_status;
    public LinearLayout item_ll_root;
    public TextView account_status_tv;

    public AccountTabHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {

        item_ll_root = (LinearLayout) view.findViewById(R.id.item_ll_root);

        account_name = (TextView) view.findViewById(R.id.account_name);
        account_status = (TextView) view.findViewById(R.id.account_status);
        service_status = (TextView) view.findViewById(R.id.service_status);
        service_count = (TextView) view.findViewById(R.id.service_count);

        bt_use_service = (Button) view.findViewById(R.id.bt_use_service);
//        device_status = (ImageView) view.findViewById(R.id.device_status);

        ll_progress = (LinearLayout) view.findViewById(R.id.ll_Progress);
        ll_service_status = (LinearLayout) view.findViewById(R.id.ll_service_status);


        tv_service_count = (TextView) view.findViewById(R.id.tv_service_count);//服务
        tv_service_status = (TextView) view.findViewById(R.id.tv_service_status);//状态

        account_status_tv = (TextView) view.findViewById(R.id.account_status_tv);//状态
    }
}
