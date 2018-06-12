package com.kongrongqi.shopmall.modules.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.model.WorkingModel;
import com.kongrongqi.shopmall.modules.task.holder.FinishHolder;
import com.kongrongqi.shopmall.modules.task.holder.WorkingHolder;
import com.kongrongqi.shopmall.modules.task.presenter.FinishPresenter;
import com.kongrongqi.shopmall.utils.StringUtils;

/**
 * 创建日期：2017/5/22 0022 on 20:10
 * 作者:penny
 */
public class FinishAdapter extends BaseRecycleViewAdapter {
    private final Context mContext;
    private final FinishPresenter mPresenter;


    public FinishAdapter(Context context, FinishPresenter presenter) {
        this.mPresenter = presenter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finish, parent, false);
        return new FinishHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FinishHolder mHolder = (FinishHolder) holder;
        WorkingModel model= (WorkingModel) datas.get(position);
        String account = "";
        String data = "";
        mHolder.mServiceName.setText(model.getName());//服务名称
        setProgress(mHolder,position);
        switch (model.getType()) {
            case 1:
                break;
            case 2://A
                addFried(mHolder, model.getFansNum(),model.getExpireTime());

                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
            case 3://B
                addFried(mHolder, model.getFansNum(), model.getExpireTime());

                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
            case 4://入群
                goneAddfried(mHolder); //没有加友总量
                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());  //1号机- 我的落满地
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
            case 5://托管
                goneAddfried(mHolder);
                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
            case 6://C
                addFried(mHolder, model.getFansNum(),model.getExpireTime());
                account = StringUtils.jointStr(model.getUserDeviceName(), model.getWechatNo());
                data = StringUtils.jointStr("失效时间", model.getExpireTime(), "");
                leftBottom(mHolder, account, data);
                break;
        }
        goneAddfried(mHolder);
    }


    /**
     * 布局左下方   B（账号 数据）   C（账号 类型） 其他（设备 账号）
     *
     * @param mHolder
     * @param account
     * @param data
     */
    private void leftBottom(FinishHolder mHolder, String account, String data) {
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
    private void setProgress(FinishHolder mHolder,int position) {
        mHolder.mPregressBar.setMax(10);
        mHolder.mPregressBar.setProgress(10);
        mHolder.progress_text.setText(mContext.getResources().getString(R.string.finish_1));
        mHolder.mPregressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_shape_eff3ff));
    }

    /**
     * 加友总量 + 失效时间
     * @param mHolder
     * @param todayFansNum
     * @param total
     */
    private void addFried(FinishHolder mHolder, int data,String expireTime) {
        mHolder.mData.setVisibility(View.VISIBLE);
        String totalStr = StringUtils.jointStr("好友总量", data+"", "");
        mHolder.mData.setText(totalStr);

//        mHolder.mFansTotal.setVisibility(View.VISIBLE);
//        String totalStr2 = StringUtils.jointStr("失效时间", expireTime, "");
//        mHolder.mFansTotal.setText(totalStr2);
    }

    /**
     * 隐藏 加友总量
     * @param mHolder
     */
    private void goneAddfried(FinishHolder mHolder){
        mHolder.mData.setVisibility(View.INVISIBLE);
        mHolder.mFansTotal.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
