package com.kongrongqi.shopmall.modules.me;

import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.App;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.http.API;
import com.kongrongqi.shopmall.http.URLConstans;
import com.kongrongqi.shopmall.modules.account.bean.Me;
import com.kongrongqi.shopmall.modules.main.ContainerActivity;
import com.kongrongqi.shopmall.modules.me.IView.IMeView;
import com.kongrongqi.shopmall.modules.me.presenter.MePresenter;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.NotifyUtil;
import com.kongrongqi.shopmall.utils.StringUtils;
import com.kongrongqi.shopmall.wedget.guide.GuiBuilder;
import com.kongrongqi.shopmall.wedget.guide.GuideView;
import com.kongrongqi.shopmall.wedget.progressBar.TextRoundCornerProgressBar;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建日期：2017/5/17 0017 on 17:21
 * 作者:penny
 */
public class MeFragment extends BaseMVPFragment<MePresenter> implements IMeView, View.OnClickListener {

    private TextView mBuyServiceNum;
    private TextView mChangeDevice;
    private TextView mDealHistory;
    private TextView mHaveDeviceNum;
    private TextView mHelp;
    private Button mLogout;
    private TextView mPushMSG;
    private TextView mFansNum;
    private TextView mGrounpNum;
    private TextView mOpration;
    private TextView me_about;
    private LinearLayout me_account;
    private LinearLayout me_service;
    private boolean isFoucus = false;
    private TextView mUserName;
    private TextView mPhone;
    private NotifyUtil notifyUtils;
    private RelativeLayout ll_me_update;
    private TextView versionName;
    private TextView me_fans;

    public static MeFragment newInstance() {
        return newInstance(null);
    }

    public static MeFragment newInstance(Bundle args) {

        MeFragment fragment = new MeFragment();
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
        return R.layout.fragment_me;
    }

    @Override
    protected IUI getUI() {
        return MeFragment.this;
    }

    @Override
    protected MePresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new MePresenter() :
                mPresenter;
    }


    @Override
    public void setToolBar() {
        super.setToolBar();
        toolbar.setTitle("");
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.my_account);

        right_icon.setVisibility(View.VISIBLE);
        right_icon.setImageResource(R.drawable.news_icon);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSessionActivity.lunch(getContext());
            }
        });
    }


    @Override
    public void initViews(View rootView, Bundle savedInstanceState) {
        mBuyServiceNum = (TextView) rootView.findViewById(R.id.me_buy_service);
        mDealHistory = (TextView) rootView.findViewById(R.id.me_deal);
        mHaveDeviceNum = (TextView) rootView.findViewById(R.id.me_have_devices);
        me_account = (LinearLayout) rootView.findViewById(R.id.me_account);//我的设备
        me_service = (LinearLayout) rootView.findViewById(R.id.me_service);//我的服务

        mHelp = (TextView) rootView.findViewById(R.id.me_help);
        mLogout = (Button) rootView.findViewById(R.id.me_logout);
        mChangeDevice = (TextView) rootView.findViewById(R.id.me_change_device);
        mPushMSG = (TextView) rootView.findViewById(R.id.me_push);
        me_fans = (TextView) rootView.findViewById(R.id.me_fans);

        mFansNum = (TextView) rootView.findViewById(R.id.me_total_num_fans);
        mGrounpNum = (TextView) rootView.findViewById(R.id.me_total_num_grounp);
        mOpration = (TextView) rootView.findViewById(R.id.me_total_num_opration);
        me_about = (TextView) rootView.findViewById(R.id.me_about);
        ll_me_update = (RelativeLayout) rootView.findViewById(R.id.ll_me_update);
        versionName = (TextView) rootView.findViewById(R.id.versionName);
        String versionNo = App.getInstance().getVersionName();
        versionName.setText("V" + versionNo);
        mUserName = (TextView) rootView.findViewById(R.id.me_user_name);
        mPhone = (TextView) rootView.findViewById(R.id.me_phone);
        setListener();
        getPresenter().getMeUserInfo();
    }

    @Override
    public void setVersionPoint(int hasUpdate) {
        versionName.setCompoundDrawablesWithIntrinsicBounds(hasUpdate == 1 ? R.drawable.shape_red_point : 0, 0, 0, 0);
    }


    @Override
    public void bindData(Me me) {
        mUserName.setText(getText(R.string.nike_name) + me.getNickName());
        mPhone.setText(me.getMobile());

        mFansNum.setText(me.getFansTotal() + "");//好友总量
        mGrounpNum.setText(me.getAddUpFans() + "");//累计加友
        mOpration.setText(me.getTodayAddFans() + "");//今日加友

        mHaveDeviceNum.setText(me.getDeviceNum() + "");//已有设备数量
        mBuyServiceNum.setText(me.getBuyServiceNum() + "");//购买服务数量

        red_point.setVisibility(me.getHasUnread() == 1 ? View.VISIBLE : View.GONE);
//        setVersionPoint(me.getHasUpdate());
    }

    private void setListener() {
        mHelp.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mDealHistory.setOnClickListener(this);
        mChangeDevice.setOnClickListener(this);
        mPushMSG.setOnClickListener(this);
        me_about.setOnClickListener(this);
        ll_me_update.setOnClickListener(this);
        me_fans.setOnClickListener(this);
//        me_account.setOnClickListener(this);
//        me_service.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_deal: //交易记录
                BuyRecordActivity.lunch(getContext());
                break;
            case R.id.me_push://我的消息
                UserSessionActivity.lunch(getContext());
                break;
            case R.id.me_help://常见问题
                ListActivity.lunch(getContext(),
                    Constans.LIST_TYPE_KEY,
                    Constans.LIST_TYPE_HELP);
                break;
            case R.id.me_change_device:
                ChangeDevicesActivity.lunch(getContext());
                break;
            case R.id.me_about://关于我们
                AboutActivity.lunch(getContext());
                break;
            case R.id.ll_me_update://关于KONG1
                getPresenter().requestAPPVersion();
                break;
            case R.id.me_logout: //退出
                getPresenter().logout();
                break;
            case R.id.me_fans://发烧友
//                getPresenter().aboutMe();
                FansActivity.lunch(getContext(),URLConstans.fans_url);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getPresenter().isFirst()) {
            Logger.d(TAG, "isFrist" + getPresenter().isFirst());
            showGuide();
            getPresenter().setFirst(true);
        }
    }

    @Override
    public NotifyUtil startNotifyProgress() {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(getContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.dis;
        String ticker = getResources().getString(R.string.apk_downloading);
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(getContext(), 0);
        notifyUtils.notify_progress(rightPendIntent,
                smallIcon,
                ticker,
                getResources().getString(R.string.apk_downloading),
                "正在下载中...",
                false,
                false,
                false);

        return notifyUtils;
    }


    private void showGuide() {
        GuiBuilder.builder(getActivity())
                .showGuiView(
                        mDealHistory,
                        true,
                        R.drawable.open_invoice_guide,
                        R.drawable.i_know,
                        GuideView.VIEWSTYLE_RECT,
                        GuiBuilder.ME_VIEW,
                        new GuiBuilder.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                GuiBuilder.builder(getActivity())
                                        .setMe(false);
                            }
                        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && getPresenter() != null) {
            getPresenter().getMeUserInfo();
        }
    }

    @Override
    public void timedRefresh() {
        super.timedRefresh();
        if (getPresenter() != null) {
            getPresenter().getMeUserInfo();
        }
    }
}
