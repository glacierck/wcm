package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.account.holder.AddServiceTabHolder;
import com.kongrongqi.shopmall.modules.account.presenter.ServiceListPresenter;
import com.kongrongqi.shopmall.modules.model.ServiceBean;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class ServiceAdaper extends BaseRecycleViewAdapter{
    private final ServiceListPresenter mPresenter;
    private final Context mContext;
    private AddServiceTabHolder mSholder;

    public ServiceAdaper(ServiceListPresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_service, parent, false);
        viewHolder = new AddServiceTabHolder(view);
        return viewHolder;
    }

    private boolean onBind;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        onBind = true;
        ServiceBean userService = (ServiceBean)datas.get(position);
        AddServiceTabHolder mSholder = (AddServiceTabHolder)holder;
        mSholder.service_name.setText(userService.getName());
        mSholder.service_connect.setText(userService.getContentName()+"："+userService.getContent());
        mSholder.service_time.setText(userService.getDurationName()+"："+userService.getDuration()+userService.getDurationUnit());
        mSholder.guanfanB.setVisibility(View.GONE);
        switch (userService.getType()){  //type 服务类型 1 账号号槽 2 灌粉服务A 3 灌粉服务B 4 入群服务 5 养号服务
            case 1://1 账号号槽
                break;
            case 2://2 灌粉服务A
                mSholder.count.setText(StringUtils.jointStr(userService.getPrice()));
                break;
            case 3://3 灌粉服务B
                mSholder.count.setText("视数据量而定");
                mSholder.guanfanB.setVisibility(View.VISIBLE);
                mSholder.check_service.setVisibility(View.INVISIBLE);
                mSholder.service_time.setText(userService.getDurationName()+"："+userService.getDuration());

                mSholder.guanfanB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.buyServiceB();
                    }
                });
                break;
            case 4://4 入群服务

                mSholder.count.setText("敬请期待");
                mSholder.guanfanB.setVisibility(View.VISIBLE);
                mSholder.check_service.setVisibility(View.INVISIBLE);
                mSholder.guanfanB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.buyServiceB();
                    }
                });
                break;
            case 5://5 养号服务
                mSholder.count.setText(StringUtils.jointStr(userService.getPrice()));
                break;

            case 6://6 灌粉服务C
                mSholder.count.setText("敬请期待");
                mSholder.guanfanB.setVisibility(View.VISIBLE);
                mSholder.check_service.setVisibility(View.INVISIBLE);
                mSholder.service_time.setText(userService.getDurationName()+"："+userService.getDuration());
                mSholder.guanfanB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.buyServiceB();
                    }
                });
                break;
        }

        mSholder.check_service.setChecked(userService.getCheck());
        mSholder.check_service.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                userService.setCheck(isChecked);
                if(!onBind && isChecked) {
                    isCheckRefresh(userService);
                }
            }
        });


        mSholder.item_ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.setCheck(!userService.getCheck());
                isCheckRefresh(userService);
                notifyDataSetChanged();
            }
        });



        onBind = false;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void isCheckRefresh(ServiceBean userService){
        for (int i = 0;i<datas.size();i++){
            ServiceBean userService1 = (ServiceBean) datas.get(i);
            if(!userService1.getId().equals(userService.getId())){
                if(userService1.getCheck()){
                    userService1.setCheck(false);
                }
            }
        }
        notifyDataSetChanged();
    }

}
