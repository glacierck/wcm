package com.kongrongqi.shopmall.modules.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.CallModel;
import com.kongrongqi.shopmall.modules.task.holder.CallTextBottomHolder;
import com.kongrongqi.shopmall.modules.task.holder.CallTextCustorHolder;
import com.kongrongqi.shopmall.modules.task.holder.CallTextHeadHolder;
import com.kongrongqi.shopmall.modules.task.holder.CallTextHolder;
import com.kongrongqi.shopmall.modules.task.presenter.CallTxtPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class CallTextAdaper extends BaseRecycleViewAdapter {
    private final CallTxtPresenter mPresenter;
    private final Context mContext;
    //item类型
    public static final int ITEM_TYPE_HEADER = 100;
    public static final int ITEM_TYPE_CONTENT = 101;
    public static final int ITEM_TYPE_BOTTOM = 102;

    public static final int ITEM_TYPE_CUSTOM = 103;//自定义招呼语
    private int mHeaderCount = 0;//头部View个数
    private int mBottomCount = 0;//底部View个数

    public Map<Integer, String> mapReason;

    public CallTextAdaper(CallTxtPresenter CallTxtPresenter, Context context) {
        this.mPresenter = CallTxtPresenter;
        this.mContext = context;
        mapReason = new HashMap<Integer, String>();
    }

    //内容长度
    public int getContentItemCount() {
        return datas != null ? datas.size() : 0;
    }


    //判断当前item类型
    @Override
    public int getItemViewType(int position) {

        int dataItemCount = getContentItemCount();
        CallModel callModel = null;
        if (dataItemCount > 0 && position > mHeaderCount - 1 && position - mHeaderCount < dataItemCount) {
            callModel = (CallModel) datas.get(position - mHeaderCount);
        }
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else if (callModel != null && callModel.getCallType() == 99) {
            //底部View
            return ITEM_TYPE_CUSTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_txt_head, parent, false);
            viewHolder = new CallTextHeadHolder(view);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_txt, parent, false);
            viewHolder = new CallTextHolder(view);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_txt_head, parent, false);
            viewHolder = new CallTextBottomHolder(view);
        } else if (viewType == ITEM_TYPE_CUSTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_txt_custom, parent, false);
            viewHolder = new CallTextCustorHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CallTextHeadHolder) {

        } else if (holder instanceof CallTextHolder) {
            CallTextHolder mHolder = (CallTextHolder) holder;
            CallModel callModel = (CallModel) datas.get(position - mHeaderCount);

            int itemViewType = getItemViewType(position - mHeaderCount-1);

            if(itemViewType == ITEM_TYPE_CONTENT){
                mHolder.rl_call_text_head.setVisibility(View.GONE);
            }else{
                mHolder.rl_call_text_head.setVisibility(View.VISIBLE);
            }
            mHolder.item_checkbox.setChecked(callModel.getIsSelect() == 1 ? true : false, callModel.getIsSelect() == 1 ? true : false);
            mHolder.item_checkbox.setEnabled(false);
            mHolder.item_checkbox.setClickable(false);
            mHolder.ll_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callModel.setIsSelect(callModel.getIsSelect() == 0 ? 1 : 0);
                    selectItem(callModel, position - mHeaderCount);
                }
            });

            mHolder.callTitle.setText(callModel.getCallTitle());
            mHolder.callSample.setText(callModel.getCallSample());
//            mHolder.callTxt.setText(callModel.getCallTxtInput());
            mHolder.callTxt.setHint(callModel.getCallTxt());

            if ((mHolder.callTxt.getTag() instanceof TextWatcher)) {
                mHolder.callTxt.removeTextChangedListener((TextWatcher) mHolder.callTxt.getTag());
            }

            // 第2步：移除TextWatcher之后，设置EditText的Text。
            mHolder.callTxt.setText(callModel.getCallTxtInput());

            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (TextUtils.isEmpty(editable.toString())) {
                        callModel.setCallTxtInput("");
                    } else {
                        callModel.setCallTxtInput(editable.toString());
                    }
                }
            };
            mHolder.callTxt.addTextChangedListener(watcher);
            mHolder.callTxt.setTag(watcher);

        } else if (holder instanceof CallTextCustorHolder) {
            CallTextCustorHolder mHolder = (CallTextCustorHolder) holder;
            CallModel callModel = (CallModel) datas.get(position - mHeaderCount);
            mHolder.remarks.setHint(callModel.getCallTxt());
            mHolder.remarks.setText(callModel.getCallTxtInput());
            mHolder.item_checkbox.setChecked(callModel.getIsSelect() == 1 ? true : false, callModel.getIsSelect() == 1 ? true : false);
            mHolder.item_checkbox.setEnabled(false);
            mHolder.item_checkbox.setClickable(false);
            mHolder.ll_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callModel.setIsSelect(callModel.getIsSelect() == 0 ? 1 : 0);
                    selectItem(callModel, position - mHeaderCount);
                }
            });


            if ((mHolder.remarks.getTag() instanceof TextWatcher)) {
                mHolder.remarks.removeTextChangedListener((TextWatcher) mHolder.remarks.getTag());
            }

            // 第2步：移除TextWatcher之后，设置EditText的Text。
            mHolder.remarks.setText(callModel.getCallTxtInput());

            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (TextUtils.isEmpty(editable.toString())) {
                        callModel.setCallTxtInput("");
                        mHolder.lenth.setText("0/50");
                    } else {
                        callModel.setCallTxtInput(editable.toString());
                        mHolder.lenth.setText(editable.toString().length()+"/50");
                    }
                }
            };
            mHolder.remarks.addTextChangedListener(watcher);
            mHolder.remarks.setTag(watcher);
        } else if (holder instanceof CallTextBottomHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }


    private void selectItem(CallModel callModel, int position) {
        for (int i = 0; i < datas.size(); i++) {
            CallModel call = (CallModel) datas.get(i);
            if (!call.getId().equals(callModel.getId())) {
                call.setIsSelect(0);
            }
        }
        notifyDataSetChanged();
    }
}
