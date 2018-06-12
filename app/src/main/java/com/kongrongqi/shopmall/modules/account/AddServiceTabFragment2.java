package com.kongrongqi.shopmall.modules.account;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.frame.commonframe.viewtype.BuilderProgress;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.IAddServiceTabView;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.NoUserServiceDatailsPresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.JumpEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 未使用服务列表
 */
public class AddServiceTabFragment2 extends BaseMVPFragment<NoUserServiceDatailsPresenter> implements IAddServiceTabView{

    private XRecyclerView mXrecyclerView;
    private Button user_service;
    private DeviceWechat deviceWechat;


    public static AddServiceTabFragment2 newInstance() {
        return newInstance(null);
    }

    public static AddServiceTabFragment2 newInstance(Bundle args) {

        AddServiceTabFragment2 fragment = new AddServiceTabFragment2();
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
        return R.layout.fragment_add_service;
    }

    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        super.initViews(rootView, savedInstanceState);
        Bundle arguments = getArguments();
        user_service = (Button) rootView.findViewById(R.id.user_service);
        user_service.setText("使用");
        if(arguments!=null){
            deviceWechat = (DeviceWechat) getArguments().getSerializable(ServiceDetailsActivity.DEVICE_WECHAT);
        }
        mXrecyclerView = (XRecyclerView) rootView.findViewById(R.id.xRecyclerView);
        getPresenter().setAdapter(mXrecyclerView,deviceWechat);

        user_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().ConfirmUserService();
            }
        });
    }

    @Override
    protected IUI getUI() {
        return AddServiceTabFragment2.this;
    }

    @Override
    protected NoUserServiceDatailsPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new NoUserServiceDatailsPresenter() :
                mPresenter;
    }

    @Override
    public void buyServiceB() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_NOT_USE_FRG));
        startActivity(new Intent(getContext(), ContainerActivity.class));
    }

    @Override
    public void showEmpty() {
        if (getProgressActivity() != null) {
            new BuilderProgress(getProgressActivity())
                    .setEmptyText(getString(R.string.empty_no_user_service))
                    .setEmptyImage(R.drawable.empty_no_user_service)
                    .create()
                    .showEmpty();
        }
    }

}
