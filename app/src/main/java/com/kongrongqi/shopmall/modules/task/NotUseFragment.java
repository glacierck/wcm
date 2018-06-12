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
import com.kongrongqi.shopmall.modules.task.IView.INotUseView;
import com.kongrongqi.shopmall.modules.task.presenter.NotUsePresenter;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;
import com.kongrongqi.shopmall.wedget.guide.GuideView;

/**
 * 创建日期：2017/5/22 0022 on 17:40
 * 作者:penny
 */
public class NotUseFragment extends BaseMVPFragment<NotUsePresenter> implements INotUseView {

    private XRecyclerView mRecyclerView;
    private boolean isFoucus = false;

    public static NotUseFragment newInstance() {
        return newInstance(null);
    }

    public static NotUseFragment newInstance(Bundle args) {
        NotUseFragment fragment = new NotUseFragment();
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
        return R.layout.fragment_not_use;
    }

    @Override
    protected IUI getUI() {
        return this;
    }

    @Override
    protected NotUsePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new NotUsePresenter() :
                mPresenter;
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        mRecyclerView = (XRecyclerView) rootView.findViewById(R.id.not_recyclerView);
        getPresenter().setAdapter(mRecyclerView);
    }

    @Override
    public void refrash() {
        super.refrash();
        if (mRecyclerView != null)
            mRecyclerView.refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFragmentHidden()) {
            Logger.e("notusefrg", "显示");
            if (!getPresenter().isFirst()) {
                Logger.d(TAG,"isFrist"+getPresenter().isFirst());
                showGuide();
                getPresenter().setFirst(true);
            }
        }
    }

    private void showGuide() {
        GuiBuilder.builder(getActivity())
                .setMinute(1000)
                .showGuiRecyclerView(
                        mRecyclerView,
                        true,
                        1,
                        R.drawable.use_service,
                        R.drawable.i_know,
                        GuiBuilder.NOT_USE,
                        GuideView.VIEWSTYLE_RECT,
                        new GuiBuilder.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                GuiBuilder.builder(NotUseFragment.this.getActivity())
                                        .setNotUse(false);
                            }
                        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.e("notusefrg", "onHiddenChanged:=" + hidden);
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_not_use_hint))
                    .setEmptyImage(R.drawable.empty_task_noding)
                    .create()
                    .showEmpty();
        }
    }
}
