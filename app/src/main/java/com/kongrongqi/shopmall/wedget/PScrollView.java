package com.kongrongqi.shopmall.wedget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 创建日期：2017/6/13 0013 on 15:56
 * 作者:penny
 */
public class PScrollView extends ScrollView {

    private ScrollView mAlternativeScrollView;

    public PScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setAlternativeScrollView(ScrollView alternativeScrollView) {
        mAlternativeScrollView = alternativeScrollView;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mAlternativeScrollView != null) {
            mAlternativeScrollView.scrollTo(l, t);
        }
    }
}
