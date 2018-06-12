package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.AddAccountActivity;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.holder.AccountTabHolder;
import com.kongrongqi.shopmall.modules.account.holder.BottomViewHolder;
import com.kongrongqi.shopmall.modules.account.holder.HeaderViewHolderTab;
import com.kongrongqi.shopmall.modules.account.presenter.TabPresenter;
import com.kongrongqi.shopmall.utils.ToastUtil;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class TabFragmentAdaper extends BaseRecycleViewAdapter {
    private final TabPresenter mPresenter;
    private final Context mContext;
    //item类型
    public static final int ITEM_TYPE_HEADER = 100;
    public static final int ITEM_TYPE_CONTENT = 101;
    public static final int ITEM_TYPE_BOTTOM = 102;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 1;//底部View个数
    private Device device;

    public TabFragmentAdaper(TabPresenter servicePresenter, Context context, Device device) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
        this.device = device;
    }

    //内容长度
    public int getContentItemCount() {
        return datas != null ? datas.size() : 0;
    }


    //判断当前item类型
    @Override
    public int getItemViewType(int position) {

        int dataItemCount = getContentItemCount();

        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_tab_head, parent, false);
            viewHolder = new HeaderViewHolderTab(view);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_tab, parent, false);
            viewHolder = new AccountTabHolder(view);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_service_foot, parent, false);
            viewHolder = new BottomViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolderTab) {
            int deviceState = 0;
            HeaderViewHolderTab headerViewHolderTab = (HeaderViewHolderTab) holder;
            headerViewHolderTab.account_name.setText("IMEI码：");
            headerViewHolderTab.account_status.setText(device != null ? device.getMachineCode() : "000000");
            headerViewHolderTab.bt_use_service.setText("设备异常");
            headerViewHolderTab.bt_use_service.setBackgroundResource(R.drawable.select_logout_button_bg);
            headerViewHolderTab.bt_use_service.setVisibility(View.GONE);

            headerViewHolderTab.device_status.setVisibility(View.VISIBLE);//显示设备状态

            if (datas != null && datas.size() > 0) {
                deviceState = ((DeviceWechat) datas.get(0)).getDeviceState();
            } else {
                deviceState = device.getState();
            }
            // status: 1 正常 2 网络异常 3 设备异常  msg:xxx
            switch (deviceState) {
                case 1://正常
                    headerViewHolderTab.device_status.setImageResource(R.drawable.shebei_normal);
                    break;
                case 2://网络异常
                    headerViewHolderTab.device_status.setImageResource(R.drawable.shebei_unnormal);
                    ToastUtil.showMessage(mContext,"伺服设备未联网");
//                    mPresenter.setEmpty();
                    break;
                case 3://设备异常
                    headerViewHolderTab.bt_use_service.setVisibility(View.VISIBLE);
                    headerViewHolderTab.device_status.setImageResource(R.drawable.shebei_unnormal);
                    headerViewHolderTab.bt_use_service.setOnClickListener(new View.OnClickListener() { //替换
                        @Override
                        public void onClick(View v) {
                            mPresenter.showErroyDevice();
                        }
                    });

                    break;
                default:
                    headerViewHolderTab.device_status.setImageResource(R.drawable.shebei_unnormal);
            }


        } else if (holder instanceof AccountTabHolder) {
            AccountTabHolder mSholder = (AccountTabHolder) holder;
            DeviceWechat deviceWechat = (DeviceWechat) datas.get(position - mHeaderCount);
            DeviceWechat newDeviceWechat = mPresenter.getDeviceWechat(deviceWechat);
            mSholder.account_name.setText("账号 " + newDeviceWechat.getSn() + ":");

            setAccountStatus(mSholder, newDeviceWechat.getAccountName(), R.color.cl_7895F3);
            setRed(mSholder, false);
            mSholder.account_status_tv.setVisibility(View.GONE);//显示设备状态

            switch (newDeviceWechat.getBntStatus()) {
                case 1://添加账号
                    setAccountStatus(mSholder, newDeviceWechat.getAccountName(), R.color.me_7D7D7D);
                    goneStatusAndProgress(mSholder);
                    if (position - mHeaderCount >= 1) {
                        DeviceWechat o = (DeviceWechat) datas.get(position - mHeaderCount - 1); //取上一个对象
                        if (1 == o.getBntStatus()) { //1 表示还没有绑定微信
                            setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_account_hc_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showMessage(mContext, "每台设备的账号必须按照账号顺序添加");
                                }
                            });
                        } else {
                            setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_logout_button_bg, setOnClick(newDeviceWechat));
                        }
                    } else {
                        setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_logout_button_bg, setOnClick(newDeviceWechat));
                    }
                    break;
                case 2://2等待审核
                    setLlProgressShow(mSholder, newDeviceWechat.getServiceStatus(), newDeviceWechat.getServiceCount(), false);
                    setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_account_hc_bg, null);
                    break;
                case 3://选择操作
                    setLlProgressShow(mSholder, newDeviceWechat.getServiceStatus(), newDeviceWechat.getServiceCount(), false);
                    setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_logout_button_bg, setOnClick(newDeviceWechat));
                case 4://查看详情
                    setLlProgressShow(mSholder, newDeviceWechat.getServiceStatus(), newDeviceWechat.getServiceCount(), false);
                    setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_logout_button_bg, setOnClick(newDeviceWechat));
                    break;
                case 5://查看原因
                    setLlProgressShow(mSholder, newDeviceWechat.getServiceStatus(), newDeviceWechat.getServiceCount(), false);
                    setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_logout_button_bg, setOnClick(newDeviceWechat));
                    if (newDeviceWechat.getAccountStatus() == 4) {//4账号异常  全部红色
                        setRed(mSholder, true);
                    }else{
                        mSholder.service_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
                    }
                    break;
                case 6://换号 审核
