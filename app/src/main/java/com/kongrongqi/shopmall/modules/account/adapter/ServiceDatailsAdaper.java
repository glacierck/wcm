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
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.bean.UserService;
import com.kongrongqi.shopmall.modules.account.holder.AccountInfoHeaderBntHolder;
import com.kongrongqi.shopmall.modules.account.holder.BottomViewHolder;
import com.kongrongqi.shopmall.modules.account.holder.ServiceDatailsHolder;
import com.kongrongqi.shopmall.modules.account.presenter.ServiceDatailsPresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/20 0020 on 18:41
 * 作者:penny
 */
public class ServiceDatailsAdaper extends BaseRecycleViewAdapter {
    private final ServiceDatailsPresenter mPresenter;
    private final Context mContext;
    //item类型
    public static final int ITEM_TYPE_HEADER = 100;
    public static final int ITEM_TYPE_CONTENT = 101;
    public static final int ITEM_TYPE_BOTTOM = 102;
    private int mHeaderCount = 0;//头部View个数
    private int mBottomCount = 0;//底部View个数

    public DeviceWechat deviceWechat;

    public ServiceDatailsAdaper(ServiceDatailsPresenter servicePresenter, Context context) {
        this.mPresenter = servicePresenter;
        this.mContext = context;
    }

    public ServiceDatailsAdaper(ServiceDatailsPresenter serviceDatailsPresenter, Context context, DeviceWechat deviceWechat) {
        this.mPresenter = serviceDatailsPresenter;
        this.mContext = context;
        this.deviceWechat = deviceWechat;
    }

    //内容长度
    public int getContentItemCount() {
        return datas.size();
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_info_bnt, parent, false);
            viewHolder = new AccountInfoHeaderBntHolder(view);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_datails, parent, false);
            viewHolder = new ServiceDatailsHolder(view);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_service_foot, parent, false);
            viewHolder = new BottomViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AccountInfoHeaderBntHolder) {
            AccountInfoHeaderBntHolder headerViewHolderTab = (AccountInfoHeaderBntHolder) holder;
            //1 进行中  2 已完成 3暂停 4 已取消 5 等待中  today_finish
            if (mPresenter.getDeviceWechat() != null) {
                if (mPresenter.getDeviceWechat().getHasTodayInProgress() == 1) {//是否有今日进行中服务  1 表示有
                    headerViewHolderTab.bt_change_account.setText("我要换号");
                    headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.select_logout_button_bg);//红色
                    headerViewHolderTab.me_use.setText("我要用号");
                    headerViewHolderTab.me_use.setBackgroundResource(R.drawable.select_logout_button_bg);  //红色
                } else {
                    headerViewHolderTab.bt_change_account.setText("我要换号");
                    headerViewHolderTab.bt_change_account.setBackgroundResource(R.drawable.select_service_details_blue_bg);//蓝色
                    headerViewHolderTab.me_use.setText("我要用号");
                    headerViewHolderTab.me_use.setBackgroundResource(R.drawable.select_service_details_blue_bg);
                    headerViewHolderTab.me_use.setEnabled(true);
                }
                headerViewHolderTab.user_service.setText("使用服务");
                headerViewHolderTab.user_service.setBackgroundResource(R.drawable.select_login_button_bg);  //灰色
                headerViewHolderTab.user_service.setEnabled(true);
                if (mPresenter.getDeviceWechat().getState() != null && mPresenter.getDeviceWechat().getState() == 9) {//账号登出
                    headerViewHolderTab.me_use.setText("账号登出");
                    headerViewHolderTab.me_use.setBackgroundResource(R.drawable.shape_login_edit_bg);
                    headerViewHolderTab.me_use.setEnabled(false);

                    headerViewHolderTab.user_service.setText("使用服务");
                    headerViewHolderTab.user_service.setBackgroundResource(R.drawable.service_dails_user_bg);  //灰色
                    headerViewHolderTab.user_service.setEnabled(false);
                }
                //账号异常  跳转至设备
                if (mPresenter.getDeviceWechat().getState() != null && mPresenter.getDeviceWechat().getState() == 7) {//账号异常
                    mPresenter.finishActivity();
                }
                if (mPresenter.getDeviceWechat().getServiceNum() <= 0) {
                    headerViewHolderTab.me_use.setText("我要用号");
                    headerViewHolderTab.me_use.setBackgroundResource(R.drawable.select_service_details_blue_bg);
                    headerViewHolderTab.me_use.setEnabled(true);
                }

            }
            //我要换号
            headerViewHolderTab.bt_change_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.showMeChangeDialogEnter(mPresenter.getDeviceWechat(), AddAccountActivity.CHANGE_ACCOUNT, AddAccountActivity.CHANGE_ACCOUNT_TITLE);
                }
            });
            //我要用号
            headerViewHolderTab.me_use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.getServerStart(mPresenter.getDeviceWechat());
                }
            });
            //使用服务
            headerViewHolderTab.user_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.startAddServiceTab();
                }
            });

        } else if (holder instanceof ServiceDatailsHolder) {
            ServiceDatailsHolder mSholder = (ServiceDatailsHolder) holder;
            UserService userService = (UserService) getDatas().get(position - mHeaderCount);
            //，name 服务名称， progress 进度
            //  progressRate 进度比率, fansNum 灌粉总量, todayFansNum 今日灌粉量(灌粉服务A), usedRate 已使用数据占比(灌粉服务B), groupName 群名称
            mSholder.service_name.setText(userService.getName());
            int max = 0;
            int progress = 0;
            String progress1 = "0";
            String max1 = "0";
            String maxUnit = "天";//进度条 单位

            max = StringUtils.getStrToInt(userService.getDuration());
            progress = StringUtils.getStrToInt(userService.getProgress());
            max1 = TextUtils.isEmpty(userService.getDuration()) ? "0" : userService.getDuration();
            progress1 = TextUtils.isEmpty(userService.getProgress()) ? "0" : userService.getProgress();

            mSholder.service_status.setText(userService.getTodayMsg());
//            mSholder.service_status.setText("已取消".equals(userService.getTodayMsg())?"":userService.getTodayMsg());

            showRightCount(mSholder, true);

            // type 服务类型 1 账号号槽 2 灌粉服务A 3 灌粉服务B 4 入群服务 5 养号服务
            switch (userService.getType()) {
                case 1:
                    showRightCount(mSholder, false);
                    break;
                case 2:// 加友服务A
                    setLeft(mSholder, userService.getExpireTime(), userService.getTodayServiceTime());
                    setRightCount(mSholder, userService.getFansNum(), userService.getTodayFansNum());
                    break;
                case 3:// 加友服务B
//                    max = StringUtils.getStrToFloat(userService.getUsedRate());
//                    max1 = userService.getUsedRate();
//                    maxUnit = "个";
//                    progress = StringUtils.getStrToFloat(userService.getFansNum());
//                    progress1 = userService.getFansNum();
                    setLeft(mSholder, userService.getExpireTime(), userService.getTodayServiceTime());
                    setRightCount(mSholder, userService.getFansNum(), userService.getTodayFansNum());
                    break;
                case 4:// 入群服务
                    max1 = "1";
                    maxUnit = "个";
                    showRightCount(mSholder, false);
                    showRightRestart(mSholder, false);
                    setLeft(mSholder, userService.getExpireTime(), userService.getTodayServiceTime());
                    break;
                case 5:// 养号服务
                    showRightCount(mSholder, false);
                    showRightRestart(mSholder, false);
                    setLeft(mSholder, userService.getExpireTime(), userService.getTodayServiceTime());
                    break;
                case 6:// 加友服务C
                    showRightCount(mSholder, false);
                    showRightRestart(mSholder, false);
//                    setRightText(mSholder, userService.getFansNum(), userService.getTodayFansNum());
                    setLeft(mSholder, userService.getExpireTime(), userService.getTodayServiceTime());
                    break;
            }
//            mSholder.progress.setMaxProgress(max);
            //防止复用导致进度错乱
            mSholder.progress.setProgress(10);
            mSholder.progress.setMax(90);

            mSholder.progress.setMax(max);
            mSholder.progress.setProgress(progress);
            mSholder.progress_text.setText(progress1 + "/" + max1 + maxUnit);
            mSholder.progress.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape2));

