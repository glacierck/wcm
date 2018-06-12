package com.kongrongqi.shopmall.modules.account.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BasePresenter;
import com.kongrongqi.shopmall.base.BaseRecycleViewAdapter;
import com.kongrongqi.shopmall.base.IUI;

import java.util.List;

import rx.Subscription;

/**
 * Created by Administrator on 2017/5/27 0027.
 */

public abstract class BaseLoadingPresenter<T, U extends IUI> extends BasePresenter<U> implements XRecyclerView.LoadingListener {

    public XRecyclerView mRecyclerView;
    public Subscription mSuperSubscription;
    public BaseRecycleViewAdapter mSuperAdapter;
    public int currentPage = 1;
    public int pageSize = 20;
    private boolean isLoadMore = true;

    public void setXRecyclerView(XRecyclerView xrecyclerView) {
        this.mRecyclerView = xrecyclerView;
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingMoreEnabled(isLoadMore);
    }

    public void setXRecyclerView(XRecyclerView xrecyclerView, LinearLayoutManager linearLayoutManager) {
        this.mRecyclerView = xrecyclerView;
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setXRecyclerView(xrecyclerView);
    }


    public void refreshOrLoadMore(List<T> data, boolean isRefresh) {
        mRecyclerView.setLoadingMoreEnabled(data.size() >= pageSize ? true : false);
        if (isRefresh) {
            mRecyclerView.refreshComplete();
            if (mSuperAdapter.getDatas() != null) {
                mSuperAdapter.getDatas().clear();
            }
            mSuperAdapter.setDatas(data);
        } else {
            if (data == null && data.size() < 0) {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setNoMore(true);
            } else {
                mRecyclerView.setNoMore(false);
                mRecyclerView.loadMoreComplete();
                mSuperAdapter.getDatas().addAll(mSuperAdapter.getDatas().size(), data);
            }
        }
        mSuperAdapter.notifyDataSetChanged();
        if(mSuperAdapter.getDatas() ==null || mSuperAdapter.getDatas().size()<=0 ){ //数据为空
            getUI().showEmpty();
        }else{
            getUI().showContent();
        }
    }

    @Override
    public void onUIDestory() {
        super.onUIDestory();
        releaseSubscription(mSuperSubscription);
    }


    public abstract void onRefreshList();

    public abstract void onLoadMoreList();


    @Override
    public void onRefresh() {
        currentPage = 1;
        onRefreshList();
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        onLoadMoreList();
    }

    public void setLoadingMoreEnabled(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public void onErrorOpration(boolean isRefresh) {
        currentPage = 1;
        dismissDialog();
        if (isRefresh) {
            mRecyclerView.refreshComplete();
        } else {
            mRecyclerView.loadMoreComplete();
            setLoadingMoreEnabled(false);
        }
    }

    @Override
    public void refreshView() {
        super.refreshView();
        getUI().showContent();
        mRecyclerView.refresh();
    }
}
