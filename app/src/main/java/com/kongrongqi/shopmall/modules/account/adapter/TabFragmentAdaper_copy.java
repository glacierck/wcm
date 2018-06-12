package com.kongrongqi.shopmall.modules.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class TabFragmentAdaper_copy extends BaseRecycleViewAdapter {
    private final TabPresenter mPresenter;
    private final Context mContext;
    //item类型
    public static final int ITEM_TYPE_HEADER = 100;
    public static final int ITEM_TYPE_CONTENT = 101;
    public static final int ITEM_TYPE_BOTTOM = 102;


    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 1;//底部View个数

    private Device device;

    public TabFragmentAdaper_copy(TabPresenter servicePresenter, Context context, Device device) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_tab, parent, false);
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
            HeaderViewHolderTab headerViewHolderTab = (HeaderViewHolderTab) holder;
            headerViewHolderTab.account_name.setText("IMEI码：");
            headerViewHolderTab.account_status.setText(device != null ? device.getMachineCode() : "000000");
            headerViewHolderTab.bt_use_service.setText("替换");
            headerViewHolderTab.bt_use_service.setBackgroundResource(R.drawable.select_logout_button_bg);
            headerViewHolderTab.device_status.setVisibility(View.VISIBLE);//显示设备状态
            if (device.getState() == 1) {//正常
                headerViewHolderTab.device_status.setImageResource(R.drawable.shebei_normal);
                headerViewHolderTab.device_status.setOnClickListener(null);
            } else { //异常
                headerViewHolderTab.device_status.setImageResource(R.drawable.shebei_unnormal);
                headerViewHolderTab.device_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //
                        mPresenter.showErroyDevice();
                    }
                });
            }
            headerViewHolderTab.bt_use_service.setOnClickListener(new View.OnClickListener() { //替换
                @Override
                public void onClick(View v) {
                    mPresenter.replace(device);
                }
            });

        } else if (holder instanceof AccountTabHolder) {

            DeviceWechat deviceWechat = (DeviceWechat) datas.get(position - mHeaderCount);
            AccountTabHolder mSholder = (AccountTabHolder) holder;
            mSholder.account_name.setText("账号 " + deviceWechat.getSn() + ":");

            Integer serviceNum = deviceWechat.getServiceNum();


            if (device.getState() == 2) { //设备异常
                //账号
                setAccountStatus(mSholder, "设备异常", R.color.cl_ffff0000);
                //隐藏 状态 和数量
                goneStatusAndProgress(mSholder);
                //设置 右边button
                setUserServiceBnt(mSholder, "查看详情", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.showErroyDevice();
                    }
                });
                return;
            }

            switch (deviceWechat.getHasGroove()) { //是否购买号槽  1:表示已经买了
                case 0://没买
                    //账号
                    setAccountStatus(mSholder, "尚未购买", R.color.me_7D7D7D);
                    //隐藏 状态 和数量
                    goneStatusAndProgress(mSholder);

                    if (position - mHeaderCount >= 1) { //第二个号槽
                        DeviceWechat o = (DeviceWechat) datas.get(position - mHeaderCount - 1); //取上一个对象
                        if (1 == o.getHasGroove()) { //买过
                            //设置 右边button
                            setUserServiceBnt(mSholder, "购买号槽", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.buySlot(deviceWechat);
                                }
                            });
                        } else { //上个账号没买号槽，这个就不能买
                            //设置 右边button
                            setUserServiceBnt(mSholder, "购买号槽", R.drawable.select_account_hc_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showMessage(mContext, "每台设备的号槽必须按照账号顺序购买");
                                }
                            });
                        }
                    } else {
                        //设置 右边button
                        setUserServiceBnt(mSholder, "购买号槽", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.buySlot(deviceWechat);
                            }
                        });
                    }
                    break;
                case 1:
                    //  1 未添加 2 审核中 3 未使用 4 脚本响应超时 5 审核失败（ 微信在设备未登录，微信账号密码错误，封号）
                    // 6 未绑定银行 卡，未实名  7 账号异常（执行时） 8 空闲中（逻辑判断）9 账号登出  10 服务中（逻辑判断）
                    switch (deviceWechat.getState()) {//账号状态
                        case 1://1 未添加   表示还没有绑定微信
                            //账号
                            setAccountStatus(mSholder, "尚未添加", R.color.me_7D7D7D);
                            //隐藏 状态 和数量
                            goneStatusAndProgress(mSholder);
                            if (position - mHeaderCount >= 1) { //第二个号槽
                                DeviceWechat o = (DeviceWechat) datas.get(position - mHeaderCount - 1); //取上一个对象
                                if (1 == o.getState()) { //1 表示还没有绑定微信
                                    //设置 右边button
                                    setUserServiceBnt(mSholder, "添加账号", R.drawable.select_account_hc_bg, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ToastUtil.showMessage(mContext, "每台设备的账号必须按照账号顺序添加");
                                        }
                                    });
                                } else { //上个账号没买号槽，这个就不能买
                                    //设置 右边button
                                    setUserServiceBnt(mSholder, "添加账号", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mPresenter.addAccount(deviceWechat, AddAccountActivity.ADD_OR_EDIT,AddAccountActivity.ADD_ACCOUNT_TITLE);
                                        }
                                    });
                                }
                            } else {
                                //设置 右边button
                                setUserServiceBnt(mSholder, "添加账号", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPresenter.addAccount(deviceWechat, AddAccountActivity.ADD_OR_EDIT,AddAccountActivity.ADD_ACCOUNT_TITLE);
                                    }
                                });
                            }
                            break;
                        case 2://2 审核中
                            //账号
                            setAccountStatus(mSholder, deviceWechat.getWechatNo(), R.color.cl_7895F3);
                            //显示状态  隐藏 数量
                            showStatusGoneProgress(mSholder, "等待审核中，请稍候");

                            //设置 右边button
                            setUserServiceBnt(mSholder, "等待审核", R.drawable.select_account_hc_bg, null);

                            break;
                        case 3://3 未使用
                            //账号
                            setAccountStatus(mSholder, deviceWechat.getWechatNo(), R.color.cl_7895F3);
                            //显示状态和数量
                            setLlProgressShow(mSholder, "未使用", serviceNum, false);
                            //设置 右边button
                            setUserServiceBnt(mSholder, "选择操作", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.showSelectOprator(deviceWechat);
                                }
                            });
                            break;
                        case 4://4.审核 脚本响应超时（审核失败）
                            //账号
                            setAccountStatus(mSholder, deviceWechat.getWechatNo(), R.color.cl_7895F3);
                            //显示状态 隐藏数量
                            showStatusGoneProgress(mSholder, "审核失败");
                            //设置 右边button
                            setUserServiceBnt(mSholder, "查看原因", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    mPresenter.showAccountLoginException4(deviceWechat,deviceWechat.getServiceNum()<=0,false);
                                }
                            });

                            break;
                        case 5://审核 登录失败（审核失败）
                            //账号
                            setAccountStatus(mSholder, deviceWechat.getWechatNo(), R.color.cl_7895F3);
                            //显示状态  隐藏 数量
                            showStatusGoneProgress(mSholder, "审核失败");
                            //设置 右边button
                            setUserServiceBnt(mSholder, "查看原因", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    mPresenter.showAccountLoginException5(deviceWechat,deviceWechat.getServiceNum()<=0,false);
                                }
                            });
                            break;

                        case 6://6：没有银行卡 或 未实名认证（审核失败）
                            //账号
                            setAccountStatus(mSholder, deviceWechat.getWechatNo(), R.color.cl_7895F3);
                            //显示状态  隐藏 数量
                            showStatusGoneProgress(mSholder, "审核失败");

                            setUserServiceBnt(mSholder, "查看原因", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    mPresenter.userFailureNoMonney(deviceWechat,deviceWechat.getServiceNum()<=0,false);
                                }
                            });
                            break;

                        case 7://执行中 账号异常

                            //账号
                            setAccountStatus(mSholder, deviceWechat.getWechatNo(), R.color.cl_7895F3);
                            //显示状态 和 数量
                            setLlProgressShow(mSholder, "账号异常", serviceNum, true);

                            setUserServiceBnt(mSholder, "查看详情", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.accountException(deviceWechat);
                                }
                            });
                            break;
                        //8 空闲中（逻辑判断）9 账号登出  10 服务中（逻辑判断）
                        case 8:// 8空闲中
                            //显示状态和数量
                            setLlProgressShow(mSholder, "空闲中", serviceNum, false);
                            //设置 右边button
                            setUserServiceBnt(mSholder, "查看详情", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.lookServiceDetails(deviceWechat);
                                }
                            });
                            break;
                        case 9://9 账号登出
                            //显示状态和数量
                            setLlProgressShow(mSholder, "账号登出", serviceNum, false);
                            //设置 右边button
                            setUserServiceBnt(mSholder, "查看详情", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.lookServiceDetails(deviceWechat);
                                }
                            });
                            break;
                        case 10://10 服务中
                            //显示状态和数量
                            setLlProgressShow(mSholder, "服务中", serviceNum, false);
                            //设置 右边button
                            setUserServiceBnt(mSholder, "查看详情", R.drawable.select_logout_button_bg, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.lookServiceDetails(deviceWechat);
                                }
                            });
                            break;

                    }
            }
        } else if (holder instanceof BottomViewHolder) {
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            bottomViewHolder.time_status.setText(R.string.account_no_user_buy_service);
            bottomViewHolder.time_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.buyNewService();
                }
            });
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
    private void setLlProgressShow(AccountTabHolder mSholder, String status, int count, boolean isRed) {
        mSholder.ll_progress.setVisibility(View.VISIBLE); //显示进度条
        mSholder.ll_service_status.setVisibility(View.VISIBLE); //显示状态
        mSholder.service_status.setText(status);//服务状态
        mSholder.service_count.setText(count + "个");//服务数
//        if (isRed) {
//            mSholder.tv_service_count.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
//            mSholder.tv_service_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
//            mSholder.account_name.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
//            mSholder.account_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
//        }else{
//            mSholder.tv_service_count.setTextColor(mContext.getResources().getColor(R.color.me_7D7D7D));
//            mSholder.tv_service_status.setTextColor(mContext.getResources().getColor(R.color.logout_bt_bj));
//            mSholder.account_name.setTextColor(mContext.getResources().getColor(R.color.service_font));
//            mSholder.account_status.setTextColor(mContext.getResources().getColor(R.color.me_7D7D7D));
//        }
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

}
