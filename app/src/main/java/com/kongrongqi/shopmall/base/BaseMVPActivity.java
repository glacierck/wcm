package com.kongrongqi.shopmall.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.commonframe.Constant;
import com.frame.commonframe.viewtype.BuilderProgress;
import com.frame.commonframe.viewtype.ProgressActivity;
import com.kongrongqi.shopmall.R;
import com.kongrongqi.shopmall.utils.Logger;
import com.kongrongqi.shopmall.utils.ToastUtil;


/**
 * MVP的Activity的基类.
 *
 * @author penny
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends BaseActivity {

    public static final String KEY_DATA = "keyDataOfActivity";

    protected P mPresenter;
    protected Bundle mBundle;
    public Toolbar activityToolbar;
    public TextView right_tv2;
    public TextView title;
    public LinearLayout right;
    private ProgressActivity progress_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getBundle(KEY_DATA) != null) {
                mBundle = savedInstanceState.getBundle(KEY_DATA);
            }
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            mBundle = getIntent().getExtras();
        }

        this.mPresenter = createPresenter();
        getPresenter().init(BaseMVPActivity.this, getUI());
        onCreateExecute(savedInstanceState);
        getPresenter().onUICreate(savedInstanceState);

    }

    /**
     * onCreateExecute
     *
     * @param savedInstanceState
     */
    protected void onCreateExecute(Bundle savedInstanceState) {
        if (getLayoutResID() != 0) {
            ViewDataBinding binding = initDatabinding();
            if (binding == null) {
                setContentView(getLayoutResID());
                initProgressActivity();
                initViews(savedInstanceState);
                initToolBar();

            }
        }
    }

    private void initProgressActivity() {
        try {
            progress_activity = (ProgressActivity) findViewById(R.id.progress_activity);
        } catch (Exception e) {

        }
    }

    public void initToolBar() {
        try {
            activityToolbar = (Toolbar) findViewById(R.id.toolbar);
            title = (TextView) findViewById(R.id.title);
            right_tv2 = (TextView) findViewById(R.id.right_tv2);
            right = (LinearLayout) findViewById(R.id.right);
            setToolBar();
            activityToolbar.setTitle("");
            setSupportActionBar(activityToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activityToolbar.setNavigationIcon(R.drawable.back);
            activityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishActivity();
                }
            });
            setNavigationIcon();

        } catch (Exception e) {

        }
    }

    protected void setNavigationIcon() {

    }

    ;

    public void setToolBar() {
    }

    public ProgressActivity getProgressActivity(){
        return progress_activity;
    }

//    private Toolbar getToolbar() {
//        View view = findViewById(R.id.toolbar);
//        if (view != null) {
//            return (Toolbar) view;
//        } else {
//            return null;
//        }
//
//    }

    /**
     * 数据没绑定就调用此方法
     *
     * @param savedInstanceState
     */
    protected void initViews(Bundle savedInstanceState) {

    }

    /**
     * 如不需要使用Databinding 可以返回null
     *
     * @return
     */
    protected abstract ViewDataBinding initDatabinding();

    /***
     * 获取view
     *
     * @return
     */
    protected abstract int getLayoutResID();

    /**
     * createPresenter:创建一个Presenter，子类来实现，可以通过new的方式直接new出来一个. <br/>
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * getUI:得到UI层组件，一般都是Activity或者Fragment本身. <br/>
     *
     * @return
     */
    protected abstract IUI getUI();

    /**
     * getPresenter:子类应该通过这个方法拿到Presenter的实例，而不是通过变量拿到. <br/>
     *
     * @return Presenter
     */
    protected final P getPresenter() {
        if (getgetPresenter(mPresenter))
            return mPresenter;
        else
            Logger.e("BaseMVPActivity:", "Presenter is null? please check");
        return null;

    }

    private boolean getgetPresenter(P presenter) {
        return presenter != null;
    }


    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onUIStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onUIResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onUIPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onUIStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onUIDestory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mBundle != null) {
            outState.putBundle(KEY_DATA, mBundle);
        }
        if (getPresenter() != null) {
            getPresenter().onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getBundle(KEY_DATA) != null) {
                mBundle = savedInstanceState.getBundle(KEY_DATA);
            }
        }
        getPresenter().onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * getData:得到界面必需的Bundle数据. <br/>
     *
     * @return
     */
    public Bundle getData() {
        return mBundle;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onChildKeyBack();
        }
        return false;
    }

    public void showLongToast(String text) {

        ToastUtil.showMessage(
                this,
                text,
                Toast.LENGTH_LONG
        );
    }

    /**
     * 点back键做自己想做的事情
     */
    public void onChildKeyBack() {

    }

    public void finishActivity() {
        this.finish();
    }

    @Override
    public void showLoading() {
        if (progress_activity != null) progress_activity.showLoading();
    }

    @Override
    public void showContent() {
        if (progress_activity != null) progress_activity.showContent();
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
                    .create().showError();
        }

//
//
//
//        if(progress_activity!=null) progress_activity.showError(null,null,false,new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showContent();
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
    protected void errorFinish() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
