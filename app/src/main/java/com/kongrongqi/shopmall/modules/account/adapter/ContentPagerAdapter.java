package com.kongrongqi.shopmall.modules.account.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.kongrongqi.shopmall.modules.account.TabFragment;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.presenter.AccountPresenter;
import com.kongrongqi.shopmall.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class ContentPagerAdapter extends FragmentPagerAdapter {

    List<TabFragment> tabFragments;
    private List<Device> tabIndicators;
    private AccountPresenter accountPresenter;

    public ContentPagerAdapter(AccountPresenter accountPresenter, FragmentManager fm, List<TabFragment> tabFragments, List<Device> tabIndicators) {
        super(fm);
        this.accountPresenter = accountPresenter;
        this.tabFragments = tabFragments;
        this.tabIndicators = tabIndicators;
    }

    @Override
    public TabFragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabIndicators.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabIndicators.get(position).getUserDeviceName();
    }
}
