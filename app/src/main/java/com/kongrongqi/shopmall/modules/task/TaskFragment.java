package com.kongrongqi.shopmall.modules.task;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.modules.task.IView.ITaskView;
import com.kongrongqi.shopmall.modules.task.adapter.TaskFragmentPagerAdapter;
import com.kongrongqi.shopmall.modules.task.presenter.TaskPresenter;
import com.kongrongqi.shopmall.utils.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 创建日期：2017/5/17 0017 on 17:29
 * 作者:penny
 */
public class TaskFragment extends BaseMVPFragment<TaskPresenter> implements ITaskView, TabLayout.OnTabSelectedListener {

    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private String[] indicator;
    private TaskFragmentPagerAdapter pagerAdapter;

    public static TaskFragment newInstance() {
        return newInstance(null);
    }

    public static TaskFragment newInstance(Bundle args) {

        TaskFragment fragment = new TaskFragment();
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
        return R.layout.fragment_task;
    }

    @Override
    protected IUI getUI() {
        return TaskFragment.this;
    }

    @Override
    protected TaskPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new TaskPresenter() :
                mPresenter;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        toolbar.setTitle("");
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.task);
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.task_tab_layout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.cl_ff9d9d9d), ContextCompat.getColor(getActivity(), R.color.cl_ffff0000));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.cl_ffff0000));
        ViewCompat.setElevation(mTabLayout, 10);
        viewPager = (ViewPager) rootView.findViewById(R.id.task_viewPager);
        indicator = new String[]{"未使用", "服务中", "已暂停", "已完成"};
        bindViewPager();
    }

    private void bindViewPager() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.not_use));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.working));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.stop));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.finish));
        mTabLayout.getTabAt(0).setText(R.string.not_use);
        mTabLayout.getTabAt(1).setText(R.string.working);
        mTabLayout.getTabAt(2).setText(R.string.stop);
        mTabLayout.getTabAt(3).setText(R.string.finish);
        pagerAdapter = new TaskFragmentPagerAdapter(getChildFragmentManager(), getContext(), indicator);
        mTabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
        mTabLayout.addOnTabSelectedListener(this);
    }


    @Subscribe
    public void onEventMainThread(JumpEvent event) {
        if (event.codeJump() == Constans.EVENT_TASK_NO_USER) {//未使用
            viewPager.setCurrentItem(0, false);
        } else if (event.codeJump() == Constans.EVENT_TASK_GOING) {//进行中
            viewPager.setCurrentItem(1, false);
        } else if (event.codeJump() == Constans.EVENT_TASK_FINISH) {//已完成
            viewPager.setCurrentItem(3, false);
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pagerAdapter.getItem(tab.getPosition()).refrash();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            pagerAdapter.getItem(mTabLayout.getSelectedTabPosition()).refrash();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void timedRefresh() {
        super.timedRefresh();
        try{
            pagerAdapter.getItem(mTabLayout.getSelectedTabPosition()).timedRefresh();
        }catch (Exception e){

        }
        
    }
}
