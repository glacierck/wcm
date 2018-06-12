package com.kongrongqi.shopmall.modules.account;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.IView.IAddServiceTabView;
import com.kongrongqi.shopmall.modules.account.bean.DeviceWechat;
import com.kongrongqi.shopmall.modules.account.presenter.ServiceListPresenter;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.model.JumpEvent;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * 购买服务
 *
 */
public class AddServiceTabFragment extends BaseMVPFragment<ServiceListPresenter> implements IAddServiceTabView{

    private XRecyclerView mXrecyclerView;

    private Button user_service;
    private DeviceWechat deviceWechat;

    public static AddServiceTabFragment newInstance() {
        return newInstance(null);
    }

    public static AddServiceTabFragment newInstance(Bundle args) {

        AddServiceTabFragment fragment = new AddServiceTabFragment();
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
        if(arguments!=null){
            deviceWechat = (DeviceWechat) getArguments().getSerializable(ServiceDetailsActivity.DEVICE_WECHAT);
        }
        mXrecyclerView = (XRecyclerView) rootView.findViewById(R.id.xRecyclerView);
        getPresenter().setAdapter(mXrecyclerView, deviceWechat);

//        user_service = (Button) rootView.findViewById(R.id.user_service);
//        user_service.setText("购买");
//        user_service.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPresenter().ConfirmUserService();
//            }
//        });
    }

    @Override
    protected IUI getUI() {
        return AddServiceTabFragment.this;
    }

    @Override
    protected ServiceListPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ServiceListPresenter() :
                mPresenter;
    }

    @Override
    public void buyServiceB() {
        EventBus.getDefault().post(new JumpEvent(Constans.EVENT_SERVICE_FRG));
        startActivity(new Intent(getContext(), ContainerActivity.class));
    }
}
