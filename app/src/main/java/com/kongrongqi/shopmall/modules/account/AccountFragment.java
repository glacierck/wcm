package com.kongrongqi.shopmall.modules.account;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.IAccountView;
import com.kongrongqi.shopmall.modules.account.IView.ShowGuideListener;
import com.kongrongqi.shopmall.modules.account.adapter.ContentPagerAdapter;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.presenter.AccountPresenter;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;
import com.kongrongqi.shopmall.wedget.guide.GuideView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class AccountFragment extends BaseMVPFragment<AccountPresenter> implements IAccountView, TabLayout.OnTabSelectedListener {

    private TabLayout tl_tab;
    private ViewPager vp_content;
    private List<Device> tabIndicators;
    private List<TabFragment> tabFragments;
    private LinearLayout more_equipment;
    protected static ShowGuideListener mShowGuideListener;

    public static int SELECT_DEVICE = 30001;
    public static String PAGE_INDEX = "pageindex";
    private ImageView mImgList;
    private int eventCode;

    public static int NOT_CACHE = 1001;//不取缓存
    public static int GET_CACHE = 1002;//取缓存

    public static int GET_CACHE_NOE = 1003;//默认情况

    private int refrashType = GET_CACHE_NOE;


    @Override
    public int getRefrashType() {
        return refrashType;
    }

    @Override
    public void setRefrashType(int refrashType) {
        this.refrashType=refrashType;
    }

    public static AccountFragment newInstance() {
        return newInstance(null);
    }

    public static AccountFragment newInstance(Bundle args) {

        AccountFragment fragment = new AccountFragment();
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
        return R.layout.fragment_account;
    }

    public static int ADD_RESULT_CODE = 200;
    public static int REPLACE_RESULT_CODE = 201;


    @Override
    public void setToolBar() {
        super.setToolBar();
        toolbar.setTitle("");
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.account);
        toolbar.setNavigationIcon(R.drawable.shebei_user);
        right_icon.setVisibility(View.VISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAcount();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountListActivity.lunch(getContext());
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AddAcount() {
        Intent intent = new Intent(getActivity(), ReplaceIMEIActivity.class);
        intent.putExtra(ReplaceIMEIActivity.TYPE, ReplaceIMEIActivity.ADD);
        startActivityForResult(intent, ADD_RESULT_CODE);
    }

    @Subscribe
    public void onEventMainThread(JumpEvent event) {

        eventCode = event.codeJump();
        if (eventCode == Constans.EVENT_ACCOUNT_REFRESH) {//替换设备 刷新设备列表
            refrashType = NOT_CACHE;
            getPresenter().refreshView();
        }
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        tl_tab = (TabLayout) rootView.findViewById(R.id.tl_tab);
        vp_content = (ViewPager) rootView.findViewById(R.id.vp_content);
        more_equipment = (LinearLayout) rootView.findViewById(R.id.more_equipment);//更多设备列表
        mImgList = (ImageView) rootView.findViewById(R.id.account_list);
        more_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoreEquipListActivity.class);
                startActivityForResult(intent, SELECT_DEVICE);
            }
        });
        initContent();
        initTab();
        getPresenter().getAccountData();
    }

    protected void initTab() {
        tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tl_tab.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.cl_ff9d9d9d), ContextCompat.getColor(getActivity(), R.color.cl_ffff0000));
        tl_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.cl_ffff0000));
        ViewCompat.setElevation(tl_tab, 10);
        tl_tab.setupWithViewPager(vp_content);
        tl_tab.addOnTabSelectedListener(this);
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabFragments = new ArrayList<>();
        getPresenter().setAdapter(vp_content, getActivity().getSupportFragmentManager(), tabFragments, tabIndicators);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == ADD_RESULT_CODE) { //添加设备返回
            refrashType= GET_CACHE;
            getPresenter().refreshView();
        } else if (resultCode == getActivity().RESULT_OK && requestCode == SELECT_DEVICE) { //选择设备
            int intExtra = data.getIntExtra(PAGE_INDEX, 0);
            vp_content.setCurrentItem(intExtra, false);
        }
    }


    @Override
    protected IUI getUI() {
        return AccountFragment.this;
    }

    @Override
    protected AccountPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new AccountPresenter() :
                mPresenter;
    }

    @Override
    public void notifyDataSetChangedAdapter() {
        vp_content.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ContentPagerAdapter pageAdaper = getPresenter().getPageAdaper();
            if (pageAdaper.getCount() > 0) {
                getPresenter().getPageAdaper().getItem(tl_tab.getSelectedTabPosition()).refrash();
            }
        }
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.account_empty_hint))
                    .setEmptyButtonText(getString(R.string.add_account_hint))
                    .setEmptyButtonIsShow()
                    .setEmptyImage(R.drawable.empty_account)
                    .setEmptyButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddAcount();
                        }
                    })
                    .create()
                    .showEmpty();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        if (eventCode == Constans.EVENT_ACCOUNT_REFRESH) return;
        try{
            ContentPagerAdapter pageAdaper = getPresenter().getPageAdaper();
            if (pageAdaper.getCount() > 0) {
                pageAdaper.getItem(tl_tab.getSelectedTabPosition()).refrash();
            }
        }catch (Exception e){}
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void invisibleMoreEq(boolean visible) {
        if (visible)
            more_equipment.setVisibility(View.GONE);
        else
            more_equipment.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFragmentHidden()) {
            if (!getPresenter().isFirst()) {
                Logger.d(TAG, "isFrist" + getPresenter().isFirst());
                showGuide();
                getPresenter().setFirst(true);
            }
        } else {
            Logger.e("AccountFrg", "不显示");
        }
    }

    private void showGuide() {
        GuiBuilder.builder(getActivity())
                .showGuiComboView(getRightIconView(), false, R.drawable.add_devices_guide, R.drawable.i_know,
                        GuideView.VIEWSTYLE_CIRCLE, GuiBuilder.ACCOUNT, new GuiBuilder.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                ImageButton lImageButton = getToolNavigationButton(toolbar);
                                if (lImageButton == null) return;
                                GuiBuilder.builder(getActivity())
                                        .showGuiComboView(lImageButton, false, R.drawable.check_account_detail, 0,
                                                GuideView.VIEWSTYLE_SELF, GuiBuilder.ACCOUNT, new GuiBuilder.OnDismissListener() {
                                                    @Override
                                                    public void onDismiss() {
                                                        if (more_equipment.getVisibility() == View.GONE)
                                                            return;

                                                        GuiBuilder.builder(getActivity())
                                                                .showGuiComboView(mImgList, false, R.drawable.check_devices_list, 0, GuideView.VIEWSTYLE_CIRCLE,
                                                                        GuiBuilder.ACCOUNT, new GuiBuilder.OnDismissListener() {
                                                                            @Override
                                                                            public void onDismiss() {
                                                                                if (mShowGuideListener != null) {
                                                                                    Logger.d("mShowGuideListener", "mShowGuideListener");
                                                                                    mShowGuideListener.startGui();
                                                                                }
                                                                            }
                                                                        });
                                                    }
                                                });
                            }
                        });
    }

    protected static void setShowGuideListener(ShowGuideListener pListener) {
        mShowGuideListener = pListener;
    }


    @Override
    public void timedRefresh() {
        super.timedRefresh();
        ContentPagerAdapter pageAdaper = getPresenter().getPageAdaper();
        if (pageAdaper.getCount() > 0) {
            pageAdaper.getItem(tl_tab.getSelectedTabPosition()).timedRefresh();
        }
    }
}
