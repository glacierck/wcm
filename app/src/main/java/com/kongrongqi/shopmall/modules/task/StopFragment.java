package com.kongrongqi.shopmall.modules.task;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.modules.task.IView.IStopView;
import com.kongrongqi.shopmall.modules.task.IView.IWorkingView;
import com.kongrongqi.shopmall.modules.task.presenter.StopPresenter;
import com.kongrongqi.shopmall.modules.task.presenter.WorkingPresenter;

/**
 * 创建日期：2017/5/22 0022 on 17:40
 * 作者:penny
 */
public class StopFragment extends BaseMVPFragment<StopPresenter> implements IStopView {

    private XRecyclerView mRecylerView;

    public static StopFragment newInstance() {
        return newInstance(null);
    }

    public static StopFragment newInstance(Bundle args) {

        StopFragment fragment = new StopFragment();
        if (null != args) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    protected ViewDataBinding initDataBinding(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_working;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    @Override
    protected StopPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new StopPresenter() :
                mPresenter;
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        mRecylerView = (XRecyclerView) rootView.findViewById(R.id.working_recylerView);
        getPresenter().setAdapter(mRecylerView);
    }

    @Override
    public void refrash() {
        super.refrash();
        if (mRecylerView != null)
            mRecylerView.refresh();
    }

    @Override
    public void timedRefresh() {
        super.timedRefresh();
        if (getPresenter() != null) {
            getPresenter().requestWorkingList(true);
        }
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_stop_hint))
                    .setEmptyImage(R.drawable.empty_stop_tasking)
                    .create()
                    .showEmpty();
        }
    }

}
