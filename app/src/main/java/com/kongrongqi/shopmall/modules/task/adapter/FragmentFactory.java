package com.kongrongqi.shopmall.modules.task.adapter;

import android.util.SparseArray;

import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.modules.task.FinishFragment;
import com.kongrongqi.shopmall.modules.task.NotUseFragment;
import com.kongrongqi.shopmall.modules.task.StopFragment;
import com.kongrongqi.shopmall.modules.task.WorkingFragment;

import java.util.AbstractSequentialList;
import java.util.HashMap;

/**
 * 创建日期：2017/5/22 0022 on 17:22
 * 作者:penny
 */
public class FragmentFactory {

    private static SparseArray<BaseMVPFragment> fragmentCache = new SparseArray<BaseMVPFragment>();

    public static BaseMVPFragment creatFragments(int position) {

        BaseMVPFragment fragment = fragmentCache.get(position);

        if(fragment==null){
            switch (position) {
                case 0:
                    fragment = new NotUseFragment().newInstance();
                    break;
                case 1:
                    fragment = new WorkingFragment().newInstance();
                    break;
                case 2:
                    fragment = new StopFragment().newInstance();
                    break;
                case 3:
                    fragment = new FinishFragment().newInstance();
                    break;
                default:
                    break;
            }
            fragmentCache.put(position, fragment);
        }

        return fragment;
    }
}
