package com.kongrongqi.shopmall.modules.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.WorkingModel;
import com.kongrongqi.shopmall.modules.task.holder.WorkingHolder;
import com.kongrongqi.shopmall.modules.task.presenter.StopPresenter;
import com.kongrongqi.shopmall.modules.task.presenter.WorkingPresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/22 0022 on 20:10
 * 作者:penny
 */
public class StopAdapter extends BaseRecycleViewAdapter {
    private final Context mContext;
    private final StopPresenter mPresenter;

    public StopAdapter(Context context, StopPresenter presenter) {
        this.mPresenter = presenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_working, parent, false);
        viewHolder = new WorkingHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WorkingHolder mHolder = (WorkingHolder) holder;
        WorkingModel model = (WorkingModel) datas.get(position);
        String account = "";
        String data = "";
        //运行状态  异常情况   0 未使用  1 服务中 2 已完成 3 异常  5 暂停  status
//        setError(mHolder,model);
        mHolder.mServiceName.setText(model.getName());//服务名称
        switch (model.getType()) {
            case 1:
                break;
            case 2://A
//                addFried(mHolder, model.getTodayFansNum(), model.getFansNum());
//                goneAddfried(mHolder);
                setProgress(mHolder, model.getProgress() + "", model.getDuration(),"天");

                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
            case 3://B
//                addFried(mHolder, model.getTodayFansNum(), model.getFansNum());
//                goneAddfried(mHolder);
//                setProgress(mHolder, model.getFansNum() + "", model.getUsedRate(),"个");
                setProgress(mHolder, model.getProgress() + "", model.getDuration(),"天");
                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());  //1号机- 我的落满地
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);

                break;
            case 4://入群
//                addFried(mHolder,model.getGroupName());
//                goneAddfried(mHolder);
                setProgress(mHolder, model.getProgress() + "", "1","个");


                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());  //1号机- 我的落满地
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
            case 5://托管
//                goneAddfried(mHolder);
                setProgress(mHolder, model.getProgress() + "", model.getDuration(),"天");

                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());  //1号机- 我的落满地
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
            case 6://C
//                addFried(mHolder, model.getTodayFansNum(), model.getFansNum());
//                goneAddfried(mHolder);
                setProgress(mHolder, model.getProgress() + "", model.getDuration(),"天");

                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());  //1号机- 我的落满地
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
        }
        goneAddfried(mHolder,model);
    }


    /**
     * 布局左下方   B（账号 数据）   C（账号 类型） 其他（设备 账号）
     *
     * @param mHolder
     * @param account
     * @param data
     */
    private void leftBottom(WorkingHolder mHolder, String account, String data) {
        mHolder.devices.setText(account);
        mHolder.mAccountName.setText(data);
    }


    /**
     * 设置进度条
     *
     * @param mHolder
     * @param progress
     * @param max
     */
    private void setProgress(WorkingHolder mHolder, String progress, String max,String unit) {

        //防止复用导致进度错乱
        mHolder.mProgressBar.setProgress(10);
        mHolder.mProgressBar.setMax(90);


        int progressInt = StringUtils.getStrToInt(progress);
        int maxInt = StringUtils.getStrToInt(max);
        mHolder.mProgressBar.setProgress(progressInt);
        mHolder.mProgressBar.setMax(maxInt);
        mHolder.progress_text.setText(progressInt + "/" + maxInt+unit);
        mHolder.mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape2_fb9f9f));
    }


    /**
     * 当日新增 + 加友总量
     *
     * @param mHolder
     * @param todayFansNum
     * @param total
     */
    private void addFried(WorkingHolder mHolder, int todayFansNum, int total) {

        mHolder.mWorkingData.setVisibility(View.VISIBLE);
        String totalStr = StringUtils.jointStr("好友总量", total + "", "");
        mHolder.mWorkingData.setText(totalStr);

        mHolder.accountError.setVisibility(View.VISIBLE);
        String todayFansNumStr = StringUtils.jointStr("当日新增", todayFansNum + "", "");
        mHolder.accountError.setText(todayFansNumStr);
    }

    /**
     * 当日新增 + 加友总量
     *
     * @param mHolder
     * @param todayFansNum
     * @param total
     */
    private void addFried(WorkingHolder mHolder, String data) {
        mHolder.accountError.setVisibility(View.INVISIBLE);
        mHolder.mWorkingData.setVisibility(View.VISIBLE);
        String totalStr = StringUtils.jointStr("目标", data, "");
        mHolder.mWorkingData.setText(totalStr);
    }

    private void goneAddfried(WorkingHolder mHolder, WorkingModel model) {
        mHolder.accountError.setVisibility(View.GONE);
        mHolder.mWorkingData.setVisibility(View.GONE);

        if(TextUtils.isEmpty(model.getMsg())){
            mHolder.bt_right_restart.setText("重启服务");
            mHolder.bt_right_restart.setBackgroundResource(R.drawable.select_logout_button_bg);
            mHolder.bt_right_restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.stopServiceDialog(model);
                }
            });
        }else{
            mHolder.bt_right_restart.setText(model.getMsg());
            mHolder.bt_right_restart.setOnClickListener(null);
            mHolder.bt_right_restart.setBackgroundResource(R.drawable.shape_login_edit_bg);
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

//    public void setError(WorkingHolder mHolder,WorkingModel model) {
//        if (model.getStatus() == 3 || model.getStatus()==5) {   //异常情况   0 未使用  1 服务中 2 已完成 3 异常  5 暂停  status
//            //只要是异常 都会显示 失效时间
//            mHolder.accountError.setVisibility(View.VISIBLE);
//            String totalStr = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
//            mHolder.mWorkingData.setVisibility(View.INVISIBLE);
//            mHolder.accountError.setText(totalStr);  //失效时间
////            StringUtils.underLineText(mHolder.accountError);//下划线
//            mHolder.mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape2_red));
//        } else  {
//            mHolder.mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape2));
//        }
//    }
}
