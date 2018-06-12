package com.kongrongqi.shopmall.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.commonframe.Constant;
import com.frame.commonframe.viewtype.BuilderProgress;
import com.frame.commonframe.viewtype.ProgressActivity;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.utils.Logger;

import java.lang.reflect.Field;

/**
 * MVP Fragment的基类.
 *
 * @author penny
 */
public abstract class BaseMVPFragment<P extends IPresenter> extends BaseFragment {

    public static final String TAG = "BaseMVPFragment";

    private static final String KEY_DATA = "keyData";

    private static final String SAVE_STATE = "save_state";

    protected P mPresenter;

    protected View mRootView;

    protected Bundle mData;

    protected Bundle mExtras;
    protected Bundle bundle;
    public Toolbar toolbar;
    public ImageView right_icon;
    public TextView title;
    public LinearLayout right;
    private ProgressActivity progress_activity;
    public ImageView red_point;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

            boolean isSupportHidden = savedInstanceState.getBoolean(SAVE_STATE);
            Logger.d(TAG,"=savedInstanceState="+isSupportHidden);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle = savedInstanceState;
        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle(KEY_DATA);
            if (bundle != null) {
                mData = bundle;
            }
        }
        if (mRootView == null) {
            mPresenter = createPresenter();
            getPresenter().init(getActivity(), getUI());
            mRootView = onCreateViewExecute(inflater, container, savedInstanceState);
        }
        initProgressActivity();
        initViews(mRootView, savedInstanceState);
        initToolBar();
        return mRootView;
    }

    /**
     * 不适用databinding 需要重写此方法
     *
     * @param rootView
     * @param savedInstanceState
     */
    public void initViews(View rootView, Bundle savedInstanceState) {

    }

    private void initProgressActivity() {
        try {
            progress_activity = (ProgressActivity) mRootView.findViewById(R.id.progress_activity);
        }catch (Exception e){

        }
    }

    private void initToolBar(){
        try {
            toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
            right_icon =(ImageView) mRootView.findViewById(R.id.right_icon);
            red_point = (ImageView) mRootView.findViewById(R.id.red_point);
            title = (TextView) mRootView.findViewById(R.id.title);
            right = (LinearLayout) mRootView.findViewById(R.id.right);

            setToolBar();
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }catch (Exception e){

        }
    }

    public void  setToolBar(){}


    /**
     * onCreateViewExecute
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected View onCreateViewExecute(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (getLayoutResID() != 0) {
            ViewDataBinding binding = initDataBinding(
                    inflater,
                    container
            );
            if (binding == null) {
                view = inflater.inflate(
                        getLayoutResID(),
                        container,
                        false
                );
            } else {
                view = binding.getRoot();
            }
        } else {
            view = super.onCreateView(
                    inflater,
                    container,
                    savedInstanceState
            );
        }
        getPresenter().onUICreate(savedInstanceState);
        return view;
    }

    /**
     * 如不使用databinding 则返回Null
     *
     * @param inflater
     * @param container
     * @return
     */
    protected abstract ViewDataBinding initDataBinding(LayoutInflater inflater, ViewGroup container);

    /**
     * 得到布局文件
     *
     * @return
     */
    protected abstract int getLayoutResID();

    /**
     * getUI:得到UI.一般都是Fragment或者Activity本身 <br/>
     *
     * @return
     */
    protected abstract IUI getUI();

    /**
     * createPresenter:创建一个Presenter，子类来实现，可以通过new的方式直接new出来一个. <br/>
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * getPresenter:子类应该通过这个方法拿到Presenter的实例，而不是通过变量拿到. <br/>
     *
     * @return
     */
    protected final P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onUIStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onUIStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onUIResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onUIPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null && mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        getPresenter().onUIDestory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mData != null) {
            outState.putBundle(KEY_DATA, mData);
        }
        if (getPresenter() != null) {
            getPresenter().onSaveInstanceState(outState);
        }
        outState.putBoolean(SAVE_STATE, isHidden());
        Logger.d(TAG, "=outState==" + isHidden());
        super.onSaveInstanceState(outState);
    }

    public ImageButton getToolNavigationButton(Toolbar pToolbar) {
        try {
            Field lNavButtonView = pToolbar.getClass().getDeclaredField("mNavButtonView");
            lNavButtonView.setAccessible(true);
            ImageButton lNav = (ImageButton) lNavButtonView.get(toolbar);
            return lNav;
        } catch (NoSuchFieldException pE) {
            pE.printStackTrace();
            return null;
        } catch (IllegalAccessException pE) {
            pE.printStackTrace();
            return null;
        }
    }

    public View getRightIconView() {
        if (right_icon != null) {
            return right_icon;
        } else {
            return null;
        }
    }

    /**
     * getData:得到传输过来的数据. <br/>
     *
     * @return
     */
    public Bundle getData() {
        return mData;
    }

    /**
     * setData:设置启动这个Fragment必须的数据. <br/>
     *
     * @param bundle
     */
    public void setData(Bundle bundle) {
        this.mData = bundle;
    }

    /**
     * getExtras:得到Extras. <br/>
     *
     * @return Extras
     */
    public Bundle getExtras() {
        return mExtras;
    }

    /**
     * setExtras:设置Extras. <br/>
     *
     * @param extras extras
     */
    public void setExtras(Bundle extras) {
        this.mExtras = extras;
    }

    /**
     * 刷新UI  咱用于 进程 几个fragment 滑动刷新数据
     */
    public void refrash(){

    }

    /**
     * 定时刷新 回调
     */
    public void timedRefresh(){

    }

    @Override
    public void showLoading() {
        if(progress_activity!=null) progress_activity.showLoading();
    }

    @Override
    public void showContent() {
        if(progress_activity!=null) progress_activity.showContent();
    }

    @Override
    public void showEmpty() {

        if (progress_activity != null) {
            new BuilderProgress(progress_activity)
                    .setEmptyButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPresenter().refreshView();
                        }
                    })
                    .create()
                    .showEmpty();
        }
//        if(progress_activity!=null) progress_activity.showEmpty();
    }

    public ProgressActivity getProgressActivity(){
        return progress_activity;
    }

    @Override
    public void showError() {

        //设置加载错误页显示
        if (progress_activity != null) {
            new BuilderProgress(progress_activity)
                    .setErrorButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPresenter().refreshView();
                        }
                    })
                    .create()
                    .showError();
        }

        //设置加载错误页显示
//        if(progress_activity!=null) progress_activity.showError(null,null,false,new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPresenter().refreshView();
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                errorFinish();
//            }
//        });
    }

    /**
     * 页面状态 失败返回上一页
     */
    protected void errorFinish(){
        getActivity().finish();
    }
}
