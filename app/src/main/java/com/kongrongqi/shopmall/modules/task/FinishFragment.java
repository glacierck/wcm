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
import com.kongrongqi.shopmall.modules.task.IView.IFinishView;
import com.kongrongqi.shopmall.modules.task.presenter.FinishPresenter;

/**
 * 创建日期：2017/5/22 0022 on 17:40
 * 作者:penny
 */
public class FinishFragment extends BaseMVPFragment<FinishPresenter> implements IFinishView{

    private XRecyclerView mRecylerView;

    public static FinishFragment newInstance() {
        return newInstance(null);
    }

    public static FinishFragment newInstance(Bundle args) {

        FinishFragment fragment = new FinishFragment();
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
        return R.layout.fragment_finish;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    @Override
    protected FinishPresenter createPresenter() {
        return  mPresenter == null ?
                mPresenter = new FinishPresenter() :
                mPresenter;
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        mRecylerView = (XRecyclerView) rootView.findViewById(R.id.finish_recyclerView);
        getPresenter().setAdapter(mRecylerView);
    }

    @Override
    public void refrash() {
        super.refrash();
        if (mRecylerView != null)
            mRecylerView.refresh();
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_finish_hint))
                    .setEmptyImage(R.drawable.empty_task_finish)
                    .create()
                    .showEmpty();
        }
    }
}
