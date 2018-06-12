package com.kongrongqi.shopmall.modules.account.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kongrongqi.shopmall.modules.account.AddServiceTabFragment;
import com.kongrongqi.shopmall.modules.account.TabFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class AddServicePageAdapter extends FragmentPagerAdapter {

    List<Fragment>  tabFragments;
    private List<String> tabIndicators;

    public AddServicePageAdapter(FragmentManager fm, List<Fragment> tabFragments, List<String> tabIndicators) {
        super(fm);
        this.tabFragments = tabFragments;
        this.tabIndicators = tabIndicators;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabIndicators.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabIndicators.get(position);
    }
}