//            mSholder.progress.setProgressText(progress1 + "/" + max1+maxUnit);
//            mSholder.progress.setProgressColor(mContext.getResources().getColor(R.color.progress_default));

            //暂停  并且 显示暂停按钮
            if (userService.getStatus() == 3) {
                mSholder.progress.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape2_fb9f9f));
                showRightRestart(mSholder, false);
            } else {
                if (userService.getTodayStatus() == 3 ) {//0 次日生效（1 进行中 4 已取消 5 等待中) 2 已完成 3 暂停 today_msg xxxx
                    mSholder.progress.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape2_fb9f9f));
                    showRightCount(mSholder, false);
                    showRightRestart(mSholder, true);

                    mSholder.bt_right_restart.setText("重启服务");
                    mSholder.bt_right_restart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.restartServiceDialog(userService);
                        }
                    });
                } else if(userService.getTodayStatus() == 1 || userService.getTodayStatus() == 5){
                    mSholder.bt_right_restart.setText("暂停服务");
                    showRightRestart(mSholder, true);
                    mSholder.bt_right_restart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.stopServiceDialog(userService);
                        }
                    });
                }else{
                    mSholder.progress.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape2));
                    showRightRestart(mSholder, false);
                }
            }
        } else if (holder instanceof BottomViewHolder) {
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            bottomViewHolder.time_status.setText(R.string.buy_more_service);
            bottomViewHolder.time_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.startAddServiceTab();
                }
            });
        }
    }

    private void setRightCount(ServiceDatailsHolder mSholder, String fansNum, String todayFansNum) {
        mSholder.right_today.setVisibility(View.GONE);
        mSholder.add_friend_count.setText(fansNum);
        mSholder.today_add_count.setText(todayFansNum);
    }


    /**
     * @param mSholder
     * @param failureTime 失效时间按
     * @param service     服务
     */
    private void setLeft(ServiceDatailsHolder mSholder, String failureTime, String service) {
        mSholder.failure_time.setText(failureTime);
        mSholder.service.setText(service);
    }


    private void showRightCount(ServiceDatailsHolder mSholder, boolean isShow) {
        mSholder.right_today.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void showRightRestart(ServiceDatailsHolder mSholder, boolean isShow) {
        mSholder.right_restart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }
}
