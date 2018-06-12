package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

import org.w3c.dom.Text;

/**
 * 创建日期：2017/5/22 0022 on 20:13
 * 作者:penny
 */
public class CallTextCustorHolder extends RecyclerView.ViewHolder {

    public LinearLayout ll_checkbox;
    public SmoothCheckBox item_checkbox;
    public EditText remarks;
    public TextView lenth;

    public CallTextCustorHolder(View view) {
        super(view);
        findView(view);
    }

    private void findView(View view) {

        ll_checkbox = (LinearLayout) view.findViewById(R.id.ll_checkbox);
        item_checkbox = (SmoothCheckBox) view.findViewById(R.id.item_checkbox);
        remarks = (EditText) view.findViewById(R.id.remarks);
        lenth = (TextView) view.findViewById(R.id.lenth);

    }
}
