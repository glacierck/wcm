package com.kongrongqi.shopmall.modules.service;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.service.IView.IServiceView;
import com.kongrongqi.shopmall.modules.service.presenter.ServicePresenter;
import com.kongrongqi.shopmall.utils.DensityUtil;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;
import com.kongrongqi.shopmall.wedget.guide.GuideView;

import java.util.List;

/**
 * 创建日期：2017/5/17 0017 on 17:26
 * 作者:penny
 */
public class ServiceFragment extends BaseMVPFragment<ServicePresenter> implements IServiceView {

    private XRecyclerView mXrecyclerView;
    private ConvenientBanner mConvenientBanner;
    private boolean isFoucus = false;

    public static ServiceFragment newInstance() {
        return newInstance(null);
    }

    public static ServiceFragment newInstance(Bundle args) {

        ServiceFragment fragment = new ServiceFragment();
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
        return R.layout.fragment_service;
    }

    @Override
    protected IUI getUI() {
        return ServiceFragment.this;
    }

    @Override
    protected ServicePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ServicePresenter() :
                mPresenter;
    }

    @Override
    public void setToolBar() {
        super.setToolBar();
        toolbar.setTitle("");
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.service);
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        mXrecyclerView = (XRecyclerView) rootView.findViewById(R.id.xRecyclerView);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_view, (ViewGroup)
                rootView.findViewById(android.R.id.content), false);
        mConvenientBanner = (ConvenientBanner) header.findViewById(R.id.head_banner);
        ViewGroup.LayoutParams layoutParams1 = mConvenientBanner.getLayoutParams();
        layoutParams1.height = (int) (DensityUtil.getScreenWidth(getContext()) / Constans.BANNER_HEIGHT);
        mConvenientBanner.setLayoutParams(layoutParams1);
        mXrecyclerView.addHeaderView(header);
        getPresenter().setAdapter(mXrecyclerView);
        getPresenter().setBanner(mConvenientBanner);
    }


    @Override
    public void onResume() {
        super.onResume();
        mConvenientBanner.startTurning(4000);
        if (!isFragmentHidden()) {
            Logger.e("ServiceFrg", "显示");
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
                        mXrecyclerView,
                        true,
                        2,
                        R.drawable.click_here_buy,
                        R.drawable.i_know,
                        GuiBuilder.SERVICE,
                        GuideView.VIEWSTYLE_RECT
                        , new GuiBuilder.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                GuiBuilder.builder(ServiceFragment.this.getActivity())
                                        .setService(false);
                            }
                        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.d("ServiceFrg", "isHidden:=" + hidden);
        if (mConvenientBanner != null) {
            if (hidden) {
                mConvenientBanner.stopTurning();
            } else {
                mConvenientBanner.startTurning(4000);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