//                    6.我要换号 （使用服务）  （1换号审核中  2脚本响应超时，3登录异常，4未绑定银行卡）
                    switch (newDeviceWechat.getAccountStatus()) {
                        case 1://1换号审核中
                            setLlProgressShow(mSholder, newDeviceWechat.getServiceStatus(), newDeviceWechat.getServiceCount(), false);
                            setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_account_hc_bg, null);
                            break;
                        case 2://2脚本响应超时
                        case 3://3登录异常
                        case 4://4未绑定银行卡
                            setLlProgressShow(mSholder, newDeviceWechat.getServiceStatus(), newDeviceWechat.getServiceCount(), false);
                            setUserServiceBnt(mSholder, newDeviceWechat.getBntText(), R.drawable.select_logout_button_bg, setOnClick(newDeviceWechat));
                            mSholder.service_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
                            break;
                    }
                    break;
            }

            //审核状态 ; 1 未审核 2 审核中 3 审核通过  （4 脚本响应超时 5 审核失败（ 微信在设备未登录，微信账号密码错误，封号） 6 未绑定银行卡，未实名）

            if(deviceWechat.getAuditStatus()==null)
                deviceWechat.setAuditStatus(0);

            switch (deviceWechat.getAuditStatus()){

                case 1://1 未审核
                    mSholder.account_status_tv.setVisibility(View.VISIBLE);//显示设备状态
                    mSholder.account_status_tv.setBackgroundResource(R.drawable.shape_account_shenhe_bj_red);
                    mSholder.account_status_tv.setText(R.string.wei_shenhe);
//                    mSholder.item_ll_root.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.showReAuditAccount(newDeviceWechat);
//                        }
//                    });
//                    mSholder.device_status.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.showReAuditAccount(newDeviceWechat);
//                        }
//                    });

                    break;
                case 2://2 审核中
                    mSholder.account_status_tv.setVisibility(View.VISIBLE);//显示设备状态
                    mSholder.account_status_tv.setBackgroundResource(R.drawable.shape_account_shenhe_bj_bule);
                    mSholder.account_status_tv.setText(R.string.shenhezhong);
                    break;
                case 3://3 审核通过
                    mSholder.account_status_tv.setVisibility(View.VISIBLE);//显示设备状态
                    mSholder.account_status_tv.setBackgroundResource(R.drawable.shape_account_shenhe_bj_bule);
                    mSholder.account_status_tv.setText(R.string.shenhe_pass);
//                    mSholder.item_ll_root.setOnClickListener(null);
//                    mSholder.device_status.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.showChangeAccount(newDeviceWechat);
//                        }
//                    });
                    break;
                case 4://4 脚本响应超时
                    mSholder.account_status_tv.setVisibility(View.VISIBLE);//显示设备状态
                    mSholder.account_status_tv.setBackgroundResource(R.drawable.shape_account_shenhe_bj_red);
                    mSholder.account_status_tv.setText(R.string.shenhe_weitongguo);
//                    mSholder.device_status.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.showAccountLoginException4(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
//                        }
//                    });

//                    mSholder.item_ll_root.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.showAccountLoginException4(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
//                        }
//                    });


                    break;
                case 5:// 5 审核失败
                    mSholder.account_status_tv.setVisibility(View.VISIBLE);//显示设备状态
                    mSholder.account_status_tv.setBackgroundResource(R.drawable.shape_account_shenhe_bj_red);
                    mSholder.account_status_tv.setText(R.string.shenhe_weitongguo);
//                    mSholder.device_status.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.showAccountLoginException5(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
//                        }
//                    });
//                    mSholder.item_ll_root.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.showAccountLoginException5(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
//                        }
//                    });
                    break;
                case 6://6 未绑定银行卡，未实名
                   mSholder.account_status_tv.setVisibility(View.VISIBLE);//显示设备状态
                    mSholder.account_status_tv.setBackgroundResource(R.drawable.shape_account_shenhe_bj_red);
                    mSholder.account_status_tv.setText(R.string.shenhe_weitongguo);
//                    mSholder.device_status.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.userFailureNoMonney(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
//                        }
//                    });
//                    mSholder.item_ll_root.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mPresenter.userFailureNoMonney(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
//                        }
//                    });

                    break;
                default:
                    mSholder.account_status_tv.setVisibility(View.GONE);//显示设备状态
            }
        } else if (holder instanceof BottomViewHolder) {
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            bottomViewHolder.time_status.setText(R.string.account_no_user_buy_service2);
            bottomViewHolder.time_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.buyNewService();
                }
            });
        }
    }

    /**
     * 1.添加账号  2等待审核 3.选择操作 4.查看详情(服务中,空闲中,账号登出)
     * <p>
     * 5.查看原因(1脚本响应超时，2登录异常，3未绑定银行卡，4账号异常)
     *
     * @param newDeviceWechat
     */
    private View.OnClickListener setOnClick(DeviceWechat newDeviceWechat) {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bntClick(newDeviceWechat);
            }
        };
        return onClickListener;
    }

    private void bntClick(DeviceWechat newDeviceWechat) {
        switch (newDeviceWechat.getBntStatus()) {
            case 1://添加账号
                mPresenter.addAccount(newDeviceWechat, AddAccountActivity.ADD_OR_EDIT, AddAccountActivity.ADD_ACCOUNT_TITLE);
                break;
            case 2://2等待审核
                break;
            case 3://选择操作
                mPresenter.showSelectOprator(newDeviceWechat);
                break;
            case 4://查看详情
                mPresenter.lookServiceDetails(newDeviceWechat);
                break;
            case 5://查看原因
                switch (newDeviceWechat.getAccountStatus()) {
                    case 1://1脚本响应超时
//                        mPresenter.showAccountLoginException4(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
                        mPresenter.lookServiceDetails(newDeviceWechat);
                        break;
                    case 2://2登录异常
//                        mPresenter.showAccountLoginException5(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
                        mPresenter.lookServiceDetails(newDeviceWechat);
                        break;
                    case 3://3未绑定银行卡
//                        mPresenter.userFailureNoMonney(newDeviceWechat, newDeviceWechat.getServiceNum()<=0,false);
                        mPresenter.lookServiceDetails(newDeviceWechat);
                        break;
                    case 4://4账号异常
                        mPresenter.accountException(newDeviceWechat);
//                        mPresenter.lookServiceDetails(newDeviceWechat);
                        break;
                }
                break;
            case 6://查看原因
                switch (newDeviceWechat.getAccountStatus()) {
                    case 2://1脚本响应超时
//                        mPresenter.showAccountLoginException4(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
                        mPresenter.lookServiceDetails(newDeviceWechat);
                        break;
                    case 3://2登录异常
//                        mPresenter.showAccountLoginException5(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
                        mPresenter.lookServiceDetails(newDeviceWechat);
                        break;
                    case 4://3未绑定银行卡
//                        mPresenter.userFailureNoMonney(newDeviceWechat,newDeviceWechat.getServiceNum()<=0, false);
                        mPresenter.lookServiceDetails(newDeviceWechat);
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    /**
     * 设置账号文字
     */
    private void setAccountStatus(AccountTabHolder mSholder, String text, int textColor) {
        //账号
        mSholder.account_status.setText(text); //帐号名称
        mSholder.account_status.setTextColor(mContext.getResources().getColor(textColor));
    }

    /**
     * y隐藏 状态 和 数量
     *
     * @param mSholder
     */
    private void goneStatusAndProgress(AccountTabHolder mSholder) {
        mSholder.ll_progress.setVisibility(View.GONE); //显示进度条
        mSholder.ll_service_status.setVisibility(View.GONE); //显示状态
    }

    /**
     * 只显示状态 不显示 舒服数量
     */
    private void showStatusGoneProgress(AccountTabHolder mSholder, String status) {
        mSholder.ll_progress.setVisibility(View.GONE); //显示进度条
        mSholder.ll_service_status.setVisibility(View.VISIBLE); //显示状态
        mSholder.service_status.setText(status);//服务状态
    }

    /**
     * 服务运行状态 添加账号 审核通过后
     *
     * @param mSholder
     * @param status
     * @param count
     */
    private void setLlProgressShow(AccountTabHolder mSholder, String status, String count, boolean isRed) {
        mSholder.ll_progress.setVisibility(View.VISIBLE); //显示进度条
        mSholder.ll_service_status.setVisibility(TextUtils.isEmpty(status) ? View.GONE:View.VISIBLE); //显示状态
        mSholder.service_status.setText(status);//服务状态
        mSholder.service_count.setText(count);//服务数
    }

    private void setRed(AccountTabHolder mSholder, boolean isRed) {
        if (isRed) {
            mSholder.tv_service_count.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
            mSholder.tv_service_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
            mSholder.service_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
            mSholder.service_count.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
            mSholder.account_name.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
            mSholder.account_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
        } else {
            mSholder.tv_service_count.setTextColor(mContext.getResources().getColor(R.color.service_font));
            mSholder.tv_service_status.setTextColor(mContext.getResources().getColor(R.color.service_font));
            mSholder.service_status.setTextColor(mContext.getResources().getColor(R.color.service_font));
            mSholder.service_count.setTextColor(mContext.getResources().getColor(R.color.service_font));
            mSholder.account_name.setTextColor(mContext.getResources().getColor(R.color.service_font));
//            mSholder.account_status.setTextColor(mContext.getResources().getColor(R.color.me_7D7D7D));
        }
    }


    /**
     * 右边button 状态
     *
     * @param mSholder
     * @param text
     * @param bntBg
     * @param onClickListener
     */
    private void setUserServiceBnt(AccountTabHolder mSholder, String text, int bntBg, View.OnClickListener onClickListener) {
        mSholder.bt_use_service.setText(text);//服务状态
        mSholder.bt_use_service.setBackgroundResource(bntBg);
        mSholder.bt_use_service.setOnClickListener(onClickListener);
    }

    /**
     * s设置 按钮 颜色
     *
     * @param mSholder
     * @param text
     * @param bntBg
     */
    private void setUserServiceBntBg(AccountTabHolder mSholder, int bntBg) {
        mSholder.bt_use_service.setBackgroundResource(bntBg);
    }


}
