package com.kongrongqi.shopmall.modules.task.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.modules.account.holder.AccountTabHolder;
import com.kongrongqi.shopmall.modules.account.holder.BottomViewHolder;
import com.kongrongqi.shopmall.modules.account.holder.HeaderViewHolderTab;
import com.kongrongqi.shopmall.modules.model.BindWechatAccountModel;
import com.kongrongqi.shopmall.modules.task.holder.UseServiceHolder;
import com.kongrongqi.shopmall.modules.task.presenter.UseServicePresenter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/5/23 0023 on 13:13
 * 作者:penny
 */
public class UseServiceAdapter extends BaseRecycleViewAdapter {
    public static final String TAG = "UseServiceAdapter";
    private final UseServicePresenter mPresenter;
    private final Context mContext;
    private final int mType;
//    private UseServiceHolder mHolder;
    public List<BindWechatAccountModel> storageList;

    //item类型
    public static final int ITEM_TYPE_HEADER = 100;
    public static final int ITEM_TYPE_CONTENT = 101;
    public static final int ITEM_TYPE_BOTTOM = 102;


    private int mHeaderCount = 0;//头部View个数
    private int mBottomCount = 1;//底部View个数


    //内容长度
    public int getContentItemCount() {
        return datas != null ? datas.size() : 0;
    }

    public UseServiceAdapter(UseServicePresenter useServicePresenter, Context context, int type) {
        this.mPresenter = useServicePresenter;
        this.mContext = context;
        this.mType = type;
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_use_service, parent, false);
            viewHolder = new UseServiceHolder(view);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_service_foot, parent, false);
            viewHolder = new BottomViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolderTab) {

        } else if (holder instanceof UseServiceHolder) {
            UseServiceHolder mHolder = (UseServiceHolder) holder;
            BindWechatAccountModel mModel = (BindWechatAccountModel) datas.get(position);
            mHolder.mId.setText(mModel.getWechatNo());
            mHolder.mCheckBox.setChecked(mModel.isCheck());

            if(mModel.getIsSameTypeService()==1){

                mHolder.wechatNo_hint.setText("使用则暂停同类服务");
            }else if(mModel.getState()==9){
                mHolder.wechatNo_hint.setText("账号登出，使用则登入");
            }else{
                mHolder.wechatNo_hint.setText("");
            }
            if (mType == 0) {//  没有多选
//                if (mType == 3 || mType==6) {//灌粉B
                //多选
                Logger.d(TAG, "多选");
                mHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null == storageList) {
                            storageList = new ArrayList<BindWechatAccountModel>();
                        }
                        BindWechatAccountModel currentP =(BindWechatAccountModel) datas.get(position);
                        if (currentP.isCheck()) {
                            currentP.setCheck(false);
                            //避免角标越界
                            if (storageList.size() == 1) {
                                storageList = null;
                            } else {
                                storageList.remove(currentP);
                            }
                            mPresenter.updateUI();
                        } else {
                            Logger.d(TAG, "nn");
                            currentP.setCheck(true);
                            storageList.add(currentP);
                            mPresenter.updateUI();
                            Logger.d(TAG, "listSize:" + storageList.size());
                        }

                        mPresenter.multiChooseListData(storageList);

                    }
                });

            } else {
                //单选
                Logger.d(TAG, "单选");
                mHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mHolder.mCheckBox.isChecked()) {//选中
                            int size = datas.size();
                            for (int i = 0; i < size; i++) {
                                BindWechatAccountModel o =(BindWechatAccountModel) datas.get(i);
                                o.setCheck(false);
                            }
                            BindWechatAccountModel mWechatAccountModel =(BindWechatAccountModel) datas.get(position);
                            mWechatAccountModel.setCheck(true);
                            mPresenter.updateUI();
                            mPresenter.singleChoose(mWechatAccountModel);
                        }else{//取消
                            BindWechatAccountModel mWechatAccountModel =(BindWechatAccountModel) datas.get(position);
                            mWechatAccountModel.setCheck(false);
                            mPresenter.updateUI();
                            mPresenter.singleChoose(null);
                        }
                    }
                });
            }

        } else if (holder instanceof BottomViewHolder) {
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            bottomViewHolder.time_status.setText(R.string.no_account);
            bottomViewHolder.time_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.buyAccount();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

}
