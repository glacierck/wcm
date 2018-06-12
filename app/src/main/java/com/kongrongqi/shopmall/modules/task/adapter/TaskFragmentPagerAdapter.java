package com.kongrongqi.shopmall.modules.task.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kongrongqi.shopmall.base.BaseMVPFragment;

/**
 * 创建日期：2017/5/22 0022 on 17:05
 * 作者:penny
 */
public class TaskFragmentPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final String[] mTab;

    public TaskFragmentPagerAdapter(FragmentManager fm, Context context, String[] title1) {
        super(fm);
        this.mContext = context;
        this.mTab = title1;
    }

    @Override
    public BaseMVPFragment getItem(int position) {
        return FragmentFactory.creatFragments(position);
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
