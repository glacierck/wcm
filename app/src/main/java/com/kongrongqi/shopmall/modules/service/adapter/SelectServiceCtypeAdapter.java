package com.kongrongqi.shopmall.modules.service.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.FansModel;
import com.kongrongqi.shopmall.modules.model.WechatGrounpModel;
import com.kongrongqi.shopmall.modules.service.holder.GrounpHolder;
import com.kongrongqi.shopmall.modules.service.holder.ServiceCtypeHolder;
import com.kongrongqi.shopmall.modules.service.presenter.JoinGrounpPresenter;
import com.kongrongqi.shopmall.modules.service.presenter.SelectServiceCtypePresenter;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/31 0031 on 13:27
 * 作者:penny
 */
public class SelectServiceCtypeAdapter extends BaseRecycleViewAdapter<FansModel,ServiceCtypeHolder>{
    private final Context mContext;
    private final SelectServiceCtypePresenter mPresenter;

    public SelectServiceCtypeAdapter(Context context, SelectServiceCtypePresenter joinGrounpPresenter) {
        this.mContext = context;
        this.mPresenter = joinGrounpPresenter;
    }

    @Override
    public ServiceCtypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ServiceCtypeHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_service_c, parent, false);
        viewHolder = new ServiceCtypeHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceCtypeHolder holder, int position) {
        FansModel fansModel = getDatas().get(position);
//        holder.check_service
        holder.service_name.setText(fansModel.getFansName());
        holder.count.setText(StringUtils.jointStr(fansModel.getPrice()));

        holder.check_service.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                fansModel.setCheck(isChecked);
                mPresenter.bindCheckIvoice();
            }
        });
        holder.check_service.setChecked(fansModel.isCheck());
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }
}
