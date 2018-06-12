package com.kongrongqi.shopmall.modules.account;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.ITabView;
import com.kongrongqi.shopmall.modules.account.IView.ShowGuideListener;
import com.kongrongqi.shopmall.modules.account.bean.Device;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.TabPresenter;
import com.kongrongqi.shopmall.modules.login.request.OrderNumRequest;
import com.kongrongqi.shopmall.modules.service.PayActivity;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;
import com.kongrongqi.shopmall.wedget.guide.GuideView;

/**
 * 创建日期：2017/5/17 0017 on 17:33
 * 作者:penny
 */
public class TabFragment extends BaseMVPFragment<TabPresenter> implements ITabView {

    private XRecyclerView mXrecyclerView;
    public static String DEVICE = "Device";
    private Device device;

    public static String DEVICE_WECHAT = "DeviceWechat";

    public static int ADD_SUCCUCE = 10001;

    public static int ADD_SOLT = 10002;//购买号槽

    public static String MACHINE_CODE = "machineCode";


    public void setDevice(Device device) {
        this.device = device;
        notifyDataSetChangedAdapter();
    }

    public Device getDevice() {
        return device;
    }

    public static TabFragment newInstance() {
        return newInstance(null);
    }

    public static TabFragment newInstance(Bundle args) {

        TabFragment fragment = new TabFragment();
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
        return R.layout.fragment_tab_account;
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        super.initViews(rootView, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            device = (Device) arguments.getSerializable(DEVICE);
        }
        mXrecyclerView = (XRecyclerView) rootView.findViewById(R.id.xRecyclerView);
        getPresenter().setAdapter(mXrecyclerView, device);
    }

    @Override
    protected IUI getUI() {
        return TabFragment.this;
    }

    @Override
    protected TabPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new TabPresenter() :
                mPresenter;
    }

    @Override
    public void replace() {
        Intent intent = new Intent(getActivity(), ReplaceIMEIActivity.class);
        intent.putExtra(ReplaceIMEIActivity.TYPE, ReplaceIMEIActivity.REPLACE);
        intent.putExtra(DEVICE, device);
        startActivityForResult(intent, AccountFragment.REPLACE_RESULT_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == AccountFragment.REPLACE_RESULT_CODE) { //替换设备返回
            String machineCode = data.getStringExtra(ReplaceIMEIActivity.MACHINE_CODE);
            device.setMachineCode(machineCode);
            notifyDataSetChangedAdapter();
        } else if (resultCode == getActivity().RESULT_OK && requestCode == ADD_SUCCUCE) {
            getPresenter().mRecyclerView.refresh();
        } else if (resultCode == getActivity().RESULT_OK && requestCode == ADD_SOLT) {
            getPresenter().mRecyclerView.refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AccountFragment.setShowGuideListener(new ShowGuideListener() {
            @Override
            public void startGui() {
                if (!getPresenter().isFirst()) {
                    Logger.d(TAG, "isFrist" + getPresenter().isFirst());
                    showGuide();
                    getPresenter().setFirst(true);
                }
            }
        });
    }

    private void showGuide() {
        GuiBuilder.builder(getActivity())
                .showGuiRecyclerView(mXrecyclerView,
                        true,
                        2,
                        R.drawable.add_devices,
                        R.drawable.i_know,
                        GuiBuilder.ACCOUNT,
                        GuideView.VIEWSTYLE_RECT,
                        new GuiBuilder.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                GuiBuilder.builder(getActivity()).setAccountRight(false);
                            }
                        });
    }

    @Override
    public void addAccount(DeviceWechat deviceWechat, int optType, String title) {
        AddAccountActivity.lunch(getContext(), deviceWechat, optType, title);
    }

    @Override
    public void buySlot(OrderNumRequest orderNumRequest) {
        Intent intent = new Intent(getContext(), PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constans.PAY, orderNumRequest);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_SOLT);
    }

    @Override
    public void buyNewService() {
        NewDeviceServiceActivity.lunch(getContext());
    }

    /**
     * 使用服务
     */
    @Override
    public void userService(DeviceWechat deviceWechat) {
        NoUseServiceActivity.lunch(getContext(), deviceWechat);
    }

    @Override
    public void lookServiceDetails(DeviceWechat deviceWechat) {
        ServiceDetailsActivity.lunch(getContext(), deviceWechat);
    }

    @Override
    public void notifyDataSetChangedAdapter() {
        if (mXrecyclerView != null)
            mXrecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void refrashIview() {
        if (mXrecyclerView != null)
            mXrecyclerView.refresh();
    }


    @Override
    public void refrash() {
        super.refrash();
        if (mXrecyclerView != null)
            mXrecyclerView.refresh();
    }

    public void timedRefresh() {
        super.refrash();
        if (getPresenter() != null) {
            getPresenter().getAccountData(true);
        }
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.account_empty_hint))
                    .setEmptyButtonText(getString(R.string.add_account_hint))
                    .setEmptyImage(R.drawable.empty_account)
                    .create()
                    .showEmpty();
        }
    }


    /**
     * 设备异常
     */
    @Override
    public void deviceErroy() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + "027-59303512");
        intent.setData(data);
        startActivity(intent);
    }
}
