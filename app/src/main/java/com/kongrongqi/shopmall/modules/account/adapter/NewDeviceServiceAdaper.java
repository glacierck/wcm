package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.account.holder.NewDeviceServiceHolder;
import com.kongrongqi.shopmall.modules.account.presenter.NewDeviceServicePresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class NewDeviceServiceAdaper extends BaseRecycleViewAdapter<UserService,NewDeviceServiceHolder> {
    private final NewDeviceServicePresenter mPresenter;
    private final Context mContext;

    public NewDeviceServiceAdaper(NewDeviceServicePresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    //内容长度
    public int getContentItemCount() {
        return datas.size();
    }


    @Override
    public NewDeviceServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewDeviceServiceHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_device_service, parent, false);
        viewHolder = new NewDeviceServiceHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewDeviceServiceHolder holder, int position) {
        UserService userService = datas.get(position);
        holder.service_name.setText(userService.getName());
        holder.service_connect.setText(userService.getContentName() + "：" + userService.getContent());
        holder.service_time.setText(userService.getDetailName() + "：" + userService.getDetail());
        holder.count.setText(StringUtils.jointStr(userService.getPrice()));
        holder.count.setTextColor(mContext.getResources().getColor(R.color.cl_7895F3));
        holder.bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirmUserService(userService);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getContentItemCount();
    }



}
