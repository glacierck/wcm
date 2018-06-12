package com.kongrongqi.shopmall.modules.task.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/22 0022 on 20:13
 * 作者:penny
 */
public class CallTextHolder extends RecyclerView.ViewHolder {

    public LinearLayout ll_checkbox;
    public SmoothCheckBox item_checkbox;
    public TextView callTitle;
    public EditText callTxt;
    public TextView callSample;
    public RelativeLayout rl_call_text_head;

    public CallTextHolder(View view) {
        super(view);
        findView(view);
    }

    private void findView(View view) {

        ll_checkbox = (LinearLayout) view.findViewById(R.id.ll_checkbox);
        item_checkbox = (SmoothCheckBox) view.findViewById(R.id.item_checkbox);
        callTitle = (TextView) view.findViewById(R.id.callTitle);
        callTxt = (EditText) view.findViewById(R.id.callTxt);
        callSample = (TextView) view.findViewById(R.id.callSample);

        rl_call_text_head = (RelativeLayout) view.findViewById(R.id.rl_call_text_head);


    }
}
