package com.kongrongqi.shopmall.modules.service.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/31 0031 on 13:40
 * 作者:penny
 */
public class ServiceCtypeHolder extends RecyclerView.ViewHolder {

    public SmoothCheckBox check_service;
    public TextView service_name;
    public TextView count;

    public ServiceCtypeHolder(View view) {
        super(view);
        findViews(view);
    }

    private void findViews(View view) {
        check_service = (SmoothCheckBox) view.findViewById(R.id.check_service);
        service_name = (TextView) view.findViewById(R.id.service_name);
        count = (TextView) view.findViewById(R.id.count);
    }
}
