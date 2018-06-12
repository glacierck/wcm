package com.kongrongqi.shopmall.modules.main;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.base.BaseMVPActivity;
import com.kongrongqi.shopmall.base.BaseMVPFragment;
import com.kongrongqi.shopmall.base.IUI;
import com.kongrongqi.shopmall.global.Constans;
import com.kongrongqi.shopmall.modules.account.AccountFragment;
import com.kongrongqi.shopmall.modules.main.Iview.IContainerUI;
import com.kongrongqi.shopmall.modules.main.presenter.ContainerPresenter;
import com.kongrongqi.shopmall.modules.me.MeFragment;
import com.kongrongqi.shopmall.modules.model.JumpEvent;
import com.kongrongqi.shopmall.modules.service.ServiceFragment;
import com.kongrongqi.shopmall.modules.task.TaskFragment;
import com.kongrongqi.shopmall.service.TimedRefreshService;
import com.kongrongqi.shopmall.service.TimedSmsService;
import com.kongrongqi.shopmall.utils.DoubleClickExitHelper;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.NotifyUtil;
import com.kongrongqi.shopmall.utils.ToastUtil;
import com.kongrongqi.shopmall.utils.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 创建日期：2017/5/16 0016 on 16:56
 * 作者:penny
 */
public class ContainerActivity extends BaseMVPActivity<ContainerPresenter> implements
        IContainerUI, BottomNavigationBar.OnTabSelectedListener {
    public static final String TAG = "ContainerActivity";
    private BottomNavigationBar mNavigation;
    private int mCurrenPage = 0;
    private MeFragment mMeFragment;
    private ServiceFragment mServiceFragment;
    private TaskFragment mTaskFragment;
    private AccountFragment mAccountFragment;
    private FrameLayout mReplace;
    private int mJumpType;
    private String navigation = "navigation";
    private String PAGE = "page";
    private NotifyUtil notifyUtils;
    private boolean isOnKeyBacking;
    private Toast mBackToast;
    private DoubleClickExitHelper doubleClick;
    private ConnectionService connectionService;

    private ConnectionSmsService connectionSmsService;

    public static void lunch(Context context) {
        Intent intent = new Intent(context, ContainerActivity.class);
        context.startActivity(intent);
    }

    /**
     * 如使用databinding这里返回布局
     *
     * @return
     */
    @Override
    protected ViewDataBinding initDatabinding() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_container;
    }

    @Override
    protected ContainerPresenter createPresenter() {
        return mPresenter == null ?
                mPresenter = new ContainerPresenter() :
                mPresenter;
    }

    @Override
    protected IUI getUI() {
        return ContainerActivity.this;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Logger.d(TAG, "initViews");
        EventBus.getDefault().register(this);
//        if (savedInstanceState == null) {
        initBottom(mCurrenPage);
//        }
        doubleClick = new DoubleClickExitHelper(this);

        connectionService = new ConnectionService();//连接

        bindService(new Intent(this, TimedRefreshService.class), connectionService, 1);//绑定服务


        connectionSmsService = new ConnectionSmsService();//连接
        bindService(new Intent(this, TimedSmsService.class), connectionSmsService, 1);//绑定服务
    }

    protected void initBottom(int page) {
        mNavigation = (BottomNavigationBar) findViewById(R.id.bottom_navigation);
        mReplace = (FrameLayout) findViewById(R.id.main_relace);
        mNavigation.setMode(BottomNavigationBar.MODE_FIXED);
        mNavigation.setBarBackgroundColor(R.color.rippelColor);
        mNavigation.addItem(new BottomNavigationItem(R.drawable.shebei_2, R.string.account)
                .setActiveColorResource(R.color.login_button_bj)
                .setInactiveIcon(getResources().getDrawable(R.drawable.shebei_1))
                .setInActiveColor("#66666A"));
        mNavigation.addItem(new BottomNavigationItem(R.drawable.jincheng_2, R.string.task)
                .setActiveColorResource(R.color.login_button_bj)
                .setInactiveIcon(getResources().getDrawable(R.drawable.jincheng_1))
                .setInActiveColor("#66666A"));
        mNavigation.addItem(new BottomNavigationItem(R.drawable.fuwu_2, R.string.service)
                .setActiveColorResource(R.color.login_button_bj)
                .setInactiveIcon(getResources().getDrawable(R.drawable.fuwu_1))
                .setInActiveColor("#66666A"));
        mNavigation.addItem(new BottomNavigationItem(R.drawable.wo_2, R.string.me)
                .setActiveColorResource(R.color.login_button_bj)
                .setInactiveIcon(getResources().getDrawable(R.drawable.wo_1))
                .setInActiveColor("#66666A"));
        mNavigation.setFirstSelectedPosition(page);
        mNavigation.initialise();
        mNavigation.setTabSelectedListener(this);

        swichFragment(mCurrenPage);
    }

    @Override
    public void onTabSelected(int position) {
        Logger.d(TAG, "position:" + position);
        swichFragment(position);
        mCurrenPage = position;
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (mAccountFragment != null) {
            transaction.hide(mAccountFragment);
        }
        if (mTaskFragment != null) {
            transaction.hide(mTaskFragment);
        }
        if (mServiceFragment != null) {
            transaction.hide(mServiceFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }

    public void swichFragment(int currenPage) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (currenPage) {
            case 0:
                if (mAccountFragment == null) {
                    mAccountFragment = AccountFragment.newInstance();
                    transaction.add(R.id.main_relace, mAccountFragment, Constans.ACCOUNT_PAGE);
                } else {
                    transaction.show(mAccountFragment);
                }
                break;
            case 1:
                if (mTaskFragment == null) {
                    mTaskFragment = TaskFragment.newInstance();
                    transaction.add(R.id.main_relace, mTaskFragment, Constans.TASK_PAGE);
                } else {
                    transaction.show(mTaskFragment);
                }
                break;
            case 2:
                if (mServiceFragment == null) {
                    mServiceFragment = ServiceFragment.newInstance();
                    transaction.add(R.id.main_relace, mServiceFragment, Constans.SERVICE_PAGE);
                } else {
                    transaction.show(mServiceFragment);
                }
                break;
            case 3:
                if (mMeFragment == null) {
                    mMeFragment = MeFragment.newInstance();
                    transaction.add(R.id.main_relace, mMeFragment, Constans.ME_PAGE);
                } else {
                    transaction.show(mMeFragment);
                }
                break;
        }
        if (mJumpType == Constans.EVENT_NOT_USE_FRG
                || mJumpType == Constans.EVENT_ACCOUNT_FRG || mJumpType == Constans.EVENT_SERVICE_FRG) {
            transaction.commitAllowingStateLoss();
        } else {

            transaction.commit();
        }
    }

    public BottomNavigationBar getBottomBar() {
        if (null != mNavigation) {
            Logger.d(TAG, "mBottomBar不为空");
            return mNavigation;
        }
        return null;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavigation != null) {
            Logger.d(TAG, "onSaveInstanceState" + mNavigation.isHidden());
            outState.putBoolean(navigation, mNavigation.isHidden());
            outState.putInt(PAGE, mCurrenPage);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean isHideden = savedInstanceState.getBoolean(navigation, false);
        int page = savedInstanceState.getInt(this.PAGE, 0);
        Logger.d(TAG, "onRestoreInstanceState" + isHideden);
        Logger.d(TAG, "onRestoreInstanceState" + page);
    }

    @Override
    public void onChildKeyBack() {
        super.onChildKeyBack();
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        //解除service 绑定
        unbindService(connectionService);
        myService.stopService();
        myService = null;

        unbindService(connectionSmsService);
        smsService.stopService();
        smsService=null;
    }

    @Subscribe
    public void onEventMainThread(JumpEvent event) {
        mJumpType = event.codeJump();
        Logger.d(TAG, "event:" + mJumpType);
        if (mJumpType == Constans.EVENT_NOT_USE_FRG) {//进程
            mNavigation.selectTab(1, true);
        } else if (mJumpType == Constans.EVENT_ACCOUNT_FRG) {//设备
            mNavigation.selectTab(0, true);
        } else if (mJumpType == Constans.EVENT_SERVICE_FRG) {//服务
            mNavigation.selectTab(2, true);
        }
    }

    @Override
    public NotifyUtil startNotifyProgress() {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.dis;
        String ticker = getResources().getString(R.string.apk_downloading);
        //实例化工具类，并且调用接口
        notifyUtils = new NotifyUtil(this, 0);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return doubleClick.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


    private BaseMVPFragment[] fragments = {mAccountFragment, mTaskFragment, mServiceFragment, mMeFragment};


    public void timedRefresh() {
//        Logger.d("定时刷新","刷新 123");
        if (UserUtil.isForeground(this)) {
            BaseMVPFragment currenFragment = getCurrenFragment();
            if (currenFragment != null) {
                currenFragment.timedRefresh();
            }
        }
    }



    public void timedRefreshSms(){
        if (UserUtil.isForeground(this)) {
            getPresenter().getDeviceRequest();
        }
    }



    private BaseMVPFragment getCurrenFragment() {
        BaseMVPFragment fragment = null;
        switch (mCurrenPage) {
            case 0:
                fragment = mAccountFragment;
                break;
            case 1:
                fragment = mTaskFragment;
                break;
            case 2:
                fragment = mServiceFragment;
                break;
            case 3:
                fragment = mMeFragment;
                break;
        }
        return fragment;
    }


    private TimedRefreshService myService;

    /**
     * @author Administrator
     *         实现service接口，用于service绑定时候，回调
     */
    private class ConnectionService implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((TimedRefreshService.TimedRefreshBuild) service).getMyService(); //获取Myservice对象
            /**
             * 直接把当前对象传给service，这样service就可以随心所欲的调用本activity的各种可用方法
             */
            myService.setMainActivity(ContainerActivity.this); //把当前对象传递给myservice
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }


    private TimedSmsService smsService;

    /**
     * @author Administrator
     *         实现service接口，用于service绑定时候，回调
     */
    private class ConnectionSmsService implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            smsService = ((TimedSmsService.TimedRefreshBuild) service).getMyService(); //获取Myservice对象
            /**
             * 直接把当前对象传给service，这样service就可以随心所欲的调用本activity的各种可用方法
             */
            smsService.setMainActivity(ContainerActivity.this); //把当前对象传递给myservice
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

}
